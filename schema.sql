SET foreign_key_checks=0;

DROP TABLE IF EXISTS hotel;
CREATE TABLE hotel (
  hotel_id INT NOT NULL AUTO_INCREMENT,
  hotel_name VARCHAR(128) NOT NULL,
  city VARCHAR(45) NOT NULL,
  street_address VARCHAR(128) NOT NULL,
  hotel_phone_number VARCHAR(45) NOT NULL,
  PRIMARY KEY (hotel_id));

DROP TABLE IF EXISTS staff;
CREATE TABLE staff (
  staff_id INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  age INT NOT NULL,
  job_title VARCHAR(45) NOT NULL,
  department VARCHAR(45) NOT NULL,
  hotel_id INT NOT NULL,
  phone VARCHAR(45) NULL,
  address VARCHAR(45) NULL,
  PRIMARY KEY (staff_id),
  CHECK(age>=0));
    
DROP TABLE IF EXISTS room;
CREATE TABLE room (
  hotel_id INT NOT NULL,
  room_number INT NOT NULL,
  room_type VARCHAR(45) NOT NULL,
  availability tinyint(1) NOT NULL DEFAULT 0, 
  PRIMARY KEY (hotel_id, room_number));

DROP TABLE IF EXISTS room_type;
CREATE TABLE room_type (
  room_type VARCHAR(45) NOT NULL,
  max_occupancy INT NOT NULL,
  nightly_rate INT NOT NULL,
  PRIMARY KEY (room_type),
  CHECK(max_occupancy>=0 AND nightly_rate>=0));

DROP TABLE IF EXISTS service_record ;
CREATE TABLE service_record (
  service_id INT NOT NULL AUTO_INCREMENT,
  service_type VARCHAR(45) NOT NULL,
  staff_id INT NULL,
  checkin_id INT NOT NULL,
  PRIMARY KEY (service_id));

DROP TABLE IF EXISTS service_type ;
CREATE TABLE service_type (
  service_type VARCHAR(45) NOT NULL,
  fee INT NOT NULL,
  PRIMARY KEY (service_type),
  CHECK(fee>=0));

DROP TABLE IF EXISTS checkin;
CREATE TABLE checkin (
  checkin_id INT NOT NULL AUTO_INCREMENT,
  checkin_time DATETIME NOT NULL,
  checkout_time DATETIME NULL,
  hotel_id INT NOT NULL,
  room_number INT NOT NULL,
  guest_num INT NOT NULL,
  customer_id INT NOT NULL,
  account_id INT,
  amount INT NULL,
  PRIMARY KEY (checkin_id),
  CHECK(guest_num>=0 AND amount>=0));

DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
  customer_id INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  date_of_birth DATE NULL,
  phone VARCHAR(45) NULL,
  email VARCHAR(45) NULL,
  PRIMARY KEY (customer_id));

DROP TABLE IF EXISTS account;
CREATE TABLE account (
  account_id INT NOT NULL AUTO_INCREMENT,
  billing_address VARCHAR(128) NOT NULL,
  payment_method VARCHAR(32) NOT NULL,
  card_num INT NULL,
  customer_id INT NOT NULL,
  payer_ssn VARCHAR(45) NOT NULL,
  PRIMARY KEY (account_id));

ALTER TABLE room 
ADD FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id),
ADD FOREIGN KEY (room_type) REFERENCES room_type(room_type);

ALTER TABLE staff 
 ADD FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id);

ALTER TABLE account 
ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

ALTER TABLE service_record 
ADD FOREIGN KEY (service_type) REFERENCES service_type(service_type),
ADD FOREIGN KEY (checkin_id) REFERENCES checkin(checkin_id);

ALTER TABLE checkin 
ADD FOREIGN KEY (hotel_id, room_number) REFERENCES room(hotel_id, room_number),
ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
ADD FOREIGN KEY (account_id) REFERENCES account(account_id);


SET foreign_key_checks=1;

delimiter //

DROP TRIGGER IF EXISTS trig_staff_check;

CREATE TRIGGER trig_staff_check BEFORE INSERT ON staff 
FOR EACH ROW 
BEGIN 
IF NEW.age<=0 THEN 
SIGNAL SQLSTATE '45000' 
SET MESSAGE_TEXT='age violation';
END IF; 
END
//

DROP TRIGGER IF EXISTS trig_room_type_check;

CREATE TRIGGER trig_room_type_check BEFORE INSERT ON room_type 
FOR EACH ROW 
BEGIN 
IF (NEW.max_occupancy<=0 AND NEW.nightly_rate<0) THEN 
SIGNAL SQLSTATE '45000' 
SET MESSAGE_TEXT='value violation';
END IF; 
END
//

DROP TRIGGER IF EXISTS trig_service_type_check;

CREATE TRIGGER trig_service_type_check BEFORE INSERT ON service_type 
FOR EACH ROW 
BEGIN 
IF NEW.fee<0 THEN 
SIGNAL SQLSTATE '45000' 
SET MESSAGE_TEXT='fee violation';
END IF; 
END
//


delimiter ;


#done
INSERT INTO hotel(hotel_id,hotel_name, city, street_address, hotel_phone_number) VALUES 
(0001,'Hotel A', 'Raleigh', '21 ABC St , Raleigh NC 27', '919'),
(0002,'Hotel B', 'Rochester', '25 XYZ St , Rochester NY 54', '718'),
(0003,'Hotel C', 'Greensboro', '29 PQR St , Greensboro NC 27', '984'),
(0004,'Hotel D', 'Raleigh', '28 GHW St , Raleigh NC 32', '920');

#done
INSERT INTO staff(staff_id, name, age, job_title, department, hotel_id, phone, address) VALUES
(100, 'Mary', 40, 'Manager', 'Management', 0001, '654', '90 ABC St , Raleigh NC 27'),
(101, 'John', 45, 'Manager', 'Management', 0002, '564', '798 XYZ St , Rochester NY 54'),
(102, 'Carol', 55, 'Manager', 'Management', 0003, '546', '351 MH St , Greensboro NC 27'),
(103, 'Emma', 55, 'Front Desk Staff', 'Management', 0001, '546', '49 ABC St , Raleigh NC 27'),
(104, 'Ava', 55, 'Catering Staff', 'Catering', 0001, '777', '425 RG St , Raleigh NC 27'),
(105, 'Peter', 52, 'Manager', 'Management', 0004, '724', '475 RG St , Raleigh NC 27'),
(106, 'Olivia', 27, 'Front Desk Staff', 'Management', 0004, '799', '325 PD St , Raleigh NC 27');

#done
INSERT INTO customer (customer_id, name, date_of_birth, phone, email) VALUES
(1001, 'David', '1980-01-30', '123', 'david@gmail.com'),
(1002, 'Sarah', '1971-01-30', '456', 'sarah@gmail.com'),
(1003, 'Joseph', '1987-01-30', '789', 'joseph@gmail.com'),
(1004, 'Lucy', '1985-01-30', '213', 'lucy@gmail.com');

#done
INSERT INTO room_type(room_type, max_occupancy, nightly_rate) VALUES
('Economy', 1, 100),
('Deluxe', 2, 200),
('Executive', 3, 1000),
('Presidential', 4, 5000);

#done
INSERT INTO room(hotel_id, room_number, room_type, availability) VALUES 
(0001, 01, 'Economy', 1),
(0001, 02, 'Deluxe', 1),
(0002, 03, 'Economy', 1),
(0003, 02, 'Executive', 0),
(0004, 01, 'Presidential', 1),
(0001, 05, 'Deluxe', 1);

#done
INSERT INTO account(account_id, billing_address, payment_method, card_num, customer_id, payer_ssn) VALUES
(1, '980 TRT St , Raleigh NC', 'credit', 1052, 1001, '593-9846'),
(2, '7720 MHT St , Greensboro NC', 'hotel credit', 3020, 1002, '777-8352'),
(3, '231 DRY St , Rochester NY 78', 'credit', 2497, 1003, '858-9430'),
(4, '24 BST Dr , Dallas TX 14', 'cash', NULL, 1004, '440-9328');

#done
INSERT INTO checkin(checkin_id, checkin_time, checkout_time, hotel_id, room_number, guest_num, customer_id, account_id, amount) VALUES
(1, '2017-05-10 15:17:01', '2017-05-13 10:22:01', 0001, 01, 1, 1001, 1, NULL),
(2, '2017-05-10 16:11:01', '2017-05-13 09:27:01', 0001, 02, 2, 1002, 2, NULL),
(3, '2016-05-10 15:45:01', '2016-05-14 11:10:01', 0002, 03, 1, 1003, 3, NULL),
(4, '2018-05-10 14:30:01', '2018-05-12 10:00:01', 0003, 02, 2, 1004, 4, NULL);

#done
INSERT INTO service_type(service_type, fee)VALUES
('phone bills', 5),
('dry cleaning', 16),
('gyms', 15),
('room service', 10),
('special requests', 20);

#done
INSERT INTO service_record(service_id, service_type, staff_id, checkin_id)VALUES
(1, 'dry cleaning', 100, 1),
(2, 'gyms', 100, 1),
(3, 'gyms', 101, 2),
(4, 'room service', 102, 3),
(5, 'phone bills', 103, 4);

# Update all the check in bill amount
UPDATE
 checkin AS C
 INNER JOIN
  (SELECT checkin.checkin_id, room_type.nightly_rate
  FROM checkin, room, room_type
  WHERE room.room_type = room_type.room_type
  AND checkin.hotel_id=room.hotel_id
  AND checkin.room_number=room.room_number) AS rate
  ON C.checkin_id=rate.checkin_id
 INNER JOIN
  (SELECT checkin_id, SUM(ST.fee) AS tot_price
  FROM (service_record AS SR
   INNER JOIN
   service_type AS ST
   ON SR.service_type=ST.service_type)
  GROUP BY checkin_id) AS service
  ON C.checkin_id=service.checkin_id
SET C.amount=DATEDIFF(C.checkout_time, C.checkin_time)*rate.nightly_rate+service.tot_price;

# Update discount
UPDATE checkin AS C INNER JOIN account AS A ON C.account_id = A.account_id
SET C.amount = C.amount * 0.95
WHERE A.payment_method = 'hotel credit';
