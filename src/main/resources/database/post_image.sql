create table post_image
(
    id      varchar(255) not null
        primary key,
    post_id varchar(255),
    url     varchar(255)
);

alter table post_image
    owner to postgres;

INSERT INTO public.post_image (id, post_id, url) VALUES ('a0622673-5634-462b-907a-f4ddcd50336d', '28599c22-0127-424f-a5eb-ab6eb40c4f1c', '5ddb2290-87ed-413d-ae78-e75485002ddc.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('50588de7-b5df-4ed4-9f18-25e23fed38bf', '28599c22-0127-424f-a5eb-ab6eb40c4f1c', 'cdc6e150-3a5b-45b7-aa61-0d415d09afe5.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('2edf1716-24d8-453b-9ce9-4ce5b275480e', '28599c22-0127-424f-a5eb-ab6eb40c4f1c', '1d9ae76e-96f2-4c64-9028-915518cbfb2c.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('edfe4aec-2975-4866-9001-6dd474dcb8fa', 'e6857390-313b-484f-9dfb-47aa70f97a06', 'e3d0eb1c-0934-4bc1-9c4e-8cbd87e52037.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('b98dc124-3440-433b-b7d3-7a9307d036c1', 'e6857390-313b-484f-9dfb-47aa70f97a06', '285a199d-f4e5-4a63-b641-6c5432f909f7.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('f04c41f3-3ccd-402a-aec2-7b84b1286269', 'e6857390-313b-484f-9dfb-47aa70f97a06', '386d2670-d543-4081-9c81-17334351cbee.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('36df3446-1b51-405f-a2c5-20b34e512da4', 'efaced2d-374a-43c6-b5ef-e9cde1407fbf', 'd8696074-53c3-4570-94f5-ce589cdcaecb.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('9792eef9-c431-4a23-9ee7-0add89dd245e', 'efaced2d-374a-43c6-b5ef-e9cde1407fbf', 'c4e24cf3-9ae7-440b-a16a-ccb3a14d8220.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('190e9197-624b-4419-aba2-aab772749dda', 'efaced2d-374a-43c6-b5ef-e9cde1407fbf', '4d6ef1d4-59d9-488f-9ca8-be118538990e.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('8c0876e3-ea4b-40f2-867d-bf4af3e5f944', '556785a2-97fe-4cb4-872b-20ad4f16b712', '6692ce61-3bdb-400d-9f11-d9118242a533.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('8b29a420-311f-4fb2-baca-48f56613e5a7', '556785a2-97fe-4cb4-872b-20ad4f16b712', '1eeb26ec-fc14-4c4d-a5e7-c54eb68bb7c4.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('ae575103-1b64-465e-aecb-abb6370082f7', '556785a2-97fe-4cb4-872b-20ad4f16b712', 'e8204852-43fe-4475-b471-6c76a5ccdd91.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('486aaf91-c2ff-4ca2-a7ae-e44bfbea349b', '9a2b8539-fb29-4673-ab51-3e9a0097d6b7', '0b4a3b12-bfe0-42c7-aa53-3316ae6341dd.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('2b9b5316-f5d5-49d2-b806-4f55823a4612', '9a2b8539-fb29-4673-ab51-3e9a0097d6b7', '59b10211-8096-476d-a203-2c69d39f23e7.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('cb2cd3a0-3cb3-4ccb-9a9b-6f4d1fbd5872', '9a2b8539-fb29-4673-ab51-3e9a0097d6b7', '13ea9286-5aa3-49e4-b87a-92255e94edfb.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('f853f891-0d05-4f5d-a22a-5acd9d93a7f8', '9a2b8539-fb29-4673-ab51-3e9a0097d6b7', '9feaa478-714d-43b3-9875-03e7c3379f23.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('bc234f5a-dc42-49bf-a58f-1c3e9f6ba392', '9a2b8539-fb29-4673-ab51-3e9a0097d6b7', 'd865d8d8-5581-402f-bb58-0b77d9f12848.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('41a4c064-cf73-4cd5-9e88-6a630f68c7ce', '9a2b8539-fb29-4673-ab51-3e9a0097d6b7', '3f2cb1d8-2694-4b51-91ad-a079722f626f.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('1b271809-af4f-4f41-a989-3cba8c88353b', '4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', 'f5d2cffc-fc70-4ff5-8141-1089b994676f.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('0b19b29e-fe12-4a06-b10d-ad9e59060aac', '4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', 'cff510dd-c483-4d92-b33b-73d7123b2352.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('f22d5a76-fa51-412d-a20c-46dc39d06d59', '4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', '9c0b95cc-a3e4-4046-8cd1-55fcf72cbe2c.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('bb0b5ecc-c7f3-4377-aaf6-5a7920a08d1d', '4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', 'b56fdc6b-9732-4e4d-835c-cb6db869a5be.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('55ac31dd-7a18-48b8-919a-d3cee5ae90b2', '4d03c428-fae6-4ec2-abc8-0ea6d484f5b6', '59e9970f-80c4-4366-a1ba-5fbec6e9829b.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('1e346e93-2c32-490c-b062-cc100afd4927', '955cb6ea-d8d2-48d6-b2d3-e604e4e8fd53', '8e7c23b3-28e5-4d90-9f6c-bb98db952648.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('4996e3cd-2450-48ab-ba2b-5158776e5bb7', '955cb6ea-d8d2-48d6-b2d3-e604e4e8fd53', 'bf8ec9b2-038f-49f8-9470-2c44843e47fd.png');
INSERT INTO public.post_image (id, post_id, url) VALUES ('2153ccb2-2289-4e55-a12a-5212437c7f8f', '955cb6ea-d8d2-48d6-b2d3-e604e4e8fd53', 'a3f90633-6f7e-4a28-8868-8212d8004b28.png');
