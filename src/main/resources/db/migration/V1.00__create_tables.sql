CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL,
    CONSTRAINT uk_email UNIQUE (email)
);

CREATE TABLE role (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT pk_role UNIQUE (id),
    CONSTRAINT uk_nome UNIQUE (nome)
);

CREATE TABLE roleusuario (
    rolefk UUID REFERENCES role(id),
    usuariofk BIGINT REFERENCES usuario(id),
    CONSTRAINT fk_role FOREIGN KEY (rolefk),
    CONSTRAINT fk_usuario FOREIGN KEY (usuariofk),
    PRIMARY KEY (rolefk, usuariofk)
);

CREATE TABLE medicamento (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    datainiciotratamento DATE NULL,
    datafimtratamento DATE NULL,
    horaaplicacao TIME NOT NULL,
    viaaplicacao VARCHAR(255) NOT NULL,
    usuariofk BIGINT REFERENCES usuario(id) NOT NULL,
);

CREATE TABLE exame (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NULL,
    datarealizacao DATE NULL,
    resultado TEXT NULL,
    usuariofk BIGINT REFERENCES usuario(id)
);

CREATE TABLE alergia (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NULL,
    usuariofk BIGINT REFERENCES usuario(id)
);

CREATE TABLE vacina (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    totaldoses INTEGER NULL,
    usuariofk BIGINT REFERENCES usuario(id)
);

CREATE TABLE dosevacina (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER NULL,
    dataaplicacao DATE NULL,
    vacinafk BIGINT REFERENCES vacina(id)
);

INSERT INTO role(id, nome) VALUES('d8f6b9ca-471b-45ca-a6df-a64d20e883c1', 'USUARIO');
INSERT INTO role(id, nome) VALUES('06813833-fad6-4240-8c12-992e60e26bed', 'ADMIN');
