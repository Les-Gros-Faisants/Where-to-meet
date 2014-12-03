package WebService::Controller::Index;
use Mojo::Base 'Mojolicious::Controller';

sub intro {
  my $self = shift;
  
  return $self->render( text => 'coucou' );
}

1;
