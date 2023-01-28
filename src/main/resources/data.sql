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




