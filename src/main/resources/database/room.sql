create table room
(
    id            varchar(255) not null
        primary key,
    acreage       integer,
    lessor        varchar(255),
    number_of_rom integer,
    price         bigint,
    room_code     varchar(255),
    room_status   varchar(255),
    room_type_id  varchar(255)
);

alter table room
    owner to postgres;

INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('75d0b682-6c5f-4817-b25a-96f504b00379', 18, 'lessor01', 1, 3000000, 'MO3', 'EMPTY', '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('eeaf0ac4-6646-4d00-a4a7-cc1195ae2836', 15, 'lessor01', 1, 2000000, 'MO4', 'EMPTY', '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('941c14b9-cef3-4fc9-9a35-823b95570fa5', 35, 'lessor01', 1, 2800000, 'MO5', 'EMPTY', '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('53e6e3ee-829e-44a0-b662-7882bd0a2ccd', 20, 'lessor01', 1, 3000000, 'MO6', 'EMPTY', '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('8678d0e2-b843-47af-a6e2-74e048cb233c', 40, 'lessor01', 2, 6500000, 'MDP7', 'EMPTY', '052f5a0a-442a-4ce0-8058-c73db36bd639');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('6f708e38-dfaa-4f25-b559-4e65c2ccd411', 12, 'lessor01', 1, 2000000, 'MO2', 'EMPTY', '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('de602876-9cdf-4c58-906d-bdb477073bb6', 5000000, 'lessor01', 5000000, 5000000, 'STU9', 'EMPTY', 'ea2585f1-fc32-46d4-a9cf-83fb80e9788d');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 50, 'lessor01', 2, 8000000, 'DP8', 'EMPTY', '451122e0-c4ef-402f-9a49-7c591104cc2d');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 29, 'lessor01', 1, 4000000, 'MDP1', 'EMPTY', '052f5a0a-442a-4ce0-8058-c73db36bd639');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('4ce872f2-e2bd-4912-a1a8-6b6973bf7973', 30, 'lessor01', 1, 4000000, 'MDP10', 'EMPTY', '052f5a0a-442a-4ce0-8058-c73db36bd639');
INSERT INTO public.room (id, acreage, lessor, number_of_rom, price, room_code, room_status, room_type_id) VALUES ('179eae01-c452-4a5e-9ea7-508b45c99d43', 30, 'lessor01', 2, 8000000, 'STU11', 'EMPTY', 'ea2585f1-fc32-46d4-a9cf-83fb80e9788d');
