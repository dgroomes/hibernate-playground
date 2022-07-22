create table observation_types (
    id int not null primary key,
    description text not null
);

create table observations (
    id int not null primary key,
    observation text not null,
    type int not null,

    foreign key(type) REFERENCES observation_types(id)
);
