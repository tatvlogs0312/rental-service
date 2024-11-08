create table user_profile_upload
(
    id       varchar(255) not null
        primary key,
    type     varchar(255),
    url      varchar(255),
    username varchar(255)
);

alter table user_profile_upload
    owner to postgres;

INSERT INTO public.user_profile_upload (id, type, url, username) VALUES ('adb91eb6-0fde-4a49-93d5-3f78b929d07c', 'SELFIE', 'a0752a4b-5b91-4966-b9b9-233344f599d6.png', 'tenant01');
