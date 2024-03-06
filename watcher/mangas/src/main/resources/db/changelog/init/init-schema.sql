CREATE SEQUENCE IF NOT EXISTS medias_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE medias
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
    media_type         TEXT,
    CONSTRAINT pk_mangas PRIMARY KEY (id)
);

CREATE INDEX index_medias_name ON medias (name);

CREATE SEQUENCE IF NOT EXISTS reading_medias_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE reading_medias
(
    id                 BIGINT                      NOT NULL,
    created_by         TEXT                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by   TEXT                        NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id            TEXT,
    media_id           BIGINT,
    reading_format     TEXT,
    CONSTRAINT pk_readingmedia PRIMARY KEY (id)
);

ALTER TABLE reading_medias
    ADD CONSTRAINT uc_readingmedia_mediaid UNIQUE (media_id);

ALTER TABLE reading_medias
    ADD CONSTRAINT FK_READINGMEDIA_ON_USER FOREIGN KEY (user_id) REFERENCES user_entity (id);

CREATE SEQUENCE IF NOT EXISTS reading_format_status_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE reading_format_status
(
    id                 BIGINT                      NOT NULL,
    created_by         TEXT                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by   TEXT                        NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reading_media_id   BIGINT,
    media_number       INTEGER                     NOT NULL,
    read               BOOLEAN                     NOT NULL,
    CONSTRAINT pk_readingformatstatus PRIMARY KEY (id)
);

ALTER TABLE reading_format_status
    ADD CONSTRAINT FK_READINGFORMATSTATUS_ON_READING_MEDIA FOREIGN KEY (reading_media_id) REFERENCES reading_medias (id);