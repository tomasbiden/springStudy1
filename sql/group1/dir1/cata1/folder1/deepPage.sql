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
    and son_table.is_delete=0

)
and  tenant_id=8
and is_delete=0
LIMIT 10;

explain
select id
from user_answer_0
where  tenant_id=8
and is_delete=0
limit 4000000,1

-- 查询第 400 万行的 ID
SELECT id FROM user_answer_0 ORDER BY id LIMIT 1 OFFSET 3999999;


explain
select *
from user_answer_0 limit 4000000,20
# 1893257146937565370




SELECT id
FROM user_answer_0 son_table
WHERE
    son_table.id > 1893257146937565370
and tenant_id=8
and is_delete=0
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