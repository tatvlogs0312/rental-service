create table utility
(
    id   varchar(255) not null
        primary key,
    name varchar(255)
);

alter table utility
    owner to postgres;

INSERT INTO public.utility (id, name) VALUES ('d0dd23ed-ea8a-4d2a-a4a8-0acb0374d656', 'Điện');
INSERT INTO public.utility (id, name) VALUES ('3c5f7ef2-63c4-495f-a680-eac6c4365c57', 'Nước');
INSERT INTO public.utility (id, name) VALUES ('261e8edf-5ba0-4419-93e9-7ca6d7ecf543', 'Mạng');
INSERT INTO public.utility (id, name) VALUES ('2cd3e21e-7277-48d1-9b5b-35cd35a5a7b4', 'Gửi xe');
INSERT INTO public.utility (id, name) VALUES ('4476da6f-081c-446b-b20d-575da8d62147', 'Giặt là');
INSERT INTO public.utility (id, name) VALUES ('02dbdd1d-23ab-41ef-81e5-66fe220b3a3b', 'Rác');
INSERT INTO public.utility (id, name) VALUES ('9fa69f47-3c9c-4beb-bf10-b5c93dd2324b', 'Vệ sinh');
INSERT INTO public.utility (id, name) VALUES ('c6ee8183-5c44-4e69-aa80-b1d0292cbc74', 'Thang máy');
INSERT INTO public.utility (id, name) VALUES ('cfd70b5f-cac4-4502-98bb-6e271016e7a5', 'Nóng lạnh');
INSERT INTO public.utility (id, name) VALUES ('53a089b2-5d40-4735-b8ed-c8e6760ab57c', 'Khác');
