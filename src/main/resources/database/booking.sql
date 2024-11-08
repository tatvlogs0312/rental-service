create table booking
(
    id              varchar(255) not null
        primary key,
    booking_message text,
    room_id         varchar(255),
    status          varchar(255),
    tenant          varchar(255),
    date_watch      timestamp,
    date_booking    timestamp
);

alter table booking
    owner to postgres;

INSERT INTO public.booking (id, booking_message, room_id, status, tenant, date_watch, date_booking) VALUES ('4ccc9ae6-08ec-4c36-b3fc-6b7f82027da1', null, '8678d0e2-b843-47af-a6e2-74e048cb233c', 'BOOKED', 'tenant01', '2024-10-23 14:00:00.000000', '2024-10-23 22:59:20.246214');
INSERT INTO public.booking (id, booking_message, room_id, status, tenant, date_watch, date_booking) VALUES ('ca52ea40-0599-4b5d-8ec6-8c8b8d6cdd77', null, 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'BOOKED', 'tenant01', '2024-02-11 14:00:00.000000', '2024-11-02 23:58:22.245179');
INSERT INTO public.booking (id, booking_message, room_id, status, tenant, date_watch, date_booking) VALUES ('b127931e-bab3-465b-ac27-0f8f51e8738d', null, '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'BOOKED', 'tenant01', '2024-02-11 15:00:00.000000', '2024-11-03 00:00:07.500219');
INSERT INTO public.booking (id, booking_message, room_id, status, tenant, date_watch, date_booking) VALUES ('77da67a6-8537-4e36-ade2-29853e93228f', null, 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'BOOKED', 'tenant01', '2024-11-04 14:00:00.000000', '2024-11-03 20:10:05.971924');
INSERT INTO public.booking (id, booking_message, room_id, status, tenant, date_watch, date_booking) VALUES ('3ca5863c-b669-4052-87fb-896ab1e38055', null, 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'BOOKED', 'tenant02', '2024-11-05 09:00:00.000000', '2024-11-03 16:51:25.781521');
