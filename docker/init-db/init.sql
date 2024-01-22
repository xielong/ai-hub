CREATE TABLE `api_credential` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `provider` INT NOT NULL,
    `key` VARCHAR(255) NOT NULL,
    `value` VARCHAR(1024) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(`provider`, `key`)
);

CREATE TABLE `model_answer` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `question_hash` CHAR(64) NOT NULL,
    `question` TEXT NOT NULL,
    `provider` INT NOT NULL,
    `model_name` VARCHAR(255) NOT NULL,
    `answer` TEXT NOT NULL,
    `rating` TINYINT UNSIGNED,
    `comment` TEXT,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX(`question_hash`, `model_name`)
);

CREATE TABLE `model_evaluation` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `provider` INT NOT NULL,
    `model_name` VARCHAR(255) NOT NULL,
    `scenario_id` INT NOT NULL,
    `rating` TINYINT UNSIGNED  NOT NULL,
    `comment` TEXT,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX(`provider`, `model_name`, `scenario_id`)
);