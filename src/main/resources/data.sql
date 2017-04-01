/**
 * CREATE Script for init of DB
 */

 -- Create test users

insert into user (id, date_created, username, password) values (1, now(), 'test1', 'test1');
insert into user (id, date_created, username, password) values (2, now(), 'test2', 'test2');

-- Create test exchange rates

insert into exchange_rate (id, date_created, currency_from, currency_to, rate, user) values (1, now(), 'AUD', 'GBP', 0.93, 2);
insert into exchange_rate (id, date_created, currency_from, currency_to, rate, user) values (2, now(), 'AUD', 'GBP', 0.931, 2);
