<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: general.include.php 29 2008-11-09 17:02:26Z aj3 $


function GNgetEscapedMail ($address) {
	return str_replace('@', '&#64;', $address);
}


function GNgetEscapedMailLink ($address) {
	$escapedAddress = GNgetEscapedMail($address);
	return '<A HREF="mailto:'.$escapedAddress.'">'.$escapedAddress.'</A>';
}


function GNprintEscapedMailLink ($address) {
	echo GNgetEscapedMailLink($address);
}


function GNprintHtmlHeader ($headerConfig) {
	$contentType = 'text/html; charset='.(isset($headerConfig['charset']) ? ($headerConfig['charset']) : ('iso-8859-1'));
	header('Content-Type: '.$contentType);
	$language = isset($headerConfig['language']) ? ($headerConfig['language']) : ('de');
	$authorName = isset($headerConfig['author']) ? ($headerConfig['author']) : ('Arne Johannessen');
//	$authorMail = GNgetEscapedMail(isset($headerConfig['mailto']) ? ($headerConfig['mailto']) : ('webmaster@thaw.de'));
	if (isset($headerConfig['mailto'])) {
		$authorMail = $headerConfig['mailto'];
	}
	else {
		import('domains');
		$authorMail = GNgetEscapedMail(DMgetWebmaster());
	}
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<HTML LANG="<?php echo $language; ?>"><HEAD>
	<META HTTP-EQUIV="Content-Type" CONTENT="<?php echo $contentType; ?>">
	<META NAME="author" CONTENT="<?php echo $authorName; ?>">
	<LINK REV="made" HREF="mailto:<?php echo $authorMail; ?>">
<?php
}


function GNgetHtmlValidatorUri ($fussy) {
	return 'http://validator.w3.org'.($fussy ? ':8001' : '').'/check?uri=http%3A%2F%2F'.urlencode($_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI']).';verbose=1';
}


function GNgetCssValidatorUri ($profile) {
	// values for $profile: none, css1, css2, css3, ...
	return 'http://jigsaw.w3.org/css-validator/validator?uri=http%3A%2F%2F'.urlencode($_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI']).'&amp;warning=2&amp;profile='.$profile.'&amp;usermedium=all';
}


function GNprintHtmlFooter () {
?>
</BODY></HTML>
<?php
}



// deprecated method bodys and calls for backward compatibility
require_once('import.include.php');
function escapeEMail ($address) {
	return GNgetEscapedMail($address);
}
function printHtmlHeader ($headerConfig) {
	GNprintHtmlHeader($headerConfig);
}
function printHtmlFooter () {
	GNprintHtmlFooter();
}
function GNprintEscapedMail ($address) {
	GNprintEscapedMailLink($address);
}
?>
