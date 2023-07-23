CREATE TABLE IF NOT EXISTS PRODUCT_LIST
(
    PRODUCT_ID          VARCHAR(36)     PRIMARY KEY,
    TYPE                VARCHAR(30) 	NOT NULL,
    CATEGORY_ID         VARCHAR(36)     NOT NULL,
    NAME                VARCHAR(30)     NOT NULL,
    PRICE               INT             NOT NULL,
    STATUS              VARCHAR(30)     NOT NULL,
    CREATED_AT          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    MODIFIED_AT         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    DELETED_AT          TIMESTAMP WITH TIME ZONE,
    VERSION             BIGINT
);