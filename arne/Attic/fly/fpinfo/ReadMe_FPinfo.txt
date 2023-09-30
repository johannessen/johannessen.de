FPinfo 1.1
Flightplan info for Flyscripts! by Anthony Merton

(c) 2001 Arne Johannessen
mailto:ajohannessen@mac.com
Freeware!  :-)


This little script displays your flight plan while flying. Now the
lazy people (like myself ;-) ) don't need to write it down or even
interrupt the flight anymore -- just press a key to get the heading
you have to fly after reaching the next waypoint. Simple!


System requirements:
This script runs on any Mac or PC running Fly! with Flyscripts! 1.2.1
by Anthony Merton. I think older versions of Flyscripts can be used as
well, but I've only tested it with 1.2.1 on a Mac.

Installation:
1) Unless you already have Flyscripts!, go and get the latest version
from the AVSIM library!
2) Download the FPinfo package and unzip it (as you're reading this,
I suppose you've done this step already :-) ).
3) Move the unzipped FPinfoMac.fsc file (or FPinfoPC.fsc, depending
on your system) to your Flyscripts! folder. You're done!

Usage:
1) Start Fly!, set up a flight plan and -- according to the
Flyscripts! manual -- save it as "fp" (the resulting file *must* be
named "fp.rio").
2) Get into your plane and activate Flyscripts! -- usually by
pressing ALT F12.
FPinfo is now ready.
- In order to display information on the next waypoint, press ALT_=
(on the US-keyboard, the = key is to the left of the backspace key).
- In order to display information on the previous waypoint, press
ALT_- (on the US-keyboard, the - key is to the right of the zero key).
- In order to display information on the same waypoint again, press
ALT_[ (on the US-keyboard, the [ key is just below the two other
keys).
A string like "Leg 5: WARBURG (6000ft) to MOHNE: 261° 40.7nm" (Mac: ¡
instead of °) is being displayed in the lower left corner of the
screen.

Troubleshooting:
If you've got problems using this script, make sure that
1) Your Fly! system is patched to version 1.1.88 (SDK)
2) Flyscripts! is installed (perhaps try using version 1.2.1),
3) flyscripts.dll is inside your Modules:Mac: or Modules\PC\ folder,
4) FPinfo.fsc is inside your Flyscripts! folder,
5) you've saved your flight plan as "fp", resulting in the file name
"fp.rio" (type "fp" when prompted for a file name, omitting the
".rio"),
6) you've activated Flyscripts! (usually ALT F12, consult your
Flyscripts! manual).
If your problem can't be solved by these tips, send me email or post
your question at AVSIMs General Fly! Forum. The URL is:
http://www.avsim.com/cgi-bin/dcforum/dcboard.cgi?az=list&forum=DCForumID4

Version history:
1.1:
- changed redisplay key to ALT_[ for compatibility with APkeys
- now there is an error message if there is no flight plan available
- degree character bug fixed, there are different Mac(¡) and PC(°)
versions now
- speed is correctly displayed
- heading is always reported using three digits
1.0:
- initial release


Enjoy! :-)

Arne
