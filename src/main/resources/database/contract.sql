create table contract
(
    id            varchar(255) not null
        primary key,
    actual_price  bigint,
    contract_code varchar(255),
    effect_date   date,
    end_date      date,
    room_id       varchar(255),
    tenant        varchar(255)
);

alter table contract
    owner to postgres;

