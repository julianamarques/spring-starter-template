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
    rolefk UUID,
    usuariofk BIGINT,
    PRIMARY KEY (rolefk, usuariofk),
    CONSTRAINT fk_role FOREIGN KEY (rolefk) REFERENCES role(id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuariofk) REFERENCES usuario(id)
);

INSERT INTO role(id, nome) VALUES('d8f6b9ca-471b-45ca-a6df-a64d20e883c1', 'USUARIO');
INSERT INTO role(id, nome) VALUES('06813833-fad6-4240-8c12-992e60e26bed', 'ADMIN');
