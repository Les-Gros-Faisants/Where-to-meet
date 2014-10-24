use strict;
use warnings;
use Mojolicious::Lite;
use Mojo::JSON qw( encode_json );
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
    $self->app->log->debug( Dumper( $tag->id_aggressor->id_user ) );
    $ret{ $tag->id_tag } = {
 	'tag_name'  => $tag->tag_name,
        'aggressor' => $tag->id_aggressor->id_user,
    };
  }
  return $self->render( text => encode_json( \%ret ) );
};

put '/:req' => sub { # add stuff to database; gets json, parses it and add it to bd
  my ( $self ) = @_;

  return $self->render( text => 'put' );
};

del '/:req' => sub { # delete :req in db
  my ( $self ) = @_;

  return $self->render( text => 'post' );
};

app->startup;
app->start;

__DATA__

@@ debug.html.ep
  %= t h1 => 'debug !!'
    helper returns = <%= $ret %>
