
    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        price decimal(38,2) not null,
        quantity_on_hand integer,
        style smallint not null check ((style between 0 and 10)),
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        id varchar(36) not null,
        name varchar(100) not null,
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        zip_code varchar(20),
        id varchar(36) not null,
        country varchar(100),
        address varchar(255),
        city varchar(255),
        email varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;
drop table if exists beer cascade ;
drop table if exists customer cascade ;
create table beer (price numeric(38,2) not null, quantity_on_hand integer, style smallint not null check ((style between 0 and 10)), version integer, created_date timestamp(6), updated_date timestamp(6), id varchar(36) not null, name varchar(100) not null, upc varchar(255) not null, primary key (id));
create table customer (version integer, created_date timestamp(6), updated_date timestamp(6), zip_code varchar(20), id varchar(36) not null, country varchar(100), address varchar(255), city varchar(255), email varchar(255), name varchar(255), primary key (id));

    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        price decimal(38,2) not null,
        quantity_on_hand integer,
        style smallint not null check ((style between 0 and 10)),
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        id varchar(36) not null,
        name varchar(100) not null,
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        zip_code varchar(20),
        id varchar(36) not null,
        country varchar(100),
        address varchar(255),
        city varchar(255),
        email varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;
drop table if exists beer;
drop table if exists customer;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, style smallint not null, version integer, created_date datetime(6), updated_date datetime(6), id varchar(36) not null, name varchar(100) not null, upc varchar(255) not null, primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), updated_date datetime(6), zip_code varchar(20), id varchar(36) not null, country varchar(100), address varchar(255), city varchar(255), email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;

    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        price decimal(38,2) not null,
        quantity_on_hand integer,
        style smallint not null check ((style between 0 and 10)),
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        id varchar(36) not null,
        name varchar(100) not null,
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        updated_date datetime(6),
        zip_code varchar(20),
        id varchar(36) not null,
        country varchar(100),
        address varchar(255),
        city varchar(255),
        email varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;
drop table if exists beer;
drop table if exists customer;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, style smallint not null, version integer, created_date datetime(6), updated_date datetime(6), id varchar(36) not null, name varchar(100) not null, upc varchar(255) not null, primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), updated_date datetime(6), zip_code varchar(20), id varchar(36) not null, country varchar(100), address varchar(255), city varchar(255), email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
