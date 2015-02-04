use utf8;
package Schema::Result::User;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

Schema::Result::User

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

=head1 TABLE: C<users>

=cut

__PACKAGE__->table("users");

=head1 ACCESSORS

=head2 id_user

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0

=head2 pseudo_user

  data_type: 'varchar'
  is_nullable: 1
  size: 20

=head2 passwd_user

  data_type: 'varchar'
  is_nullable: 1
  size: 30

=head2 mail_user

  data_type: 'varchar'
  is_nullable: 1
  size: 40

=cut

__PACKAGE__->add_columns(
  "id_user",
  { data_type => "integer", is_auto_increment => 1, is_nullable => 0 },
  "pseudo_user",
  { data_type => "varchar", is_nullable => 1, size => 20 },
  "passwd_user",
  { data_type => "varchar", is_nullable => 1, size => 30 },
  "mail_user",
  { data_type => "varchar", is_nullable => 1, size => 40 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id_user>

=back

=cut

__PACKAGE__->set_primary_key("id_user");

=head1 RELATIONS

=head2 junction_user_events

Type: has_many

Related object: L<Schema::Result::JunctionUserEvent>

=cut

__PACKAGE__->has_many(
  "junction_user_events",
  "Schema::Result::JunctionUserEvent",
  { "foreign.id_user" => "self.id_user" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 past_events

Type: has_many

Related object: L<Schema::Result::PastEvent>

=cut

__PACKAGE__->has_many(
  "past_events",
  "Schema::Result::PastEvent",
  { "foreign.id_organizer" => "self.id_user" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 tags_id_aggressors

Type: has_many

Related object: L<Schema::Result::Tag>

=cut

__PACKAGE__->has_many(
  "tags_id_aggressors",
  "Schema::Result::Tag",
  { "foreign.id_aggressor" => "self.id_user" },
  { cascade_copy => 0, cascade_delete => 0 },
);

=head2 tags_id_victims

Type: has_many

Related object: L<Schema::Result::Tag>

=cut

__PACKAGE__->has_many(
  "tags_id_victims",
  "Schema::Result::Tag",
  { "foreign.id_victim" => "self.id_user" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07042 @ 2015-02-04 11:50:13
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:TkKj2TTcFL58gBo1h2jzmw


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
