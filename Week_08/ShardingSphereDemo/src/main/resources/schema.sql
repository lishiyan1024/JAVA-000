create database if not exists `db0` default character set utf8mb4 collate utf8mb4_unicode_ci;

use `db0`;

create table if not exists `t_order_0`
(
    id bigint(20) auto_increment comment '主键' primary key,
	create_time datetime comment '创建时间',
	update_time datetime comment '更新时间',
	order_no varchar(24) comment '订单号',
	user_id bigint(20) comment '下单用户id',
	product_id bigint(20) comment '商品id',
	pay_amount int(11) comment '支付金额'，
	order_status smallint(6) comment '订单状态',
	expire_time datetime comment '订单过期时间'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_unicode_ci comment '订单表';

create table `t_order_1` like `t_order_0`;
create table `t_order_2` like `t_order_0`;
create table `t_order_3` like `t_order_0`;
create table `t_order_4` like `t_order_0`;
create table `t_order_5` like `t_order_0`;
create table `t_order_6` like `t_order_0`;
create table `t_order_7` like `t_order_0`;
create table `t_order_8` like `t_order_0`;
create table `t_order_9` like `t_order_0`;
create table `t_order_10` like `t_order_0`;
create table `t_order_11` like `t_order_0`;
create table `t_order_12` like `t_order_0`;
create table `t_order_13` like `t_order_0`;
create table `t_order_14` like `t_order_0`;
create table `t_order_15` like `t_order_0`;

create database if not exists `db1` default character set utf8mb4 collate utf8mb4_unicode_ci;

use `db1`;

create table if not exists `t_order_0`
(
    id bigint(20) auto_increment comment '主键' primary key,
	create_time datetime comment '创建时间',
	update_time datetime comment '更新时间',
	order_no varchar(24) comment '订单号',
	user_id bigint(20) comment '下单用户id',
	product_id bigint(20) comment '商品id',
	pay_amount int(11) comment '支付金额'，
	order_status smallint(6) comment '订单状态',
	expire_time datetime comment '订单过期时间'
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_unicode_ci comment '订单表';

create table `t_order_1` like `t_order_0`;
create table `t_order_2` like `t_order_0`;
create table `t_order_3` like `t_order_0`;
create table `t_order_4` like `t_order_0`;
create table `t_order_5` like `t_order_0`;
create table `t_order_6` like `t_order_0`;
create table `t_order_7` like `t_order_0`;
create table `t_order_8` like `t_order_0`;
create table `t_order_9` like `t_order_0`;
create table `t_order_10` like `t_order_0`;
create table `t_order_11` like `t_order_0`;
create table `t_order_12` like `t_order_0`;
create table `t_order_13` like `t_order_0`;
create table `t_order_14` like `t_order_0`;
create table `t_order_15` like `t_order_0`;
