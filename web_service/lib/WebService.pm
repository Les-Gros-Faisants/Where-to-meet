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
  $r->get( '/api/users' )		         ->to( 'fetch#get_all_user' );
  $r->get( '/api/users/:id' )		     ->to( 'fetch#get_user' );
  $r->get( '/api/users/:id/tags' )	 ->to( 'fetch#get_user_tags' );
  $r->get( '/api/users/:id/events' ) ->to( 'fetch#get_user_events' );
  $r->get( '/api/tags' )		         ->to( 'fetch#get_all_tags' );
  $r->get( '/api/events' )		       ->to( 'fetch#get_all_events' );
  $r->get( '/api/events/:id' )       ->to( 'fetch#get_event' );
  $r->get( '/api/events/:id/tags')   ->to( 'fetch#get_event_tags')
}

1;
