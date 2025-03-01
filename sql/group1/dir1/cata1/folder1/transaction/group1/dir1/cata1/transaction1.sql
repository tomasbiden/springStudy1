use yudada_2


select * from user_answer_0
where id= 1893175297024397312
limit 10


# 1893175297024397312
start transaction
update user_answer_0
set app_id=9
where id= 1893175297024397312

rollback