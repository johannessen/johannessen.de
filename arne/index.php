<?php
// by Arne Johannessen
// mailto:webmaster@johannessen.de
// last modified 2004-11-05

ini_set('include_path', '/www/.lib');

//$developmentStatus = true;
require_once('import.include.php');
import('general');
import('domains');
import('online');
import('music');

printHtmlHeader(array(
		'mailto' => 'webmaster@johannessen.de',
		'charset' => 'utf-8'));
?>

	<TITLE>Arne Johannessen</TITLE>
	<STYLE TYPE="text&#47;css"><!--
BODY {
	background: #dfd;
	color: black;
	margin: 25px;
	font-size: 100%;
	font-family: "Garamond", "Palatino", serif;
}
P {
	margin: 0 0 24px;
}
#content-abovefold {
	background: white;
	color: inherit;
	border-color: green;
	border-width: 1px 1px 0 1px;
	border-style: solid solid none solid;
	padding: 25px;
}
#content-belowfold {
	float: left;
	clear: left;
	width: 50%;
	padding-bottom: 20px;
	background: white;
	color: inherit;
	border-color: green;
	border-width: 0 1px 1px 1px;
	border-style: none solid solid solid;
}
#content-belowfold P {
	margin: .5em 25px;
}
#content-belowfold P:first-child {
	margin-top: -25px;
	z-index: 1;
}
#sidebar {
	padding: 25px 0 0 25px;
	margin-left: 50%;
	border-top: green 1px solid;
}

H1 {
	margin: 0 0 22px 0;
	padding: 0;
	font-size: 120%;
}
/* #onlineBox, #musicBox {
	clear: left; */
#bothBoxes {
	background: #dfd;
	color: inherit;
	float: left;
	margin: .2em 25px 20px -26px;
	padding: 0 1em .5em 26px;
	border-color: green;
	border-width: 1px 1px 1px 0;
	border-style: solid solid solid none;
}
#bothBoxes H3 {
	margin-bottom: 0;
}
#content-abovefold P, #sidebar P, #sidebar UL {
	margin: .5em 0;
}
#sidebar P:first-child {
	margin-top: 0;
}
.interco {
	text-transform: uppercase;
	font-size: 0.8em;
}
#studyWink {
	vertical-align: top;
}

#footer {
	padding-top: 25px;
	clear: both;
}
--></STYLE>
</HEAD><BODY>
	<P>My random stuff used to reside at <A HREF="//dev.thaw.de/">dev.thaw.de</A> until the beginning of 2004, when I finally decided that due to the incremental addition of more and more not-so-random stuff, I needed another place for some of it. Since February 2004, I am finally able to write:</P>
	<DIV ID="content-abovefold">
		<H1>&#8220;Welcome to the personal home page of Arne Johannessen.&#8221;</H1>
		<DIV ID="bothBoxes">
			<DIV ID="musicBox"><?php
				printMusicBox();
			?><!--P><A HREF="/music/">How does this work?</A></P--></DIV>
			<DIV ID="onlineBox"><?php
				printOnlineBox();
			?><!--P><A HREF="/online/">How does this work?</A></P--></DIV>
		</DIV>
		<P>Like most other personal home pages, this one is being constantly but irregularly updated. Unlike most other personal home pages, this one contains little information about the person behind it. I&#8217;m not yet entirely clear about why I thought I needed a <EM>personal</EM> home page in the first place: If you know me personally, you don&#8217;t need a personal page about me. If you don&#8217;t know me, this home page certainly won&#8217;t change that.</P>
		<P>This page consists of <A HREF="<?php echo GNgetHtmlValidatorUri(FALSE); ?>">valid</A> tableless <A HREF="http://www.w3.org/TR/html401/">HTML 4.01 Strict</A> and <A HREF="<?php echo GNgetCssValidatorUri('css2'); ?>">valid</A> <A HREF="http://www.w3.org/TR/REC-CSS2/">CSS Level 2</A>. <A HREF="http://www.php.net/">PHP</A> is also used behind the scenes. While I enjoy the technical aspects of designing web sites, I really don&#8217;t think that I&#8217;m a good graphic designer. Additionally, I&#8217;ve never liked to write long meaningless texts like this one.</P>
		<P>I used too many words to say too little about <A HREF="/islets/">Arne&#8217;s Islets</A>, but if you have got too much time, you can <A HREF="/islets/">read the essay</A> anyway. By contrast, many other people are good at saying much with few words while making reading them entertaining. I&#8217;ve created a small <A HREF="/collection">collection</A> of email signatures, quotations and other one-liners.</P>
		<P>If you are interested in some of my other activities and / or <!--A HREF="//servo.johannessen.de/hosts/hosts.var"-->web sites<!--/A-->, you are encouraged to link to the <A HREF="//www.skgb.de/">Segel- und Kanugemeinschaft Bruchertalsperre</A>&nbsp;<IMG SRC="//servo.johannessen.de/countries/de.gif" WIDTH="16" HEIGHT="12" TITLE="in German" ALT="[de]" CLASS="hreflang"> or to <A HREF="//software.thaw.de/">THAWsoftware</A>. And while we&#8217;re at it, do you know a good tag-line for my <A HREF="//www.interco.info/" TITLE="interco.info" CLASS="interco">Interco</A> project? I&#8217;m giving this one some heavy thought: &#8220;<A HREF="//www.interco.info/">interco.info</A>: Bringing you no information about the International Code of Signals since 2002.&#8221; It still lacks punch, though. <A HREF="mailto:<?php echo GNgetEscapedMail('kilo@interco.info'); ?>"><IMG SRC="//www.interco.info/images/flag-small/kilo.gif" WIDTH="16" HEIGHT="12" ALT="Interco Kilo" TITLE="INTERCO Kilo"></A>.</P>
		<P>Flying has also been one of my favourite occupations for a long time. In fact, I would have liked to fly for a living as a professional pilot, but my bad eyesight has been preventing that; hence, I became interested in flight simulators. I prefer <A HREF="http://www.terminalreality.com/" TITLE="Terminal Reality">TRI</A>&#8217;s <A HREF="http://fly.godgames.com/">Fly!</A> over other products, mainly because of the ultra-realistic flight decks. (If you consider yourself an advanced simulator pilot, you may want to try <A HREF="http://www.avsim.com/pages/0899/fly_hawker/fly3.htm">starting up</A> the <A HREF="http://www.raytheonaircraft.com/hawker/800xp/hawker_800xp.shtml">Hawker 800XP</A>&#8217;s engines manually.) <A HREF="http://fly.godgames.com/">Fly!</A> also features a flexible add-on system, allowing developers to tweak many aspects of the simulator. I&#8217;ve even created some small <A HREF="/fly/">add-ons</A> myself.</P>
	</DIV>
	<DIV ID="content-belowfold">
		<P>When I&#8217;m not busy hacking away at <!--A HREF="//servo.johannessen.de/hosts/hosts.var"-->web pages<!--/A--> or driving a Piper around the virtual skies, I like to <A HREF="//www.skgb.de/">sail</A>&nbsp;<IMG SRC="//servo.johannessen.de/countries/de.gif" WIDTH="16" HEIGHT="12" TITLE="in German" ALT="[de]" CLASS="hreflang">, read, watch feature motion pictures (&#8220;not <ACRONYM TITLE="Television">TV</ACRONYM>&#8221;) and link myself to <A HREF="http://www.cyanworlds.com/">other worlds</A>. Oh yeah, and I&#8217;m studying <A HREF="http://www.informatik.uni-ulm.de/engl/">Computer Science</A> at the <A HREF="http://www.uni-ulm.de/indexen">University of Ulm</A> after sunrise. <IMG SRC="//servo.johannessen.de/emots/wink.gif" WIDTH="15" HEIGHT="15" ALT=";-)" ID="studyWink"></P>
		<!-- <P>And finally, I proudly present the obligatory <A HREF="/sitemap/">site map</A>.</P> --
		-- -> karte von der Columbia Bar in monochrome mit daraufgelegten Links in Linkfarbe (Navy-Blau). Beim rollover wird der Kartenhintergrund farbig. Echte Linklisten mit absoluter positionierung. Pacific Ocean ist "other sites" -> hosts.var --
		-- http://www.alistapart.com/articles/imagemap/ -->
	</DIV>
	<DIV ID="sidebar">
		<P>Other links:</P>
		<UL>
			<LI><A HREF="/Hotlist">Hotlist</A></LI>
			<LI><A HREF="/network/">Network</A></LI>
			<LI><A HREF="/charts/">Charts</A>&nbsp;<IMG SRC="//servo.johannessen.de/countries/de.gif" WIDTH="16" HEIGHT="12" TITLE="in German" ALT="[de]" CLASS="hreflang"></LI>
		</UL>
		<HR>
		<P><EM>Made with <A HREF="//www.apple.com/">Macintosh</A>.</EM></P>
	</DIV>
	<DIV ID="footer">
		<ADDRESS>Last modified: 23rd May 2004, <?php DMprintWebmasterLink(); ?></ADDRESS>

<!--
Side-bar links:
music/
online/
friends/ "Friends will be Friends."
Miscellaneous
-->

<!--
iCab is simply the best power user browser ever made. Absolutely nothing comes even close to its functionality. As of time of this writing, it still lacks some CSS2 support, but is otherwise just great! Many unique iCab functions like cmd-shift-click for opening a link in a new background window eventually found their way into other browser like Safari, but I still need only a few minutues with any other browser to fell the urgent need for at least one special iCab feature. And the best of it: iCab 2.9.7 works just fine on all versions of Mac OS from System 7.0.1 to Mac OS X 10.3.2!
-- --
Real-time collaborative editing with SubEthaEdit is just insanely great! It's hard to describe the uniqueness of having your co-worker's changes appear magically on your screen in your document. and being able to discuss the matter at the same time even if the co-worker is in his office at the other end of the town. If we had this application a year ago, it would have saved us days!

-- --
MS Needs to update IE for 1999 by supporting full CSS 1 and full PNG 1.0, right now!
I'd hope to see somewhat improved CSS 2, slighly more standard DOM support and general UI / API improvements [others have already mentioned] before 2010, but I remain pessimistic.
-- --
	<P>For those who care: This entire web site is web standards compliant. The pages validate as HTML 4.01 Strict and the style sheets as CSS Level 2.</P>
	<P>For those who don&#8217;t care: This entire web site is web standards compliant. The pages validate as HTML 4.01 Strict and the style sheets as CSS Level 2.</P>
-->

<!--DIV ID="icon"><IMG SRC="http://servo.johannessen.de/images/NoteAlert.png" WIDTH="64" HEIGHT="64" ALT=""></DIV>
<DIV ID="note">
	<H2>Note:</H2>
	<P>These pages are currently under active development, especially in the sitemap and regarding CSS layout and general navigation. (Remember: Your browser has a &#8220;back&#8221; button.)</P>
	<ADDRESS><A HREF="mailto:webmaster@johannessen.de">webmaster@johannessen.de</A>, 2004-02-07</ADDRESS>
</DIV-->

<!--
There's yet more to it than meets the eye!
/arne.thaw.de/
/wunschliste
/filmliste
/kjhgdkjhf1goifj2lktjelj34knfhjguih8bbj
-->
	</DIV>
<?php
printHtmlFooter();
?>
