create table post
(
    id          varchar(255) not null
        primary key,
    content     text,
    create_time timestamp(6),
    lessor      varchar(255),
    room_id     varchar(255),
    title       text
);

alter table post
    owner to postgres;

INSERT INTO public.post (id, content, create_time, lessor, room_id, title) VALUES ('9a2b8539-fb29-4673-ab51-3e9a0097d6b7', 'Cho thuê chung cư mini ở Phạm văn Đồng. Giờ giấc tự do. Gần các trường đại học như Điện Lực, Thương Mại, Quốc Gia, ...', '2024-09-16 21:51:24.859686', 'lessor01', '8678d0e2-b843-47af-a6e2-74e048cb233c', 'Cho thuê chung cư mini ở Phạm Văn Đồng');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title) VALUES ('4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', 'Cho thuê chung cư mini ở ngõ 132 Hoa Bằng. Giờ giấc tự do', '2024-09-16 21:48:36.418856', 'lessor01', 'f8cde6f7-10b0-4655-a9b6-9728923aa6ea', 'Cho thuê chung cư mini ở Hoa Bằng');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title) VALUES ('efaced2d-374a-43c6-b5ef-e9cde1407fbf', 'Cho thuê chung cư mini ở ngõ 132 Quan Hoa. Giờ giấc tự do, không chung chủ. Gần các trường đại học như GTVT, Ngoại thương', '2024-09-16 21:55:59.887245', 'lessor01', '6f708e38-dfaa-4f25-b559-4e65c2ccd411', 'Cho thuê phòng trọ sinh viên ở Quan Hoa');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title) VALUES ('28599c22-0127-424f-a5eb-ab6eb40c4f1c', 'Vị trí: 68 Cầu Giấy, gần các trường nhưng Quốc gia, Điện lực, Thương mại', '2024-09-16 22:00:10.500869', 'lessor01', '53e6e3ee-829e-44a0-b662-7882bd0a2ccd', 'Cho thuê phòng trọ sinh viên ở Cầu giấy');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title) VALUES ('e6857390-313b-484f-9dfb-47aa70f97a06', 'Không chung chủ, giờ giấc thoải mái', '2024-09-16 22:02:32.279527', 'lessor01', '941c14b9-cef3-4fc9-9a35-823b95570fa5', 'Cho thuê phòng trọ Hoa Bằng, Yên Hòa');
INSERT INTO public.post (id, content, create_time, lessor, room_id, title) VALUES ('556785a2-97fe-4cb4-872b-20ad4f16b712', e'Cho thuê chung cư tại Hoa Bằng gần chợ, cách công viên Cầu Giấy 1km.
- Phù hợp cho sinh viên, người đi làm
- Để xe 🚗 tầng 1 free', '2024-10-19 21:25:56.418535', 'lessor01', 'fe8fe7e7-5206-4e2c-8a4a-ae24f27f243a', 'Cho thuê chung cư tại Hoa Bằng');
