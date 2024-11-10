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

INSERT INTO public.view_history (id, room_id, room_type, position, price, username, time_view, post_id) VALUES ('7430e25b-d132-4a2c-8bc0-331f5336b671', '941c14b9-cef3-4fc9-9a35-823b95570fa5', '2a3addb6-8d91-41a4-ac39-840156f9b9ca', 'Yên Hòa', 2800000, 'tenant01', '2024-11-09 22:11:52.955034', 'e6857390-313b-484f-9dfb-47aa70f97a06');
INSERT INTO public.view_history (id, room_id, room_type, position, price, username, time_view, post_id) VALUES ('9b0744f7-7969-46fb-b8cd-9c3198468256', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', '451122e0-c4ef-402f-9a49-7c591104cc2d', 'Yên Hòa', 8000000, 'tenant01', '2024-11-10 16:02:56.873994', '556785a2-97fe-4cb4-872b-20ad4f16b712');
