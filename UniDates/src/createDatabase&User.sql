create database spring_unidates; -- Creates the new database
create user 'springadmin'@'localhost' identified by 'password'; -- Creates the user
grant all on spring_unidates.* to 'springadmin'@'localhost'; -- Gives all privileges to the new user on the newly created database