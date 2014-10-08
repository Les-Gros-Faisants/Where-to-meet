use Mojolicious::Lite;
use Mojo::JSON qw( decode_json encode_json );
#use Mojo::Redis;
use DBI;

#my $redis = Mojo::Redis->new( server => '127.0.0.1:6379' );
my %hash_func = (
    get => \&rest_get,
    
);

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
    $self->render( text => $user_pseudo );
};

get '/:req' => sub { # fetch info and returns json
    my ( $self ) = @_;
};

put '/:req' => sub { # add stuff to database; gets json, parses it and add it to bd
    my ( $self ) = @_;

    $self->render( text => 'put' );
};

del '/:req' => sub { # delete :req in db
    my ( $self ) = @_;

    $self->render( text => 'post' );
};

app->start;
