create table observations (
    id int not null primary key,
    observation text not null,
    type int not null,

    foreign key(type) REFERENCES observation_types(id)
);
