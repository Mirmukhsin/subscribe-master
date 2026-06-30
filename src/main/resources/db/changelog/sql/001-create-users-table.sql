create table users
(
    id primary key bigserial,
    username   varchar(255),
    email      varchar(255),
    password   varchar(255),
    role       varchar(50),
    created_at timestamp,
    updated_at timestamp,
);