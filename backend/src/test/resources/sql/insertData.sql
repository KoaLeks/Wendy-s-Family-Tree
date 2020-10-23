-- insert initial test data
-- the id is hardcoded to enable references between further test data
INSERT INTO breed (ID, NAME, DESCRIPTION)
VALUES (1, 'Arabian', 'Originated on the Arabian Peninsula'),
       (2, 'Morgan', ''),
       (3, 'Paint', ''),
       (4, 'Danish Warmblood', 'Sport horse breed from Denmark'),
       (5, 'Giara', 'Native to the island of Sardinia'),
       (6, 'Banker', 'Feral horses living on barrier islands in North Carolina'),
       (7, 'Lokai', 'Mountain horse in Tajikistan, used as riding/packhorse'),
       (8, 'Danube Delta', 'Feral horses in Romania'),
       (9, 'Noma', 'Critically-endangered Japanese breed'),
       (10, 'Spanish Barb', '');

INSERT INTO horse (ID, NAME, DESCRIPTION, BIRTH_DATE, IS_MALE, BREED_ID, FATHER_ID, MOTHER_ID)
VALUES (1, 'Joesph', 'lazy, old',   '2017-10-03', true,  2, 10, null),
       (2, 'Maria', 'fast',         '2011-06-23', false, 1, null, null),
       (3, 'Alberto', 'nervös',     '2015-01-31', true,  3, null, 6),
       (4, 'Linda', 'schlachter',   '2011-12-21', false, 4, null, 2),
       (5, 'Charlie', '',           '2018-09-11', true,  2, 3, 4),
       (6, 'Mimi', '',              '1931-03-17', false,  1, null, null),
       (7, 'Jenny', '',             '2020-04-29', false, 1, null, 6),
       (8, 'Jimi', '',              '2019-06-23', true,  5, 1, 2),
       (9, 'Kiki', '',              '2019-06-23', false, 5, 1, 2),
       (10, 'Ronald', '',           '2015-11-13', true,  4, 3, null);


