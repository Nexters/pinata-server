USE db_pinata;

## Create Tables

CREATE TABLE tb_event_histories
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    use_flag          BIT          NOT NULL,
    event_id          BIGINT       NOT NULL,
    event_item_id     BIGINT       ,
    is_hit            BIT          NOT NULL,
    participant_email VARCHAR(255) NOT NULL,
    participant_id    BIGINT       NOT NULL,
    participant_name  VARCHAR(100) NOT NULL,
    title             VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE tb_event_items
(
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    created_at      DATETIME,
    updated_at      DATETIME,
    use_flag        BIT          NOT NULL,
    image_file_name VARCHAR(100),
    is_accepted     BIT          NOT NULL,
    ranking         INTEGER      NOT NULL,
    title           VARCHAR(500) NOT NULL,
    event_id        BIGINT,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE tb_events
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    organizer_id         BIGINT       NOT NULL,
    created_at           DATETIME,
    updated_at           DATETIME,
    use_flag             BIT          NOT NULL,
    code                 VARCHAR(500) NOT NULL,
    close_at             DATETIME,
    is_period            BIT,
    open_at              DATETIME,
    hit_count            INTEGER      NOT NULL,
    hit_image_file_name  VARCHAR(100),
    hit_message          VARCHAR(1000),
    limit_count          INTEGER      NOT NULL,
    miss_image_file_name VARCHAR(100),
    miss_message         VARCHAR(1000),
    participant_count    INTEGER      NOT NULL,
    status               VARCHAR(255),
    title                VARCHAR(500) NOT NULL,
    type                 VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE tb_users
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    email             VARCHAR(100) NOT NULL,
    nickname          VARCHAR(255),
    profile_image_url VARCHAR(255),
    provider_id       BIGINT       NOT NULL,
    state             INTEGER,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

## Set Constraint
ALTER TABLE tb_event_items
    ADD CONSTRAINT fk_event_item_to_event
        FOREIGN KEY (event_id)
            REFERENCES tb_events (id);
