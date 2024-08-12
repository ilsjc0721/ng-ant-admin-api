# TSVA

#### Table schema
1. sys_user

| Column Name                    | Data Type      | Default Value                                         | Comment       |
|--------------------------------|----------------|-------------------------------------------------------|---------------|
| id                             | int            | NOT NULL AUTO_INCREMENT                               |               |
| user_name                      | varchar(20)    | NOT NULL                                              | 帳號/手機      |
| user_name_ch                   | varchar(50)    | DEFAULT NULL                                          | 中文姓名       |
| user_name_en                   | varchar(50)    | DEFAULT NULL                                          | 英文姓名       |
| email                          | varchar(50)    | DEFAULT NULL                                          | Email         |
| address                        | varchar(50)    | DEFAULT NULL                                          | 地址           |
| password                       | varchar(255)   | NOT NULL                                              | 密碼           |
| is_available                   | tinyint(1)     | NOT NULL DEFAULT '1'                                  | 帳號狀態       |
| emergency_contact_person       | varchar(20)    | DEFAULT NULL                                          | 緊急連絡人     |
| emergency_contact_relationship | varchar(20)    | DEFAULT NULL                                          | 稱謂           |
| emergency_contact_phone        | varchar(20)    | DEFAULT NULL                                          | 緊急連絡人電話  |
| bank_code                      | varchar(3)     | DEFAULT NULL                                          | 銀行代碼       |
| bank_account                   | varchar(16)    | DEFAULT NULL                                          | 銀行帳號       |
| create_time                    | timestamp      | NOT NULL DEFAULT CURRENT_TIMESTAMP                    | 建立時間       |
| update_time                    | timestamp      | NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 修改時間       |
| last_login_time                | timestamp      | NULL DEFAULT NULL                                     | 最後登入時間   |
| picture_file_name              | varchar(50)    | DEFAULT NULL                                          | 圖片檔名       |
| join_time                      | timestamp      | NULL DEFAULT NULL                                     |               |
| email_account                  | varchar(50)    | DEFAULT NULL                                          |               |
| email_key                      | varchar(50)    | DEFAULT NULL                                          |               |

2. sys_user_child

| Column Name   | Data Type      | Default Value                                         | Comment       |
|---------------|----------------|-------------------------------------------------------|---------------|
| id            | int            | NOT NULL AUTO_INCREMENT                               |               |
| parent_id     | int            | NOT NULL                                              | sys_user.id   |
| id_number     | varchar(20)    | DEFAULT NULL                                          | 身份證字號      |
| user_name_ch  | varchar(50)    | DEFAULT NULL                                          | 中文姓名       |
| user_name_en  | varchar(50)    | DEFAULT NULL                                          | 英文姓名       |
| birth         | timestamp      | NULL DEFAULT NULL                                     | 生日           |
| gender        | varchar(20)    | DEFAULT NULL                                          | 性別           |
| phone         | varchar(20)    | DEFAULT NULL                                          | 電話           |
| is_available  | tinyint(1)     | NOT NULL DEFAULT '1'                                  | 帳號狀態       |
| update_time   | timestamp      | NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP         |               |

3. tsva_course (課程基本檔)

| Column Name | Data Type      | Default Value                                         | Comment   |
|-------------|----------------|-------------------------------------------------------|-----------|
| id          | int            | NOT NULL AUTO_INCREMENT                               |           |
| name        | varchar(20)    | DEFAULT NULL                                          | 課程名稱    |
| course_type | varchar(20)    | DEFAULT NULL                                          | 課程類別    |
| status      | varchar(20)    | DEFAULT NULL                                          | 狀態        |
| limit       | int            | DEFAULT NULL                                          | 人數限制    |
| apply       | tinyint(1)     | DEFAULT NULL                                          | 開放報名    |
| update_user | int            | DEFAULT NULL                                          |           |
| update_time | timestamp      | NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP         |           |


4. tsva_course_fee (課程費用檔)

| Column Name | Data Type      | Default Value                                         | Comment       |
|-------------|----------------|-------------------------------------------------------|---------------|
| id          | int            | NOT NULL AUTO_INCREMENT                               |               |
| course_id   | int            | NOT NULL                                              | tsva_course.id |
| coach_id    | int            | NOT NULL                                              | 教練id          |
| coach_type  | varchar(20)    | NOT NULL                                              | 教練類別         |
| coach_fee   | int            | NOT NULL                                              | 教練費(小時)     |
| tuition_fee | int            | NOT NULL                                              | 學費(小時)       |
| update_user | int            | DEFAULT NULL                                          |               |
| update_time | timestamp      | NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP         |               |

5. tsva_class (課程排定)

| Column Name     | Data Type      | Default Value                                         | Comment                   |
|-----------------|----------------|-------------------------------------------------------|---------------------------|
| id              | int            | NOT NULL AUTO_INCREMENT                               |                           |
| start_datetime  | timestamp      | NULL DEFAULT NULL                                     | 課程開始日期時間           |
| end_datetime    | timestamp      | NULL DEFAULT NULL                                     | 課程結束日期時間           |
| hours           | decimal(6,2)   | NOT NULL                                              | 課程時間(小時)             |
| course_id       | int            | NOT NULL                                              | 選擇課程 tsva_course.id     |
| course_checked  | tinyint(1)     | DEFAULT '0'                                           | 到課確認                   |
| update_user     | int            | DEFAULT NULL                                          |                           |
| update_time     | timestamp      | NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP         |                           |

6. tsva_class_coach (課程排定-教練費)

| Column Name | Data Type      | Default Value | Comment            |
|-------------|----------------|---------------|--------------------|
| id          | int            | NOT NULL AUTO_INCREMENT |                |
| class_id    | int            | NOT NULL      | tsva_class.id      |
| coach_id    | int            | NOT NULL      | 教練id              |
| coach_type  | varchar(20)    | NOT NULL      | 教練類別             |
| coach_fee   | int            | NOT NULL      | 教練費(小時)         |
| coach_total | int            | NOT NULL      | 總教練費             |

7. tsva_class_student (課程排定-學費)

| Column Name     | Data Type      | Default Value                                         | Comment               |
|-----------------|----------------|-------------------------------------------------------|-----------------------|
| id              | int            | NOT NULL AUTO_INCREMENT                               |                       |
| class_id        | int            | NOT NULL                                              | tsva_class.id          |
| student_id      | int            | NOT NULL                                              | 學員id                  |
| start_datetime  | timestamp      | NULL DEFAULT NULL                                     | 實際開始時間             |
| end_datetime    | timestamp      | NULL DEFAULT NULL                                     | 實際結束時間             |
| hours           | decimal(6,2)   | DEFAULT NULL                                          | 實際課程時間(小時)        |
| tuition_fee     | int            | NOT NULL                                              | 學費(小時)               |
| tuition_total   | int            | NOT NULL                                              | 總學費                  |

8. tsva_fee (費用總表)

| Column Name | Data Type      | Default Value                                         | Comment                            |
|-------------|----------------|-------------------------------------------------------|------------------------------------|
| id          | int            | NOT NULL AUTO_INCREMENT                               |                                    |
| period      | varchar(20)    | NOT NULL                                              | 收費期間, 年月, e.g. 202301          |
| type        | varchar(20)    | NOT NULL                                              | 收費類別 (coach/tuition)            |
| user_id     | int            | NOT NULL                                              | 教練id / 家長id                      |
| hours       | decimal(6,2)   | DEFAULT NULL                                          | 總時數                               |
| amount      | int            | DEFAULT NULL                                          | 總金額                               |
| status      | varchar(20)    | DEFAULT NULL                                          | 狀態 (new/confirm/wrong)            |
| memo        | varchar(200)   | DEFAULT NULL                                          | 備註說明                             |
| update_user | int            | DEFAULT NULL                                          |                                    |
| update_time | timestamp      | NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP         |                                    |

9. tsva_fee_detail (費用明細)

| Column Name        | Data Type      | Default Value | Comment                                                                                        |
|--------------------|----------------|---------------|------------------------------------------------------------------------------------------------|
| id                 | int            | NOT NULL AUTO_INCREMENT |                                                                                             |
| fee_id             | int            | NOT NULL      | tsva_fee.id                                                                                  |
| class_id           | int            | NOT NULL      | tsva_class.id                                                                                |
| class_date         | timestamp      | NOT NULL      | tsva_class.start_datetime                                                                    |
| class_hours        | decimal(6,2)   | DEFAULT NULL  | tsva_class.hours                                                                             |
| class_name         | varchar(20)    | NOT NULL      | tsva_course.name                                                                             |
| class_fee          | int            | NOT NULL      | coach_total / tuition_total                                                                  |
| class_coach_name   | varchar(500)   | NOT NULL      | by class_id 把 tsva_class_coach 寫入, 使用Function: sp_GetCoach(class_id)                       |
| class_student_name | varchar(2000)  | NOT NULL      | by class_id 把 tsva_class_student 寫入, 使用Function: sp_GetStudent(class_id)                    |

10. tsva_apply (學員報名課程)

| Column Name | Data Type      | Default Value                                         | Comment               |
|-------------|----------------|-------------------------------------------------------|-----------------------|
| id          | int            | NOT NULL AUTO_INCREMENT                               |                       |
| course_id   | int            | NOT NULL                                              | tsva_class.id          |
| apply_date  | timestamp      | NULL DEFAULT NULL                                     | 報名日期                |
| start_time  | timestamp      | NULL DEFAULT NULL                                     | 開始時間                |
| end_time    | timestamp      | NULL DEFAULT NULL                                     | 結束時間                |
| hours       | decimal(6,2)   | DEFAULT NULL                                          | 課程時間(小時)           |
| update_user | int            | DEFAULT NULL                                          |                       |
| update_time | timestamp      | NULL DEFAULT NULL                                     |                       |

11. tsva_apply_student (學員報名課程)

| Column Name | Data Type | Default Value           | Comment          |
|-------------|-----------|-------------------------|------------------|
| id          | int       | NOT NULL AUTO_INCREMENT |                  |
| apply_id    | int       | NOT NULL                | tsva_apply.id    |
| student_id  | int       | DEFAULT NULL            | 學員id             |

12. tsva_config

| Column Name | Data Type   | Default Value           | Comment |
|-------------|-------------|-------------------------|---------|
| id          | int         | NOT NULL AUTO_INCREMENT |         |
| name        | varchar(25) | DEFAULT NULL            |         |
| value       | varchar(2000) | DEFAULT NULL          |         |

#### API List 
1. 課程管理
	> 取得課程 <u>POST</u>
	> > /api/course/list
	
	> 新增課程 <u>POST</u>
	> > /api/course
	
	> 修改課程 <u>PUT</u>
	> > /api/course
	
	> 刪除課程 <u>POST</u>
	> > /api/course/del
	
2. 課程排定
	> 取得排課清單 <u>POST</u>
	> > /api/class/list
	
	> 新增排課 <u>POST</u>
	> > /api/class
	
	> 修改排課 <u>PUT</u>
	> > /api/class
	
	> 删除排課 <u>POST</u>
	> > /api/class/del
	
	> 取得學生清單 <u>POST</u>
	> > /api/course/student
	
	> 取得學生明細 <u>GET</u>
	> > /api/course/student/{id}
	
	> 取得排課明細 <u>GET</u>
	> > /api/course/detail/{id}
	
	> 取得月曆 <u>GET</u>
	> > /api/course/calendar

3. 到課確認
	> 到課確認 <u>POST</u>
	> > /api/course/confirm
	
	> 到課取消 <u>POST</u>
	> > /api/course/rollback-confirm	
	
4. 課程報名
	> 取得報名資料 <u>POST</u>
	> > /api/apply/list
	
	> 新增報名 <u>POST</u>
	> > /api/apply
	
	> 刪除報名 <u>POST</u>
	> > /api/apply/del

5. 費用
	> 費用總表 <u>POST</u>
	> > /api/fee

	> 更新費用總表狀態 <u>PUT</u>
	> > /api/fee
	
	> 教練費 <u>POST</u>
	> > /api/fee/coach
	
	> 學費 <u>POST</u>
	> > /api/fee/tuition
	
	> 費用郵件通知 <u>POST</u>
	> > /api/fee/mail
	
5. 設定檔
	> 取得Value <u>GET</u>
	> > /api/config/{name}
	
	> 修改Value <u>PUT</u>
	> > /api/config

#### 使用說明

1. 使用 javax.mail.jar 發送信件, 需要先 mvn install
	> mvn install:install-file -Dfile=javax.mail.jar -DpomFile=./pom.xml
2. 打包 jar, 輸出目錄: app\target
	> mvn install


***
# springBoot純淨框架

#### 介紹
springBoot+JWT+redis+mysql
裡面圖片上傳下載，excel匯入匯出，rbac權限校驗，aop日誌管理都有


#### 軟體架構
軟體架構說明


#### 安裝教學課程
1. xxxx
2. xxxx
3. xxxx


#### 參與貢獻

1. Fork 本倉庫
2. 新建 Feat_xxx 分支
3. 提交代碼
4. 新建 Pull Request


#### 特技

1. 使用 Readme\_XXX.md 來支援不同的語言，例如 Readme\_en.md, Readme\_zh.md
2. Gitee 官方部落格 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 這個網址來了解 Gitee 上的優秀開源項目
4. [GVP](https://gitee.com/gvp) 全名為 Gitee 最有價值開源項目，是綜合評定的優秀開源項目
5. Gitee 官方提供的使用手冊 [https://gitee.com/help](https://gitee.com/help)
6. Gitee 封面人物是一檔用來展示 Gitee 會員風采的欄位 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)