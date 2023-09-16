------------------------------------
-- Naming Conventions -
-- 1. Table names are appended with "_T"
-- 2. Sequence names are appended with "_S"
------------------------------------

-- TODO_T table - this will hold all the todos creating in this application
CREATE TABLE "TODO_T" (
    ID NUMBER PRIMARY KEY,
    NAME VARCHAR2(500),
    STATUS VARCHAR2(20)
);

-- Sequence for primary key of TODO_T table
CREATE SEQUENCE "TODO_S"
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCACHE;
