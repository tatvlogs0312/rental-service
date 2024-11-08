create table bill
(
    id               varchar(255) not null
        primary key,
    contract_id      varchar(255),
    create_date      date,
    is_rent_continue boolean,
    month            integer,
    payment_date     date,
    status           varchar(255),
    year             integer
);

alter table bill
    owner to postgres;

