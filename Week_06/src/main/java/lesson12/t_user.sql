DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id`                    BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `username`              VARCHAR (20) COMMENT '用户名',
    `pwd`                   VARCHAR (64) COMMENT '密码',
    `gender`                TINYINT COMMENT '性别: 0:男，1:女',
    `birthday`              DATETIME     COMMENT '生日',
    `state`                 TINYINT COMMENT '状态: 0:正常，1:删除',
    `created_time`          TIMESTAMP COMMENT '创建时间',
    `updated_time`          TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';