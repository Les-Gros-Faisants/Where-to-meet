use utf8;
package Schema::Result::JunctionUserEvent;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

Schema::Result::JunctionUserEvent

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

=head1 TABLE: C<junction_user_event>

=cut

__PACKAGE__->table("junction_user_event");

=head1 ACCESSORS

=head2 id_event

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 id_user

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id_event",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "id_user",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
);

=head1 RELATIONS

=head2 id_event

Type: belongs_to

Related object: L<Schema::Result::PastEvent>

=cut

__PACKAGE__->belongs_to(
  "id_event",
  "Schema::Result::PastEvent",
  { id_event => "id_event" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "RESTRICT",
    on_update     => "RESTRICT",
  },
);

=head2 id_user

Type: belongs_to

Related object: L<Schema::Result::User>

=cut

__PACKAGE__->belongs_to(
  "id_user",
  "Schema::Result::User",
  { id_user => "id_user" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "RESTRICT",
    on_update     => "RESTRICT",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07042 @ 2015-02-04 11:50:13
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:fnuaRtb1RxHM7Tr9+jMEcg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
