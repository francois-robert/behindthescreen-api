DROP TABLE IF EXISTS behindthescreen.games;

CREATE TABLE behindthescreen.games (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    type VARCHAR(30) NOT NULL,
    description VARCHAR(2500),
    version VARCHAR(5),
    release_date DATE NOT NULL
);
