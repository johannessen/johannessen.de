<?php
// by Arne Johannessen
// mailto:webmaster@thaw.de
// $Id: music.auth.include.php 29 2008-11-09 17:02:26Z aj3 $

import('error');


function MSassertMusicAuthentication () {
	if ($_SERVER['HTTP_USER_AGENT'] != 'Java/1.4.2_05' || substr($_SERVER["REMOTE_ADDR"], 0, 5) != '217.1') {
		printErrorPage(403);
		exit();
	}
}


?>