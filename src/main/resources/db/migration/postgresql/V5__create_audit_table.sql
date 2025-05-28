create sequence audit_id_seq start with 1 increment by 50;

create table audit_log (
    id smallint default nextval('audit_id_seq') not null,
    action varchar(30) not null,
    order_id  smallint not null,
    time_stamp timestamp not null,
    primary key (id)
);

