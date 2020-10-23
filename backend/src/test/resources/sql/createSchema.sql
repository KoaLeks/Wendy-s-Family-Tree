CREATE TABLE IF NOT EXISTS breed
(
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  name              VARCHAR(255) NOT NULL,
  description       VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS horse
(
  id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
  name                        VARCHAR(255)          NOT NULL,
  description                 VARCHAR(255),
  birth_date                  DATETIME              NOT NULL,
  is_male                     BOOLEAN               NOT  NULL,
  breed_id                    BIGINT,
  father_id                   BIGINT,
  mother_id                   BIGINT,
  FOREIGN KEY (breed_id)      REFERENCES breed(id)
);