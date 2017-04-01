/**
 * CREATE Script for init of DB
 */

 -- Create users

insert into user (id, date_created, username, password) values (1, now(), 'test1', 'test1');
