create table post
(
    id              varchar(255) not null
        primary key,
    content         text,
    create_time     timestamp(6),
    lessor          varchar(255),
    room_id         varchar(255),
    title           text,
    number_watch    bigint,
    position_detail text,
    ward            text,
    district        text,
    province        text,
    price           bigint,
    room_type_id    varchar(255)
);

alter table post
    owner to postgres;

INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', 'Cho thuê chung cư mini ở ngõ 132 Hoa Bằng. Giờ giấc tự do', '2024-09-16 21:48:36.418856', 'lessor01', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Cho thuê chung cư mini ở Hoa Bằng', 20, 'Số 7A ngõ 132 Phố Hoa Bằng', 'Yên Hòa', 'Cầu Giấy', 'Hà Nội', 4000000, '052f5a0a-442a-4ce0-8058-c73db36bd639');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('efaced2d-374a-43c6-b5ef-e9cde1407fbf', 'Cho thuê chung cư mini ở ngõ 132 Quan Hoa. Giờ giấc tự do, không chung chủ. Gần các trường đại học như GTVT, Ngoại thương', '2024-09-16 21:55:59.887245', 'lessor01', '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'Cho thuê phòng trọ sinh viên ở Quan Hoa', 11, 'Số 48 ngõ 132 Đường Cầu Giấy', 'Quan Hoa', 'Cầu Giấy', 'Hà Nội', 2000000, '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('e6857390-313b-484f-9dfb-47aa70f97a06', 'Không chung chủ, giờ giấc thoải mái', '2024-09-16 22:02:32.279527', 'lessor01', '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'Cho thuê phòng trọ Hoa Bằng, Yên Hòa', 6, 'Số 8 ngõ 99 Phố Hoa Bằng', 'Yên Hòa', 'Cầu Giấy', 'Hà Nội', 2800000, '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('28599c22-0127-424f-a5eb-ab6eb40c4f1c', 'Vị trí: 68 Cầu Giấy, gần các trường nhưng Quốc gia, Điện lực, Thương mại', '2024-09-16 22:00:10.500869', 'lessor01', '53e6e3ee-829e-44a0-b662-7882bd0a2ccd', 'Cho thuê phòng trọ sinh viên ở Cầu giấy', 21, '68 Cầu Giấy', 'Xuân Thủy', 'Cầu Giấy', 'Hà Nội', 3000000, '2a3addb6-8d91-41a4-ac39-840156f9b9ca');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('9a2b8539-fb29-4673-ab51-3e9a0097d6b7', 'Cho thuê chung cư mini ở Phạm văn Đồng. Giờ giấc tự do. Gần các trường đại học như Điện Lực, Thương Mại, Quốc Gia, ...', '2024-09-16 21:51:24.859686', 'lessor01', '8678d0e2-b843-47af-a6e2-74e048cb233c', 'Cho thuê chung cư mini ở Phạm Văn Đồng', 10, '355 Phạm Văn Đồng', 'Cổ Nhuế', 'Bắc Từ Liêm', 'Hà Nội', 6500000, '052f5a0a-442a-4ce0-8058-c73db36bd639');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('556785a2-97fe-4cb4-872b-20ad4f16b712', e'Cho thuê chung cư tại Hoa Bằng gần chợ, cách công viên Cầu Giấy 1km.
- Phù hợp cho sinh viên, người đi làm
- Để xe 🚗 tầng 1 free', '2024-10-19 21:25:56.418535', 'lessor01', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'Cho thuê chung cư tại Hoa Bằng', 29, 'Số 12 ngõ 132 Phố Hoa Bằng', 'Yên Hòa', 'Cầu Giấy', 'Hà Nội', 8000000, '451122e0-c4ef-402f-9a49-7c591104cc2d');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title, number_watch, position_detail, ward, district, province, price, room_type_id) VALUES ('955cb6ea-d8d2-48d6-b2d3-e604e4e8fd53', e'Cho thuê chung cư tại Hoa Bằng gần chợ, cách công viên Cầu Giấy 1km.
- Phù hợp cho sinh viên, người đi làm
- Để xe 🚗 tầng 1 free', '2024-11-10 21:09:50.025859', 'tenant01', null, 'Cho thuê chung cư tại Hoa Bằng', 0, 'Số 15 ngõ 132 Phố Hoa Bằng', 'Yên Hòa', 'Cầu Giấy', 'Hà Nội', 5000000, '451122e0-c4ef-402f-9a49-7c591104cc2d');
