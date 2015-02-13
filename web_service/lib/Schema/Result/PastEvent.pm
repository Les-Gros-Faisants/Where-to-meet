use utf8;
package Schema::Result::PastEvent;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

Schema::Result::PastEvent

=cut

use strict;
use warnings;

use base 'DBIx::Class::Core';

=head1 COMPONENTS LOADED

=over 4

=item * L<DBIx::Class::InflateColumn::DateTime>

=back

=cut

__PACKAGE__->load_components("InflateColumn::DateTime");

=head1 TABLE: C<past_events>

=cut

__PACKAGE__->table("past_events");

=head1 ACCESSORS

=head2 id_event

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0

=head2 id_organizer

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 lat

  data_type: 'float'
  is_nullable: 1

=head2 lng

  data_type: 'float'
  is_nullable: 1

=head2 description_event

  data_type: 'varchar'
  is_nullable: 1
  size: 300

=head2 event_name

  data_type: 'varchar'
  is_nullable: 1
  size: 60

=head2 date_event

  data_type: 'date'
  datetime_undef_if_invalid: 1
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id_event",
  { data_type => "integer", is_auto_increment => 1, is_nullable => 0 },
  "id_organizer",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "lat",
  { data_type => "float", is_nullable => 1 },
  "lng",
  { data_type => "float", is_nullable => 1 },
  "description_event",
  { data_type => "varchar", is_nullable => 1, size => 300 },
  "event_name",
  { data_type => "varchar", is_nullable => 1, size => 60 },
  "date_event",
  { data_type => "date", datetime_undef_if_invalid => 1, is_nullable => 1 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id_event>

=back

=cut

__PACKAGE__->set_primary_key("id_event");

=head1 RELATIONS

=head2 id_organizer

Type: belongs_to

Related object: L<Schema::Result::User>

=cut

__PACKAGE__->belongs_to(
  "id_organizer",
  "Schema::Result::User",
  { id_user => "id_organizer" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "RESTRICT",
    on_update     => "RESTRICT",
  },
);

=head2 junction_event_tags

Type: has_many

Related object: L<Schema::Result::JunctionEventTag>

=cut

__PACKAGE__->has_many(
  "junction_event_tags",
  "Schema::Result::JunctionEventTag",
  { "foreign.id_event" => "self.id_event" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 junction_user_events

Type: has_many

Related object: L<Schema::Result::JunctionUserEvent>

=cut

__PACKAGE__->has_many(
  "junction_user_events",
  "Schema::Result::JunctionUserEvent",
  { "foreign.id_event" => "self.id_event" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07042 @ 2015-02-13 14:14:49
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:7vbIOs57an9/hF6+JeOH7w


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
