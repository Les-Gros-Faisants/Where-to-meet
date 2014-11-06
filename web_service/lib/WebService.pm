package WebService;
use Mojo::Base 'Mojolicious';
use Schema;

has schema => sub {
  return Schema->connect(
      'DBI:mysql:database=wtm;host=127.0.0.1;port=3306',
      'root',
      'lol'
  );
};

# This method will run once at server start
sub startup {
  my $self = shift;

  $self->plugin( 'PODRenderer' );
  $self->helper( 'db' => sub { shift->app->schema } );

  my $r = $self->routes;
  # Get Routes
  $r->get( '/api/users' )		                 ->to( 'fetch#get_all_user' );
  $r->get( '/api/users/:id' )		             ->to( 'fetch#get_user' );
  $r->get( '/api/users/:id/tags' )	         ->to( 'fetch#get_user_tags' );
  $r->get( '/api/users/:id/events' )         ->to( 'fetch#get_user_events' );
  $r->get( '/api/tags' )		                 ->to( 'fetch#get_all_tags' );
  $r->get( '/api/events' )		               ->to( 'fetch#get_all_events' );
  $r->get( '/api/events/:id' )               ->to( 'fetch#get_event' );
  $r->get( '/api/events/:id/tags')           ->to( 'fetch#get_event_tags' );
  $r->get( '/api/events/:id/users')          ->to( 'fetch#get_event_users' );

  # Put Routes
  $r->put( '/api/insert/users' )             ->to( 'insert#add_user' );
  $r->put( '/api/insert/users/:id' )         ->to( 'insert#update_user' );
  $r->put( '/api/insert/users/:id/passwd' )  ->to( 'insert#update_user_passwd' );
  $r->put( '/api/insert/users/:id/username' )->to( 'insert#update_user_pseudo' );
  $r->put( '/api/insert/tags' )              ->to( 'insert#add_tag' );
  $r->put( '/api/insert/events' )            ->to( 'insert#add_event' );
}

1;
