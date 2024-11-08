create table room_position
(
    id       varchar(255) not null
        primary key,
    detail   text,
    district text,
    province text,
    room_id  varchar(255),
    ward     text
);

alter table room_position
    owner to postgres;

INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('f9ec9244-6eb8-45b1-a462-a07082cc7298', 'Số 7A ngõ 132 Phố Hoa Bằng', 'Cầu Giấy', 'Hà Nội', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Yên Hòa');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('8ad5dbd1-bc1d-434e-afd8-d5b411630db9', 'Số 48 ngõ 132 Đường Cầu Giấy', 'Cầu Giấy', 'Hà Nội', '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'Quan Hoa');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('4eb94865-a9b0-4778-b2dc-e71888e290bc', 'Số 45C ngõ 85 Đường Cầu Giấy', 'Cầu Giấy', 'Hà Nội', '75d0b682-6c5f-4817-b25a-96f504b00379', 'Xuân Thủy');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('42365f93-5d9d-419e-b246-63c2aad48cdd', 'Số 7A ngõ 132 Đường Cầu Giấy', 'Cầu Giấy', 'Hà Nội', 'eeaf0ac4-6646-4d00-a4a7-cc1195ae2836', 'Quan Hoa');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('2bbdd400-4eb2-4144-818f-3cb25afdce96', 'Số 8 ngõ 99 Phố Hoa Bằng', 'Cầu Giấy', 'Hà Nội', '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'Yên Hòa');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('e62d2974-f8d8-4b1f-bad5-8432b535dc6b', '68 Cầu Giấy', 'Cầu Giấy', 'Hà Nội', '53e6e3ee-829e-44a0-b662-7882bd0a2ccd', 'Xuân Thủy');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('6c0c271d-f962-43aa-988e-90cd94c868b3', '355 Phạm Văn Đồng', 'Bắc Từ Liêm', 'Hà Nội', '8678d0e2-b843-47af-a6e2-74e048cb233c', 'Cổ Nhuế');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('c84a60d3-e0ce-42f8-a81c-d9ce3129e197', 'Số 12 ngõ 132 Phố Hoa Bằng', 'Cầu Giấy', 'Hà Nội', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'Yên Hòa');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('d1fa1a7f-b8d5-49df-b463-db90b7f92ce8', 'Số 12 ngõ 324 đường Kim Mã', 'Ba Đình', 'Hà Nội', 'de602876-9cdf-4c58-906d-bdb477073bb6', 'Kim Mã');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('8482ca40-26b7-4c74-aba2-52c784b95d37', 'Ngõ 381 Nguyễn Khang', 'Cầu Giấy', 'Hà Nội', '4ce872f2-e2bd-4912-a1a8-6b6973bf7973', 'Phường Quan Hoa');
INSERT INTO public.room_position (id, detail, district, province, room_id, ward) VALUES ('926b1a57-4c73-4635-9d3a-b416aadba891', '58 đường Đội Cấn', 'Quận Ba Đình', 'Thành phố Hà Nội', '179eae01-c452-4a5e-9ea7-508b45c99d43', 'Phường Đội Cấn');
