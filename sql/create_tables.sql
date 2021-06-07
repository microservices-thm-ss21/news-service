CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


create table projects (
    project_id uuid primary key
);

create table users (
    user_id uuid primary key
);

create table news (
    newsId uuid primary key DEFAULT uuid_generate_v4(),
    user uuid not null,
    timestamp timestamp  not null,
    event json not null
    CONSTRAINT fk_project
        FOREIGN KEY (project_id)
            REFERENCES projects(project_id),
    CONSTRAINT fk_user
        FOREIGN KEY (assigned_user_id)
            REFERENCES users(user_id)
);


insert into projects values ('54ed2c8e-054d-4fb0-81ac-d7ed726b1879');
insert into users values ('a443ffd0-f7a8-44f6-8ad3-87acd1e91042');
