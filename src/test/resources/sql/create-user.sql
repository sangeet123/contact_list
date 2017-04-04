use contactlisttest1;
CREATE TABLE users(
  id INT NOT NULL AUTO_INCREMENT,
  username varchar(16) NOT NULL UNIQUE,
  email varchar(255) NOT NULL UNIQUE,
  password varchar(256) NOT NULL,
  enabled bool,
  create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(id)
);
CREATE TABLE user_roles(
  username varchar(16) NOT NULL,
  role varchar(16) NOT NULL
);
insert into users (id, username, email, password, enabled) values (1,"contactlist","contactlist@email.com","$2a$12$pwp17fLsOcMM4T8TMyt/NuVXBUt0SWE16CJGWNOGyY2h/E4YCstZy", true);
insert into user_roles (role, username) values ("ROLE_APP_ADMIN","contactlist");
insert into users (id, username, email, password, enabled) values (2, "user","user@email.com","$2a$12$pwp17fLsOcMM4T8TMyt/NuVXBUt0SWE16CJGWNOGyY2h/E4YCstZy", true);
insert into user_roles (role, username) values ("ROLE_APP_USER","user");
