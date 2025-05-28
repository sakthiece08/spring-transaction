create sequence inventory_id_seq start with 1 increment by 50;

create table inventory (
    id smallint default nextval('inventory_id_seq') not null,
    product_name varchar(30) not null,
    price  int not null,
    quantity int not null,
    primary key (id)
);

alter table if exists inventory add constraint unique_constraint_name unique (product_name);

create sequence order_id_seq start with 100 increment by 1;

create table user_order (
                       id smallint default nextval('order_id_seq') not null,
                       product_id smallint REFERENCES inventory (id),
                       status varchar(30) not null,
                       primary key (id)
);

