
drop table if exists comentario;

drop table if exists grupo;

drop table if exists likes;

drop table if exists membrosgrupo;

drop table if exists post;

drop table if exists utilizador;

/*==============================================================*/
/* Table: COMENTARIO                                            */
/*==============================================================*/
create table comentario 
(
  
   DataHora       	datetime                       null,
   TextoComentario      text                           null,
   ComentarioID        int                            not null,
   UtilizadorComent     int                            not null,
   PostComent           int                            not null,
   constraint PK_COMENTARIO primary key clustered (comentarioID)
);

/*==============================================================*/
/* Table: GRUPO                                                 */
/*==============================================================*/
create table grupo 
(
   DesignacaoGrupo      varchar(100)                   not null,
   Data                 date                           null,
   DescricaoGrupo       text                           not null,
   GrupoID             int                            not null,
   constraint PK_GRUPO primary key clustered (GrupoID),
   constraint AK_DESIGNACAO_GRUPO unique (DesignacaoGrupo)
);

/*==============================================================*/
/* Table: "LIKE"                                                */
/*==============================================================*/
create table likes 
(
   PostLike            int                            not null,
   UtilizadorLike      int                            not null,
   constraint PK_LIKE primary key clustered (PostLike, UtilizadorLike)
);

/*==============================================================*/
/* Table: MEMBROSGRUPO                                          */
/*==============================================================*/
create table membrosgrupo 
(
   GrupoMembro             int                            not null,
   UtilizadorMembro        int                            not null,
   Dono                    int                            not null,
   constraint PK_MEMBROSGRUPO primary key clustered (GrupoMembro, UtilizadorMembro)
);

/*==============================================================*/
/* Table: POST                                                  */
/*==============================================================*/
create table post 
(

   DataHora             datetime                           null,
   TextoPost            text                               null,
   PostID               int                            not null,
   PostAutor            int                            not null,
   constraint PK_POST primary key clustered (PostID)
);

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table utilizador 
(
   EmailUtilizador      varchar(100)                   not null,
   NomeUtilizador       varchar(100)                   not null,
   PaisResidencia       varchar(50)                    null,
   UtilizadorID         int                            not null,
   constraint PK_UTILIZADOR primary key clustered (UtilizadorID),
   constraint AK_KEY_EMAIL_UTILIZAD unique (EmailUtilizador)
);

alter table comentario
   add constraint FK_COMENTAR_COLOCACOM_UTILIZAD foreign key (UtilizadorComent)
      references utilizador (UtilizadorID)
      on update cascade
      on delete restrict;

alter table comentario
   add constraint FK_COMENTAR_COMENTARI_POST foreign key (PostComent)
      references post (PostID)
      on update cascade
      on delete restrict;

alter table likes
   add constraint FK_LIKE_LIKE_POST_POST foreign key (PostLike)
      references post (PostID)
      on update cascade
      on delete cascade;

alter table likes
   add constraint FK_LIKE_LIKE_UTIL_UTILIZAD foreign key (UtilizadorLike)
      references utilizador (UtilizadorID)
      on update cascade
      on delete cascade;

alter table membrosgrupo
   add constraint FK_MEMBROSG_GRUPO_MEM_UTILIZAD foreign key (UtilizadorMembro)
      references utilizador (UtilizadorID)
      on update restrict
      on delete restrict;

alter table membrosgrupo
   add constraint FK_MEMBROSG_MEMBROS_G_GRUPO foreign key (GrupoMembro)
      references grupo (GrupoID)
      on update restrict
      on delete restrict;

alter table post
   add constraint FK_POST_COLOCA_UT_UTILIZAD foreign key (PostAutor)
      references utilizador (UtilizadorID)
      on update cascade
      on delete restrict;

