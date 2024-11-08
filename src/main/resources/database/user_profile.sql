create table user_profile
(
    id              varchar(255) not null
        primary key,
    email           varchar(255),
    first_name      varchar(255),
    identity_number varchar(255),
    last_name       varchar(255),
    password        varchar(255),
    phone_number    varchar(255),
    role            varchar(255),
    status          varchar(255),
    username        varchar(255)
);

alter table user_profile
    owner to postgres;

INSERT INTO public.user_profile (id, email, first_name, identity_number, last_name, password, phone_number, role, status, username) VALUES ('6b2b6e80-dd30-4cd5-951a-36e9e892241f', 'tanhtuan093@gmail.com', 'Trần Anh', '036202008596', 'Tuấn', 'k3o5KlBj6a+igfL4wu+A3Sz8lw1foll5lFKizS+ytNu2aGmOeOTM0gCMOwBk/Ec8p1qGatdoDJz7pCoPteTrIHl0+HkwEmi6UO/UZd7YoOOlFxrbJuE+0xFVCebVhuRR91ITfZc4urUF/pzdI3cAaL7KrC0hRxBIYyxoaIWKhPk=', '0384551953', 'TENANT', 'ACTIVE', 'tenant01');
INSERT INTO public.user_profile (id, email, first_name, identity_number, last_name, password, phone_number, role, status, username) VALUES ('df181cb9-ab71-4c90-8461-20930b90e6df', null, null, null, null, 'gPGokzmPpcbZ+crpPSD4xkBYZGU2diH/P5oWIgjWuhcuhOB9pp9LJoWT1rfE+Kj9ZPezS4CtW4hCQvQJutKxaOqRLniLKxmNQS9qinMcoiWWz2FRvbXwm+58A6JaUtxFR2MGU6d8dhQTNN2EfTFIwMagprNTxWif1luO/MqDpIs=', null, 'ADMIN', 'PENDING', 'admin01');
INSERT INTO public.user_profile (id, email, first_name, identity_number, last_name, password, phone_number, role, status, username) VALUES ('d72d5510-4af5-48a2-bde6-49fa7907df31', 'tanhtuan093@gmail.com', 'Trần Anh', '036202008596', 'Tuấn', 'dkmCwlIW+IRBOJygfUV5rOfxJnO1BPy9//2N+APOh9XCMByaer2LX9adFWIuligpV7lUkD4hyJ0Nyd3kiBE1jBayDu8kE1wWOW3SDm9fEP4rYoxbW5op+PaA2znLLJAtZWGzj03bcD+28sEw7rVmG7G2dooAzUoUp6NXHFgNJgM=', '0384551953', 'LESSOR', 'ACTIVE', 'lessor01');
INSERT INTO public.user_profile (id, email, first_name, identity_number, last_name, password, phone_number, role, status, username) VALUES ('c67e9699-85a1-4ff1-9a7d-210ad5008945', null, null, null, null, 'YbrqbFdV+s7WeTH9L6LkqAuWYbG93ut+pHGrlU8DqBepek6ES9S3WQdhDuUzQpg2VGJhsbaNAZqkFbfvk2t/28n+sYeZEYy0JnVX+qRKuQYDC6lmUL76OGMkc2ATUia3/b3yC1qpJ8sXMcWelkfvcC29S3vUCkkg8/RObMCQojQ=', null, 'LESSOR', 'PENDING', 'lessor02');
INSERT INTO public.user_profile (id, email, first_name, identity_number, last_name, password, phone_number, role, status, username) VALUES ('1da80bd3-f84f-4b65-826a-7bdef9bd4771', 'tuanta.tt@mcredit.com.vn', 'Phạm Thị Hồng', null, 'Ngọc', 'tIuXBhXFJw94/VcD5R17XKxEx4iWOML4WMRtKn/2++4s3nXRzDUiR/iTmekXZCpXlaCdSRFEfG5aHxTSfJBo2Wt7gEiOdsbAaEZbOnB1NmFtvGjCCuYWNQ74dn8uqnqVsaIv9wrWyxJmYiqVX8KHIL5dIzuZynUZSh40Dz49hSo=', '0359004943', 'TENANT', 'ACTIVE', 'tenant02');
