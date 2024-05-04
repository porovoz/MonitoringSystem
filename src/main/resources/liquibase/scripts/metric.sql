-- liquibase formatted sql

-- changeset sokol:1
CREATE TABLE metric (
    id BIGINT NOT NULL generated by default as identity PRIMARY KEY,
    name VARCHAR(64),
    max_memory DOUBLE PRECISION,
    used_memory DOUBLE PRECISION,
    created_at TIMESTAMP
);