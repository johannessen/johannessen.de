<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: domains.include.php 301 2010-12-21 15:03:01Z aj3 $


import('general');


function DMgetDomainList () {
	static $domainList = array(
			'johannessen.de',
			'skgb.de',
			'thaw.de');
	return $domainList;
}


function DMgetDomainByHost ($host) {
	$domainList = DMgetDomainList();
	foreach ($domainList as $domain) {
		if (substr($host, 0 - strlen($domain)) == $domain) {
			return $domain;
		}
	}
	return NULL;
}


function DMgetDomain () {
	$serverName = array_key_exists('SERVER_NAME', $_SERVER) ? $_SERVER['SERVER_NAME'] : '';
	return DMgetDomainByHost(strtolower($serverName));
}


function DMgetHost () {
	if (! array_key_exists('SERVER_NAME', $_SERVER)) {
		return NULL;
	}
	return substr($_SERVER['SERVER_NAME'], 0, max(strlen($_SERVER['SERVER_NAME']) - strlen(DMgetDomain()) - 1, 0));
}


function DMprintDomain () {
	echo DMgetDomain();
}


/*
function DMgetCommonServiceMail ($service) {
	if ($service == 'webmaster' || $service == 'www') {
		return 'webmaster@'.$_SERVER['HTTP_HOST'];
	}
	else {
		// abuse, hostmaster, noc, postmaster, security (fully serviced)
		// info, marketing, support (partially serviced)
		return $service.'@'.DMgetDomain();
	}
}
*/


function DMgetWebmasterByDomain ($domain) {
	if (! $domain) {
		return 'webmaster';
	}
	return 'webmaster@'.$domain;
}


function DMgetWebmaster () {
	$host = array_key_exists('HTTP_HOST', $_SERVER) ? $_SERVER['HTTP_HOST'] : NULL;
	return DMgetWebmasterByDomain($host);
}


function DMprintWebmasterLinkByDomain ($domain) {
	echo GNprintEscapedMail(DMgetWebmasterByDomain($domain));
}


function DMprintWebmasterLink () {
	$host = array_key_exists('HTTP_HOST', $_SERVER) ? $_SERVER['HTTP_HOST'] : NULL;
	DMprintWebmasterLinkByDomain($host);
}

?>
