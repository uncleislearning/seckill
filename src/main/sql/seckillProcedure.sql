-- 使用MySQL存储过程来执行事务逻辑

DELIMITER **

CREATE PROCEDURE seckill.seckill_executeSecKillProcedure(
  IN  v_seckill_id BIGINT,IN v_user_phone BIGINT,IN v_kill_time BIGINT,OUT result INT
)
  BEGIN

    DECLARE insert_count INT DEFAULT 0;

    START TRANSACTION;
    INSERT IGNORE INTO success_killed(seckill_id, user_phone,state) VALUE (v_seckill_id,v_user_phone,0);

    SELECT row_count() INTO insert_count; -- 获取插入语句执行的结果

    IF (insert_count <0) THEN -- 内部执行异常
       ROLLBACK;
      SET result = -2;
    ELSEIF (insert_count = 0) THEN -- 重复秒杀
        ROLLBACK;
      SET result = -1;

    ELSE    -- 插入秒杀记录成功

      -- 执行更新操作
      UPDATE seckill
      SET num = num - 1
      WHERE seckill_id = v_seckill_id
            AND start_time < v_kill_time
            AND end_time >= v_kill_time
            AND num > 0;

      SELECT row_count() INTO insert_count;  -- 获取插入语句执行的结果

      IF (insert_count < 0) THEN -- 内部错误
        ROLLBACK ;
        SET result = -2;
      ELSEIF (insert_count = 0) THEN -- 秒杀结束(或者秒杀未开始、库存不够)
        ROLLBACK ;
        SET result = 0;
      ELSE    -- 更新成功
        COMMIT;
        SET result = 1;

      END IF;

    END IF;

END;

**

DELIMITER ;

SET @result = -3;

CALL seckill_executeSecKillProcedure(1004,18778563342,now(),@result);

SELECT @result;