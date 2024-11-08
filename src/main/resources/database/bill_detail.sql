create table bill_detail
(
    id              varchar(255) not null
        primary key,
    bill_id         varchar(255),
    number_used     integer,
    room_utility_id varchar(255)
);

alter table bill_detail
    owner to postgres;

