CREATE TABLE IF NOT EXISTS EVENT_HIST
(
    EVENT_ID            BIGINT AUTO_INCREMENT PRIMARY KEY,
    TYPE                VARCHAR(100) 	NOT NULL,
    PAYLOAD             TEXT            NOT NULL,
    OCCURRED_AT         TIMESTAMP WITH TIME ZONE NOT NULL,
    CREATED_AT          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);