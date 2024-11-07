create sequence hibernate_sequence start with 1 increment by 1;

create table assignments (
    id bigint not null,
    creation_time timestamp,
    description varchar(255),
    is_finished boolean not null,
    modification_time timestamp,
    name varchar(255),
    assignee_name bigint,
    project_id bigint,
    videoset_id bigint,
    primary key (id));

create table events (
    id bigint not null,
    end timestamp,
    start timestamp,
    assignment bigint,
    label_id bigint,
    primary key (id));

create table labels (
    id bigint not null,
    color varchar(255),
    name varchar(255),
    shortcut varchar(255),
    type varchar(255),
    project_id bigint,
    primary key (id));

create table projects (
    id bigint not null,
    creation_time timestamp,
    description varchar(255),
    modification_time timestamp,
    name varchar(255),
    scientist_name bigint,
    primary key (id));

create table users (
    user_role varchar(31) not null,
    id bigint not null,
    login varchar(255),
    password varchar(255),
    primary key (id));

create table videos (
    id bigint not null,
    data blob,
    name varchar(255),
    videoset_id bigint,
    primary key (id));

create table videosets (
    id bigint not null,
    max_video_count integer not null,
    name varchar(255),
    project_id bigint,
    primary key (id));

alter table videos add constraint UK_our5j9a2bluhtjnhlo9gfoc7s unique (name);
alter table assignments add constraint FKhhe7ui4go2dl1if8ycb6yu441 foreign key (assignee_name) references users;
alter table assignments add constraint FKodfes9swh7cx1bvgndkbx3grc foreign key (project_id) references projects;
alter table assignments add constraint FKchp7ybtgdohjcqrxe4oeuh9mg foreign key (videoset_id) references videosets;
alter table events add constraint FKf79ntb7shoka6a510lktwlth7 foreign key (assignment) references assignments;
alter table events add constraint FKqpr7cea22iqirckycr0bi885k foreign key (label_id) references labels;
alter table labels add constraint FK3sxl6x5sa83ojn87msxofr650 foreign key (project_id) references projects;
alter table projects add constraint FKetj7dge422asws6ujdwyxyw3o foreign key (scientist_name) references users;
alter table videos add constraint FKnq2hf5lsdys7r3m0xajblx2li foreign key (videoset_id) references videosets;
alter table videosets add constraint FKascdhid92apx4m3vtbanhh12n foreign key (project_id) references projects;
