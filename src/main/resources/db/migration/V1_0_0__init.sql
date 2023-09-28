create type state as enum ('ACTIVE', 'INACTIVE', 'SUSPENDED');

create table user_account
(
    id               serial primary key,
    company_id       int                      not null,
    external_id      varchar(255),
    username         varchar(100)             not null,
    email            varchar(100)             not null,
    state            state                    not null,
    updated_by       varchar(100)             not null,
    updated_datetime timestamp with time zone not null,
    created_by       varchar(100)             not null,
    created_datetime timestamp with time zone not null
);

create table company
(
    id               serial primary key,
    bin              varchar(12)              not null,
    name             varchar(100),
    state            state                    not null,
    updated_by       varchar(100)             not null,
    updated_datetime timestamp with time zone not null,
    created_by       varchar(100)             not null,
    created_datetime timestamp with time zone not null
)
