use Mojolicious::Lite;
use Mojo::UserAgent;

my $ua = Mojo::UserAgent->new;

get '/' => { text => 'i <3 Mojolicious' };

app->start;
