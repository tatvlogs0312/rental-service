create table room_utility
(
    id         varchar(255) not null
        primary key,
    is_active  boolean,
    price      bigint,
    room_id    varchar(255),
    unit       varchar(255),
    utility_id varchar(255)
);

alter table room_utility
    owner to postgres;

INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('c3e5a80d-9d02-4f97-b313-2ebb405185d8', true, 4000, 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Số', 'd0dd23ed-ea8a-4d2a-a4a8-0acb0374d656');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('a63eed46-bb82-4d80-9b3e-7c69d066e557', true, 100000, 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Người', '3c5f7ef2-63c4-495f-a680-eac6c4365c57');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('8eb5a3e7-1587-4520-8ab2-26823807227f', true, 100000, 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Phòng', '261e8edf-5ba0-4419-93e9-7ca6d7ecf543');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('7b72299b-6875-4d77-a7e9-4477f2306b20', true, 50000, 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Phòng', '9fa69f47-3c9c-4beb-bf10-b5c93dd2324b');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('924490a5-77ca-49a8-b8de-bf6b3cdb287d', true, 4000, '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'Số', '2cd3e21e-7277-48d1-9b5b-35cd35a5a7b4');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('7b8d6771-0e93-4207-bc72-33cfd0aa44f7', true, 3800, '8678d0e2-b843-47af-a6e2-74e048cb233c', 'Số', 'd0dd23ed-ea8a-4d2a-a4a8-0acb0374d656');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('e5b70fc2-dbaa-4f8e-8082-54be4dcff7e4', true, 10000, '8678d0e2-b843-47af-a6e2-74e048cb233c', 'Khối', '3c5f7ef2-63c4-495f-a680-eac6c4365c57');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('a3ca90c1-6cd7-4ae3-97fc-1fb6a96d165b', true, 4000, '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'Số', 'd0dd23ed-ea8a-4d2a-a4a8-0acb0374d656');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('74ae9dc8-bda8-4071-9bae-ab960bb8c969', true, 2800, 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'Số', 'd0dd23ed-ea8a-4d2a-a4a8-0acb0374d656');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('7affdf9c-b6ae-4761-88cc-2825aceea8ac', true, 4000, '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'Số', 'd0dd23ed-ea8a-4d2a-a4a8-0acb0374d656');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('141acf80-aa41-47c8-b669-e7f77929f46b', true, 30000, '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'Người', '261e8edf-5ba0-4419-93e9-7ca6d7ecf543');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('bda4a7f0-e739-4a6f-9897-e89894bc9198', true, 10000, '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'Khối', '3c5f7ef2-63c4-495f-a680-eac6c4365c57');
INSERT INTO public.room_utility (id, is_active, price, room_id, unit, utility_id) VALUES ('3f6742b4-0083-469a-bccb-2527139fb197', true, 100000, 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'Phòng', '2cd3e21e-7277-48d1-9b5b-35cd35a5a7b4');
