/**
 * CREATE Script for init of DB
 */

 -- Create test users

insert into user (id, date_created, username, password) values (1, now(), 'test1', 'test1');
insert into user (id, date_created, username, password) values (2, now(), 'test2', 'test2');
insert into user (id, date_created, username, password) values (3, now(), 'test3', 'test3');
insert into user (id, date_created, username, password) values (4, now(), 'test4', 'test4');
insert into user (id, date_created, username, password) values (5, now(), 'test5', 'test5');
insert into user (id, date_created, username, password) values (6, now(), 'test6', 'test6');
insert into user (id, date_created, username, password) values (7, now(), 'test7', 'test7');
insert into user (id, date_created, username, password) values (8, now(), 'test8', 'test8');
insert into user (id, date_created, username, password) values (9, now(), 'test9', 'test9');
insert into user (id, date_created, username, password) values (10, now(), 'cron', 'cron');

-- Create test exchange rates

insert into exchange_rate (id, date_created, currency_from, currency_to, rate, user) values (1, now(), 'AUD', 'GBP', 0.93, 2);
insert into exchange_rate (id, date_created, currency_from, currency_to, rate, user) values (2, now(), 'AUD', 'GBP', 0.931, 2);
