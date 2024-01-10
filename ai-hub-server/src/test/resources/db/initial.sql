drop table if exists `api_credential`;

CREATE TABLE `api_credential` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `provider` INT NOT NULL,
    `key` VARCHAR(255) NOT NULL,
    `value` VARCHAR(1024) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(`provider`, `key`)
);

INSERT INTO `api_credential` (`provider`, `key`, `value`) VALUES
(1, 'token', 'gpt_token_value'),
(2, 'secretKey', 'baichuan_secret_key_value'),
(2, 'accessToken', 'yiyan_access_token_value'),
(3, 'API_KEY', 'dashscope_api_key_value'),
(5, 'appId', 'hunyuan_app_id_value'),
(6, 'secretId', 'hunyuan_secret_id_value'),
(7, 'secretKey', 'hunyuan_secret_key_value');