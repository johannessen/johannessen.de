property theartist2 : ""
property thetitle2 : ""
property javaPath : "/usr/bin/java"
property classPath : "/Users/arne/Sites/de.johannessen.arne/music"
property className : "Music"

on connect to javaClass
	set thetitle to ""
	set theartist to ""
	tell application "System Events" to set |running| to (get name of every process) contains "iTunes"
	if |running| then -- the iTunes app is running
		tell application "iTunes"
			set |running| to player state is playing
			if |running| then -- a track is running, i. e. playing
				set thetitle to (name of current track)
				set theartist to (artist of current track)
				set thetime to (time of current track)
				set thebitrate to (bit rate of current track)
			end if
		end tell
	end if
	
	if (thetitle is not equal to thetitle2) or (theartist is not equal to theartist2) then
		--display dialog "debug: " & thetitle & "/" & thetitle2 & ", " & theartist & "/" & theartist2
		set shellPath to "cd " & classPath & "; " & javaPath & " " & javaClass
		if |running| then
			do shell script shellPath & " --title \"" & thetitle & "\" --artist \"" & theartist & "\" --time \"" & thetime & "\" --bitrate \"" & thebitrate & "\""
		else
			do shell script shellPath & " --mute"
		end if
		set theartist2 to theartist
		set thetitle2 to thetitle
	end if
end connect

on run {}
	set theartist2 to ""
	set thetitle2 to ""
	connect to className
end run

on idle
	connect to className
	return 10
end idle

on quit
	do shell script "cd " & classPath & "; " & javaPath & " " & className & " --mute"
	continue quit
end quit