create table public.employees
(
    id          int          not null generated by default as identity primary key,
    full_name   varchar(250) not null,
    leader      int,
--     position    varchar(100) not null,
    branch_name varchar(250) not null
);
