GRANT ALL ON *.* TO ''@'%.%.%.%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS snowball
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;