package WebService::Controller::Fetch;
use Mojo::Base 'Mojolicious::Controller';
use Mojo::JSON qw( encode_json );
use Mojo::Log;

use Data::Dumper;

my $log = Mojo::Log->new;

sub get_all_user {
  my $self = shift;

  my @users = $self->db->resultset( 'User' )->all;
  my %ret;
  foreach my $tmp ( @users ) {
    $ret{ $tmp->id_user } = {
       'user_pseudo' => $tmp->pseudo_user,
       'passwd_user' => $tmp->passwd_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
}

sub get_user {
  my $self = shift;

  my $id = $self->param( 'id' );
  my $user = $self->db->resultset( 'User' )->find( { id_user => $id } );
  my %ret;
  $ret{ 'user_pseudo' } = $user->pseudo_user;
  $ret{ 'passwd_user' } = $user->passwd_user;
  return $self->render( text => encode_json( \%ret ) );
}

sub get_user_tags {
  my $self = shift;

  my $id = $self->param( 'id' );
  my %ret;
  my @tags = $self->db->resultset( 'Tag' )->search( { id_victim => $id } )->all;
  foreach my $tag ( @tags ) {
    $ret{ $tag->id_tag } = {
	 'tag_name' => $tag->tag_name,
	 'aggressor' => $tag->id_aggressor->id_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
}

sub get_user_events {
  my $self = shift;

  my $id = $self->param( 'id' );
  my %ret;
  my @events = $self->db->resultset( 'JunctionUserEvent' )->search( { id_user => $id } )->all;
  foreach my $event ( @events ) {
    $ret{ $event->id_event->id_event } = {
	'organizer' => $event->id_event->id_organizer->id_user,
        'geolocation' => $event->id_event->geolocation,
	'desc' => $event->id_event->description_event,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
}

sub get_all_tags {
  my $self = shift;

  my @tags = $self->db->resultset( 'Tag' )->all;
  my %ret;
  foreach my $tmp ( @tags ) {
    $log->debug( Dumper( $tmp ) );
    $ret{ $tmp->id_tag } = {
        'tag_name' => $tmp->tag_name,
        'id_victim' => $tmp->id_victim->id_user,
        'id_aggressor' => $tmp->id_aggressor->id_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
}

sub get_all_events {
  
}

1;
