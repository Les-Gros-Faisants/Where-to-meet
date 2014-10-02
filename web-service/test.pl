use Mojolicious::Lite;
use Mojo::Redis;

my $redis = Mojo::Redis->new( server => '127.0.0.1:6379' );

get '/' => sub {
  my ( $self ) = @_;

  $self->render( text => "coucou" );
};

get '/:foo' => sub {
  my ( $self ) = @_;

  my $foo = $self->param( 'foo' );
  $self->render( text => "hello from $foo" );
};

app->start;
