--liquibase formatted sql

create table department (
    id              bigserial           primary key,
    name_uz         varchar(63)         not null,
    name_en         varchar(63)         not null,
    name_ru         varchar(63)         not null,
    is_blocked      boolean             not null
);

create table app_user (
    id              bigserial           primary key,
    username        varchar(127)        not null    unique,
    password        varchar(60)         not null,
    user_role       varchar(31)         not null,
    hemis_id        varchar(31),
    first_name      varchar(63)         not null,
    last_name       varchar(63)         not null,
    middle_name     varchar(63),
    image_name      varchar(36),
    created_at      timestamp           not null,
    is_blocked      boolean             not null,
    department_id   bigint,
    constraint  app_user_department_id_fkey
        foreign key (department_id)
        references department (id) match simple
        on update restrict
        on delete restrict
);

create table doc_type (
    id              bigserial           primary key,
    name_uz         varchar(63)         not null,
    name_en         varchar(63)         not null,
    name_ru         varchar(63)         not null
);

create table document (
    id              bigserial           primary key,
    created_at      timestamp           not null,
    title           varchar(63)         not null,
    subtitle        varchar(127),
    doc_lang        varchar(2)          not null,
    doi             varchar(510),
    about           varchar(510),
    is_published    boolean             not null,
    published_at    timestamp           not null,
    department_id   bigint              not null,
    doc_type        bigint              not null,
    constraint  document_department_id_fkey
        foreign key (department_id)
        references department (id) match simple
        on update restrict
        on delete restrict,
    constraint  document_doc_type_fkey
        foreign key (doc_type)
        references doc_type (id) match simple
        on update restrict
        on delete restrict
);

create table doc_role (
    id              bigserial           primary key,
    name_uz         varchar(63)         not null,
    name_en         varchar(63)         not null,
    name_ru         varchar(63)         not null
);

create table doc_contributor (
    id              bigserial           primary key,
    doc_id          bigint              not null,
    user_id         bigint              not null,
    role_id         bigint              not null,
    constraint  doc_contributor_doc_id_fkey
        foreign key (doc_id)
        references document (id) match simple
        on update restrict
        on delete restrict,
    constraint  doc_contributor_user_id_fkey
        foreign key (user_id)
        references app_user (id) match simple
        on update restrict
        on delete restrict,
    constraint  doc_contributor_role_id_fkey
        foreign key (role_id)
        references doc_role (id) match simple
        on update restrict
        on delete restrict,
    constraint  doc_contributor_doc_id_user_id_unique unique
        (doc_id, user_id)
);
