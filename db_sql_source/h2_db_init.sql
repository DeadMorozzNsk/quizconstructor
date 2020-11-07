drop table if exists questions;
drop table if exists answers;
drop table if exists quiz;
drop table if exists usr;

create table USR
(
    id integer auto_increment,
    username varchar not null,
    password varchar not null,
    role integer not null,
    constraint USR_PK
        primary key (id)
);

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