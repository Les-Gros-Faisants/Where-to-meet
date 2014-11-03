package WebService::Controller::Insert;
use Mojo::Base 'Mojolicious::Controller';
use Mojo::JSON qw( decode_json );
use Mojo::Log;

my $log = Mojo::Log->new;

sub add_user {
  my $self = shift;

  my $json = decode_json( $self->req->body );
  my $users = $self->db->resultset( 'User' );
  my $ret = $user->update_or_create( {
    passwd_user => $json->{ passwd },
    pseudo_user => $json->{ username }
  });
  return $self->render( text => $ret->id_user );
}

sub update_user {
  my $self = shift;
  
}
1;
