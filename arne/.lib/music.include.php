<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: music.include.php 29 2008-11-09 17:02:26Z aj3 $


define('MUSIC_TEXTFILE', '/music/music.txt');  // path to data textfile
define('MUSIC_INIFILE', '/music/music.ini');  // path to uri file


function MSsetMusicMute () {
	MSwriteMusic("###\n\n");
}


function MSsetMusic ($title, $artist, $time, $bitrate) {
	MSwriteMusic($title."\n".$artist."\n".$time.'@'.$bitrate);
}


function MSgetMusic () {
	$musicData = MSreadMusic();
	if ($musicData == "###\n\n") {
		return false;  // mute
	}
	else {
		$musicArray = explode("\n", $musicData, 4);
		$sizeArray = explode('@', $musicArray[2], 3);
		$uris = parse_ini_file($_SERVER['DOCUMENT_ROOT'].MUSIC_INIFILE, FALSE);
		$artist = str_replace('&amp;', '+', $musicArray[1]);
		$uri = $artist.' '.$musicArray[0];
		if (array_key_exists($uri, $uris)) {
			$uri = $uris[$uri];
			$titleUri = TRUE;
		}
		else if (array_key_exists($artist, $uris)) {
			$uri = $uris[$artist];
			$titleUri = FALSE;
		}
		else {
			$uri = FALSE;
			$titleUri = FALSE;
		}
		return array('title' => $musicArray[0], 'artist' => $musicArray[1], 'time' => $sizeArray[0], 'bitrate' => $sizeArray[1], 'uri' => $uri, 'titleuri' => $titleUri);
	}
}


function MSprintMusicBox () {
	$music = MSgetMusic();
	?>
<H3>iTunes is <?php echo ($music ? 'playing' : ''); ?> <IMG SRC="/music/itunes.16.png" WIDTH="16" HEIGHT="16" ALT=""></H3>
<P CLASS="music">
	<?php
	if ($music == false) {
		echo 'silent';
	}
	else {
		if (strlen($music['title']) > 0) {
			if ($music['titleuri'] && $music['uri'] !== FALSE) {
				echo '<A HREF="', $music['uri'], '">';
			}
			echo '<EM>', $music['title'], '</EM>';
			if ($music['titleuri'] && $music['uri'] !== FALSE) {
				echo '</A>';
			}
			$title = true;
		}
		else {
			$title = false;
		}
		if (strlen($music['artist']) > 0) {
			if ($title === false) {
				echo 'A title';
			}
			echo ' by ';
			if (! $music['titleuri'] && $music['uri'] !== FALSE) {
				echo '<A HREF="', $music['uri'], '">';
			}
			echo '<EM>', $music['artist'], '</EM>';
			if (! $music['titleuri'] && $music['uri'] !== FALSE) {
				echo '</A>';
			}
		}
/*		if (strlen($music['time']) > 0 && strlen($music['bitrate']) > 0) {
			echo "<BR>(", $music['time'], '@', $music['bitrate'], ')';
		}
*/	}
	?>
</P>
	<?php
}


function MSwriteMusic ($musicData) {
	$file = fopen($_SERVER['DOCUMENT_ROOT'].MUSIC_TEXTFILE, 'w');
	if (! $file) {
		// fatal error
		echo '* unable to write data *';
		exit;
	}
	fwrite($file, $musicData);
	fclose($file);
}


function MSreadMusic () {
	$file = fopen($_SERVER['DOCUMENT_ROOT'].MUSIC_TEXTFILE, 'r');
	if (! $file) {
		// fatal error
		echo '* unable to read data *';
		exit;
	}
	$musicData = '';
	while (! feof($file)) {
		$line = fgets($file);
		$musicData .= $line;
	}
	fclose($file);
	return $musicData;
}


function MSprintMusicAscii () {
	// return plain music text file
	$lastModified = date('r', filemtime($_SERVER['DOCUMENT_ROOT'].MUSIC_TEXTFILE));
	header('Last-Modifed: '.$lastModified);
	header('Date: '.$lastModified);
	readfile($_SERVER['DOCUMENT_ROOT'].MUSIC_TEXTFILE);
}


function printMusicBox () {
	MSprintMusicBox();
}

?>