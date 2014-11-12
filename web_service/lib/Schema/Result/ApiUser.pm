use utf8;
package Schema::Result::ApiUser;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

=head1 NAME

Schema::Result::ApiUser

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

=head1 TABLE: C<_api_users>

=cut

__PACKAGE__->table("_api_users");

=head1 ACCESSORS

=head2 id_user

  data_type: 'integer'
  is_auto_increment: 1
  is_nullable: 0

=head2 passwd_user

  data_type: 'varchar'
  is_nullable: 0
  size: 100

=cut

__PACKAGE__->add_columns(
  "id_user",
  { data_type => "integer", is_auto_increment => 1, is_nullable => 0 },
  "passwd_user",
  { data_type => "varchar", is_nullable => 0, size => 100 },
);

=head1 PRIMARY KEY

=over 4

=item * L</id_user>

=back

=cut

__PACKAGE__->set_primary_key("id_user");


# Created by DBIx::Class::Schema::Loader v0.07042 @ 2014-11-10 14:45:35
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:x8i+ExtTuqM5DcY9h9QVzQ


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
