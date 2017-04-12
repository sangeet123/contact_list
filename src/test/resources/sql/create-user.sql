use usertestDB;
insert into users(id,firstname, lastname, username, email, password, enabled) values (1,"sangeet","dahal","contactlist","contactlist@email.com","$2a$12$pwp17fLsOcMM4T8TMyt/NuVXBUt0SWE16CJGWNOGyY2h/E4YCstZy", true);
insert into user_roles (role, username) values ("ROLE_APP_ADMIN","contactlist");
insert into users (id,firstname, lastname, username, email, password, enabled) values (2, "Ram", "Bahadur", "user","user@email.com","$2a$12$pwp17fLsOcMM4T8TMyt/NuVXBUt0SWE16CJGWNOGyY2h/E4YCstZy", true);
insert into user_roles (role, username) values ("ROLE_APP_USER","user");
