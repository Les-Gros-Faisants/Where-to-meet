package WebService::Controller::Insert;
use Mojo::Base 'Mojolicious::Controller';
use Mojo::JSON qw( encode_json );
use Mojo::Log;

use Data::Dumper;
use Data::Dumper;

my $log = Mojo::Log->new;

sub add_user {
    my $self = shift;

    my $user = $self->db->resultset('User');
    my $ret  = $user->update_or_create(
        {
            passwd_user => $self->req->param('passwd'),
            pseudo_user => $self->req->param('username'),
            mail_user   => $self->req->param('mail'),
        }
    );
    my %res = ( ret => 'OK', new_id => $ret->id_user );
    return $self->render( text => encode_json( \%res ) );
}

sub update_user {
    my $self = shift;

    my $id   = $self->param('id');
    my $user = $self->db->resultset('User');
    my $ret  = $user->update_or_create(
        {
            id_user     => $id,
            passwd_user => $self->req->param('passwd'),
            pseudo_user => $self->req->param('username'),
            mail_user   => $self->req->param('mail'),
        }
    );
    $log->debug($ret);
    my %res = ( ret => 'OK' );
    return $self->render( text => encode_json( \%res ) );
}

sub update_user_passwd {
    my $self = shift;

    my $id   = $self->param('id');
    my $user = $self->db->resultset('User')->find(
        {
            id_user => $id
        }
    );
    my $ret = $user->update(
        {
            password_user => $self->req->param->('passwd'),
        }
    );
    my %res = ( ret => 'OK' );
    return $self->render( text => encode_json( \%res ) );
}

sub update_user_pseudo {
    my $self = shift;

    my $id   = $self->param('id');
    my $user = $self->db->resultset('User')->find(
        {
            id_user => $id
        }
    );
    my $ret = $user->update(
        {
            pseudo_user => $self->req->param('username'),
        }
    );
    my %res = ( ret => 'OK' );
    return $self->render( text => encode_json( \%res ) );
}

sub add_tag {
    my $self = shift;

    my $tag = $self->db->resultset('Tag');
    my $ret = $tag->create(
        {
            tag_name     => $self->req->param('tag_name'),
            id_victim    => $self->req->param('id_victim'),
            id_aggressor => $self->req->param('id_aggressor'),
        }
    );
    my %res = ( ret => 'OK' );
    return $self->render( text => encode_json( \%res ) );
}

sub add_event {
    my $self = shift;

    $log->debug( $self->req->param('date') );
    my $event = $self->db->resultset('PastEvent');
    my $ret   = $event->create(
        {
            event_name        => $self->req->param('event_name'),
            description_event => $self->req->param('event_desc'),
            id_organizer      => $self->req->param('id_organizer'),
            lat               => $self->req->param('lat'),
            lng               => $self->req->param('lng'),
            timeout           => $self->req->param('timeout')
        }
    );
    my $jnevent = $self->db->resultset('JunctionUserEvent');
    $ret = $jnevent->update_or_create(
        {
            id_event => $event->get_column('id_event')->max,
            id_user  => $self->req->param('id_organizer'),
        }
    );
    my %res = ( ret => 'OK', new_event => $ret->id_event->id_event );
    return $self->render( text => encode_json( \%res ) );
}

sub add_event_user {
    my $self = shift;

    my $id    = $self->param('id');
    my $event = $self->db->resultset('JunctionUserEvent');
    my $ret   = $event->create(
        {
            id_event => $id,
            id_user  => $self->req->param('id_user'),
        }
    );
    my %res = ( ret => 'OK' );
    return $self->render( text => encode_json( \%res ) );
}

sub add_event_tags {
    my $self = shift;

    my $json = decode_json( $self->req->body );
    my $id   = $self->param('id');
    $log->debug( Dumper($json) );
    for my $i ( 0 .. 15 ) {
        if ( $json->{tags}[$i] ne '__stop__' ) {
            my $tag = $self->db->resultset('Tag')->find(
                {
                    tag_name => { like => $json->{tags}[$i] }
                }
            );
            $log->debug($tag);
            my $eventag =
              $self->db->resultset('JunctionEventTag')->update_or_create(
                {
                    id_tag   => $tag->id_tag,
                    id_event => $id,
                }
              );
        }
        last if ( $json->{tags}[$i] eq '__stop__' );
    }
    return $self->render( text => 'ok' );
}

1;
