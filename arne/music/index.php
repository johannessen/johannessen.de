<?php
// by Arne Johannessen
// mailto:webmaster@johannessen.de
// last modified 2004-10-24

ini_set('include_path', '../.lib');

$developmentStatus = true;
require_once('import.include.php');
import('general');
import('music');

GNprintHtmlHeader(array(
		'mailto' => 'webmaster@johannessen.de',
		'charset' => 'utf-8'));
?>

	<TITLE>Wie funktioniert die Online-Musikanzeige?</TITLE>
</HEAD><BODY>

<DIV ID="musicBox"><?php
	printMusicBox();
	?>
</DIV>

<?php
printHtmlFooter();
?>
