create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table person (id bigint not null, name varchar(255), primary key (id)) engine=InnoDB;
