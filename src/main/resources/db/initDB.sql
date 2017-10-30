--drop table if exists user_roles;
drop table if exists users;
drop sequence if exists global_seq;

create sequence global_seq start 1000;

create table users 
(
	id	integer primary key default nextval('global_seq'),
	name	varchar,
	surname	varchar,
	email	varchar not null,
	password	varchar not null,
	role varchar not null
);
create unique index unique_email on users (email);

