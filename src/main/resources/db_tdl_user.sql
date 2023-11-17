#db users postgresql
CREATE DATABASE thedevlair;

CREATE TABLE roles (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(125),
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_name varchar(125),
    password varchar(125),
    email varchar(125),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    profile_picture VARCHAR(125),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    user_id INTEGER REFERENCES users(id),
    role_id INTEGER REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE UNIQUE INDEX idx_username ON users (username);
CREATE UNIQUE INDEX idx_email ON users (email);


INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

CREATE TABLE refreshtoken (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id INTEGER,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    PRIMARY KEY (id)
);



#For sql server

CREATE TABLE roles (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(125)
);

CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_name NVARCHAR(125),
    password NVARCHAR(125),
    email NVARCHAR(125),
    first_name NVARCHAR(50),
    last_name NVARCHAR(50),
    profile_picture NVARCHAR(125),
    is_active BIT NOT NULL,
    created_at DATETIME2 NOT NULL,
    updated_at DATETIME2,
);

CREATE TABLE user_roles (
    user_id INT REFERENCES users(id),
    role_id INT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE UNIQUE INDEX idx_username ON users (user_name);
CREATE UNIQUE INDEX idx_email ON users (email);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

CREATE TABLE refreshtoken (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT REFERENCES users (id),
    token NVARCHAR(255) NOT NULL UNIQUE,
    expiry_date DATETIME2 NOT NULL
);


