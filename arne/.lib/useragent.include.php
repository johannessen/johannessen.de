<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// based on work by Peter-Paul Koch
// $Id: useragent.include.php 29 2008-11-09 17:02:26Z aj3 $


function UAparseAgent ($httpUserAgent) {
	$userAgent = strtolower($httpUserAgent);
	$agent = array(
			'name' => NULL,
			'version' => NULL,
			'system' => NULL,
			'human' => NULL,
			'css' => NULL,
			'digest' => NULL,
			'graphical' => NULL);
	
	
	// css2-capable agents
	if (($offset = strpos($userAgent, 'safari')) !== FALSE) {
		$agent['name'] = 'Safari';
		$agent['system'] = 'Mac OS X';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'omniweb')) !== FALSE) {
		$agent['name'] = 'OmniWeb';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'applewebkit')) !== FALSE) {
		$agent['name'] = 'Apple WebKit';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'konqueror')) !== FALSE) {
		$agent['name'] = 'Konqueror';
		$agent['system'] = 'Linux';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'opera')) !== FALSE) {
		$agent['name'] = 'Opera';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'netscape')) !== FALSE) {
		$agent['name'] = 'Netscape Navigator';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'firebird')) !== FALSE) {
		$agent['name'] = 'Mozilla Firebird';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'camino')) !== FALSE) {
		$agent['name'] = 'Camino';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'galeon')) !== FALSE) {
		$agent['name'] = 'Galeon';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	else if (strpos($userAgent, 'gecko') !== FALSE && ($offset = strpos($userAgent, 'rv:')) !== FALSE) {
		$agent['name'] = 'Mozilla';
		$agent['human'] = TRUE;
		$agent['css'] = 2;
		$agent['digest'] = TRUE;
		$agent['graphical'] = TRUE;
	}
	
	// css1-capable agents
	else if (($offset = strpos($userAgent, 'msie')) !== FALSE) {
		$agent['name'] = 'Internet Explorer';
		$agent['human'] = TRUE;
		$agent['css'] = 1;
		$agent['digest'] = FALSE;
		$agent['graphical'] = TRUE;
	}
	
	// other graphical agents
	else if (($offset = strpos($userAgent, 'icab')) !== FALSE) {
		$agent['name'] = 'iCab';
		$agent['human'] = TRUE;
		$agent['css'] = 0;
		$agent['digest'] = FALSE;
		$agent['graphical'] = TRUE;
	}
	else if (($offset = strpos($userAgent, 'webtv')) !== FALSE) {
		$agent['name'] = 'WebTV';
		$agent['human'] = TRUE;
		$agent['css'] = 0;
		$agent['graphical'] = TRUE;
	}
	
	// non-graphical agents
	else if (($offset = strpos($userAgent, 'lynx')) !== FALSE) {
		$agent['name'] = 'Lynx';
		$agent['human'] = TRUE;
		$agent['css'] = 0;
		$agent['digest'] = FALSE;
		$agent['graphical'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'links')) !== FALSE) {
		$agent['name'] = 'Links';
		$agent['human'] = TRUE;
		$agent['css'] = 0;
		$agent['graphical'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'wannaba')) !== FALSE) {
		$agent['name'] = 'WannaBe';
		$agent['human'] = TRUE;
		$agent['css'] = 0;
		$agent['digest'] = FALSE;
		$agent['graphical'] = FALSE;
	}
	
	// non-human agents
	else if (($offset = strpos($userAgent, 'w3c_validator')) !== FALSE) {
		$agent['name'] = 'W3C Validator';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'jigsaw')) !== FALSE) {
		$agent['name'] = 'W3C CSS Validator';
		$agent['version'] = substr($_SERVER['HTTP_USER_AGENT'], $offset + strlen($_SERVER['HTTP_USER_AGENT']), 6);
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'googlebot')) !== FALSE) {
		$agent['name'] = 'Googlebot';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'scooter')) !== FALSE) {
		$agent['name'] = 'AltaVista Scooter';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'slurp')) !== FALSE) {
		$agent['name'] = 'Inktomi Slurp';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'grub')) !== FALSE) {
		$agent['name'] = 'Grub';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'fast')) !== FALSE) {
		$agent['name'] = 'FAST-WebCrawler';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'turnitin')) !== FALSE) {
		$agent['name'] = 'TurnitinBot';
		$agent['human'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'eventax')) !== FALSE) {
		$agent['name'] = 'EventAX';
		$agent['human'] = FALSE;
	}
	
	// undetermined
	else if (($offset = strpos($userAgent, 'java')) !== FALSE) {
		$agent['name'] = 'Java';
	}
	else if (($offset = strpos($userAgent, 'curl')) !== FALSE) {
		$agent['name'] = 'cURL';
		$agent['css'] = 0;
		$agent['graphical'] = FALSE;
	}
	else if (($offset = strpos($userAgent, 'wget')) !== FALSE) {
		$agent['name'] = 'wget';
		$agent['css'] = 0;
		$agent['graphical'] = FALSE;
	}
	
	// No unambigous match, so we try to guess
	else if (($offset = strpos($userAgent, 'mozilla')) !== FALSE && strpos($userAgent, 'mozilla/5') === FALSE && strpos($userAgent, 'compatible') === FALSE) {  // Netscape <= 4
		$agent['name'] = 'Netscape Navigator';
		$agent['human'] = TRUE;
		$agent['css'] = 0;
		$agent['graphical'] = TRUE;
	}
	else if (preg_match('/^(([-_a-z]+)[-\/\ _]+|([-_a-z]+))(([0-9]+[^\(;\ \)]?)+)/i', $httpUserAgent, $matches) == 1) {
		$agent['name'] = $matches[1];
		$agent['version'] = $matches[4];
	}
	
	
	// Version feststellen
	if ($agent['version'] === NULL) {
		if (preg_match('/([0-9]+[^\(;\ \)]?)+|[-_a-z]*\/([-_a-z]+)/i', substr($httpUserAgent, $offset), $matches) == 1) {
			$agent['version'] = $matches[(count($matches) == 3) ? 2 : 0];
		}
		else if (preg_match('/([0-9]+[^\(;\ \)]?)+/i', $httpUserAgent, $matches) == 1) {
			$agent['version'] = $matches[0];
		}
		
		// make some adjustmenst for fuzzy version numbers
		if ($agent['name'] == 'Safari') {
			switch (substr($agent['version'], 0, strpos($agent['version'], '.'))) {
				case '100':
					$agent['version'] = '1.1';
					break;
				case '85':
					$agent['version'] = '1.0';
					break;
				default:
					$agent['version'] = 'v'.$agent['version'];
			}
		}
	}
	
	
	// System feststellen
	if ($agent['system'] === NULL) {
		if (strpos($userAgent, 'linux') !== FALSE) {
			$agent['system'] = 'Linux';
		}
		else if (strpos($userAgent, 'mac') !== FALSE || strpos($userAgent, 'apple') !== FALSE && strpos($userAgent, 'darwin') === FALSE) {
			if (($offset = strpos($userAgent, 'mac os x')) !== FALSE && $offset + 8 <= strlen($userAgent) || strpos($userAgent, 'darwin') !== FALSE) {
				$agent['system'] = 'Mac OS X';
			}
			else {
				$agent['system'] = 'Mac OS';
			}
			if ($agent['name'] == 'Internet Explorer' && $agent['version'] >= '5') {
				$agent['css'] = 2;
				$agent['digest'] = TRUE;
			}
		}
		else if (strpos($userAgent, 'darwin') !== FALSE) {
			$agent['system'] = 'Darwin';
		}
		else if (strpos($userAgent, 'win') !== FALSE) {
			$agent['system'] = 'Windows';
		}
		else if (strpos($userAgent, 'x11') !== FALSE || strpos($userAgent, 'mach') !== FALSE || strpos($userAgent, 'bsd') !== FALSE) {
			$agent['system'] = 'Unix';
		}
		else if (strpos($userAgent, 'ppc') !== FALSE || strpos($userAgent, 'powerpc') !== FALSE) {
			$agent['system'] = 'PowerPC';
		}
		else if (strpos($userAgent, 'pentium') !== FALSE || strpos($userAgent, 'amd') !== FALSE || strpos($userAgent, '86') !== FALSE) {
			$agent['system'] = 'x86';
		}
	}
	
	
	if ($agent['human'] === FALSE) {
		$agent['css'] = 0;
		$agent['graphical'] = FALSE;
	}
	
	return $agent;
}


function UAparseMyAgent () {
	return UAparseAgent($_SERVER['HTTP_USER_AGENT']);
}


function UAgetHumanReadableName ($agent) {
	if ($agent['name'] == NULL || $agent['version'] == NULL) {
		return $_SERVER['HTTP_USER_AGENT'];
	}
	else {
		return $agent['name'].' '.$agent['version'];
	}
}


function UAgetMyHumanReadableName () {
	return UAgetHumanReadableName(UAparseMyAgent());
}

?>