BEGIN;
DELETE FROM orderlines;
DELETE FROM bottom;
DELETE FROM topping;
DELETE FROM orders;
DELETE FROM public.user;

ALTER SEQUENCE bottom_id_seq RESTART WITH 1;
ALTER SEQUENCE orderlines_id_seq RESTART WITH 1;
ALTER SEQUENCE orders_id_seq RESTART WITH 1;
ALTER SEQUENCE topping_id_seq RESTART WITH 1;
ALTER SEQUENCE user_id_seq RESTART WITH 1;

INSERT INTO bottom(name, price)	VALUES 
	('Chocolate', 5.00),
	('Vanilla', 5.00),
	('Nutmeg', 5.00),
	('Pistacio', 6.00),
	('Almond', 7.00);
	
INSERT INTO topping(name, price) VALUES 
	('Chocolate', 5.00),
	('Blueberry', 5.00),
	('Rasberry', 5.00),
	('Crispy', 6.00),
	('Strawberry', 6.00),
	('Rum/Raisin', 7.00),
	('Orange', 8.00),
	('Lemon', 8.00),
	('Blue cheese', 9.00);

INSERT INTO public.user(username, password, role, balance) VALUES 
    ('admin', 'admin', 'admin', 200),
    ('user', 'user', 'user', 200);

END;