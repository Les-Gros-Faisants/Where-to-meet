package WebService;
use Mojo::Base 'Mojolicious';
use Schema;
use Mojo::Redis2;

has schema => sub {
  return Schema->connect(
      'DBI:mysql:database=wtm;host=127.0.0.1;port=3306',
      'root',
      'lol'
  );
};

has redis => sub {
    return Mojo::Redis2->new( url => 'redis://localhost:6379' );
};


# This method will run once at server start
sub startup {
  my $self = shift;

  $self->plugin( 'PODRenderer' );
  $self->helper( 'db' => sub { shift->app->schema } );
  $self->helper( 'redis' => sub { shift->app->redis } );

  my $r = $self->routes;
  # Get Routes
  $r->get( '/api/users' )		          ->to( 'fetch#get_all_user' );
  $r->get( '/api/users/:id' )		      ->to( 'fetch#get_user' );
  $r->get( '/api/users/:id/tags' )	  ->to( 'fetch#get_user_tags' );
  $r->get( '/api/users/:id/events' )  ->to( 'fetch#get_user_events' );
  $r->get( '/api/tags' )		          ->to( 'fetch#get_all_tags' );
  $r->get( '/api/events' )		        ->to( 'fetch#get_all_events' );
  $r->get( '/api/events/:id' )        ->to( 'fetch#get_event' );
  $r->get( '/api/events/:id/tags')    ->to( 'fetch#get_event_tags' );
  $r->get( '/api/events/:id/users')   ->to( 'fetch#get_event_users' );

  # Put Routes
  $r->put( '/api/users' )             ->to( 'insert#add_user' );
  $r->put( '/api/users/:id' )         ->to( 'insert#update_user' );
  $r->put( '/api/users/:id/passwd' )  ->to( 'insert#update_user_passwd' );
  $r->put( '/api/users/:id/username' )->to( 'insert#update_user_pseudo' );
  $r->put( '/api/tags' )              ->to( 'insert#add_tag' );
  $r->put( '/api/events' )            ->to( 'insert#add_event' );
  $r->put( '/api/tmpevent' )          ->to( 'insert#add_tmp_event' );

  # Delete Routes
  $r->delete( '/api/users/:id' )      ->to( 'del#remove_user' );
}

1;
