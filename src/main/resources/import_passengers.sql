BEGIN;

INSERT INTO passengers(id, surname, firstname, email_address) VALUES(NEXTVAL('passengers_sequence_in_database'), 'ASLIKH', 'Walid', 'walidaslikh@gmail.com');
INSERT INTO passengers(id, surname, firstname, email_address) VALUES(NEXTVAL('passengers_sequence_in_database'), 'ASLIKH1', 'Walid1', 'asliikhwalid@gmail.com');

COMMIT;