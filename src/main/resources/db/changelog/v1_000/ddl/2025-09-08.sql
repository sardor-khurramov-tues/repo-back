--liquibase formatted sql

create table department (
    id              bigserial           primary key,
    dep_type        varchar(31)         not null,
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
    orcid           varchar(19),
    ror             varchar(30),
    image_name      varchar(36),
    created_at      timestamp           not null,
    is_blocked      boolean             not null,
    department      bigint,
    constraint  app_user_department_fkey
        foreign key (department)
        references department (id) match simple
        on update restrict
        on delete restrict
);

create table document (
    id              bigserial           primary key,
    created_at      timestamp           not null,
    doc_key         varchar(41)         not null,
    doc_type        varchar(31)         not null,
    title           varchar(255)        not null,
    series_title    varchar(255),
    issn            varchar(9),
    isbn            varchar(17),
    volume          bigint,
    series_number   bigint,
    edition_number  bigint,
    ror             varchar(30),
    degree          varchar(31),
    first_page      bigint,
    last_page       bigint,
    proceed_subj    varchar(255),
    doc_abstract    varchar(5000),
    approval_date   date,
    pub_date        date,
    is_published    boolean             not null,
    submitter       bigint              not null,
    department      bigint              not null,
    constraint  document_submitter_fkey
        foreign key (submitter)
        references app_user (id) match simple
        on update restrict
        on delete restrict,
    constraint  document_department_fkey
        foreign key (department)
        references department (id) match simple
        on update restrict
        on delete restrict
);

create table doc_contributor (
    id              bigserial           primary key,
    document        bigint              not null,
    app_user        bigint              not null,
    doc_role        varchar(31)         not null,
    constraint  doc_contributor_document_fkey
        foreign key (document)
        references document (id) match simple
        on update restrict
        on delete restrict,
    constraint  doc_contributor_app_user_fkey
        foreign key (app_user)
        references app_user (id) match simple
        on update restrict
        on delete restrict,
    constraint  doc_contributor_document_app_user_unique unique
        (document, app_user)
);
