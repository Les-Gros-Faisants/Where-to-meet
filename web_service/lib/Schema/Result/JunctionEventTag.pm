use utf8;
package Schema::Result::JunctionEventTag;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

Schema::Result::JunctionEventTag

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

=head1 TABLE: C<junction_event_tag>

=cut

__PACKAGE__->table("junction_event_tag");

=head1 ACCESSORS

=head2 id_event

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 id_tag

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=cut

__PACKAGE__->add_columns(
  "id_event",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "id_tag",
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

=head2 id_tag

Type: belongs_to

Related object: L<Schema::Result::Tag>

=cut

__PACKAGE__->belongs_to(
  "id_tag",
  "Schema::Result::Tag",
  { id_tag => "id_tag" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "RESTRICT",
    on_update     => "RESTRICT",
  },
);


# Created by DBIx::Class::Schema::Loader v0.07042 @ 2015-02-04 11:50:13
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:1Ydr6GahoUC/HIlpKpwAhA


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
