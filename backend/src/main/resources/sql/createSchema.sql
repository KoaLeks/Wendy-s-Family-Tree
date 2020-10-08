CREATE TABLE IF NOT EXISTS breed
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS horse
(
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(255)     NOT NULL,
  description   VARCHAR(255),
  birth_date    DATETIME         NOT NULL,
  is_male       BOOLEAN          NOT NULL,
  breed_id      BIGINT           REFERENCES breed(id),
  breed_name    VARCHAR(255),
  father_id     BIGINT           ,
  mother_id     BIGINT
);
