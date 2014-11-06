package WebService::Controller::Insert;
use Mojo::Base 'Mojolicious::Controller';
use Mojo::JSON qw( decode_json );
use Mojo::Log;

my $log = Mojo::Log->new;

sub add_user {
  my $self = shift;

  my $json = decode_json( $self->req->body );
  my $user = $self->db->resultset( 'User' );
  my $ret = $user->update_or_create( {
    passwd_user => $json->{ passwd },
    pseudo_user => $json->{ username },
  } );
  return $self->render( text => $ret->id_user );
}

sub update_user {
  my $self = shift;

  my $id = $self->param( 'id' );
  my $json = decode_json( $self->req->body );
  my $user = $self->db->resultset( 'User' );
  my $ret = $user->update_or_create( {
    id_user => $id,
    passwd_user => $json->{ passwd },
    pseudo_user => $json->{ username },
  } );
  $log->debug( $ret );
  return $self->render( text => 'ok' );
}

sub update_user_passwd {
  my $self = shift;

  my $id = $self->param( 'id' );
  my $json = decode_json( $self->req->body );
  my $user = $self->db->resultset( 'User' )->find( {
    id_user => $id
  } );
  my $ret = $user->update( {
    password_user => $json->{ passwd }
  } );
  return $self->render( text => 'ok' );
}

sub update_user_pseudo {
  my $self = shift;

  my $id = $self->param( 'id' );
  my $json = decode_json( $self->req->body );
  my $user = $self->db->resultset( 'User' )->find( {
    id_user => $id
  } );
  my $ret = $user->update( {
    pseudo_user => $json->{ username }
  } );
  return $self->render( text => 'ok' );
}

sub add_tag {
  my $self = shift;

  my $json = decode_json( $self->req->body );
  my $tag = $self->db->resultset( 'Tag' );
  my $ret = $tag->create( {
    tag_name => $json->{ tag_name },
    id_victim => $json->{ id_victim },
    id_aggressor => $json->{ id_aggressor },
  });
  return $self->render( text => 'ok' );
}

sub add_event {
  my $self = shift;

  my $json = decode_json( $self->req->body );
  my $event = $self->db->resultset( 'PastEvent' );
  my $ret = $event->create( {
    event_name => $json->{ event_name },
    decription_event => $json->{ desc },
    id_organizer => $json->{ id_organize },
    geolocation => $json->{ geolocation },
  });
  return $self->render( text => 'ok' );
}

sub add_tmp_event {
  my $self = shift;

  my $json = decode_json( $self->req->body );
  
}

1;
