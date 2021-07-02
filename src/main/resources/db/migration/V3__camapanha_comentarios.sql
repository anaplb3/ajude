create table campanha_comentarios(
   campanha_id bigint not null,
   comentario_id bigint not null,
   CONSTRAINT camapanha_comentario_pk PRIMARY KEY (comentario_id),
   CONSTRAINT campanha_id_fk FOREIGN KEY (campanha_id) REFERENCES campanhas(id_campanha),
   CONSTRAINT comentario_id_fk FOREIGN KEY (comentario_id) REFERENCES comentarios(id_comentario)
);


--alter table campanhas add constraint campanha_comentarios_campanha_id_fk foreign key (id_campanha) references campanha_comentarios(campanha_id);

--alter table comentarios add constraint campanha_comentarios_comentario_id_fk foreign key (id_comentario) references campanha_comentarios(comentario_id);
