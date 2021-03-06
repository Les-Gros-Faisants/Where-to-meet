package WebService::Controller::Fetch;
use Mojo::Base 'Mojolicious::Controller';
use Mojo::JSON qw( encode_json );
use Mojo::Log;
use Math::Trig;
use Digest::MD5 qw( md5_hex );

use Data::Dumper;
my $log = Mojo::Log->new;


sub auth_user {
    my $self = shift;

    my $passwd = $self->param('passwd');
    my $id     = $self->param('id');
    my $user   = $self->db->resultset('User')->find( { pseudo_user => $id } );
    my %res;
    if ( defined $user ) {
	    $res{'ret'} = md5_hex($passwd, '0N est TR0P secure l0l') eq $user->passwd_user ? 'OK' : 'KO';
        $res{'id'} = $user->id_user;
    }
    else {
        $res{'ret'} = 'KO';
    }
    return $self->render( text => encode_json( \%res ) );
}

sub get_all_user {
    my $self = shift;

    my @users = $self->db->resultset('User')->all;
    my %ret;
    foreach my $tmp (@users) {
        $ret{ $tmp->id_user } = {
            'user_pseudo' => $tmp->pseudo_user,
            'mail_user'   => $tmp->mail_user,
        };
    }
    return $self->render( text => encode_json( \%ret ) );
}

sub get_user {
    my $self = shift;

    my $id         = $self->param('id');
    my $user       = $self->db->resultset('User')->find( { id_user => $id } );
    my @user_event = $self->db->resultset('JunctionUserEvent')
      ->search( { id_user => $user->id_user } )->all;
    my @tags = $self->db->resultset('Tag')->search(
        {},
        {
            select => [ 'tag_name', { count => 'id_victim' } ],
            as     => [qw / name tag_count /],
            where    => { id_victim => $id },
            group_by => [qw / tag_name /],
        }
    );

    my %ret;
    $ret{'user_pseudo'} = $user->pseudo_user;
    $ret{'mail_user'}   = $user->mail_user;
    $ret{'id_user'}     = $user->id_user;
    my %events;
    my $i = 0;

    foreach my $event (@user_event) {
        $events{ 'id_event' . $i } = {
            'id_event'        => $event->id_event->id_event,
            'lat'             => $event->id_event->lat,
            'lng'             => $event->id_event->lng,
            'desc_event'      => $event->id_event->description_event,
            'event_name'      => $event->id_event->event_name,
            'event_date'      => $event->id_event->date_event,
            'event_organizer' => $event->id_event->id_organizer->id_user,
        };
        $i++;
    }
    $ret{'events'} = \%events;

    my %tags;
    $i = 0;
    foreach my $tag (@tags) {
        $tags{ 'id_tag' . $i } = {
            'tag_name'      => $tag->get_column('name'),
            'tag_occurence' => $tag->get_column('tag_count'),
        };
        $i++;
    }
    $ret{'tags'} = \%tags;
    return $self->render( text => encode_json( \%ret ) );
}

sub get_user_tags {
    my $self = shift;

    my $id = $self->param('id');
    my %ret;
    my @tags = $self->db->resultset('Tag')->search( { id_victim => $id } )->all;
    foreach my $tag (@tags) {
        $ret{ $tag->id_tag } = {
            'tag_name'  => $tag->tag_name,
            'aggressor' => $tag->id_aggressor->id_user,
        };
    }
    return $self->render( text => encode_json( \%ret ) );
}

sub get_user_events {
    my $self = shift;

    my $id = $self->param('id');
    my %ret;
    my @events =
      $self->db->resultset('JunctionUserEvent')->search( { id_user => $id } )
      ->all;
    foreach my $event (@events) {
        $ret{ $event->id_event->id_event } = {
            'organizer' => $event->id_event->id_organizer->id_user,
            'lat'       => $event->id_event->lat,
            'lng'       => $event->id_event->lng,
            'desc'      => $event->id_event->description_event,
        };
    }
    return $self->render( text => encode_json( \%ret ) );
}

sub get_all_tags {
    my $self = shift;

    my @tags = $self->db->resultset('Tag')->all;
    my %ret;
    foreach my $tmp (@tags) {
        $ret{ $tmp->id_tag } = {
            'tag_name'     => $tmp->tag_name,
            'id_victim'    => $tmp->id_victim->id_user,
            'id_aggressor' => $tmp->id_aggressor->id_user,
        };
    }
    return $self->render( text => encode_json( \%ret ) );
}

sub get_all_events {
    my $self = shift;

    my @events = $self->db->resultset('PastEvent')->all;
    my %ret;
    foreach my $tmp (@events) {
        $ret{ $tmp->id_event } = {
            'event_name'   => $tmp->event_name,
            'id_organizer' => $tmp->id_organizer->id_user,
            'lat'          => $tmp->lat,
            'lng'          => $tmp->lng,
            'desc'         => $tmp->description_event,
        };
    }
    return $self->render( text => encode_json( \%ret ) );
}

sub get_event {
    my $self = shift;

    my $id    = $self->param('id');
    my $event = $self->db->resultset('PastEvent')->find( { id_event => $id } );
    my @users = $self->db->resultset('JunctionUserEvent')
      ->search( { id_event => $event->id_event } )->all;
    my @tags =
      $self->db->resultset('JunctionEventTag')->search( { id_event => $id } )
      ->all;
    my %ret;
    $ret{'id_event'}       = $event->id_event;
    $ret{'date_event'}     = $event->date_event;
    $ret{'event_name'}     = $event->event_name;
    $ret{'id_organizer'}   = $event->id_organizer->id_user;
    $ret{'name_organizer'} = $event->id_organizer->pseudo_user;
    $ret{'lat'}            = $event->lat;
    $ret{'lng'}            = $event->lng;
    $ret{'description'}    = $event->description_event;

    my %users;
    foreach my $tmp (@users) {
        $users{ 'id_user' . $tmp->id_user->id_user } = {
            'id_user'   => $tmp->id_user->id_user,
            'user_name' => $tmp->id_user->pseudo_user,
        };
    }
    my %tags;
    foreach my $tmp (@tags) {
        $tags{ 'id_tag' . $tmp->id_tag->id_tag } = {
            'id_tag'       => $tmp->id_tag->id_tag,
            'id_victim'    => $tmp->id_tag->id_victim->id_user,
            'id_aggressor' => $tmp->id_tag->id_aggressor->id_user,
            'tag_name'     => $tmp->id_tag->tag_name,
        };
    }
    $ret{'users'} = \%users;
    $ret{'tags'}  = \%tags;
    return $self->render( text => encode_json( \%ret ) );
}

sub get_event_radius {
    my $self = shift;

    $log->debug( Dumper( $self->param ) );
    my %coord = (
        lat => $self->param('lat'),
        lng => $self->param('long'),
    );
    my $radius = $self->param('radius');
    my @events =
      $self->db->resultset('PastEvent')->search( { 'active' => 1 } )->all;
    my %ret;
    my @tmp;
    foreach my $event (@events) {

        if (
            (
                (
                    acos(
                        sin( $event->lat * pi / 180 ) *
                          sin( $coord{lat} * pi / 180 ) +
                          cos( $event->lat * pi / 180 ) *
                          cos( $coord{lat} * pi / 180 ) *
                          cos( ( $event->lng - $coord{lng} ) * pi / 180 )
                    ) * 180 / pi * 60 * 1.1515
                )
            ) <= $radius
          )
        {
            $ret{ 'event' . $event->id_event } = {
                'id_event'       => $event->id_event,
                'date_event'     => $event->date_event,
                'event_name'     => $event->event_name,
                'id_organizer'   => $event->id_organizer->id_user,
                'organizer_name' => $event->id_organizer->pseudo_user,
                'description'    => $event->description_event,
                'lat'            => $event->lat,
                'lng'            => $event->lng
            };
        }
    }
    return $self->render( text => encode_json( \%ret ) );
}


sub get_event_tags {
    my $self = shift;

    $log->debug('coucou');
    my $id = $self->param('id');
    my @tags =
      $self->db->resultset('JunctionEventTag')->search( { id_event => $id } )
      ->all;
    my %ret;
    foreach my $tmp (@tags) {
        $ret{ $tmp->id_tag->id_tag } =
          { 'tag_name' => $tmp->id_tag->tag_name, };
    }
    return $self->render( text => encode_json( \%ret ) );
}

sub get_event_users {
    my $self = shift;

    my $id = $self->param('id');
    my @users =
      $self->db->resultset('JunctionUserEvent')->search( { id_event => $id } )
      ->all;
    my %ret;
    foreach my $tmp (@users) {
        $ret{ $tmp->id_user->id_user } =
          { 'user_name' => $tmp->id_user->pseudo_user, };
    }
    return $self->render( text => encode_json( \%ret ) );
}

1;
