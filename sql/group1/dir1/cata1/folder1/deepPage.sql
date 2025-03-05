use yudada_2;
explain
SELECT *
FROM  user_answer_0 t1
          inner join  (select id from user_answer_0 WHERE id > 1893229440170622976 limit 20) as t2 on t1.id=t2.id
order by t1.id desc

select *

from  user_answer_0
order by id limit  5000000,10

select *
from user_answer_0
where id =1893230945074315264


select count(*)
from user_answer_0


explain
select *
from user_answer_0  limit 1,20

select *
from user_answer_0  father_table
where exists( where father_table.id=tmp_table .id)

# 错误
select *
from user_answer_0  father_table
where exists((select son_table.id from user_answer_0 son_table  where son_table.id > 1893229443865804800 limit 10) as tmp_table  where father_table.id=tmp_table.id)

# 错误
select *
from user_answer_0  father_table
where exists((select son_table.id from user_answer_0 son_table  where son_table.id > 1893229443865804800 limit 10) as tmp_table  where father_table.id=tmp_table.id)



SELECT *
FROM user_answer_0 father_table
WHERE EXISTS (
    SELECT 1
    FROM user_answer_0 son_table
    WHERE
        son_table.id >1893257146937565362
      AND father_table.id = son_table.id  -- 关联条件写在子查询内部

) LIMIT 10;



SELECT *
FROM user_answer_0 father_table
WHERE EXISTS (
    SELECT 1
    FROM user_answer_0 son_table
    WHERE
        son_table.id >1893257146937565363
      AND father_table.id = son_table.id  -- 关联条件写在子查询内部

) LIMIT 10;




SELECT *
FROM user_answer_0 father_table
WHERE EXISTS (
    SELECT 1
    FROM user_answer_0 son_table
    WHERE
        son_table.id >1893257146937565363
      AND father_table.id = son_table.id  -- 关联条件写在子查询内部
    and son_table.tenant_id=8
    and son_table.app_id=2
    and son_table.is_delete=0

)
and  tenant_id=8
and is_delete=0
and app_id=2
LIMIT 10;


set profiling = 1

SET profiling_history_size = 1000

-- 开启优化器跟踪
SET optimizer_trace = "enabled=on",END_MARKERS_IN_JSON=on;

SET OPTIMIZER_TRACE_MAX_MEM_SIZE=1000000;

SET optimizer_trace_offset=-10, optimizer_trace_limit=10;
explain

select id
from user_answer_0
# force index(`PRIMARY`)
# force index(index1)
# force index(index_with_isdeleted)
where  tenant_id=8
and is_delete=0
# order by id asc
# and app_id=2
# order by tenant_id
limit 4000000,10

SELECT * FROM information_schema.optimizer_trace

show profiles
show profile for query 70

-- 查询第 400 万行的 ID
SELECT id FROM user_answer_0 ORDER BY id LIMIT 1 OFFSET 3999999;


explain
select *
from user_answer_0
limit 4000000,20;
union
select *
from user_answer_1
limit 4000000,50


select *
from user_answer_0 limit 4000000,20
# 1893257146937565370




SELECT id
FROM user_answer_0 son_table
WHERE
    son_table.id > 1893257146937565370
and tenant_id=8
and is_delete=0
and app_id=2
limit 10

select *
from user_answer_0  father_table
where id > (select id from  user_answer_0 where id >=1893257146937565362 and  user_answer_0.tenant_id=8 limit 1)
and tenant_id=8
limit 10

explain
select id from user_answer_0
where tenant_id=8
and is_delete=0
order by create_time
limit 4000000,20




explain
SELECT id,tenant_id,app_id,app_type,scoring_strategy,choices,result_id,result_name,result_desc,result_picture,result_score,user_id,create_time,update_time,is_delete FROM user_answer_0 WHERE is_delete=0 AND (tenant_id = 8 AND app_id = 2 AND id >= 1893256356797849687) limit 20

explain analyze
SELECT id,tenant_id,app_id,app_type,scoring_strategy,choices,result_id,result_name,result_desc,result_picture,result_score,user_id,create_time,update_time,is_delete
FROM user_answer_0
WHERE
    is_delete=0
AND tenant_id = 8
  AND app_id = 2
  AND id <= 1893256356797849687
order by create_time
desc

limit 20
# extra using index using where 覆盖索引，  using where 回表
explain
select id
from user_answer_1
where  id > 1893255003803418626
# and is_delete=0
limit 10




SET profiling = 1;
SET profiling_history_size = 1000;


SHOW PROFILES;


-- 开启优化器跟踪
SET optimizer_trace = "enabled=on",END_MARKERS_IN_JSON=on;

SET OPTIMIZER_TRACE_MAX_MEM_SIZE=1000000;

SET optimizer_trace_offset=-10, optimizer_trace_limit=10


-- 查看跟踪结果
SELECT * FROM information_schema.optimizer_trace

explain
select id
from  user_answer_0
force index(`PRIMARY`)
where
# tenant_id=8
# and is_delete=0
 id=1893229494142159633

set global slow_query_log=1;


show global variables like '%slow_query_log%';

# long_query_time 表示慢查询的阈值，默认10秒
show global variables like '%long_query_time%';

set global long_query_time=1;
explain
select id
from user_answer_0
# force index(`PRIMARY`)
# force index(index1)
force index(index_with_isdeleted)
where  tenant_id=8
  and is_delete=0
# order by id asc
# and app_id=2
# order by tenant_id
limit 4000000,10


                                                                                                                                                                                        limit 20