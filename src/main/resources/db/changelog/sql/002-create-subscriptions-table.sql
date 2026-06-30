create table users
(
    id primary key bigserial,
    name   varchar(255),
    price      varchar(255),
    password   varchar(255),
    role       varchar(50),
    created_at timestamp,
    updated_at timestamp,
);