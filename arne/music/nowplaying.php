<?php
// by Arne Johannessen
// mailto:webmaster@johannessen.de
// last modified 2004-11-05

ini_set('include_path', $_SERVER['PHP_BASE'].'/.lib');

//$developmentStatus = true;
require_once('import.include.php');
import('error');
import('music');
import('music.auth');


// turn away unauthorized visitors (uses music.auth.include.php)
MSassertMusicAuthentication();
if ($_SERVER['REQUEST_METHOD'] != 'POST' || ! array_key_exists('method', $_POST) || $_POST['method'] != 'post') {
	if (! array_key_exists('method', $_GET) || $_POST['method'] != 'get' && $_GET['method'] != 'head') {
		ERprintErrorPage(403, NULL);
	}
	else {
		ERprintErrorPage(405, 'POST');
	}
	exit();
}


define('TEXTFILE', 'nowplaying.txt');  // name of data textfile


if (array_key_exists('mute', $_POST) && trim($_POST['mute']) == 'mute') {
	MSsetMusicMute();
}
else if (array_key_exists('title', $_POST)) {
	
	$title = htmlspecialchars(stripslashes(trim($_POST['title'])));
	if (array_key_exists('artist', $_POST)) {
		$artist = htmlspecialchars(stripslashes(trim($_POST['artist'])));
	}
	else {
		$artist = '';
	}
	if (array_key_exists('time', $_POST)) {
		$time = htmlspecialchars(stripslashes(trim($_POST['time'])));
	}
	else {
		$time = '';
	}
	if (array_key_exists('bitrate', $_POST)) {
		$bitrate = htmlspecialchars(stripslashes(trim($_POST['bitrate'])));
	}
	else {
		$bitrate = '';
	}
	
	MSsetMusic($title, $artist, $time, $bitrate);

}

// return no content
header('HTTP/1.0 204 '.ERgetErrorText(204));

/*
// ensure that nobody tries to parse any HTML
header('Content-type: text/plain; charset=utf-8');
// return scores file
MSprintMusicAscii();
*/
?>