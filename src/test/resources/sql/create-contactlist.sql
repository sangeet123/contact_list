use contactlisttest1;
create table contactlist (userid INT, name VARCHAR(30), id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id));
insert into contactlist (userid, name) VALUES (1,"Friends");
insert into contactlist (userid, name) VALUES (1,"Family");
insert into contactlist (userid, name) VALUES (1, "Others");
