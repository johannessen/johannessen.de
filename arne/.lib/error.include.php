<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: error.include.php 29 2008-11-09 17:02:26Z aj3 $


function ERgetErrorCode () {
	return (array_key_exists('REDIRECT_STATUS', $_SERVER) ? $_SERVER['REDIRECT_STATUS'] : 200);
}


function ERhandleError () {
	$status = ERgetErrorCode();
	$location = NULL;
	if ($status == 404) {  // Not Found
		// look up the uri in the redirect configuration file
		list($status, $location) = _ERfindRedirect();
	}
	ERprintErrorPage ($status, $location);
}


function ERprintErrorPage ($errorCode, $additionalData) {
	header('Content-Type: text/html; charset=iso-8559-1');
	header('Content-Style-Type: text/css');
	header('HTTP/1.0 '.$errorCode.' '.ERgetErrorText($errorCode));
	switch ($errorCode) {  // special treatment for some codes
		case 201:  // Created
		case 301:  // Moved Permanently
		case 302:  // Found
		case 303:  // See Other
		case 305:  // Use Proxy
		case 307:  // Temporary Redirect
			if ($additionalData == NULL) {
				ERprintErrorPage(500, NULL);
				exit();
			}
			header('Location: '.$additionalData);
			break;
		
		case 304:  // Not Modified
			return;
		
		case 405:  // Method Not Allowed
			if ($additionalData == NULL) {
				ERprintErrorPage(500, NULL);
				exit();
			}
			header('Accept: '.$additionalData);
			break;
	}
	?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<HTML LANG="en"><HEAD>
	<META NAME="robots" CONTENT="noindex">
	<TITLE><?php echo $errorCode, ' ', ERgetErrorText($errorCode); ?></TITLE>
</HEAD><BODY>

<?php
echo '<H1>', ERgetErrorText($errorCode), ' <SPAN STYLE="font: .45em \'Geneva\', sans-serif; font-weight: normal">(',$errorCode,')</SPAN></H1>';
echo "\n", ERgetErrorDescriptionParagraph($errorCode, $additionalData);
echo "\n<HR>\n", $_SERVER['SERVER_SIGNATURE'];
?>	

</BODY></HTML>
<?php
}


function ERgetErrorText ($errorCode) {
	switch ($errorCode) {
		case 100:
			return 'Continue';
		case 101:
			return 'Switching Protocols';
		case 200:
			return 'OK';
		case 201:
			return 'Created';
		case 202:
			return 'Accepted';
		case 203:
			return 'Non-Authoritative Information';
		case 204:
			return 'No Content';
		case 205:
			return 'Reset Content';
		case 206:
			return 'Partial Content';
		case 300:
			return 'Multiple Choices';
		case 301:
			return 'Moved Permanently';
		case 302:
			return 'Found';
		case 303:
			return 'See Other';
		case 304:
			return 'Not Modified';
		case 305:
			return 'Use Proxy';
		case 306:
			return '(Unused)';
		case 307:
			return 'Temporary Redirect';
		case 400:
			return 'Bad Request';
		case 401:
			return 'Unauthorized';
		case 402:
			return 'Payment Required';
		case 403:
			return 'Forbidden';
		case 404:
			return 'Not Found';
		case 405:
			return 'Method Not Allowed';
		case 406:
			return 'Not Acceptable';
		case 407:
			return 'Proxy Authentication Required';
		case 408:
			return 'Request Timeout';
		case 409:
			return 'Conflict';
		case 410:
			return 'Gone';
		case 411:
			return 'Length Required';
		case 412:
			return 'Precondition Failed';
		case 413:
			return 'Request Entity Too Large';
		case 414:
			return 'Request-URI Too Long';
		case 415:
			return 'Unsupported Media Type';
		case 416:
			return 'Requested Range Not Satisfiable';
		case 417:
			return 'Expectation Failed';
		case 500:
			return 'Internal Server Error';
		case 501:
			return 'Not Implemented';
		case 502:
			return 'Bad Gateway';
		case 503:
			return 'Service Unavailable';
		case 504:
			return 'Gateway Timeout';
		case 505:
			return 'HTTP Version Not Supported';
		case 506:
			return 'Variant Also Negotiates';
		case 507:
			return 'Insufficient Storage';
		default:
			return 'Unknown Error';
	}
}


function ERgetErrorDescriptionParagraph ($errorCode, $additionalData) {
	$html = ERgetErrorDescription($errorCode, $additionalData);
	$html = '<P>'.str_replace("\n", '</P><P>', $html);
	if (($errorCode == 405 || $errorCode == 300) && $additionalData != NULL) {
		// treat the additional data as if it were a
		// comma-seperated list to be converted to an HTML list
		return str_replace($additionalData, '</P><UL><LI>'.str_replace(', ', '</LI><LI>', $additionalData).'</LI></UL>', $html);
	}
	else {
		return $html.'</P>';
	}
}


function ERgetErrorDescription ($errorCode, $additionalData) {
	// see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10#sec10
	switch ($errorCode) {
		case 100:  // fall-through
		case 101:
			return '';  // no entity
		
		case 204:  // fall-through
		case 205:
			return '';  // no entity
		case 200:  // fall-through
		case 201:  // fall-through
		case 202:  // fall-through
		case 203:  // fall-through
		case 206:
			return 'Successful.';  // standard entity
		
		case 300:
			return 'The document name you requested ('.$_SERVER['REQUEST_URI'].") could not be found on this server. However, we found documents with names similar to the one you requested.\nAvailable documents:".$additionalData;
		case 301:
			return 'The document has permanently moved to <A HREF="'.$additionalData.'">'.$additionalData."</A>.\nPlease update your bookmarks and hyperlinks.";
		case 302:
			return 'The document  was found at <A HREF="'.$additionalData.'">'.$additionalData.'</A>.';
		case 303:
			return 'The answer to your request is located at <A HREF="'.$additionalData.'">'.$additionalData.'</A>.';
		case 304:
			return '';  // no entity
		case 305:
			return 'This resource is only accessible through the proxy <A HREF="'.$additionalData.'">'.$additionalData."</A>\nYou will need to configure your client to use that proxy.";
		case 306:
			return 'Redirection.';  // code unused
		case 307:
			return 'The document has temporarily moved to <A HREF="'.$additionalData.'">'.$additionalData.'</A>.';
		
		case 400:
			return 'Your browser sent a request that this server could not understand.';
		case 401:  // fall-through
		case 407:
			return 'This server could not verify that you are authorized to access the document requested. Either you supplied the wrong credentials (e.&nbsp;g. bad password), or your browser doesn&#8217;t understand how to supply the credentials required.';
		case 403:
			return 'You don&#8217;t have permission to access '.$_SERVER['REQUEST_URI'].' on this server.';
		case 404:
			return 'The requested URI '.$_SERVER['REQUEST_URI'].' was not found on this server.';
		case 405:
			return 'The requested method '.$_SERVER['REQUEST_METHOD'].' is not allowed for the URL '.$_SERVER['REQUEST_URI'].".\nAllowed methods: ".$additionalData;
		case 406:
			return 'An appropriate representation of the requested resource '.$_SERVER['REQUEST_URI'].' could not be found on this server.';
		case 408:
			return 'Server timeout waiting for the HTTP request from the client.';
		case 410:
			return 'The requested resource '.$_SERVER['REQUEST_URI'].' is no longer available on this server and there is no forwarding address. Please remove all references to this resource.';
		case 411:
			return 'A request of the requested method '.$_SERVER['REQUEST_METHOD'].' requires a valid Content-length.';
		case 412:
			return 'The precondition on the request for the URI '.$_SERVER['REQUEST_URI'].' evaluated to false.';
		case 413:
			return 'The requested resource '.$_SERVER['REQUEST_URI'].' does not allow request data with '.$_SERVER['REQUEST_METHOD'].' requests, or the amount of data provided in the request exceeds the capacity limit.';
		case 414:
			return 'The requested URI&#8217;s length exceeds the capacity limit for this server.';
		case 415:
			return 'The supplied request data is not in a format acceptable for processing by this resource.';
		case 416:
			return 'None of the range-specifier values in the Range request-header field overlap the current extent of the selected resource.';
		case 417:
			return 'The expectation given in the Expect request-header field could not be met by this server.';
		case 402:  // fall-through
		case 409:  // fall-through
			return 'Client Error.';  // standard entity (or code reserved)
		
		case 500:
			return "The server encountered an internal error or misconfiguration and was unable to complete your request.\nPlease contact the server administrator, ".(($additionalData == NULL) ? 'webmaster@thaw.de' : $additionalData)." and inform them of the time the error occurred, and anything you might have done that may have caused the error.\nMore information about this error may be available in the server error log.";
		case 501:
			return $_SERVER['REQUEST_METHOD'].' to '.$_SERVER['REQUEST_URI'].' not supported.';
		case 502:
			return 'The proxy server received an invalid response from an upstream server.';
		case 503:
//			return 'The server is temporarily unable to service your request due to maintenance downtime or capacity problems. Please try again later.';
			return 'The server is temporarily unable to service your request due to maintenance downtime. Please try again later.';
		case 504:
			return 'The proxy server did not receive a timely response from the upstream server.';
		case 505:
			return 'The server does not support the HTTP protocol version that was used in the request message. Supported HTTP versions include HTTP/1.1 and HTTP/1.0.';
		case 506:
			return 'A variant for the requested resource '.$_SERVER['REQUEST_URI'].' is itself a negotiable resource. This indicates a configuration error.';
		case 507:
			return 'The method could not be performed on the resource because the server is unable to store the representation needed to successfully complete the request. There is insufficient free space left in your storage allocation.';
		
		default:
			return 'An unknown error occured while processing the request for the URL '.$_SERVER['REQUEST_URI'].' on this server.';
	}
}


function _ERgetStatusCode ($status) {
	static $statusCodesByKeyword = array(
			'permanent' => 301,
			'found' => 302,
			'seeother' => 303,
			'temporary' => 307,
			'forbidden' => 403,
			'not-found' => 404,
			'gone' => 410,
			'server-error' => 500,
			'not-implemented' => 501,
			'maintenance' => 503);
	
	if (is_numeric($status)) {
		return intval($status);
	}
	else if (array_key_exists($status, $statusCodesByKeyword)) {
		return $statusCodesByKeyword[$status];
	}
	else {
		return NULL;
	}
}


function _ERfindRedirect () {
	if (! array_key_exists('REDIRECT_URL', $_SERVER)) {
		return array(404, NULL);  // no redirect can possibly be found
	}
	$redirects = parse_ini_file(HOME_DIR.'/.lib/redirect.ini', TRUE);
	reset($redirects);
	while (list($host, $redirect) = each($redirects)) {
		if ($host != $_SERVER['SERVER_NAME']) {
			continue;
		}
		reset($redirect);
		while (list($old, $new) = each($redirect)) {
			// does this particular redirect rule apply to the current url?
			$identical = $_SERVER['REDIRECT_URL'] == $old || $_SERVER['REDIRECT_URL'].'/' == $old;
			$subdirLocations = FALSE;
			if (! $identical) {
				$subdirLocations = strpos($_SERVER['REDIRECT_URL'], $old) === 0 && substr($old, -1) == '/' && (substr($new, -1) == '/' || strpos($new, '?') > 0);
				if (! $subdirLocations) {
					continue;
				}
			}
			// determine redirect properties
			$status = 301;
			$location = NULL;
			if (_ERgetStatusCode($new) !== NULL) {
				// no location
				// e. g.  ... = 410  or  ... = gone
				$status = $new;
			}
			else {
				if ($subdirLocations) {
					// location with possible subdirs
					//  .../ = .../  or  .../ = ...?...
					$new .= substr($_SERVER['REDIRECT_URL'], strlen($old));
				}
				if (strpos($new, ':') > 0) {
					// location with specific status code // *or* full URL
					// e. g.  ... = 303:...  or  ... = seeother:... // or  ... = http://...
					list($status, $new) = explode(':', $new);
				}
				$location = 'http:'.((substr($new, 0, 2) == '//') ? '' : '//'.$host).$new;
			}
			$status = _ERgetStatusCode($status);
			if ($status === NULL) {
				// an illegal status code is a server setup error
				$status = 500;
				$location = NULL;
			}
			return array($status, $location);
		}
	}
	return array(404, NULL);  // no redirect could be found
}


function getErrorCode () {
	return ERgetErrorCode();
}

function printErrorPage ($errorCode) {
	ERprintErrorPage($errorCode, NULL);
}

function getErrorText ($errorCode) {
	return ERgetErrorText($errorCode);
}

function getErrorDescriptionParagraph ($errorCode) {
	return ERgetErrorDescriptionParagraph($errorCode, NULL);
}

function getErrorDescription ($errorCode) {
	return ERgetErrorDescription($errorCode, NULL);
}

?>
