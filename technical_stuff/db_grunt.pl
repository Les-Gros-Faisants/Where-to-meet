#! /usr/bin/env perl

use strict;
use warnings;
use DBI;

$SIG{INT}  = \&clean_exit;
$SIG{KILL} = \&clean_exit;

my $dbh = DBI->connect( 'dbi:mysql:database=wtm:host=localhost',
    'root', 'Pangea/poil.21' )
  || die "Can't connect to db: $DBI::errstr";

sub clean_exit {
    $dbh->disconnect
      || die "Disconnection error: $DBI::errstr\n";
	exit 1;
}

while (42) {
    my $sth = $dbh->do(
        'UPDATE past_events SET active=false WHERE now() - date_event > timeout');
    sleep(5);
}
