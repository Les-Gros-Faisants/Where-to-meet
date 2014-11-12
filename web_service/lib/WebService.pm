package WebService;
use Mojo::Base 'Mojolicious';
use Crypt::OpenSSL::RSA;
use Schema;
use AuthDB;

use Data::Dumper;
use Mojo::Log;

my $log = Mojo::Log->new;

has schema => sub {
  my %auth = AuthDB::get_auth();
  return Schema->connect(
      'DBI:mysql:database=wtm;host=127.0.0.1;port=3306',
      $auth{ username },
      $auth{ passwd },
  );
};

# This method will run once at server start
sub startup {
  my $self = shift;

  $self->helper( 'db' => sub { shift->app->schema } );

  my $r = $self->routes;
  my $auth = $r->under( '/api' => sub {
    my $self = shift;

    $log->debug( "body = " . Dumper( $self->req->body ) );
    my $passwd = qx( echo "$self->req->body" | openssl base64 -d | openssl rsautl -decrypt -inkey private.key );
    my $serv_hash = $self->db->resultset( 'ApiUser' )->find( { id_user => 1 } );
    $serv_hash->passwd_user =~ /(?<=\$[1-6]\$)(?<salt>.+?)(?=\$)/s;
    my $client_hash = qx( openssl passwd -1 -salt $+{salt} $passwd);
    return 1 if $client_hash eq $serv_hash;
    $self->render( text => 'you suck' );
    return undef;
  });

  # Get Routes
  $auth->get( '/users' )		          ->to( 'fetch#get_all_user' );
  $auth->get( '/users/:id' )		      ->to( 'fetch#get_user' );
  $auth->get( '/users/:id/tags' )	    ->to( 'fetch#get_user_tags' );
  $auth->get( '/users/:id/events' )   ->to( 'fetch#get_user_events' );
  $auth->get( '/tags' )		            ->to( 'fetch#get_all_tags' );
  $auth->get( '/events' )		          ->to( 'fetch#get_all_events' );
  $auth->get( '/events/:id' )         ->to( 'fetch#get_event' );
  $auth->get( '/events/:id/tags')     ->to( 'fetch#get_event_tags' );
  $auth->get( '/events/:id/users')    ->to( 'fetch#get_event_users' );

  # Put Routes
  $auth->put( '/users' )              ->to( 'insert#add_user' );
  $auth->put( '/users/:id' )          ->to( 'insert#update_user' );
  $auth->put( '/users/:id/passwd' )   ->to( 'insert#update_user_passwd' );
  $auth->put( '/users/:id/username' ) ->to( 'insert#update_user_pseudo' );
  $auth->put( '/tags' )               ->to( 'insert#add_tag' );
  $auth->put( '/events' )             ->to( 'insert#add_event' );
  $auth->put( '/events/:id/user' )    ->to( 'insert#add_event_user' );
  $auth->put( '/events/:id/tags' )    ->to( 'insert#add_event_tags' );

  # Delete Routes
  $auth->delete( '/users/:id' )       ->to( 'del#remove_user' );
}

1;
