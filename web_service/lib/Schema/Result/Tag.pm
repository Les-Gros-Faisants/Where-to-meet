use utf8;
package Schema::Result::Tag;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

Schema::Result::Tag

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

=head1 TABLE: C<tags>

=cut

__PACKAGE__->table("tags");

=head1 ACCESSORS

=head2 id_tag

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0

=head2 id_aggressor

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 id_victim

  data_type: 'integer'
  is_foreign_key: 1
  is_nullable: 1

=head2 tag_name

  data_type: 'varchar'
  is_nullable: 1
  size: 30

=cut

__PACKAGE__->add_columns(
  "id_tag",
  { data_type => "integer", is_auto_increment => 1, is_nullable => 0 },
  "id_aggressor",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "id_victim",
  { data_type => "integer", is_foreign_key => 1, is_nullable => 1 },
  "tag_name",
  { data_type => "varchar", is_nullable => 1, size => 30 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id_tag>

=back

=cut

__PACKAGE__->set_primary_key("id_tag");

=head1 RELATIONS

=head2 id_aggressor

Type: belongs_to

Related object: L<Schema::Result::User>

=cut

__PACKAGE__->belongs_to(
  "id_aggressor",
  "Schema::Result::User",
  { id_user => "id_aggressor" },
  {
    is_deferrable => 1,
    join_type     => "LEFT",
    on_delete     => "RESTRICT",
    on_update     => "RESTRICT",
  },
);

=head2 id_victim

Type: belongs_to

Related object: L<Schema::Result::User>

=cut

__PACKAGE__->belongs_to(
  "id_victim",
  "Schema::Result::User",
  { id_user => "id_victim" },
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
  { "foreign.id_tag" => "self.id_tag" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07042 @ 2015-02-04 11:50:13
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:kmWWCBFvgHaqwfiSYVqt6Q


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
