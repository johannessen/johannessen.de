<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: online.include.php 29 2008-11-09 17:02:26Z aj3 $

//define('ONLINE_SERVER', 'status.icq.com');  // name of icq server
define('ONLINE_SERVER', 'big.oscar.aol.com');  // name of aim server
//define('ONLINE_REQUEST', '/online.gif?icq=159331440');  // icq status request
define('ONLINE_REQUEST', '/brucher591?on_url=http://a.thaw.de/online1&off_url=http://a.thaw.de/online0');  // aim status request
//define('ONLINE_METHOD', 'HEAD');  // icq request method
define('ONLINE_METHOD', 'GET');  // aim request method

define('ONLINE_CONNECT_TIMEOUT', 2);  // seconds until timeout while connecting


function ONgetOnline () {
	$file = fsockopen (ONLINE_SERVER, getservbyname('www', 'tcp'), $errno, $errstr, ONLINE_CONNECT_TIMEOUT);
	if ($file) {
		$head_raw = '';
		fputs ($file, ONLINE_METHOD.' '.ONLINE_REQUEST." HTTP/1.0\r\nHost: ". ONLINE_SERVER."\r\n\r\n");
		while (! feof($file)) {
			$head_raw .= fgets($file);
		}
		fclose ($file);
		$head_array = explode("\r\n", $head_raw, 20);
		foreach ($head_array as $header) {
			if (substr($header, 0, strpos($header, ': ')) == 'Location') {
				if (strpos($header, 'online1') !== false) {
					return array('online' => true, 'error' => false);
				}
				else if (strpos($header, 'online0') !== false) {
					return array('online' => false, 'error' => false);
				}
				else {
					return array('online' => true, 'error' => true);
				}
			}
		}
	}
	return array('online' => false, 'error' => true);
}


function ONprintOnlineBox () {
	$nowOnline = getOnline();
	if (! $nowOnline['error']) {
		?>
<H3>Arne is <IMG SRC="/online/aim.16.png" WIDTH="16" HEIGHT="16" ALT=""></H3>
<P CLASS="music"><?php echo ($nowOnline['online'] ? 'on' : 'off'); ?>line</P>
		<?php
	}
}



// deprecated method bodys
function getOnline () {
	return ONgetOnline();
}
function printOnlineBox () {
	return ONprintOnlineBox();
}
?>