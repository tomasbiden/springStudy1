

use yudada_2;
explain
SELECT *
FROM  user_answer_0 t1
          inner join  (select id from user_answer_0 WHERE id > 1893229440170622976 limit 20) as t2 on t1.id=t2.id
order by t1.id desc

