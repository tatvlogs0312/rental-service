create table view_history
(
    id        varchar(255) not null
        primary key,
    room_id   varchar(255),
    room_type varchar(255),
    position  varchar(255),
    price     bigint,
    username  varchar(255),
    time_view timestamp,
    post_id   varchar(255)
);

alter table view_history
    owner to postgres;

