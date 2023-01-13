
create table roles
(
    id   bigint primary key generated always as identity,
    role text not null unique
);

create table accounts
(
    id       bigint primary key generated always as identity,
    email    text not null unique,
    password text not null,
    role_id  bigint references roles (id)
);

create table surf_employees
(
    id         bigint primary key generated always as identity,
    name       text not null,
    account_id bigint references accounts (id)
);


create table event_types
(
    id   bigint primary key generated always as identity,
    type text unique not null
);

create table state_types
(
    id   bigint primary key generated always as identity,
    type text unique not null
);

create table events
(
    id                bigint primary key generated always as identity,
    about             text,
    candidates_number integer,
    trainees_number   integer,
    offers_number     integer,
    event_type_id     bigint references event_types (id),
    event_initiator_id   bigint references surf_employees (id)
);

create table states_events
(
    id       bigint,
    state_id bigint references state_types (id),
    event_id bigint references events (id),
    date     date not null,
    primary key (id)
);

create table candidates
(
    id         bigint primary key generated always as identity,
    name       text not null,
    email      text not null unique,
    is_new     boolean,
    hr_from_id bigint references surf_employees (id)
);

create table candidates_events
(
    candidate_id bigint references candidates (id),
    event_id     bigint references events (id),
    primary key (event_id, candidate_id)
);


create table teams
(
    id                bigint primary key generated always as identity,
    about             text,
    project_git_link  text,
    project_miro_link text,
    mentor_id         bigint references surf_employees (id)
);

create table teams_feedbacks
(
    id        bigint,
    mentor_id bigint references surf_employees (id),
    team_id   bigint references teams (id),
    comment   text,
    score     integer,
    feedback_date      date,
    primary key (id)
);

create table trainees
(
    id           bigint primary key generated always as identity,
    score        integer,
    is_active    boolean,
    event_id     bigint references events (id),
    candidate_id bigint references candidates (id),
    account_id   bigint references accounts (id),
    team_id      bigint references teams (id)
);

create table trainees_feedbacks
(
    id               bigint primary key generated always as identity,
    score            integer,
    comment          text,
    date             date,
    surf_employee_id bigint references surf_employees (id),
    trainee_id       bigint references surf_employees (id)
);


create table tests
(
    id           bigint primary key generated always as identity,
    link         text,
    score        integer,
    start_date   timestamp,
    end_date     timestamp,
    candidate_id bigint references candidates (id),
    event_id     bigint references events (id)
);

create table question_types
(
    id   bigint primary key generated always as identity,
    type text not null unique
);

create table answers
(
    id     bigint primary key generated always as identity,
    answer text
);

create table questions
(
    id               bigint primary key generated always as identity,
    question         text,
    question_type_id bigint references question_types (id),
    right_answer_id  bigint references answers (id)
);

create table questions_answers
(
    question_id bigint references questions (id),
    answer_id   bigint references answers (id),
    primary key (question_id, answer_id)
);

create table tests_questions
(
    test_id     bigint references tests (id),
    question_id bigint references questions (id),
    primary key (test_id, question_id)
);
