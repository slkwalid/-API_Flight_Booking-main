BEGIN;

INSERT INTO flights(id, number, origin, destination, departure_date, departure_time, arrival_date, arrival_time, plane_id) VALUES(NEXTVAL('flights_sequence_in_database'), 'V1', 'France', 'Maroc', '2023-12-04', '23:29:00', '2023-12-05', '01:29:00', 1);
INSERT INTO flights(id, number, origin, destination, departure_date, departure_time, arrival_date, arrival_time, plane_id) VALUES(NEXTVAL('flights_sequence_in_database'), 'V2', 'Maroc', 'France', '2023-12-01', '15:06:00', '2023-12-01', '18:30:00', 2);

COMMIT;