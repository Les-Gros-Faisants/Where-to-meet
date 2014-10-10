use Mojolicious::Lite;
use Mojo::JSON qw( decode_json encode_json );
#use Mojo::Redis;
use DBI;
use Data::Dumper;

#my $redis = Mojo::Redis->new( server => '127.0.0.1:6379' );

get '/' => sub {
    my ( $self ) = @_;

    my $dsn = 'DBI:mysql:database=wtm;host=127.0.0.1;port=3306';
    my $user = 'root';
    my $pass = '';
    my $dbh =  DBI->connect( $dsn, $user, $pass, { RaiseError => 1} )
	|| die "Couldn't connect to database: " . DBI->errstr;

    my $sth = $dbh->prepare( 'SELECT pseudo_user FROM users' );
    $sth->execute;

    my $tmp = $sth->fetchrow_arrayref;
    my $user_pseudo = @$tmp[0];
    return $self->render( text => $user_pseudo );
};

get '/#hash1/#json2/#timestamp3' => sub { # fetch info and returns json
    my ( $self ) = @_;

    my ( $hash, $json, $timestamp ) = ( 
	$self->param( 'hash1' ),
	decode_json( $self->param( 'json2' ) ),
        $self->param( 'timestamp3' )
    );

    my $ret = '';
    my $key;
    my $val;
    my $key_sort;
    my $sort_order;
    my $limit;
    for my $prim_key ( keys %$json ) { # get first key, ie: name of the hash
      $self->app->log->debug( $prim_key );
      for my $key ( keys $json->{ $prim_key } ) {
	if ( $key =~ /(query[0-9]{1, 2}|query)/ ) { 
	  $self->app->log->debug( Dumper $json->{ $prim_key }->{ $key } );
	  if ( exists $json->{ $prim_key }->{ $key }->{ search_key } ) {
	    $key = $json->{ $prim_key }->{ $key }->{ search_key }[0];
	    $val = $json->{ $prim_key }->{ $key }->{ search_key }[1];
	    $self->app->log->debug( 'key/value = ' . $key . '/' . $val );
	  }
	  if ( exists $json->{ $prim_key }->{ $key }->{ sort } ) {
	    $key_sort = $json->{ $prim_key }->{ $key }->{ sort }[0];
	    $sort_order = $json->{ $prim_key }->{ $key }->{ sort }[1];
	    $self->app->log->debug( 'sort = ' . $key_sort . '/' . $sort_order );
	  }
	  if (  exists $json->{ $prim_key }->{ $key }->{ limit } ) {
	    $limit = $json->{ $prim_key }->{ $key }->{ limit };
	    $self->app->log->debug( 'limit = ' . $limit );
	  }
	}
      }
    }
    return $self->render( text => Dumper $json );
};

put '/:req' => sub { # add stuff to database; gets json, parses it and add it to bd
    my ( $self ) = @_;

    return $self->render( text => 'put' );
};

del '/:req' => sub { # delete :req in db
    my ( $self ) = @_;

    return $self->render( text => 'post' );
};

app->start;

__DATA__

@@ debug.html.ep
  %= t h1 => 'debug !!'
    helper returns = <%= $ret %>
