package WebService::Controller::Del;
use Mojo::Base 'Mojolicious::Controller';
use Mojo::Log;

my $log = Mojo::Log->new;

sub remove_user {
  my $self = shift;

  $log->debug( 'lol' );
  my $id = $self->param( 'id' );
  my $user = $self->db->resultset( 'User' )->find( {
    id_user => $id
  })->delete();
  return $self->render( text => 'ok' );
}



1;
