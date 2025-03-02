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
        son_table.id > 1893229443865804800
      AND father_table.id = son_table.id  -- 关联条件写在子查询内部

) LIMIT 10;

SELECT id
FROM user_answer_0 son_table
WHERE
    son_table.id > 1893229443865804800
limit 10

select *
from user_answer_0  father_table
where id > (select id from  user_answer_0 where id >1893229443865804800 limit 1) limit 10