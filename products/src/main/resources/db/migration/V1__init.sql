create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;
insert into hibernate_sequence
values (1);
create table product
(
    id        bigint not null,
    buy_count integer,
    name      varchar(255),
    price     integer,
    primary key (id)
) engine = InnoDB;
