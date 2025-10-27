CREATE TABLE singlechoicetask (
    id BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES task(id)
);

CREATE TABLE multiplechoicetask (
    id BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES task(id)
);

CREATE TABLE opentexttask (
    id BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES task(id)
);
