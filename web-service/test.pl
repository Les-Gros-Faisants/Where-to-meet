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

    for my $key ( keys %$json ) {
      $self->app->log->debug( $key );
      
    }
    return $self->render( 'debug', ret => Dumper $json );
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
    helper resturns = <%= $ret %>
