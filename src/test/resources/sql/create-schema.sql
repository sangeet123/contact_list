DROP database IF EXISTS `integrationtestcontactlist`;
create database `integrationtestcontactlist`;
use integrationtestcontactlist;
create table contactlist (
    userid INT,
    name VARCHAR(30),
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    CONSTRAINT UK_contactlistname UNIQUE (name,userid));

create table contacts(
    id INT NOT NULL AUTO_INCREMENT,
    contactlistid INT,
    firstname VARCHAR(30),
    lastname VARCHAR(30),
    email VARCHAR(30) NOT NULL,
    phonenumber VARCHAR(30),
    PRIMARY KEY(id),
    CONSTRAINT UK_emailInContactList UNIQUE (email,contactlistid),
    FOREIGN KEY (contactlistid) REFERENCES contactlist(id));

DROP database IF EXISTS `usertestDB`;
create database usertestDB;
use usertestDB;

CREATE TABLE users(
  id INT NOT NULL AUTO_INCREMENT,
  firstname varchar(30) NOT NULL UNIQUE,
  lastname varchar(30) NOT NULL UNIQUE,
  username varchar(30) NOT NULL UNIQUE,
  email varchar(255) NOT NULL UNIQUE,
  password varchar(256) NOT NULL,
  enabled bool,
  create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
);

CREATE TABLE user_roles (
  id INT NOT NULL AUTO_INCREMENT,
  username varchar(30) NOT NULL,
  role varchar(16) NOT NULL,
  primary key(id)
);
