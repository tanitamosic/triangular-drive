-- addresses
insert into addresses (street, number, city) values ('Sutjeska', '12', 'KG');
insert into addresses (street, number, city) values ('Bulevar oslobođenja', '120', 'NS');
insert into addresses (street, number, city) values ('Bulevar oslobođenja', '123', 'NS');
insert into addresses (street, number, city) values ('Bulevar oslobođenja', '25', 'NS');
insert into addresses (street, number, city) values ('Bulevar oslobođenja', '80', 'NS');
insert into addresses (street, number, city) values ('Bulevar oslobođenja', '60', 'NS');
insert into addresses (street, number, city) values ('Bulevar cara Lazara', '10', 'NS');
insert into addresses (street, number, city) values ('Bulevar cara Lazara', '54', 'NS');
insert into addresses (street, number, city) values ('Bulevar cara Lazara', '70', 'NS');
insert into addresses (street, number, city) values ('Bulevar cara Lazara', '33', 'NS');
insert into addresses (street, number, city) values ('Bulevar cara Lazara', '27', 'NS');
insert into addresses (street, number, city) values ('Vojvode Putnika', '10', 'NS');
insert into addresses (street, number, city) values ('Vojvode Putnika', '17', 'NS');
insert into addresses (street, number, city) values ('Vojvode Putnika', '35', 'NS');
insert into addresses (street, number, city) values ('Vojvode Putnika', '28', 'NS');
insert into addresses (street, number, city) values ('Stražilovska', '7', 'NS');
insert into addresses (street, number, city) values ('Stražilovska', '13', 'NS');
insert into addresses (street, number, city) values ('Stražilovska', '22', 'NS');
insert into addresses (street, number, city) values ('Stražilovska', '46', 'NS');
insert into addresses (street, number, city) values ('Bulevar despota Stefana', '27', 'NS');
insert into addresses (street, number, city) values ('Bulevar despota Stefana', '3', 'NS');
insert into addresses (street, number, city) values ('Bulevar despota Stefana', '8', 'NS');
insert into addresses (street, number, city) values ('Bulevar despota Stefana', '12', 'NS');
insert into addresses (street, number, city) values ('Bulevar Jaše Tomića', '23', 'NS');
insert into addresses (street, number, city) values ('Bulevar Jaše Tomića', '38', 'NS');
insert into addresses (street, number, city) values ('Bulevar Jaše Tomića', '59', 'NS');
insert into addresses (street, number, city) values ('Bulevar Jaše Tomića', '12', 'NS');
insert into addresses (street, number, city) values ('Bulevar Jaše Tomića', '84', 'NS');
insert into addresses (street, number, city) values ('Rumenačka', '15', 'NS');
insert into addresses (street, number, city) values ('Hajduk Veljkova', '18', 'NS');

-- CARS
insert into cars (model, make, seats, color, pet_friendly, baby_friendly, type) values
('Accent', 'HYUNDAI', 4, 'RED', FALSE, TRUE, 'STANDARD');
insert into cars (model, make, seats, color, pet_friendly, baby_friendly, type) values
('Clio', 'RENAULT', 4, 'BLUE', TRUE, TRUE, 'STANDARD');
insert into cars (model, make, seats, color, pet_friendly, baby_friendly, type) values
('Caravan', 'NISSAN', 4, 'WHITE', FALSE, FALSE, 'CARAVAN');

-- ADMINS
insert into users (type, name, last_name, email, password, phone, city, blocked, activated) values
('AD', 'Tanita', 'Mošić', 'admintm@siit.com', '{bcrypt}$2a$10$nlwpRrP8gTFa7ngDwDuB7.TmV1JmiU5nanPEdhbKdwCG/BrVhoIta', '064481513','NS', FALSE, TRUE);
insert into users (type, name, last_name, email, password, phone, city, blocked, activated) values
('AD', 'Petar', 'Kupusarević', 'adminpk@siit.com', '{bcrypt}$2a$10$nlwpRrP8gTFa7ngDwDuB7.TmV1JmiU5nanPEdhbKdwCG/BrVhoIta', '06444865','NS', FALSE, TRUE);
insert into users (type, name, last_name, email, password, phone, city, blocked, activated) values
('AD', 'Mihajlo', 'Đorđević', 'adminmdj@siit.com', '{bcrypt}$2a$10$nlwpRrP8gTFa7ngDwDuB7.TmV1JmiU5nanPEdhbKdwCG/BrVhoIta', '06459613','NS', FALSE, TRUE);
-- CLIENTS
insert into users (type, name, last_name, email, password, phone, city, credit_available, credit_reserved, blocked, activated) values
('CL', 'Marko', 'Trijanić', 'markotrijanic@gmail.com', '{bcrypt}$2a$10$fl3Ele2FvPgXUC091uHg/eHxy8zKiCHGYjF8FW66AV/z9eJPgkcwa', '0602135578','NS', 800, 120, FALSE, TRUE);
insert into users (type, name, last_name, email, password, phone, city, credit_available, credit_reserved, blocked, activated) values
('CL', 'Ivana', 'Petrović', 'ivanapetrovic@gmail.com', '{bcrypt}$2a$10$4H1TlPp1tmZBATqj4cgVl.BVW8GWpbQ2OXd80qNGrpy6eTTAzs0xG', '060287964','NS', 52, 48, FALSE, TRUE);
insert into users (type, name, last_name, email, password, phone, city, credit_available, credit_reserved, blocked, activated) values
('CL', 'Vlada', 'Garović', 'valdagarovic@gmail.com', '{bcrypt}$2a$10$wRDr7j6Gc/zraG.r1iPMhO9pPzhlUTim5yWBVW7IZpav25154Qvma', '060264895','NS', 30, 0, FALSE, TRUE);
insert into users (type, name, last_name, email, password, phone, city, credit_available, credit_reserved, blocked, activated) values
('CL', 'Dragor', 'Draganović', 'dragordraganovic@gmail.com', '{bcrypt}$2a$10$3.MtlmHbIwdHUm5m406zQu/vq9egwaXE7vgd4DKWhF19OmUplCa5.', '064785765','NS', 52, 48, FALSE, TRUE);
-- DRIVERS
insert into users (type, name, last_name, email, password, phone, city, blocked, activated, car_id) values
('DR', 'Milan', 'Akeljić', 'milanakeljic@gmail.com', '{bcrypt}$2a$10$mT/9g85GK6KdJaTxtb.tjurqziB2nhxcxP6xPDjtghsIfuKjSTOE.', '062896525','NS', FALSE, TRUE,1);
insert into users (type, name, last_name, email, password, phone, city, blocked, activated, car_id) values
('DR', 'Davor', 'Savić', 'davorsavic@gmail.com', '{bcrypt}$2a$10$kmc.XyxMeZPNQtXtwCsqSu7/VCY20v.d9YEXW/m5g7KGEI6Mkd.12', '063848152','NS', FALSE, TRUE,2);
insert into users (type, name, last_name, email, password, phone, city, blocked, activated,car_id) values
('DR', 'Ognjen', 'Mirković', 'ognjenmirkovic@gmail.com', '{bcrypt}$2a$10$aqO4W5nLe1PETNw922drIOG097Tjn7yI90LeX0X9j5sB66QHmNbSS', '062896525','NS', FALSE, TRUE,3);

-- PRICES BY CAR TYPE
insert into prices(car_type,price) values ('STANDARD',100);
insert into prices(car_type,price) values ('CARAVAN',170);
insert into prices(car_type,price) values ('LIMOUSINE',250);
insert into prices(car_type,price) values ('VAN',200);
insert into prices(car_type,price) values ('MINIVAN',180);
insert into prices(car_type,price) values ('SUV',190);




INSERT INTO REPORT (solved, text, reportee, reporter) VALUES (false, 'stringcina', 4, 8);
INSERT INTO REPORT (solved, text, reportee, reporter) VALUES (false, 'stringcina', 5, 9);
INSERT INTO REPORT (solved, text, reportee, reporter) VALUES (false, 'stringcina', 10, 6);


-- TEMP

INSERT INTO routes (start_id, destination_id, distance)
VALUES (1, 6, 2.791797768505874);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (2, 7, 0.29917563808326775);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (3, 8, 1.5087727021655517);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (4, 9, 0.7342933777317417);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (5, 10, 8.609164246709565);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (6, 11, 5.346136142604117);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (7, 12, 0.12474365276651578);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (8, 13, 5.927415182557788);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (9, 14, 3.293112827951148);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (10, 15, 0.12830011099893346);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (11, 16, 0.6994591331769329);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (12, 17, 1.4921504513306483);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (13, 18, 8.322089273396898);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (14, 19, 5.528972936626484);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (15, 20, 5.620386865575409);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (16, 21, 9.712955166304907);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (17, 22, 9.872188844808747);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (18, 23, 4.768577996423902);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (19, 24, 6.656771884758296);
INSERT INTO routes (start_id, destination_id, distance)
VALUES (20, 25, 5.886518853394753);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (1, 8, '2023-01-01T00:00:00', '2023-01-01T01:15:00', 'FINISHED', 435.0157322207049);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (2, 9, '2023-01-02T00:00:00', '2023-01-02T01:15:00', 'FINISHED', 135.90107656999214);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (3, 10, '2023-01-03T00:00:00', '2023-01-03T01:15:00', 'FINISHED', 281.0527242598662);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (4, 8, '2023-01-04T00:00:00', '2023-01-04T01:15:00', 'FINISHED', 188.115205327809);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (5, 9, '2023-01-05T00:00:00', '2023-01-05T01:15:00', 'FINISHED', 1133.0997096051478);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (6, 10, '2023-01-06T00:00:00', '2023-01-06T01:15:00', 'FINISHED', 741.536337112494);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (7, 8, '2023-01-07T00:00:00', '2023-01-07T01:15:00', 'FINISHED', 114.9692383319819);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (8, 9, '2023-01-08T00:00:00', '2023-01-08T01:15:00', 'FINISHED', 811.2898219069345);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (9, 10, '2023-01-09T00:00:00', '2023-01-09T01:15:00', 'FINISHED', 495.17353935413774);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (10, 8, '2023-01-10T00:00:00', '2023-01-10T01:15:00', 'FINISHED', 115.39601331987201);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (11, 9, '2023-01-11T00:00:00', '2023-01-11T01:15:00', 'FINISHED', 183.93509598123194);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (12, 10, '2023-01-12T00:00:00', '2023-01-12T01:15:00', 'FINISHED', 279.0580541596778);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (13, 8, '2023-01-13T00:00:00', '2023-01-13T01:15:00', 'FINISHED', 1098.6507128076278);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (14, 9, '2023-01-14T00:00:00', '2023-01-14T01:15:00', 'FINISHED', 763.4767523951781);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (15, 10, '2023-01-15T00:00:00', '2023-01-15T01:15:00', 'FINISHED', 774.446423869049);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (16, 8, '2023-01-16T00:00:00', '2023-01-16T01:15:00', 'FINISHED', 1265.5546199565888);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (17, 9, '2023-01-17T00:00:00', '2023-01-17T01:15:00', 'FINISHED', 1284.6626613770495);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (18, 10, '2023-01-18T00:00:00', '2023-01-18T01:15:00', 'FINISHED', 672.2293595708683);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (19, 8, '2023-01-19T00:00:00', '2023-01-19T01:15:00', 'FINISHED', 898.8126261709955);
INSERT INTO rides (route_id, driver_id, departure_time, arrival_time, status, price)
VALUES (20, 9, '2023-01-20T00:00:00', '2023-01-20T01:15:00', 'FINISHED', 806.3822624073704);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (1, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (2, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (3, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (3, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (3, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (4, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (5, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (5, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (5, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (6, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (6, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (7, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (8, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (8, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (8, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (8, 7);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (9, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (9, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (9, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (10, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (11, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (11, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (12, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (12, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (12, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (13, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (14, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (14, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (15, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (15, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (15, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (15, 7);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (16, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (16, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (17, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (17, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (17, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (17, 7);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (18, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (18, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (18, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (19, 4);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (19, 5);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (19, 6);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (19, 7);
INSERT INTO ride_passengers (ride_id, passenger_id) VALUES (20, 4);