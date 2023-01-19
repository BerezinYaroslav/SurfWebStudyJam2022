create table if not exists roles
(
    id   uuid default gen_random_uuid() primary key,
    role text not null unique
);

create table if not exists accounts
(
    id       uuid default gen_random_uuid() primary key,
    email    text not null unique,
    password text unique,
    role_id  uuid references roles (id)
        constraint accounts_roles_role_id_fk
);

create table if not exists surf_employees
(
    id         uuid default gen_random_uuid() primary key,
    name       text not null,
    account_id uuid references accounts (id)
        constraint surf_employees_accounts_account_id_fk
);


create table if not exists event_types
(
    id   uuid default gen_random_uuid() primary key,
    type text not null unique
);

create table if not exists state_types
(
    id   uuid default gen_random_uuid() primary key,
    type text not null unique
);

create table if not exists events
(
    id                 uuid default gen_random_uuid() primary key,
    about              text unique,
    candidates_number  integer,
    trainees_number    integer,
    offers_number      integer,
    event_type_id      uuid references event_types (id)
        constraint events_event_types_event_type_id_fk,
    event_initiator_id uuid references surf_employees (id)
        constraint events_surf_employees_event_initiator_id_fk
);

create table if not exists states_events
(
    id       uuid default gen_random_uuid() primary key,
    state_id uuid references state_types (id)
        constraint states_events_state_types_state_id_fk,
    event_id uuid references events (id)
        constraint states_events_events_event_id_fk,
    date     date not null
);

create table if not exists candidates
(
    id         uuid default gen_random_uuid() primary key,
    name       text not null,
    email      text not null unique,
    is_new     boolean,
    hr_from_id uuid references surf_employees (id)
        constraint candidates_surf_employees_hr_from_id_fk
);

create table if not exists candidates_events
(
    candidate_id uuid references candidates (id)
        constraint candidates_events_candidates_candidate_id_fk,
    event_id     uuid references events (id)
        constraint candidates_events_events_event_id_fk,
    primary key (event_id, candidate_id)
);


create table if not exists teams
(
    id                uuid default gen_random_uuid() primary key,
    about             text unique,
    project_git_link  text unique,
    project_miro_link text unique,
    mentor_id         bigint references surf_employees (id)
        constraint teams_surf_employees_mentor_id_fk
);

create table if not exists teams_feedbacks
(
    id            uuid default gen_random_uuid() primary key,
    mentor_id     uuid references surf_employees (id)
        constraint teams_feedbacks_surf_employees_mentor_id_fk,
    team_id       uuid references teams (id)
        constraint teams_feedbacks_teams_team_id_fk,
    comment       text    not null unique,
    score         integer not null,
    feedback_date date    not null
);

create table if not exists trainees
(
    id           uuid default gen_random_uuid() primary key,
    score        integer,
    is_active    boolean not null,
    event_id     uuid references events (id)
        constraint trainees_events_event_id_fk,
    candidate_id uuid references candidates (id)
        constraint trainees_candidates_candidate_id_fk,
    account_id   uuid references accounts (id)
        constraint trainees_accounts_account_id_fk,
    team_id      uuid references teams (id)
        constraint trainees_teams_team_id_fk
);

create table if not exists trainees_feedbacks
(
    id               uuid default gen_random_uuid() primary key,
    score            integer not null,
    comment          text    not null unique,
    date             date    not null,
    surf_employee_id uuid references surf_employees (id)
        constraint trainees_feedbacks_surf_employees_surf_employee_id_fk,
    trainee_id       uuid references surf_employees (id)
        constraint trainees_feedbacks_surf_employees_trainee_id_fk
);


create table if not exists tests
(
    id           uuid default gen_random_uuid() primary key,
    link         text unique,
    score        integer,
    start_date   timestamp not null,
    end_date     timestamp not null,
    candidate_id uuid references candidates (id)
        constraint tests_candidates_candidate_id_fk,
    event_id     uuid references events (id)
        constraint tests_events_event_id_fk
);

create table if not exists question_types
(
    id   uuid default gen_random_uuid() primary key,
    type text not null unique
);

create table if not exists answers
(
    id     uuid default gen_random_uuid() primary key,
    answer text not null unique
);

create table if not exists questions
(
    id               uuid default gen_random_uuid() primary key,
    question         text unique,
    question_type_id uuid references question_types (id)
        constraint questions_question_types_question_type_id_fk,
    right_answer_id  uuid references answers (id)
        constraint questions_answers_right_answer_id_fk
);

create table if not exists questions_answers
(
    question_id uuid references questions (id)
        constraint questions_answers_questions_question_id_fk,
    answer_id   uuid references answers (id)
        constraint questions_answers_answers_answer_id_fk,
    primary key (question_id, answer_id)
);

create table if not exists tests_questions
(
    test_id     uuid references tests (id)
        constraint tests_questions_tests_test_id_fk,
    question_id uuid references questions (id)
        constraint tests_questions_questions_question_id_fk,
    primary key (test_id, question_id)
);
