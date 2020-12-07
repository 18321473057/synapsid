create table if not exists t_test
(
    id              bigint     primary key,
    is_active       tinyint   ,
    create_time      datetime          ,
    create_user_code varchar(50)         ,
    modify_time      datetime          ,
    modify_user_code varchar(50)
);
