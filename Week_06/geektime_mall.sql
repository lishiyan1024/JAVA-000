CREATE TABLE geektime_mall_user
(
	id bigint(20) auto_increment comment '主键' primary key,
	create_time datetime comment '创建时间',
	update_time datetime comment '更新时间',
	mobile varchar(11) unique comment '手机号',
	email varchar(64) comment '邮箱',
	nick_name varchar(64) comment '昵称'
)
comment '用户表';

CREATE TABLE geektime_mall_product
(
	id bigint(20) auto_increment comment '主键' primary key,
	create_time datetime comment '创建时间',
	update_time datetime comment '更新时间',
	display_name varchar(128) comment '名称'
	detail varchar(256) comment '简介'
	on_sale tinyint(1) comment '是否在售',
	original_price decimal(10,2) comment '原价'
	img_url varchar(256) comment '图片地址'
)
comment '商品表';

CREATE TABLE geektime_mall_order
(
	id bigint(20) auto_increment comment '主键' primary key,
	create_time datetime comment '创建时间',
	update_time datetime comment '更新时间',
	order_no varchar(24) comment '订单号',
	user_id bigint(20) comment '下单用户id',
	product_id bigint(20) comment '商品id',
	pay_amount int(11) comment '支付金额'，
	original_amount int(11) comment '原价金额',
	discount_amount int(11) comment '折扣金额',
	order_status smallint(6) comment '订单状态',
	expire_time datetime comment '订单过期时间'
)
comment '订单表';
