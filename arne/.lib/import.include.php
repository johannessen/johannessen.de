<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: import.include.php 318 2015-01-09 18:59:29Z aj $


//define('HOME_DIR', $_SERVER['PHP_BASE']);
#phpinfo(); die();
define('HOME_DIR', '/srv');


if (isset($developmentStatus) && $developmentStatus) {
	ini_set('error_reporting', E_ALL | E_STRICT);
	ini_set('display_errors', '1');
/*	$globalsArray = array_merge(&$_ENV, &$_GET, &$_POST, &$_COOKIE, &$_SESSION);
	while(list($key) = each($globalsArray)) {
		unset(${$key});
	}
*/
}
else {
	ini_set('display_errors', '0');
//	ini_set('variables_order', 'S');
}


function import ($library) {
	if (strpos($library, '..') !== FALSE) {
		IMsecurityAlert('$library == '.$library, TRUE);
	}
	$classFileName = '/class.'.$library.'.php';  // :KLUDGE: shouldn't this better be *.class.php ?
	$includeFileName = '/'.$library.'.include.php';
	
	$filePath = substr($_SERVER['SCRIPT_FILENAME'], 0, strrpos($_SERVER['SCRIPT_FILENAME'], '/'));
	if (file_exists($filePath.$classFileName)) {
		require_once($filePath.$classFileName);
		return;
	}
	if (file_exists($filePath.$includeFileName)) {
		require_once($filePath.$includeFileName);
		return;
	}
	$filePath = $_SERVER['DOCUMENT_ROOT'].'/.lib';
	if (file_exists($filePath.$classFileName)) {
		require_once($filePath.$classFileName);
		return;
	}
	if (file_exists($filePath.$includeFileName)) {
		require_once($filePath.$includeFileName);
		return;
	}
	$filePath = HOME_DIR.'/.lib';
	if (file_exists($filePath.$classFileName)) {
		require_once($filePath.$classFileName);
		return;
	}
	if (file_exists($filePath.$includeFileName)) {
		require_once($filePath.$includeFileName);
		return;
	}
	# Cat
	$filePath = $_SERVER['DOCUMENT_ROOT'];
	if (file_exists($filePath.$classFileName)) {
		require_once($filePath.$classFileName);
		return;
	}
	if (file_exists($filePath.$includeFileName)) {
		require_once($filePath.$includeFileName);
		return;
	}
	$filePath = '/srv/Lib';
	if (file_exists($filePath.$classFileName)) {
		require_once($filePath.$classFileName);
		return;
	}
	if (file_exists($filePath.$includeFileName)) {
		require_once($filePath.$includeFileName);
		return;
	}
	
	IMfatalError('import.include.php: Failed opening required library "'.$library.'" in <STRONG>'.@$_SERVER['REQUEST_URI'].'</STRONG>');
}


function IMsecurityAlert ($alert, $die) {
	IMinformAdmin("Security Alert\r\n\r\n".$alert);
	if ($die) {
		if (! headers_sent()) {
			header('HTTP/1.0 403 Forbidden');
		}
		die('<TITLE>403 Forbidden</TITLE>Security Violation');
	}
}


function IMfatalError ($message, $hideLevels = 0) {
	global $developmentStatus;
	
	$message = "<P><STRONG>Fatal Error:</STRONG>\n\n" . $message . "\n";
	
	// get a sensible stacktrace
	$stacktrace = IMdebugStacktrace($hideLevels);
	array_shift($stacktrace);  // remove this function from the stacktrace
	$stacktrace = IMdebugStacktraceAsHtml($stacktrace);
	
	if (! $developmentStatus) {
		@header('HTTP/1.0 500 Internal Server Error');
		IMinformAdmin(str_replace("\n", "\r\n", strip_tags(str_replace('<LI>', "\t", $message . $stacktrace))));
	}
	
	$message .= $stacktrace;
	die('<TITLE>500 Internal Server Error</TITLE>' . $message);
}


function IMdebugStacktrace ($hideLevels) {
	$cleanStacktrace = array();
	$debugBacktrace = debug_backtrace();
	$debugBacktrace[] = array('function' => '&lt;init>');
	for ($i = 1 + $hideLevels; $i < count($debugBacktrace); $i++) {
		$cleanLevel = array(
				'file' => $debugBacktrace[$i - 1]['file'],
				'line' => $debugBacktrace[$i - 1]['line']);
		$cleanLevel['block'] = @$debugBacktrace[$i]['class'] . @$debugBacktrace[$i]['type'];
		$cleanLevel['block'] .= @$debugBacktrace[$i]['method'] ? $debugBacktrace[$i]['method'] : $debugBacktrace[$i]['function'];
		$cleanStacktrace[] = $cleanLevel;
	}
	return $cleanStacktrace;
}


function IMdebugStacktraceAsHtml ($stacktrace) {
	$html = "<OL>\n";
	foreach ($stacktrace as $level) {
		$html .= '<LI>' . $level['block'] . ' (' . $level['file'] . ':' . $level['line'] . ")\n";
	}
	$html .= "</OL>\n";
	return $html;
}


function IMinformAdmin ($message) {
	ob_start();
	phpinfo();
	error_log($message."\r\n\r\n".ob_get_contents()/*.base64_encode(http_build_query($_POST))*/, 1, 'root');
	ob_end_clean();
}

?>
