CREATE TABLE `properties` (
    `id` bigint not null auto_increment,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    application varchar(255),
    profile varchar(255),
    label varchar(255),
    `key` varchar(255),
    `value` varchar(255),
    primary key (id)
) engine=InnoDB;