create table entities (id uuid not null default gen_random_uuid(), name character varying(50) not null, primary key (id));
