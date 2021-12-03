DROP TABLE IF EXISTS Products;

drop table if exists category CASCADE;
drop table if exists category_item CASCADE;
drop table if exists delivery CASCADE;
drop table if exists item CASCADE;
drop table if exists member CASCADE;
drop table if exists order_item CASCADE;
drop table if exists orders CASCADE;

drop table if exists boards CASCADE;

CREATE TABLE Products
(
    prod_id     IDENTITY        PRIMARY KEY,
    prod_name   VARCHAR(255)    NOT NULL,
    prod_price  INT             NOT NULL
);

create table category (
	category_id bigint auto_increment not null,
	name varchar(255),
	parent_id bigint,
	primary key (category_id)
);

create table category_item (
	category_id bigint not null,
	item_id bigint not null
);

create table delivery (
	delivery_id bigint auto_increment not null,
	city varchar(255),
	street varchar(255),
	zipcode varchar(255),
	status varchar(255),
	primary key (delivery_id)
);

create table item (
	item_id bigint auto_increment not null,
	name varchar(255),
	price integer not null,
	stock_quantity integer not null,
	dtype varchar(31) not null,
	artist varchar(255),
	etc varchar(255),
	author varchar(255),
	isbn varchar(255),
	actor varchar(255),
	director varchar(255),
	primary key (item_id)
);

create table member (
	member_id bigint auto_increment not null,
	name varchar(255),
	city varchar(255),
	street varchar(255),
	zipcode varchar(255),
	primary key (member_id)
);

create table order_item (
	order_item_id bigint auto_increment not null,
	item_id bigint,
	order_id bigint,
	order_price integer not null,
	count integer not null,
	primary key (order_item_id)
);

create table orders (
	order_id bigint auto_increment not null,
	member_id bigint,
	delivery_id bigint,
	order_date timestamp,
	status varchar(255),
	primary key (order_id)
);

create table boards (
    idx bigint auto_increment not null,
    writer varchar(255),
    title varchar(500),
    content clob,
    reg_date date,
    hit integer default(0),
    primary key (idx)
);

create table attach (
    idx bigint auto_increment not null,
    board_idx bigint not null,
    original_file_name varchar(255),
    stored_file_name varchar(255),
    file_size bigint,
    reg_date date,
    primary key (idx)
);