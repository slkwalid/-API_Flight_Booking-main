BEGIN;

INSERT INTO reservations(id, flight_id, passenger_id) VALUES(NEXTVAL('reservations_sequence_in_database'), 1, 1);
INSERT INTO reservations(id, flight_id, passenger_id) VALUES(NEXTVAL('reservations_sequence_in_database'), 1, 2);
INSERT INTO reservations(id, flight_id, passenger_id) VALUES(NEXTVAL('reservations_sequence_in_database'), 2, 1);
INSERT INTO reservations(id, flight_id, passenger_id) VALUES(NEXTVAL('reservations_sequence_in_database'), 2, 2);

COMMIT;