<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: lang.include.php 29 2008-11-09 17:02:26Z aj3 $

import('domains');


$LGlanguage = 'en';
$LGstr = NULL;


function LGprintHrefLang ($hrefLang, $lang) {
	if ($hrefLang != NULL && strlen($hrefLang) > 0 && $hrefLang != $lang) {
		echo ' <IMG SRC="http://servo.', DMgetDomain(), '/countries/', $hrefLang, '.gif" WIDTH="16" HEIGHT="12" ALT="[', $hrefLang, ']">';
	}
}

/*
function LGprintHrefLang ($hrefLang) {
	global $LGlanguage;
	if ($hrefLang != NULL && strlen($hrefLang) > 0 && $hrefLang != $LGlanguage) {
		echo ' <IMG SRC="http://servo.', DMgetDomain(), '/countries/', $hrefLang, '.gif" WIDTH="16" HEIGHT="12" ALT="[', $hrefLang, ']">';
	}
}
*/


function __getReplacedParams ($text, $params) {
	if ($params == NULL) {
		return $text;
	}
	if (is_string($params)) {
		return str_replace('^0', $params, $text);
	}
	else {  // array
		$replacedText = $text;
		$replaceCount = min(count($params), 9);
		for ($index = 0; $index < $replaceCount; $index++) {
			$replacedText = str_replace('^'.$index, $params[$index], $replacedText);
		}
		return $replacedText;
	}
}


function LGgetParams ($key, $params) {
	global $LGlanguage, $LGstr;
	if ($LGstr == NULL) {
		IMfatalError('$LGstr == NULL');
	}
	if (! array_key_exists($key, $LGstr) || $LGstr[$key] == NULL) {
		return $key;
	}
	else if (is_string($LGstr[$key])) {
		return __getReplacedParams($LGstr[$key], $params);
	}
	else if (is_array($LGstr[$key]) && count($LGstr[$key]) > 0) {
		return __getReplacedParams(array_key_exists($LGlanguage, $LGstr[$key]) ? $LGstr[$key][$LGlanguage] : (array_key_exists('en', $LGstr[$key]) ? $LGstr[$key]['en'] : (array_key_exists('de', $LGstr[$key]) ? $LGstr[$key]['de'] : $LGstr[$key][0])), $params);
	}
	else {  // never happens
		IMfatalError("bogus array content in \$LGstr['$key'] in LGprint()");
	}
}


function LGprintParams ($key, $params) {
	echo LGgetParams($key, $params);
}


function LGprint ($key) {
	echo LGgetParams($key, NULL);
}


function LGget ($key) {
	return LGgetParams($key, NULL);
}

?>