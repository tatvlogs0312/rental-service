create table room_image
(
    id      varchar(255) not null
        primary key,
    room_id varchar(255),
    url     varchar(255),
    index   integer
);

alter table room_image
    owner to postgres;

INSERT INTO public.room_image (id, room_id, url, index) VALUES ('c51bd617-abb6-46ce-8218-8016c7d95e4c', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'f5d2cffc-fc70-4ff5-8141-1089b994676f.png', 1);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('617e932b-3a28-4ac3-8997-a911c4d942df', '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'd8696074-53c3-4570-94f5-ce589cdcaecb.png', 2);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('484d2fa3-7819-48ed-96db-d7e68d4bc77d', '53e6e3ee-829e-44a0-b662-7882bd0a2ccd', '5ddb2290-87ed-413d-ae78-e75485002ddc.png', 1);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('99fec160-5b6d-402a-8ae2-e2ef0f0e810d', '8678d0e2-b843-47af-a6e2-74e048cb233c', '0b4a3b12-bfe0-42c7-aa53-3316ae6341dd.png', 3);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('b2a99b98-ed18-4f52-9c02-3b6b5e3699fc', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'cff510dd-c483-4d92-b33b-73d7123b2352.png', 4);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('a910fa4b-f9ab-44c7-8014-43ccbccf41f5', '8678d0e2-b843-47af-a6e2-74e048cb233c', '59b10211-8096-476d-a203-2c69d39f23e7.png', 6);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('02ec4548-a05e-43b9-9a72-c3aa3f8eb7d3', '8678d0e2-b843-47af-a6e2-74e048cb233c', '13ea9286-5aa3-49e4-b87a-92255e94edfb.png', 1);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('b92bb892-cd95-40c4-831b-097b0ae0926e', '8678d0e2-b843-47af-a6e2-74e048cb233c', '9feaa478-714d-43b3-9875-03e7c3379f23.png', 2);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('a6c8d08b-aeaa-47f4-a4aa-e00e86eec1a2', '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'e3d0eb1c-0934-4bc1-9c4e-8cbd87e52037.png', 2);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('ae575e78-7dad-4ffa-80a0-63235696debc', '8678d0e2-b843-47af-a6e2-74e048cb233c', 'd865d8d8-5581-402f-bb58-0b77d9f12848.png', 4);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('3b60024f-56c1-4d5c-bd11-5a4555c58552', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', '9c0b95cc-a3e4-4046-8cd1-55fcf72cbe2c.png', 5);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('d44088ac-086a-40b1-ad04-b7adf86eea26', '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'c4e24cf3-9ae7-440b-a16a-ccb3a14d8220.png', 1);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('20cbb71f-3be6-4f2a-a04e-e2ad41a4a636', '53e6e3ee-829e-44a0-b662-7882bd0a2ccd', 'cdc6e150-3a5b-45b7-aa61-0d415d09afe5.png', 2);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('9f16e127-ae44-4ff9-9109-2d64813a930f', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'b56fdc6b-9732-4e4d-835c-cb6db869a5be.png', 2);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('5e40086a-0304-452d-a219-2511fcbf6b95', '941c14b9-cef3-4fc9-9a35-823b95570fa5', '285a199d-f4e5-4a63-b641-6c5432f909f7.png', 1);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('1898c4b3-d7bc-435b-a0ac-9209aac01b3b', '6f708e38-dfaa-4f25-b559-4e65c2ccd411', '4d6ef1d4-59d9-488f-9ca8-be118538990e.png', 3);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('834b9b49-7d17-4e40-9795-74df22771ea8', '53e6e3ee-829e-44a0-b662-7882bd0a2ccd', '1d9ae76e-96f2-4c64-9028-915518cbfb2c.png', 3);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('9151061d-45df-427e-8048-e883ec057771', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', '59e9970f-80c4-4366-a1ba-5fbec6e9829b.png', 3);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('48c4dff4-5295-4d46-b972-ba339101efd0', '8678d0e2-b843-47af-a6e2-74e048cb233c', '3f2cb1d8-2694-4b51-91ad-a079722f626f.png', 5);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('40da37ec-25e8-472c-8fac-1bac1a7dd987', '75d0b682-6c5f-4817-b25a-96f504b00379', 'b29c1523-ace2-464f-946b-a62221c76177.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('543f7794-e9c0-4982-b3ab-8e2a83bad453', '75d0b682-6c5f-4817-b25a-96f504b00379', '5b7f06e5-d6fb-4e30-ba31-024a27cb403d.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('36e9ad0d-1949-458e-8081-671c9d227732', '75d0b682-6c5f-4817-b25a-96f504b00379', 'b0770539-7a27-4be0-8351-bf3f7d8a70d0.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('a0bf7821-2d25-4f86-8612-4ddb25fd09fd', '941c14b9-cef3-4fc9-9a35-823b95570fa5', '386d2670-d543-4081-9c81-17334351cbee.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('e999b3f7-5653-46c8-8994-1bf04ee1891f', '4ce872f2-e2bd-4912-a1a8-6b6973bf7973', '3e1fbbdb-9aa8-4289-84cf-04cf1a5e84f9.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('8ed75c95-24dc-4d73-a8ff-d4bec4aaef4e', '4ce872f2-e2bd-4912-a1a8-6b6973bf7973', '3925d88a-3cfd-420a-9433-d2bda5e5a8e0.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('103a10b5-f0d0-47d1-987b-d9dc88475e66', '4ce872f2-e2bd-4912-a1a8-6b6973bf7973', 'de28e957-4c7b-4b3c-b2f6-1b8a8ae0397a.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('44f6ded7-6d04-4b99-b9fc-0b9ec772859a', '4ce872f2-e2bd-4912-a1a8-6b6973bf7973', '971d164f-d8f3-4573-ad11-47954b330bfa.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('06d1e5af-f240-4924-b52b-890cb540bde9', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', '6692ce61-3bdb-400d-9f11-d9118242a533.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('2c430ff6-4f7f-42f4-9e65-7990b10ad9e5', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', '1eeb26ec-fc14-4c4d-a5e7-c54eb68bb7c4.png', null);
INSERT INTO public.room_image (id, room_id, url, index) VALUES ('074623b4-b485-4f6b-9b89-33da8d61cc41', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'e8204852-43fe-4475-b471-6c76a5ccdd91.png', null);
