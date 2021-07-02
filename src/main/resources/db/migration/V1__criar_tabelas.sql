CREATE TABLE usuarios (
    email varchar(255) PRIMARY KEY,
    primeiro_nome varchar(100) NOT NULL,
    ultimo_nome varchar(100) NOT NULL,
    numero_do_cartao varchar(60) NOT NULL,
    senha_do_usuario varchar(55) NOT NULL

);

CREATE TABLE campanhas (
    id_campanha SERIAL PRIMARY KEY,
    nome varchar(255) NOT NULL,
    descricao varchar(255) NOT NULL,
    deadline timestamp not null,
    meta numeric(11,2) not null,
    status integer not null,
    email_dono varchar(255) not null references usuarios(email)

);

CREATE TABLE doacoes (
    id_doacao SERIAL PRIMARY KEY,
    quantia_doada numeric(11,2) not null,
    data_da_doacao timestamp not null,
    email_dono varchar(255) not null references usuarios(email),
    campanha_referencia integer not null references campanhas(id_campanha)
);

CREATE TABLE comentarios (
    id_comentario SERIAL PRIMARY KEY,
    conteudo_da_mensagem varchar(255) not null,
    email_dono varchar(255) not null references usuarios(email),
    id_comentario_resposta integer references comentarios(id_comentario),
    id_campanha integer references campanhas(id_campanha)

);