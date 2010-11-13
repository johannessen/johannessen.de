<?php
/* $Id: index.php 2010-11-13 aj3 $
 * UTF-8
 * geocode-adapter
 */

function response ($status = '200 OK') {
	header("HTTP/1.1 $status");
	echo "<TITLE>$status</TITLE>\n";
	echo "<H1>$status</H1>\n";
}

function geoToMap ($lat, $lon, $zoom = 0) {
	// coordinate transformation, see /Users/aj3/Geomatik/OSM/OSM coordinates/coord latlon2xy.gcx
	$x = pow(2, $zoom) * (180 + $lon) / 360 - .5;
	$φ = M_PI / 180 * $lat;
	$p = log( tan($φ) + 1 / cos($φ) );
	$y = pow(2, $zoom - 1) * (M_PI - $p) / M_PI - .5;
	return array($x, $y);
}

function mapZoom ($x, $y, $zoom, $zoomTo = 0) {
	// coordinate transformation, see /Users/aj3/Geomatik/OSM/OSM coordinates/coord zoom.gcx
	$f = pow(2, $zoomTo - $zoom);
	$s = ($f - 1) / 2;
	$newX = $x * $f + $s;
	$newY = $y * $f + $s;
	return array($newX, $newY);
}

function mapToPixels ($distance) {
	return $distance * 256;
}



header('Content-Type: text/html; charset=UTF-8');

$queryString = $_SERVER['QUERY_STRING'];
$queryString = (substr($queryString, 0, 1) == '?') ? substr($queryString, 1) : $queryString;
$queryString = explode('%26', $queryString);
$get = $_GET;
foreach ($queryString as $queryStringItem) {
	list($key, $value) = explode('=', $queryStringItem);
	$get[$key] = $value;
}
if (@$get['lat'] && @$get['lon'] && @$get['zoom']) {  // OSM syntax
	$lat = (float)$get['lat'];
	$lon = (float)$get['lon'];
	$zoom = (int)$get['zoom'];
	$positionGiven = TRUE;
}
else if (@$get['cp'] && @$get['lvl']) {  // Bing syntax
	$pos = explode('~', $get['cp']);
	$lat = (float)$pos[0];
	$lon = (float)$pos[1];
	$zoom = (int)$get['lvl'];
	$positionGiven = TRUE;
}
else if (@$get['ll'] && @$get['z']) {  // Google syntax
	$pos = explode(',', $get['ll']);
	$lat = (float)$pos[0];
	$lon = (float)$pos[1];
	$zoom = (int)$get['z'];
	$positionGiven = TRUE;
}
$query = @$_GET['q'];


if ( !($query || $positionGiven && array_key_exists('p', $get)) ) {
	if ($positionGiven) {
		$positionString = $lat . '° N, ' . $lon . '° E';
		$positionUriString = 'lat=' . $lat . '&amp;lon=' . $lon . '&amp;zoom=' . $zoom;
	}
?>
<TITLE>Geocoding Adapter<?php if ($positionGiven) { echo ' (@ ', $positionString, ')'; } ?></TITLE>
<STYLE TYPE="text/css">A,LABEL{display:block}</STYLE>
<H1>Geocoding Adapter</H1>
<?php
	if ($positionGiven) {
?>
<P>Position: <?php echo $positionString; ?> <BR>Zoom: <?php echo $zoom; ?>
<H2>Chooser</H2>
<A HREF="<?php echo '?p=', '&amp;', $positionUriString; ?>">Google Maps</A>
<A HREF="<?php echo '?p=osm', '&amp;', $positionUriString; ?>">OpenStreetMap</A>
<A HREF="<?php echo '?p=bing', '&amp;', $positionUriString; ?>">Bing Maps</A>
<A HREF="<?php echo '?p=kartverket', '&amp;', $positionUriString; ?>">Kartverket</A>
<?php
	}
	else {
		$product = @$_GET['p'];
		$p = '';
		if ($product == 'osm' || $product == 'bing' || $product == 'kartverket') { $p = $product; }
?>
<FORM METHOD="GET" ACTION="">
<INPUT TYPE="text" NAME="q" VALUE="<?php echo htmlspecialchars(str_replace('+', ' ', implode(' ', $queryString))); ?>"> <INPUT TYPE="submit">
<LABEL><INPUT TYPE="radio" NAME="p" VALUE=""<?php echo $p == '' ? ' CHECKED' : ''; ?>> Google Maps</LABEL>
<LABEL><INPUT TYPE="radio" NAME="p" VALUE="osm"<?php echo $p == 'osm' ? ' CHECKED' : ''; ?>> OpenStreetMap</LABEL>
<LABEL><INPUT TYPE="radio" NAME="p" VALUE="bing"<?php echo $p == 'bing' ? ' CHECKED' : ''; ?>> Bing Maps</LABEL>
<LABEL><INPUT TYPE="radio" NAME="p" VALUE="kartverket"<?php echo $p == 'kartverket' ? ' CHECKED' : ''; ?>> Kartverket</LABEL>
</FORM>
<?php
	}
	die();
}

$kartverket = 'B';  // default: OSM
$geocodeQuery = '';
switch ($query) {
	case 'n': case 'N': $lat = 60.2; $lon = 8; $zoom = 7; $kartverket = '0B'; break;  // Norge
	case 'b': case 'B': $lat = 60.43; $lon = 5.32; $zoom = 11; $kartverket = '0B'; break;   // Bergen
	case 'o': case 'O': $lat = 60.005; $lon = 5.794; $zoom = 13; $kartverket = '0B'; break;   // Ølve
	case 'e': case 'E': $lat = 51; $lon = 13; $zoom = 4; $kartverket = 'B'; break;   // EU
	case 'd': case 'D': $lat = 51; $lon = 10; $zoom = 6; $kartverket = 'B'; break;   // Deutschland
	case 'm': case 'M': $lat = 51.08; $lon = 7.54; $zoom = 14; $kartverket = 'B'; break;   // Marienheide
	case 'g': case 'G': $lat = 50.99; $lon = 7.58; $zoom = 12; $kartverket = 'B'; break;   // Oberberg (Gummersbach)
	case 'k': case 'K': $lat = 49.01; $lon = 8.38; $zoom = 14; $kartverket = 'B'; break;  // Karlsruhe
	case 'f': case 'F': $lat = 27.8; $lon = -81.6; $zoom = 7; $kartverket = 'B'; break;   // Florida
	case 't': case 'T': $lat = 27.875; $lon = -82.534; $zoom = 11; $kartverket = 'B'; break;   // Tampa Bay
	default: $geocodeQuery = $query;
}

if ($geocodeQuery) {
	$uri = 'http://maps.googleapis.com/maps/api/geocode/json?sensor=false&bounds=&region=&address=' . urlencode($query);
	$jsonReply = file_get_contents($uri);
	$reply = json_decode($jsonReply);
	if (! $reply) {
		response('417 Expectation Failed');  // this is probably status code abuse, but I'll be the only one to ever see it…
		echo '<P>Internal geocoding error.';
		die();
	}
	if (count($reply->results) == 0) {
		response('404 Not Found');
		echo '<P>Location not found.';
		die();
	}
	
	$location = $reply->results[0]->geometry->location;
	$lat = $location->lat;
	$lon = $location->lng;
	
	list($mapWidth, $mapHeight) = array(687, 456);  // Google Maps at ^s window size
	$maxZoomLevel = 18;
	$zoom = 13;  // default zoom, if none can be determined below
	
	// figure out zoom from recommended viewport
	$viewport = $reply->results[0]->geometry->viewport;
	list($east, $north) = geoToMap($viewport->northeast->lat, $viewport->northeast->lng, 0);
	list($west, $south) = geoToMap($viewport->southwest->lat, $viewport->southwest->lng, 0);
	for ($zoomLevel = 1; $zoomLevel <= $maxZoomLevel + 1; $zoomLevel++) {
		list($east, $north) = mapZoom($east, $north, $zoomLevel, $zoomLevel + 1);
		list($west, $south) = mapZoom($west, $south, $zoomLevel, $zoomLevel + 1);
		$height = abs(mapToPixels($north - $south));
		$width = abs(mapToPixels($east - $west));
		if ($height >= $mapHeight && $width >= $mapWidth) {
			$zoom = $zoomLevel - 1;
			break;
		}
	}
}

if (array_key_exists('p', $_GET) && $_GET['p'] == 'osm') {
	// use OpenStreetMap
	// not sure if this is allowed by the Google geocoding license
	
	$redirectBase = 'http://www.openstreetmap.org/?layers=M&';
	$redirectQuery = 'lat=' . $lat . '&lon=' . $lon . '&zoom=' . $zoom;
}
else if (array_key_exists('p', $_GET) && $_GET['p'] == 'bing') {
	// use Bing
	// not sure if this is allowed by the Google geocoding license
	
	$redirectBase = 'http://www.bing.com/maps/?';
	$redirectQuery = 'cp=' . $lat . '~' . $lon . '&lvl=' . $zoom;
}
else if (array_key_exists('p', $_GET) && $_GET['p'] == 'kartverket') {
	// use Kartverket
	// not sure if this is allowed by the Google geocoding license
	
	$redirectBase = 'kartverket/?';
	$redirectQuery = 'lat=' . $lat . '&lon=' . $lon . '&zoom=' . $zoom . '&layers=' . $kartverket;
}
else {
	// use Google Maps
	
	$redirectBase = 'http://maps.google.de/maps?output=classic&';
	$redirectQuery = 'll=' . $lat . ',' . $lon . '&z=' . $zoom;
	
}

// :TODO: display both maps next to each other, let the user choose
// cf. <http://tools.geofabrik.de/mc/>

$redirect = $redirectBase . $redirectQuery;
header("Location: $redirect");
//header('Expires: ' . date(DateTime::RFC1123, time() + 3600 * 24));
response('307 Temporary Redirect');
echo "<P><A HREF='$redirect'>$redirect</A>";

?>
