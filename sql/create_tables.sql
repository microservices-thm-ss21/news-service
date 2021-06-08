CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table users (
    user_id uuid primary key,
    last_news_retrieval timestamp
);

create table news (
    news_id uuid primary key DEFAULT uuid_generate_v4(),
    time timestamp not null,
    event_code varchar(30) not null,
    event_data json not null,
    issue_id uuid null,
    project_id uuid null,
    user_id uuid null
);

insert into users values ('a443ffd0-f7a8-44f6-8ad3-87acd1e91042', current_timestamp);
