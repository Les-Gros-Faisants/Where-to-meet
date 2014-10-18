use Mojolicious::Lite;
use Mojo::JSON qw( decode_json encode_json );
#use Mojo::Redis;
use DBI;
use Data::Dumper;

#my $redis = Mojo::Redis->new( server => '127.0.0.1:6379' );

my $dsn = 'DBI:mysql:database=wtm;host=127.0.0.1;port=3306';
my $user = 'root';
my $pass = '';
my $dbh =  DBI->connect( $dsn, $user, $pass, { RaiseError => 1} )
  || die "Couldn't connect to database: " . DBI->errstr;

get '/' => sub {
  my ( $self ) = @_;

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

  my $ret;
  my @kvs;
  my @sort;
  my $limit;
  for my $prim_key ( keys %$json ) { # get first key, ie: name of the hash
    $self->app->log->debug( $prim_key );
    for my $key ( keys $json->{ $prim_key } ) {
      if ( $key =~ /(query[0-9]{1, 2}|query)/ ) {
	@kvs = $json->{ $prim_key }->{ $key }->{ search_key } if exists $json->{ $prim_key }->{ $key }->{ search_key };
	@sort = $json->{ $prim_key }->{ $key }->{ sort } if exists $json->{ $prim_key }->{ $key }->{ sort };
	$limit = $json->{ $prim_key }->{ $key }->{ limit } if exists $json->{ $prim_key }->{ $key }->{ limit };
	$self->app->log->debug( $prim_key . $key . ': kvs = ' . $kvs[0][0] . '/' . $kvs[0][1] . ' sort = ' . $sort[0][0] . '/' . $sort[0][1] . ' limit = ' . $limit );
      }
      delete $json->{ $prim_key }->{ $key };
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

app->startup;
app->start;

__DATA__

@@ debug.html.ep
  %= t h1 => 'debug !!'
    helper returns = <%= $ret %>
