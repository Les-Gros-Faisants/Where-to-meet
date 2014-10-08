use Mojolicious::Lite;
use Mojo::Redis;
use DBI;

my $redis = Mojo::Redis->new( server => '127.0.0.1:6379' );

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

get '/:method/:req' => sub {
  my ( $self ) = @_;

  my $method = $self->param( 'method' );
  $self->render( text => "$method method" );
};

app->start;
