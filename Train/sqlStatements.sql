#############################################################
# Create Table Statements
CREATE TABLE Manager (
                    username VARCHAR (30) NOT NULL,
                    password VARCHAR (30) NOT NULL,
                    PRIMARY KEY (username)
                );


CREATE TABLE Customer (
                  email        VARCHAR (30) NOT NULL,
                  username     VARCHAR (30) NOT NULL,
                  password     VARCHAR (30) NOT NULL,
                  is_student   BIT NOT NULL,
                  PRIMARY KEY (username)
                );


CREATE TABLE PaymentInfo (
                  username          VARCHAR (30) NOT NULL,
                  card_number       INT NOT NULL,
                  cvv               INT NOT NULL,
                  expiration_date   DATE NOT NULL,
                  name_on_card      VARCHAR (30) NOT NULL,
                  PRIMARY KEY (card_number),
                  FOREIGN KEY (username) REFERENCES Customer(username)
                );


CREATE TABLE Review (
                  username          VARCHAR (30) NOT NULL,
                  review_number     INT NOT NULL,
                  train_number      INT NOT NULL,
                  comment           VARCHAR (200),
                  rating            ENUM('Excellent', 'Good', 'Bad', 'Very Bad', 'Neutral') NOT NULL,
                  PRIMARY KEY (review_number),
                  FOREIGN KEY (username) REFERENCES Customer(username),
                  FOREIGN KEY (train_number) REFERENCES Route(train_number)
                );


CREATE TABLE SystemInfo (
                  max_num_of_baggage      INT NOT NULL,
                  num_of_free_baggage     INT NOT NULL,
                  student_discount        BOOLEAN NOT NULL DEFAULT '0',
                  change_fee              INT NOT NULL,
                  PRIMARY KEY (max_num_of_baggage)
                );


CREATE TABLE Route (
                  first_class_price     INT NOT NULL,
                  second_class_price    INT NOT NULL,
                  train_number          INT NOT NULL,
                  PRIMARY KEY (train_number)
                );

CREATE TABLE Station (
                  station_name      VARCHAR (30) NOT NULL,
                  location          VARCHAR (30) NOT NULL,
                  PRIMARY KEY (station_name)
                );


CREATE TABLE Reserves (
                  reservation_id        INT NOT NULL,
                  train_number          INT NOT NULL,
                  class                 INT NOT NULL,
                  departure_date        DATE NOT NULL,
                  passenger_name        VARCHAR (30) NOT NULL,
                  num_of_baggages       INT NOT NULL,
                  departs_from          VARCHAR (30) NOT NULL,
                  arrives_at            VARCHAR (30) NOT NULL,
                  PRIMARY KEY (reservation_id, train_number),
                  FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),
                  FOREIGN KEY (train_number) REFERENCES Route(train_number)
                );


CREATE TABLE Stop (
                  train_number        INT NOT NULL,
                  stop_name           VARCHAR (30) NOT NULL,
                  arrival_time        INT NOT NULL,
                  departure_time      INT NOT NULL,
                  PRIMARY KEY (train_number, stop_name),
                  FOREIGN KEY (train_number) REFERENCES Route(train_number),
                  FOREIGN KEY (stop_name) REFERENCES Station(station_name));



CREATE TABLE  Reservation (
                  username          VARCHAR (30) NOT NULL,
                  reservation_id    INT NOT NULL,
                  total_cost        INT NOT NULL,
                  is_cancelled      BOOLEAN NOT NULL DEFAULT '0',
                  card_number       INT NOT NULL,
                  PRIMARY KEY (reservation_id),
                  FOREIGN KEY (username) REFERENCES Customer(username),
                  FOREIGN KEY (card_number) REFERENCES PaymentInfo(card_number) ON DELETE SET NULL
                );
#############################################################


#############################################################
# Queries

# Figure 1
# Login Page
select * from Customer where username = $username and password = $password


# Figure 2
# Register
INSERT INTO Customer (email, username, password, is_student) VALUES ('$myemail', '$myusername', '$mypassword', 0);


# Figure 3
# None needed 


# Figure 4
# Add School Info
UPDATE Customer SET is_student = b'1' WHERE username = '$username';


# Figure 5
# View Train Schedule
SELECT train_number, arrival_time, departure_time, stop_name FROM Stop WHERE train_number = '$train_number';


# Figure 6
# Search Train -> MakeReservation1
SELECT Route.train_number, CONCAT(DES.departure_time,' - ', ARS.arrival_time, '\n', 
TIMEDIFF(ARS.arrival_time, DES.departure_time)) as Time, DES.departure_time, ARS.arrival_time,
TIMEDIFF(ARS.arrival_time, DES.departure_time) as Duration,
Route.first_class_price, Route.second_class_price
FROM (Route
INNER JOIN Stop as ARS ON Route.train_number = ARS.train_number
INNER JOIN Stop as DES ON Route.train_number = DES.train_number)
WHERE DES.stop_name = '$departs' AND
ARS.stop_name = '$arrives' AND
TIMEDIFF(ARS.arrival_time, DES.departure_time) > '00:00:00';


# Figure 7
# Select Departure -> MakeReservation2
# None


# Figure 8 (i)
# Travel Extras and Passenger Info -> MakeReservation3
# None


# Figure 9 (ii)
# Make Reservation 1 -> Make Reservation 4
SELECT is_student FROM Customer WHERE username = '$username';
SELECT card_number FROM PaymentInfo WHERE username='$username';
INSERT INTO Reservation (username, total_cost, card_number) VALUES ('$username', '$total', '$card');
INSERT INTO Reserves (reservation_id, train_number, class, departure_date, passenger_name, 
num_of_baggages, departs_from, arrives_at) VALUES ('$RID', '$trainNum', '$class', '$depDate', '$passName', '$numBags', '$dep', '$arrive');



# Figure 10             
# Payment Information
SELECT card_number FROM PaymentInfo WHERE username='$username';
INSERT INTO PaymentInfo (username, card_number, cvv, expiration_date, name_on_card) VALUES ('$username', '$cardNumber', '$cvv', '$expiration', '$name');
DELETE FROM PaymentInfo WHERE card_number = '$todelete' AND username = '$username';


# Figure 11
# Confirmation Screen
# None


# Figure 12
# Update Reservation 1
SELECT departs_from FROM Reserves WHERE reservation_id = '$RID';

SELECT Route.train_number, Reserves.reservation_id, Reserves.class, Reserves.departure_date, concat(DES.departure_time, ' - ' , 
ARS.arrival_time, '\n', timediff(ARS.arrival_time, DES.departure_time)) as Time,
Concat(DStation.location, ' \n(', DES.stop_name, ')') as DepartsFrom,
Concat(AStation.location, ' \n(', ARS.stop_name, ')') as ArrivesAt,
Reservation.total_cost, Route.first_class_price, Route.second_class_price, Reserves.num_of_baggages, Reserves.passenger_name
from Route inner join Reserves on Route.train_number = Reserves.train_number
inner join Reservation on Reserves.reservation_id = Reservation.reservation_id
inner join Stop as ARS on Route.train_number = ARS.train_number
inner join Stop DES on Route.train_number = DES.train_number
inner join Station as AStation on ARS.stop_name = AStation.station_name
inner join Station as DStation on DES.stop_name = DStation.station_name
where Reserves.reservation_id = '$RID' and
Reserves.train_number = '$trainNumber' and
Reservation.username = '$username' and
DES.stop_name = '$departsFrom' and
ARS.stop_name = '$arrivesAt';


# Figure 13
# Update Reservation 2
# None


# Figure 14
# Update Reservation 3
UPDATE Reserves SET departure_date = '$newDate' WHERE reservation_id = '$RID' and train_number = '$trainNo';
UPDATE Reservation SET total_cost = total_cost + 50 WHERE reservation_id = '$RID';


# Figure 15
# Cancel Reservation 1
SELECT departs_from FROM Reserves WHERE reservation_id = '$RID';

SELECT Route.train_number, Reserves.reservation_id, Reserves.class, Reserves.departure_date, concat(DES.departure_time, ' - ' , 
ARS.arrival_time, '\n', timediff(ARS.arrival_time, DES.departure_time)) as Time,
Concat(DStation.location, ' \n(', DES.stop_name, ')') as DepartsFrom,
Concat(AStation.location, ' \n(', ARS.stop_name, ')') as ArrivesAt,
Reservation.total_cost, Route.first_class_price, Route.second_class_price, Reserves.num_of_baggages, Reserves.passenger_name
from Route inner join Reserves on Route.train_number = Reserves.train_number
inner join Reservation on Reserves.reservation_id = Reservation.reservation_id
inner join Stop as ARS on Route.train_number = ARS.train_number
inner join Stop DES on Route.train_number = DES.train_number
inner join Station as AStation on ARS.stop_name = AStation.station_name
inner join Station as DStation on DES.stop_name = DStation.station_name
where Reserves.reservation_id = '$RID' and
Reserves.train_number = '$trainNumber' and
Reservation.username = '$username' and
DES.stop_name = '$departsFrom' and
ARS.stop_name = '$arrivesAt';


# Figure 16
# Cancel Reservation 2
SELECT reservation_id FROM Reservation WHERE username = '$username';

UPDATE Reservation set is_cancelled = '1',
total_cost = (Case DATEDIFF(NOW(), reserve.departure_date)
                WHEN 0 AND total_cost > 50 then total_cost - 50
                WHEN 0 AND total_cost < 50 then 0
                WHEN 1 AND total_cost > 50 then total_cost - 50
                WHEN 1 AND total_cost < 50 then 0
                WHEN 2 then total_cost * 0.5
                WHEN 3 then total_cost * 0.5
                WHEN 4 then total_cost * 0.5
                WHEN 5 then total_cost * 0.5
                WHEN 6 then total_cost * 0.5
                WHEN 7 then total_cost * 0.2
              else total_cost * 0.2)
                FROM Reserves reserve join Reservation reservation
                ON reserve.reservation_id = reservation.reservation_id
                WHERE reserve.reservation_id = '$reservationQuery'
                AND reservation.reservation_id = '$reservationQuery';


# Figure 17
# Give Review
INSERT INTO Review (username, train_number, comments, rating) VALUES ('$username', '$trainNumber', '$comments', '$rating');


# Figure 18
# None

 
# Figure 19
# Revenue Report
SELECT MONTH(NOW()), SUM(Reservation.total_cost) FROM Reservation
WHERE Reservation.reservation_id in (
SELECT reservation_id FROM Reserves WHERE MONTH(departure_date) = MONTH(NOW()))
UNION
SELECT MONTH(NOW()) - 1, SUM(Reservation.total_cost) FROM Reservation
WHERE Reservation.reservation_id in (
SELECT reservation_id FROM Reserves WHERE MONTH(departure_date) = MONTH(NOW()) - 1)
UNION
SELECT MONTH(NOW()) - 2, SUM(Reservation.total_cost) FROM Reservation
WHERE Reservation.reservation_id in (
SELECT reservation_id FROM Reserves WHERE MONTH(departure_date) = MONTH(NOW()) - 2);


# Figure 20
# Popular Report
(SELECT month(departure_date) as m, train_number as train, count(train_number) as total
from Reserves x , Reservation y
where x.reservation_id = y.reservation_id and
month(departure_date) = month(now())
GROUP BY Train)
union
(select month(departure_date) as m, train_number as train, count(train_number) as total
from Reserves x , Reservation y
where x.reservation_id = y.reservation_id and
month(departure_date) = month(now()) - 1
GROUP BY Train)
UNION
(select month(departure_date) as m, train_number as train, count(train_number) as total
from Reserves x , Reservation y
where x.reservation_id = y.reservation_id and
month(departure_date) = month(now()) - 2
GROUP BY Train)
ORDER BY m DESC, total DESC;
#############################################################
