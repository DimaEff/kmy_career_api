CREATE TABLE student
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT student_pkey PRIMARY KEY (id)
);

INSERT INTO student(id)
VALUES (1),
       (2),
       (3);