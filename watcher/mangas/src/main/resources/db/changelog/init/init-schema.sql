create table IF NOT EXISTS user_entity
(
    id                          varchar(36)           not null
        constraint constraint_fb
            primary key,
    email                       varchar(255),
    email_constraint            varchar(255),
    email_verified              boolean default false not null,
    enabled                     boolean default false not null,
    federation_link             varchar(255),
    first_name                  varchar(255),
    last_name                   varchar(255),
    realm_id                    varchar(255),
    username                    varchar(255),
    created_timestamp           bigint,
    service_account_client_link varchar(255),
    not_before                  integer default 0     not null,
    constraint uk_dykn684sl8up1crfei6eckhd7
        unique (realm_id, email_constraint),
    constraint uk_ru8tt6t700s9v50bu18ws5ha6
        unique (realm_id, username)
);

CREATE SEQUENCE IF NOT EXISTS mangas_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE mangas
(
    id                 BIGINT                      NOT NULL,
    name               TEXT                        NOT NULL,
    description        TEXT                        NOT NULL,
    released_date      INTEGER                     NOT NULL,
    cover_picture_url  TEXT                        NOT NULL,
    rate               DOUBLE PRECISION,
    review             TEXT,
    state              TEXT                        NOT NULL,
    created_by         TEXT                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by   TEXT                        NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reading_source     TEXT,
    CONSTRAINT pk_mangas PRIMARY KEY (id)
);

CREATE INDEX index_mangas_name ON mangas (name);

CREATE SEQUENCE IF NOT EXISTS reading_mangas_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE reading_mangas
(
    id                 BIGINT                      NOT NULL,
    created_by         TEXT                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by   TEXT                        NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id            TEXT,
    manga_id           BIGINT,
    reading_format     TEXT,
    CONSTRAINT pk_readingmanga PRIMARY KEY (id)
);

ALTER TABLE reading_mangas
    ADD CONSTRAINT uc_readingmanga_mangaid UNIQUE (manga_id);

ALTER TABLE reading_mangas
    ADD CONSTRAINT FK_READINGMANGA_ON_USER FOREIGN KEY (user_id) REFERENCES user_entity (id);

CREATE SEQUENCE IF NOT EXISTS reading_format_status_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE reading_format_status
(
    id                 BIGINT                      NOT NULL,
    created_by         TEXT                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by   TEXT                        NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reading_manga_id   BIGINT,
    manga_number       INTEGER                     NOT NULL,
    read               BOOLEAN                     NOT NULL,
    CONSTRAINT pk_readingformatstatus PRIMARY KEY (id)
);

ALTER TABLE reading_format_status
    ADD CONSTRAINT FK_READINGFORMATSTATUS_ON_READING_MANGA FOREIGN KEY (reading_manga_id) REFERENCES reading_mangas (id);
