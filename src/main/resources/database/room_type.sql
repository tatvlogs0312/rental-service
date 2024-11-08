create table room_type
(
    id   varchar(255) not null
        primary key,
    code varchar(255),
    name varchar(255)
);

alter table room_type
    owner to postgres;

INSERT INTO public.room_type (id, code, name) VALUES ('052f5a0a-442a-4ce0-8058-c73db36bd639', 'MDP', 'Chung cư mini');
INSERT INTO public.room_type (id, code, name) VALUES ('ea2585f1-fc32-46d4-a9cf-83fb80e9788d', 'STU', 'Studio');
INSERT INTO public.room_type (id, code, name) VALUES ('451122e0-c4ef-402f-9a49-7c591104cc2d', 'DP', 'Chung cư');
INSERT INTO public.room_type (id, code, name) VALUES ('e74c4ab0-c82b-4b8e-beed-e203bc692942', 'OT', 'Khác');
INSERT INTO public.room_type (id, code, name) VALUES ('2a3addb6-8d91-41a4-ac39-840156f9b9ca', 'MO', 'Phòng trọ');
INSERT INTO public.room_type (id, code, name) VALUES ('32065838-d798-46bd-a26d-40a6d9d9dabf', 'DOR', 'Ký túc xá');
