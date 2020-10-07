-- insert initial test data
-- the id is hardcoded to enable references between further test data
INSERT INTO breed (ID, NAME)
VALUES (1, 'Arabian'),
       (2, 'Morgan'),
       (3, 'Paint');

INSERT INTO horse (ID, NAME, description, birth_date, is_male, breed_id, breed_name)
VALUES (1, 'Joesph', 'lazy, old', '2017-10-03', true, 2, 'Morgan'),
       (2, 'Maria', '', '2011-06-23', false, 1, 'Arabian'),
       (3, 'Alberto', 'nervös, neu', '2011-06-23', true, null , null );


