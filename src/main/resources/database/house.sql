create table house
(
    id              varchar(255) not null
        primary key,
    house_name      text,
    position_detail text,
    ward            text,
    district        text,
    province        text,
    lessor          varchar(255)
);

alter table house
    owner to postgres;

