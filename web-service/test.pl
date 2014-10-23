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
    $ret{ $tmp->pseudo_user } = {
        'passwd_user' => $tmp->passwd_user,
        'id_user'  => $tmp->id_user,
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
