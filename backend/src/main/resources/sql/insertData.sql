-- insert initial test data
-- the id is hardcoded to enable references between further test data
INSERT INTO breed (ID, NAME, DESCRIPTION)
VALUES (1, 'Arabian', 'a'),
       (2, 'Morgan', 'm'),
       (3, 'Paint', 'p');

INSERT INTO horse (ID, NAME, DESCRIPTION, BIRTH_DATE, IS_MALE, BREED_ID, BREED_NAME, FATHER_ID, MOTHER_ID)
VALUES (1, 'Joesph', 'lazy, old', '2017-10-03', true, 2, 'Morgan', 0, 0),
       (2, 'Maria', '', '2011-06-23', false, 1, 'Arabian'),
       (3, 'Alberto', 'nervös, neu', '2011-06-23', true, null, null);


