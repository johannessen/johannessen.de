// Music.java
// (c) 2003-2004 by Arne Johannessen
// mailto:webmaster@johannessen.de
// last modified 2004-01-26

import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;



public class Music {
	
	public static final String URL = "http://arne.johannessen.de/music/nowplaying.php";
	public static final String ENCODING = "utf-8";
	
	public static final String QUERY_TITLE = "title";
	public static final String QUERY_ARTIST = "artist";
	public static final String QUERY_TIME = "time";
	public static final String QUERY_BITRATE = "bitrate";
	public static final String QUERY_MUTE = "mute";
	
	public static final String QUERY_METHOD = "method";
	public static final String METHOD = "post";
	
	public static final int VERSION = 2;
	
	/** Singleton Design Pattern */
	private static Music instance = null;
	
	/** Singleton Design Pattern */
	private Music () {
		Music.instance = this;
	}
	
	/** Singleton Design Pattern */
	public static Music getInstance () {
		if (Music.instance == null) {
			new Music();
		}
		return Music.instance;
	}
	
	
	private void connect (String title, String artist, String time, String bitrate, boolean mute) throws Exception {
		// create the HTTP query string
		String query = QUERY_METHOD+"="+METHOD;
		if (mute) {
			query += "&"+QUERY_MUTE+"="+QUERY_MUTE;
		}
		else if (title != null || artist != null || time != null || bitrate != null) {
			if (title.length() > 0) {
				query += "&"+Music.QUERY_TITLE+"="+URLEncoder.encode(title, Music.ENCODING);
			}
			if (artist.length() > 0) {
				query += "&"+Music.QUERY_ARTIST+"="+URLEncoder.encode(artist, Music.ENCODING);
			}
			if (time.length() > 0) {
				query += "&"+Music.QUERY_TIME+"="+URLEncoder.encode(time, Music.ENCODING);
			}
			if (bitrate.length() > 0) {
				query += "&"+Music.QUERY_BITRATE+"="+URLEncoder.encode(bitrate, Music.ENCODING);
			}
		}
		
		// create the URL
		if (METHOD == "get" || METHOD == "head") {
			URL url = new URL(Music.URL+'?'+query);
			(new BufferedReader(new InputStreamReader(url.openStream()))).close();
		}
		else {  // POST
			HttpURLConnection connection = (HttpURLConnection)(new URL(Music.URL)).openConnection();
			connection.setDoOutput(true);
			PrintWriter writer = new PrintWriter(connection.getOutputStream());
			writer.println(query);
			writer.close();
			BufferedReader replyReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String replyLine;
			while ((replyLine = replyReader.readLine()) != null) {
				System.out.println(replyLine);
			}
			connection.disconnect();
		}
	}
	
	
	public static void main (String[] args) {
		StringBuffer title = new StringBuffer();
		StringBuffer artist = new StringBuffer();
		StringBuffer time = new StringBuffer();
		StringBuffer bitrate = new StringBuffer();
		StringBuffer current = title;
		boolean mute = false;
		
		// parse command line arguments
		for (int index = 0; index < args.length; index++) {
			if (args[index].equalsIgnoreCase("--"+QUERY_MUTE)) {
				mute = ! mute;
			}
			else if (args[index].equalsIgnoreCase("--"+QUERY_TITLE)) {
				current = title;
			}
			else if (args[index].equalsIgnoreCase("--"+QUERY_ARTIST)) {
				current = artist;
			}
			else if (args[index].equalsIgnoreCase("--"+QUERY_TIME)) {
				current = time;
			}
			else if (args[index].equalsIgnoreCase("--"+QUERY_BITRATE)) {
				current = bitrate;
			}
			else {
				if (current.length() > 0) {
					current.append(" ");
				}
				current.append(args[index]);
			}
		}
		
		if (title.toString().equals("missing value")) {
			title = new StringBuffer();
		}
		if (artist.toString().equals("missing value")) {
			artist = new StringBuffer();
		}
		if (time.toString().equals("missing value")) {
			time = new StringBuffer();
		}
		if (bitrate.toString().equals("missing value")) {
			bitrate = new StringBuffer();
		}
		
		try {
			Music.getInstance().connect(title.toString(), artist.toString(), time.toString(), bitrate.toString(), mute);
		}
		catch (Exception e) {
			// ignore all errors: quiet operation
		}
	}
	
}
