use strict;
use warnings;
use Mojolicious::Lite;
use Mojo::JSON qw( decode_json encode_json );
use Mojo::Redis;
use WebService::Schema;
use Data::Dumper;

#my $redis = Mojo::Redis->new( server => '127.0.0.1:6379' );

my $schema = WebService::Schema->connect('DBI:mysql:database=wtm;host=127.0.0.1;port=3306', 'root', 'lol');

get '/' => sub {
  my ( $self ) = @_;

  my @user = $schema->resultset( 'User' )->all;
  return $self->render( text => $user[0]->pseudo_user );
};

get '/users' => sub { # fetch info and returns json
  my ( $self ) = @_;
  
  my @user = $schema->resultset( 'User' )->all;
  my %ret;
  foreach my $tmp ( @user ) {
    $ret{ $tmp->id_user } = {
	'user_pseudo'  => $tmp->pseudo_user,
        'passwd_user' => $tmp->passwd_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
};

get '/users/:id' => sub {
  my ( $self ) = @_;

  my $id = $self->param( 'id' );
  my $user = $schema->resultset( 'User' )->find( { id_user => $id } );
  my %ret;
  $ret{ 'user_pseudo' } = $user->pseudo_user;
  $ret{ 'passwd_user' } = $user->pseudo_user;
  return $self->render( text => encode_json( \%ret ) );
};

get '/users/:id/tags' => sub {
  my ( $self ) = @_;

  my $id = $self->param( 'id' );
  my %ret;
  my @tags = $schema->resultset( 'Tag' )->search( { id_victim => $id } )->all;
  foreach my $tag ( @tags ) {
  #  $self->app->log->debug( Dumper( $tag->id_aggressor->id_user ) );
    $ret{ $tag->id_tag } = {
 	'tag_name'  => $tag->tag_name,
        'aggressor' => $tag->id_aggressor->id_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
};

get '/users/:id/events' => sub {
  my ( $self ) = @_;

  my $id = $self->param( 'id' );
  my %ret;
  my @events = $schema->resultset( 'JunctionUserEvent' )->search( { id_user => $id } )->all;
  foreach my $event ( @events ) {
    $ret{ $event->id_event->id_event } = {
        'organizer' => $event->id_event->id_organizer->id_user,
        'geolocation' => $event->id_event->geolocation,
        'desc' => $event->id_event->description_event,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
};

get '/tags' => sub {
  my ( $self ) = @_;

  my @tags = $schema->resultset( 'Tag' )->all;
  my %ret;
  foreach my $tmp ( @tags ) {
    $ret{ $tmp->id_tag } = {
	'tag_name'  => $tmp->tag_name,
        'id_victim' => $tmp->id_victim->id_user,
        'id_aggressor' => $tmp->id_aggressor->id_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
};

get '/events' => sub {
  my ( $self ) = @_;

  my @events = $schema->resultset( 'PastEvent' )->all;
  my %ret;
  foreach my $tmp ( @events ) {
    $ret{ $tmp->id_event } = {
	'id_organizer'  => $tmp->id_organizer->id_user,
        'geolocation' => $tmp->geolocation,
        'desc' => $tmp->description_event,
    };
  }  
  return $self->render( text => encode_json( \%ret ) );
};

get '/events/:id' => sub {
  my ( $self ) = @_;

  my $id = $self->param( 'id' );
  my $event = $schema->resultset( 'PastEvent' )->find( { id_event => $id } );
  my %ret;
  $ret{ 'id_organizer' } = $event->id_organizer->id_user;
  $ret{ 'geolocation' } = $event->geolocation;
  $ret{ 'description' } = $event->description_event;
  return $self->render( text => encode_json( \%ret ) );
};

get '/events/:id/tags' => sub {
  my ( $self ) = @_;

  my $id = $self->param( 'id' );
  my @tags = $schema->resultset( 'JunctionEventTag' )->search( { id_event => $id } )->all;
  my %ret;
  foreach my $tmp ( @tags ) {
    $ret{ $tmp->id_tag->id_tag } = {
        'tag_name' => $tmp->id_tag->tag_name,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
};

put '/insert/' => sub { # add stuff to database; gets json, parses it and add it to bd
  my ( $self ) = @_;

  my $json = decode_json( $self->req->body );
  
  return $self->render( text => Dumper( $json ) );
};

del '/:req' => sub { # delete :req in db
  my ( $self ) = @_;

  return $self->render( text => 'del' );
};

app->startup;
app->start;
