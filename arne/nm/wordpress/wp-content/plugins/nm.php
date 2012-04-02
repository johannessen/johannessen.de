<?php
/*
 * Copyright (c) 2012 Arne Johannessen
 * UTF-8
 */

/*
Plugin Name: NM Plugin
Description: This plug-in implements the NM title prefix (e. g. "123. * (T) …") and some other stuff.
Author: Arne Johannessen
Version: 0.4.1
Author URI: http://arne.johannessen.de/
*/


add_filter('the_title', 'apply_nm_title_options_plugin', 18);
add_filter('the_content', 'apply_nm_content_options_plugin', 18);


function span_class ($content, $class) {
	if (is_admin()) {
		return $content;
	}
	return '<span class=' . $class . '>' . $content . '</span>';
}


function nm_post_year_number ($post) {
	$nmYear = NULL;
	$nmNumber = $post->ID;
	if ( strlen($post->ID) >= 4 ) {
		/* New NM numbering scheme: YYNN with YY being the two-digit year (14 = 2014)
		 * and NN being a serial number (of the internal WP post ID, not of the NMs).
		 * Automatically setting the autoindex at the beginning of the year is not
		 * yet implemented; it must be done manually in the database. If there are
		 * 100 or more serial numbers used in a single year, the post IDs must become
		 * five-digit numbers like YYNNN. This isn't done automatically, either, so
		 * the user has to be very careful during busy years.
		 */
		$nmYear = 2000 + substr($post->ID, 0, 2);
		$nmNumber = 0 + substr($post->ID, 2);
	}
	elseif ( $post->ID <= 189 ) {
		$nmYear = 2012;
	}
	elseif ( $post->ID <= 218 ) {
		$nmYear = 2013;
	}
	return array($nmYear, $nmNumber);
}


function apply_nm_title_options_plugin ($title) {
	global $id, $post;
	if (! $id || $post->post_type != 'post') {
		return $title;
	}
	
	list($nmYear, $nmNumber) = nm_post_year_number($post);
	
	$titlePrefix = span_class($nmNumber . '.', 'nmnr') . ' ';
	if ($nmYear) {
		$titlePrefix = span_class($nmYear, 'nmyear') . '-' . $titlePrefix;
	}
	if (get_post_meta($post->ID, 'original_source', true) != 'no') {
		$titlePrefix .= span_class('*', 'nmorig') . ' ';
	}
	if (in_category(14)) {
		$titlePrefix .= span_class('(T)', 'nmtandp') . ' ';
	}
	elseif (in_category(15)) {
		$titlePrefix .= span_class('(P)', 'nmtandp') . ' ';
	}
	
	if (get_post_meta($post->ID, 'cancelled', true) == 'yes' && ! is_admin()) {
		return '<s class=cancelled title="This NM has been cancelled.">' . $titlePrefix . $title . '</s>';
	}
	return $titlePrefix . $title;
}


function apply_nm_content_options_plugin ($content) {
	global $id, $post;
	if (! $id || $post->post_type != 'post' || is_admin()) {
		return $content;
	}
	
	if (get_post_meta($post->ID, 'cancelled', true) == 'yes') {
		return '<p class=entry-meta><strong>This NM has been cancelled.</strong>' . $content;
	}
	return $content;
}



#####################

// Add a custom meta box for per-post settings 
add_action('admin_menu', 'nm_add_custom_box');
add_action('save_post', 'nm_save_postdata');

/* Adds a custom section to the "advanced" Post and Page edit screens */
function nm_add_custom_box () {
  //WP 2.5+
  if ( function_exists( 'add_meta_box' )) { 
  	foreach( array('post') as $type ) {
	    add_meta_box( 'nm_meta_box', 'Notice to Mariners Options', 'nm_meta_box', $type, 'side' );
    }
  }
}

/* Displays the custom box */
function nm_meta_box () {
	global $post;
	// Use nonce for verification
	echo '<input type="hidden" name="nm_nonce" id="nm_nonce" value="' . 
	wp_create_nonce( plugin_basename(__FILE__) ) . '" />';
	
	//Output checkboxes 
	$fields = array(
		'original_source' => 'Original Source (*)',
		'cancelled' => 'NM Cancelled',
	);
	$defaults = nm_default_options();
	foreach ($fields as $field => $legend) {
		$current_setting = get_post_meta($post->ID, $field, true);
		if (! $current_setting) {
			$current_setting = $defaults[$field];
		}
		$current_setting = $current_setting != 'no';
?>
<label for="nm_<?php echo $field; ?>">
	<input type="checkbox" name="nm_<?php echo $field; ?>" id="nm_<?php echo $field; ?>" <?php
		if ($current_setting) echo ' checked="checked"';
	?>/>
	<?php echo $legend; ?>
</label>
<br /> 
<?php
	}
}

function nm_default_options () {
	return array(
		'original_source' => 'yes',
		'cancelled' => 'no',
	);
}

/* Saves post metadata */
function nm_save_postdata ( $post_id ) {
  // verify this came from the our screen and with proper authorization,
  // because save_post can be triggered at other times
  
  if ( ! isset($_POST['nm_nonce']) ) {
	return $post_id;
  }
  
  if ( ! wp_verify_nonce( $_POST['nm_nonce'], plugin_basename(__FILE__) )) {
    return $post_id;
  }

  if ( ! current_user_can( 'edit_post', $post_id )) {
    return $post_id;
  }
  
  // OK, we're authenticated: we need to find and save the data
  $defaults = nm_default_options();
  $fields  = array_keys($defaults);
  foreach ( $fields as $field ) {	
    $value = 'no';
    if ( ! empty($_POST['nm_'.$field]) ) {
      $value = 'yes';
    }
    if ($value == $defaults[$field]) {
      delete_post_meta($post_id, $field);
    }
    else {
      update_post_meta($post_id, $field, $value);
    }
  }
  
  return TRUE;
}



#############################


function nm_text_replacement_init ($definitions) {
	// calling init multiple times will just append the new definitons
	global $nm_text_replacement_expressions;
	if (! $definitions) { return; }
	if (! isset($nm_text_replacement_expressions)) {
		$nm_text_replacement_expressions = array( 'patterns' => array(), 'replacements' => array() );
	}
	foreach ($definitions as $pattern => $replacement) {
		if (! $pattern || $pattern == '//' || ! $replacement) { continue; }
		$nm_text_replacement_expressions['patterns'][] = $pattern;
		$nm_text_replacement_expressions['replacements'][] = $replacement;
	}
	if (! has_filter('the_content', 'nm_text_replacement')) {
		add_filter('the_content', 'nm_text_replacement');
	}
}
function nm_text_replacement ($the_content) {
	global $nm_text_replacement_expressions;
	return preg_replace($nm_text_replacement_expressions['patterns'], $nm_text_replacement_expressions['replacements'], $the_content);
}

// common abbreviations (see INT1 for more)
nm_text_replacement_init(array(
		'/(\W)trfall\./' => '$1<abbr title=trockenfallend>trfall.</abbr>',
		'/{\*}/' => '<span class="nmorig"><img src="http://arne.johannessen.de/nm/wordpress/wp-content/themes/nm/original.png" alt="*"></span>',
		'//' => ''));


############################


function nm_symbol_replacement_init () {
	
	$symbols_dirname = 'symbol/';
	$symbols_path = dirname(__FILE__) . '/../../../' . $symbols_dirname;
	$symbols_url = get_bloginfo('siteurl') . '/' . $symbols_dirname;
	
	$definitions = array();
	$defaultHeight = '1.4em';  // default value (usually overwritten by default in .ini)
	$ini = parse_ini_file($symbols_path . 'symbols.ini', TRUE);
	foreach ($ini as $number => $symbol) {
		if ($number == 'height') {
			$defaultHeight = $symbol;
			continue;
		}
		
		$symbol_path = $symbols_path . $number . '.png';
		$symbol_url = $symbols_url . $number . '.png';
		if (array_key_exists('image', $symbol)) {
			$symbol_path = $symbols_path . $symbol['image'];
			$symbol_url = $symbols_url . $symbol['image'];
		}
		
		if (! is_readable($symbol_path)) { continue; }
//		$link_url = array_key_exists('link', $symbol) ? $symbols_url . $symbol['link'] : $symbol_url;
		$link_url = $symbol_url;
		// :KLUDGE: this code depends on the height being specified in em:
		$heightEm = array_key_exists('height', $symbol) ? (float)$symbol['height'] : (float)$defaultHeight;
		$verticalAlignEm = sprintf('%.2f', max(.33 - 54 / 126 * $heightEm, -.27)) . 'em';  // raise symbols with very small heights such as lines
		if (array_key_exists('vertical-align', $symbol)) {
			$verticalAlignEm = $symbol['vertical-align'];
		}
		
		$pattern = '/\{([^\{\}]*)\}\{((?:[^\{\}]*\W)?' . str_replace('+', '\+', $number) . ')\}/';
		$replacement = '<a href="' . $link_url . '" class=int1symbol><img src="' . $symbol_url . '" style="';  # min-height:16px;
		if (array_key_exists('height', $symbol)) {
			$replacement .= 'height:' . $symbol['height'];
		}
		else {
			$replacement .= 'max-height:' . $defaultHeight;
		}
		$replacement .= ';vertical-align:' . $verticalAlignEm;
/*
		if (array_key_exists('css', $symbol)) {
			$replacement .= ';' . $symbol['css'];
		}
*/
		$replacement .= '';
		$replacement .= '" alt="$1 ($2)" title="INT1 $2 ($1)"></a>';
		$definitions[$pattern] = $replacement;
#		print_r($pattern);print_r($replacement);die(); 
	}
#	var_dump($definitions);
#	die();
	
##	$definitions['/Zeichen/'] = 'ugvbhj';
##	$definitions['/\{[^\{]*([A-Z][0-9]+)\}/'] = '$1';
##	$definitions['/\{([^\}]*)\}/'] = '$1';
	$definitions['/\{([^\{\}]+)\}\{\}/'] = '$1';
	$definitions['/\{([^\{\}]+)\}\{([^\{\}]+)\}/'] = '$1 ($2)';
	$definitions['/\{([^\{\}]+)\}T\{([^\{\}]+)\}/'] = '<span class=int1symbol title="INT1 $2">$1</span><span class=assistive-text> ($2)</span>';
	$definitions['/\{([^\{\}]+)\}T\{}/'] = '<span class=int1symbol>$1</span>';
	nm_text_replacement_init($definitions);
}
nm_symbol_replacement_init();



?>
