
create database if not exists qrx_blog;

use qrx_blog;

drop table if exists blog;

create table blog(
    blogId int primary key auto_increment,
    title varchar(1024),
    content mediumtext,
    userId int,
    postTime datetime
);

drop table if exists user;

create table user(
    userId int primary key auto_increment,
    username varchar(128) unique,
    password varchar(128)

);

insert into user values(null,'qrx','qrx123000');
insert into user values(null,'zhangsan','123');