drop table if exists `api_credential`;

CREATE TABLE `api_credential` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `provider` INT NOT NULL,
    `key` VARCHAR(255) NOT NULL,
    `value` VARCHAR(1024) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(`provider`, `key`)
);

drop table if exists `model_answer`;

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

drop table if exists `model_evaluation`;

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

INSERT INTO `api_credential` (`provider`, `key`, `value`) VALUES
(1, 'token', 'gpt_token_value'),
(2, 'secretKey', 'baichuan_secret_key_value'),
(2, 'accessToken', 'yiyan_access_token_value'),
(3, 'API_KEY', 'dashscope_api_key_value'),
(6, 'appId', '123456'),
(6, 'secretId', 'hunyuan_secret_id_value'),
(6, 'secretKey', 'hunyuan_secret_key_value');

INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_1', 'What is the capital of France?', 1, 'Model_XYZ', 'The capital of France is Paris.');
INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_2', 'What is the boiling point of water?', 2, 'Model_ABC', 'The boiling point of water is 100 degrees Celsius at sea level.');
INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_3', 'Who wrote Romeo and Juliet?', 1, 'Model_XYZ', 'Romeo and Juliet was written by William Shakespeare.');
INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_4', 'What is the distance to the Moon?', 1, 'Model_XYZ', 'The average distance to the Moon is about 384,400 km.');
INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_4', 'What is the distance to the Moon?', 2, 'Model_ABC', 'The Moon is approximately 384,400 kilometers away from Earth.');
INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_5', 'Who discovered penicillin?', 1, 'Model_XYZ', 'Penicillin was discovered by Alexander Fleming in 1928.');
INSERT INTO `model_answer` (`question_hash`, `question`, `provider`, `model_name`, `answer`)
VALUES
('hash_value_5', 'Who discovered penicillin?', 3, 'Model_DEF', 'Alexander Fleming is credited with the discovery of penicillin.');


INSERT INTO model_evaluation (provider, model_name, scenario_id, rating)
VALUES (1, 'ModelA', 101, 4);

INSERT INTO model_evaluation (provider, model_name, scenario_id, rating)
VALUES (2, 'ModelB', 101, 5);

INSERT INTO model_evaluation (provider, model_name, scenario_id, rating)
VALUES (2, 'ModelB', 102, 5);

INSERT INTO model_evaluation (provider, model_name, scenario_id, rating)
VALUES (1, 'ModelC', 103, 3);

INSERT INTO model_evaluation (provider, model_name, scenario_id, rating)
VALUES (3, 'ModelD', 104, 4);

INSERT INTO model_evaluation (provider, model_name, scenario_id, rating)
VALUES (1, 'ModelE', 105, 2);