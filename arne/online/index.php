<?php
// by Arne Johannessen
// mailto:webmaster@johannessen.de
// last modified 2004-10-24

ini_set('include_path', '../.lib');

//$developmentStatus = true;
require_once('import.include.php');
import('general');
import('online');

GNprintHtmlHeader(array(
		'mailto' => 'webmaster@johannessen.de'));
?>

	<TITLE>Wie funktioniert die Online-Pr&uuml;fung?</TITLE>
</HEAD><BODY>

<DIV ID="onlineBox"><?php
	printOnlineBox();
	?>
</DIV>

<?php
printHtmlFooter();
?>
