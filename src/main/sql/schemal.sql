-- 数据库初始化脚本

-- 创建数据库
CREATE database seckill;

-- 使用数据库
USE seckill;


-- 秒杀商品库存表
CREATE TABLE seckill(
  seckill_id  BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品id',
  name VARCHAR(120) NOT NULL COMMENT '商品名字',
  num INT NOT NULL COMMENT '商品库存数量',
  start_time TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '本条记录创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT ='秒杀商品库存表';


INSERT INTO
    seckill(name,num,start_time,end_time)
VALUES
  ('1000元秒杀披头士原版CD',100,'2013-11-08 10:00:00','2013-11-09 10:00:00'),
  ('5000元秒杀MACPro',200,'2013-11-08 10:00:00','2013-11-09 10:00:00'),
  ('7000元秒杀MACair',300,'2013-11-08 10:00:00','2013-11-09 10:00:00'),
  ('9000元秒杀IPhone',500,'2013-11-08 10:00:00','2013-11-09 10:00:00');

-- 秒杀成功购买行为表

CREATE TABLE success_killed(
  seckill_id BIGINT NOT NULL COMMENT '商品id',
  user_phone BIGINT NOT NULL COMMENT '用户手机号',
  state TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效，0：成功，1：已付款，2：已发货',
  create_time TIMESTAMP NOT NULL  COMMENT '本条记录创建时间', -- 为什么不适用默认值
  PRIMARY KEY (seckill_id,user_phone),
  key idx_create_time(create_time)
)ENGINE = InnoDB DEFAULT CHARSET =utf8 COMMENT '秒杀成功明细表';



