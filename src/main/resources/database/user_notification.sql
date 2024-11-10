create table user_notification
(
    id       varchar(255) not null
        primary key,
    username varchar(255),
    device   text
);

alter table user_notification
    owner to postgres;

INSERT INTO public.user_notification (id, username, device) VALUES ('d40eb385-bae0-48f1-be92-4b9cd67669ba', 'tenant01', 'dKnisgs0SfSmxns9r726kP:APA91bHzZ7whlO_YmPOmcN6oEANuAvvacI88jr9cr84KPzOEQeF3Xv5RENvO2hCivGR_JAMRqFHBEnvzE6PjhuSZc9lFECAWBkVHV2wB5xyGxz_4_W3eICw');
INSERT INTO public.user_notification (id, username, device) VALUES ('d5534554-53f4-404b-b195-2ea3a31e6b2d', 'lessor01', 'dKnisgs0SfSmxns9r726kP:APA91bHzZ7whlO_YmPOmcN6oEANuAvvacI88jr9cr84KPzOEQeF3Xv5RENvO2hCivGR_JAMRqFHBEnvzE6PjhuSZc9lFECAWBkVHV2wB5xyGxz_4_W3eICw');
INSERT INTO public.user_notification (id, username, device) VALUES ('75837903-f18f-449b-a771-997e06abbe00', 'lessor02', 'dKnisgs0SfSmxns9r726kP:APA91bHzZ7whlO_YmPOmcN6oEANuAvvacI88jr9cr84KPzOEQeF3Xv5RENvO2hCivGR_JAMRqFHBEnvzE6PjhuSZc9lFECAWBkVHV2wB5xyGxz_4_W3eICw');
INSERT INTO public.user_notification (id, username, device) VALUES ('dc6061a7-959b-4890-9ceb-96f85fe1f475', 'tenant02', 'dKnisgs0SfSmxns9r726kP:APA91bHzZ7whlO_YmPOmcN6oEANuAvvacI88jr9cr84KPzOEQeF3Xv5RENvO2hCivGR_JAMRqFHBEnvzE6PjhuSZc9lFECAWBkVHV2wB5xyGxz_4_W3eICw');
INSERT INTO public.user_notification (id, username, device) VALUES ('c84ac9f7-e315-4f8e-92a0-e957db9ee3d7', 'lessor01', 'fDdI--hVRkeFE-AFdPX6sM:APA91bHzXCFoG7Txsr8XA9d6BM8piGrWCalpXeFFWyCxCYcUNKFokgDZpBeVZb5xbI2dJKxcFPorLsSEx1QnzjB0T9DqsCX0s1GkbHhL7psYBnxT2J-RS_Y');
INSERT INTO public.user_notification (id, username, device) VALUES ('410c7f46-5b7a-406a-ae37-844d0b899055', 'tenant01', 'fDdI--hVRkeFE-AFdPX6sM:APA91bHzXCFoG7Txsr8XA9d6BM8piGrWCalpXeFFWyCxCYcUNKFokgDZpBeVZb5xbI2dJKxcFPorLsSEx1QnzjB0T9DqsCX0s1GkbHhL7psYBnxT2J-RS_Y');
INSERT INTO public.user_notification (id, username, device) VALUES ('773654e9-7f7d-434b-b34f-be37797ed7af', 'tenant01', null);
