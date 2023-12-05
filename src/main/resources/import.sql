
insert into customer (id) values (1);
insert into account (id, money, customer_id) values (1, 5000, 1);
insert into bonus (id, bonus, customer_id) values (1, 0, 1);

insert into cashback (type, percent) values ('ONLINE', 10);
insert into cashback (type, percent) values ('SHOP', 7);
insert into cashback (type, percent) values ('SMALL_PURCHASE', 10);
insert into cashback (type, percent) values ('BIG_PURCHASE', 30);

insert into price_threshold (price_type, threshold) values ('BIG_PURCHASE', 300);
insert into price_threshold (price_type, threshold) values ('SMALL_PURCHASE', 10);