use Mojolicious::Lite;
use Mojo::UserAgent;

my $ua = Mojo::UserAgent->new;

get '/:foo' => sub {
  my $self = shift;
  my $foo = $self->param( 'foo' );
  $self->render( text => "hello from $foo" );
};

app->start;
