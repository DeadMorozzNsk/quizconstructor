drop table if exists questions;
drop table if exists answers;
drop table if exists quiz;
drop table if exists usr;

create table usr
(
    id integer not null
        constraint usr_pk
            primary key autoincrement,
    username varchar not null,
    password varchar not null,
    role integer not null
);
create unique index usr_username_uindex
    on usr (username);

create table quiz
(
    id integer not null
        constraint quiz_pk
            primary key autoincrement,
    name varchar not null,
    author_id int not null
        constraint quiz_usr_id_fk
            references usr
);

create table questions
(
    id integer not null
        constraint questions_pk
        primary key autoincrement,
    quiz_id integer not null
        constraint questions_quiz_id_fk
        references quiz,
    question_text varchar not null,
    question_type int
);

create table answers
(
    id integer not null
        constraint answers_pk
            primary key autoincrement,
    question_id integer not null
        constraint answers_questions_id_fk
            references questions,
    answer_text varchar not null,
    correct boolean not null
);