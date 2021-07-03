CREATE TABLE likes (
    id_like SERIAL PRIMARY KEY,
    email_dono varchar(255) not null references usuarios(email),
    campanha_referencia integer not null references campanhas(id_campanha)
);