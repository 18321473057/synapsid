create table t_test
(
    id bigint unsigned not null comment '主键' primary key,
    is_active tinyint unsigned not null comment '是否有效，用于标识数据是否已被逻辑删除,1表示有效、未删除，0表示无效、已删除',
    create_time datetime not null comment '创建时间',
    create_user_code varchar(50) not null comment '创建人',
    modify_time datetime null comment '修改时间',
    modify_user_code varchar(50) null comment '修改人'
) comment '测试表';