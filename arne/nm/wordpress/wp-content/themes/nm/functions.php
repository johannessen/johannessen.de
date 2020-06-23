<?php

add_filter('gettext', 'nm_customize_powered_by', 20, 3);
add_filter('the_title', 'apply_nm_title_options_theme', 19);
add_filter('the_content', 'apply_nm_content_options_theme', 19);


function nm_customize_powered_by($translated_text, $untranslated_text, $domain) {
	switch ($untranslated_text) {
		
		case 'Proudly powered by %s':
		return 'Powered by %s';
		
		case '<span class="meta-nav">&larr;</span> Previous':
		case '<span class="meta-nav">&larr;</span> Older posts':
		return '<span class="meta-nav">&larr;</span> Earlier NM';
		
		case 'Next <span class="meta-nav">&rarr;</span>':
		case 'Newer posts <span class="meta-nav">&rarr;</span>':
		return 'Later NM <span class="meta-nav">&rarr;</span>';
		
		case '<span class="%1$s">Posted in</span> %2$s':
		return '<span class="%1$s">NM Category</span> %2$s';
		
		case '<span class="%1$s">Tagged</span> %2$s':
		return '<span class="%1$s">Chart</span> %2$s';
		
		case 'This entry was posted in %1$s and tagged %2$s by <a href="%6$s">%5$s</a>. Bookmark the <a href="%3$s" title="Permalink to %4$s" rel="bookmark">permalink</a>.':
		return 'NM Category %1$s | Chart %2$s';
		
		case 'This entry was posted in %1$s by <a href="%6$s">%5$s</a>. Bookmark the <a href="%3$s" title="Permalink to %4$s" rel="bookmark">permalink</a>.':
		return 'NM Category %1$s';
		
		case 'This entry was posted by <a href="%6$s">%5$s</a>. Bookmark the <a href="%3$s" title="Permalink to %4$s" rel="bookmark">permalink</a>.':
		return '';
		
		case 'Tags':
		return 'Charts';
		
/*
		
		case '':
		return '';
*/
		
		default:
	}
	return $translated_text;
}



function apply_nm_title_options_theme ($title) {
	global $id, $post;
	if (! $id || $post->post_type != 'post' || is_admin() || is_feed()) {
		return $title;
	}
	
	if (get_post_meta($post->ID, 'original_source', true) != 'no') {
		return preg_replace('|<span class=nmorig>\*</span>|', '<span class=nmorig><img src="' . get_stylesheet_directory_uri() . '/original.png" alt="*"></span>', $title);
	}
	return $title;
}



function apply_nm_content_options_theme ($content) {
	global $id, $post;
	if (! $id || $post->post_type != 'post' || is_admin() || is_feed()) {
		return $content;
	}
	
	// add logo of producer of the original NTM, if applicable
	$source_logo_html = '';
	$source_country = get_post_meta($post->ID, 'source_country', true);
	if ($source_country || get_post_meta($post->ID, 'original_source', true) == 'no') {
		
		if (! $source_country) {
			// try to auto-detect source country based on affected charts
			$tags = wp_get_post_tags($post->ID);
			foreach ($tags as $tag) {
				$tag_country = substr($tag->slug, 0, strpos($tag->slug, '_'));
				if (! $tag_country || is_numeric(substr($tag->slug, 0, 1))) {
					continue;  // own chart or no identifiable publisher; ignore
				}
				if ($source_country && $source_country != $tag_country) {
					// there are charts of two different foreign publishers affected; can't determine original producer source
					$source_country = '';
					break;
				}
				$source_country = $tag_country;
			}
		}
		
		switch ($source_country) {
		case 'DE':
			$uri = 'http://www.bsh.de/Vorlagen/images/bsh_logo.gif';
			break;
		case 'DK':
			$uri = 'http://www.gst.dk/media/gst/2059206/mim_geodatastyrelsen_rgb_dk.png';
			break;
		case 'NO':
			$uri = 'http://kartverket.no/ImageVault/publishedmedia/sbmee5jt1ys7ky0huw32/Kartverket_staende.jpg';
			break;
		case 'NO_Kystverket':
#			$uri = 'http://pbs.twimg.com/profile_images/1247588988/9168775.jpg';
			$uri = '/nm/wordpress/wp-content/themes/nm/sources/Kystverket.png';
			break;
		case 'SKGB':
			$uri = 'http://www.skgb.de/extensions/themes/skgb5/images/logo.gif';
			break;
		default:
			$uri = NULL;
		}
		
		if ($uri) {
			$source_logo_html = '<div class="source-logo ' . $source_country . '"><img src="' . $uri . '" alt=""></div>';
		}
	}
	
	return $source_logo_html . $content;
}


/*



*/

?>
