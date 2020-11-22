drop table if exists SAVED;
drop table if exists QUESTIONS;
drop table if exists ANSWERS;
drop table if exists QUIZ;
drop table if exists USR;
drop table if exists ROLES;
drop table if exists USERS_ROLES;

create table USR
(
    id integer auto_increment,
    username varchar not null,
    password varchar not null,
    role integer not null,
    constraint USR_PK
        primary key (id)
);

create table ROLES
(
    id integer auto_increment,
    name varchar(100) not null
);

create table USERS_ROLES
(
    user_id integer not null,
    role_id integer not null,
    constraint USERS_ROLES_PK
        primary key (user_id),
    constraint USERS_ROLES_ROLES_ID_FK
        foreign key (role_id) references ROLES,
    constraint USERS_ROLES_USR_ID_FK
        foreign key (user_id) references USR
);

INSERT INTO ROLES (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (name) VALUES ('ROLE_USER');
INSERT INTO USR (username, password) VALUES (
                                                   'admin',
                                                   '$2y$12$XArZr499Si/54.h60S0Ffewh55totQ9AuS1wi9ZJYECPq6l6qrrKq'
                                                  );
INSERT INTO USR (username, password) VALUES (
                                                'user',
                                                '$2y$12$XArZr499Si/54.h60S0Ffewh55totQ9AuS1wi9ZJYECPq6l6qrrKq'
                                            );
INSERT INTO USERS_ROLES (user_id, role_id) VALUES (1, 1);
INSERT INTO USERS_ROLES (user_id, role_id) VALUES (2, 2);

create table QUIZ
(
    id integer auto_increment,
    name varchar not null,
    author_id integer not null,
    constraint QUIZ_PK
        primary key (id),
    constraint QUIZ_USR_ID_FK
        foreign key (author_id) references USR
);

create table QUESTIONS
(
    id integer auto_increment,
    quiz_id integer not null,
    question_text varchar not null,
    question_type integer,
    constraint QUESTIONS_PK
        primary key(id),
    constraint questions_quiz_id_fk
        foreign key (quiz_id) references quiz
);

create table ANSWERS
(
    id integer auto_increment,
    question_id integer not null,
    answer_text varchar not null,
    correct boolean not null,
    constraint answers_pk
        primary key (id),
    constraint answers_questions_id_fk
        foreign key (question_id) references questions
);

create table SAVED
(
    ID integer auto_increment,
    USER_ID integer not null,
    PARENT_QUIZ_ID integer not null,
    ANSWERS_IDS integer not null,
    constraint " SAVED_PK"
        primary key (ID),
    constraint " SAVED_ANSWERS_ID_FK"
        foreign key (ANSWERS_IDS) references ANSWERS,
    constraint " SAVED_QUIZ_ID_FK"
        foreign key (PARENT_QUIZ_ID) references QUIZ,
    constraint " SAVED_USR_ID_FK"
        foreign key (USER_ID) references USR
);