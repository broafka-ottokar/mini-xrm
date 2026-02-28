CREATE TABLE partner (
    id           BIGSERIAL    PRIMARY KEY,
    name         VARCHAR(150) NOT NULL,
    tax_number   VARCHAR(20)  NOT NULL,
    headquarters VARCHAR(150) NOT NULL,
    status       VARCHAR(20)  NOT NULL,
    version      BIGINT       NOT NULL DEFAULT 0,
    deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    
    CONSTRAINT ck_partner_status CHECK (status in ('ACTIVE', 'INACTIVE'))
);

CREATE INDEX idx_partner_name    ON partner (name);
CREATE INDEX idx_partner_deleted ON partner (deleted);

CREATE TABLE partner_tag (
    id          BIGSERIAL   PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    version     BIGINT      NOT NULL DEFAULT 0,
    deleted     BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_partner_tag_name    ON partner_tag (name);
CREATE INDEX idx_partner_tag_deleted ON partner_tag (deleted);

CREATE TABLE partner_x_partner_tag (
    partner_id     BIGINT    NULL,
    partner_tag_id BIGINT    NULL,
    
    constraint pk_partner_x_partner_tag             PRIMARY KEY (partner_id, partner_tag_id),
    
    constraint fk_partner_x_partner_tag_partner     FOREIGN KEY (partner_id)     REFERENCES partner     (id),
    constraint fk_partner_x_partner_tag_partner_tag FOREIGN KEY (partner_tag_id) REFERENCES partner_tag (id)
);

CREATE INDEX idx_partner_x_partner_tag_partner_id     ON partner_x_partner_tag (partner_id);
CREATE INDEX idx_partner_x_partner_tag_partner_tag_id ON partner_x_partner_tag (partner_tag_id);

CREATE TABLE activity (
    id                 BIGSERIAL       PRIMARY KEY,
    subject            VARCHAR(150)    NOT NULL,
    type               VARCHAR(50)     NOT NULL,
    description        VARCHAR(100000) NOT NULL,
    duration_minutes   INTEGER         NOT NULL,
    person_responsible VARCHAR(150)    NOT NULL,
    partner_id         BIGINT          NOT NULL,
    version            BIGINT          NOT NULL DEFAULT 0,
    deleted            BOOLEAN         NOT NULL DEFAULT FALSE,           

    CONSTRAINT ck_activity_duration_minutes CHECK (duration_minutes >= 1),
        
    CONSTRAINT fk_activity_partner FOREIGN KEY (partner_id) REFERENCES partner (id)
);

CREATE VIEW person_responsible_report
    AS
SELECT row_number() over () AS id,
       person_responsible,
       partner_count,
       total_duration_minutes
  FROM (
SELECT person_responsible,
       COUNT(distinct partner_id) AS partner_count,
       SUM(duration_minutes)      AS total_duration_minutes
  FROM activity
 WHERE deleted = FALSE
 GROUP BY person_responsible
 ORDER BY total_duration_minutes DESC
       ) AS subquery;
