#! /usr/bin/perl
# $Id: news.pl 2013-08-15 aj3 $

use strict;
use warnings;
use 5.007;

use CGI ();

#use Devel::StackTrace;
#use Carp;
#use Data::Dumper;



#print "Status: 200 OK\n";
#print "Content-Type: text/plain; charset=UTF-8\n";
#print "\n";


# get query

my $query = $ENV{QUERY_STRING};
my $cgi = CGI->new();

my $scheme = $cgi->param("scheme") || "";
my $mid = $cgi->param("message-id") || "";
my $vendor = $cgi->param("vendor") || "";
my $location;


# parse input

if ($scheme && $scheme ne "http" && $scheme ne "news" || $query !~ m/.+@.+/) {
	print "Status: 404 Not Found\n";
	print "Content-Type: text/plain; charset=UTF-8\n";
	
	print "Expires: now\n";
	print "Cache-Control: no-store, no-cache, must-revalidate\n";
	print "Pragma: no-cache\n";
	
	print "\n";
	print "We couldn't make sense out of your request.";
	exit;
}

if (! $scheme) {
	$scheme = "news";
}
if (! $mid && $cgi->param("mid")) {
	$mid = $cgi->param("mid")
}
if ($mid !~ m/.+@.+/) {
	$mid = $ENV{QUERY_STRING};
}
$mid =~ s/^(%3C|<)//i;
$mid =~ s/(%3E|>)$//i;


# generate url

if ($scheme eq "http") {
	if ($vendor eq "gmane") {
		$location = "http://mid.gmane.org/$mid";
	}
	else {  # google
		$location = "http://groups.google.com/groups?as_q=&as_oq=&as_epq=&as_eq=&as_ugroup=&as_usubject=&as_uauthors=&lr=&num=100&as_umsgid=$mid";
	}
	
}
else {  # send to NNTP client
	$location = "news:$mid";
}


# generate redirect

my $locationSafe = $location;
$locationSafe =~ s/&/&amp;/g;

print "Status: 302 Found\n";
print "Location: $location\n";
print "Content-Type: text/html; charset=UTF-8\n";

print "Expires: now\n";
print "Cache-Control: no-store, no-cache, must-revalidate\n";
print "Pragma: no-cache\n";

print "\n";
print "<!DOCTYPE html>\n";
print "<a href='$locationSafe'>$locationSafe</a>\n";


exit;
