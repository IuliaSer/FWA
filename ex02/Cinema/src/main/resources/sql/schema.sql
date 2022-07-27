DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS auth CASCADE;

CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      FirstName varchar(30) NOT NULL,
                      LastName varchar(30) NOT NULL,
                      phoneNumber varchar(30),
                      password varchar(72) NOT NULL,
                      email varchar(30) NOT NULL UNIQUE
);

CREATE TABLE auth (
    user_id SERIAL references users(id),
    date varchar(30) NOT NULL,
    ip varchar(30) NOT NULL
);