CREATE TABLE `catalog` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `father_id` BIGINT(20) NULL,
  `level` INT NULL,
  PRIMARY KEY (`id`),
);

insert into catalog (`name`, `father_id`, `level`) values('文学',null,1);
insert into catalog (`name`, `father_id`, `level`) values('童书',null,1);
insert into catalog (`name`, `father_id`, `level`) values('教材教辅',null,1);
insert into catalog (`name`, `father_id`, `level`) values('人文社科',null,1);
insert into catalog (`name`, `father_id`, `level`) values('经管励志',null,1);
insert into catalog (`name`, `father_id`, `level`) values('艺术',null,1);
insert into catalog (`name`, `father_id`, `level`) values('科学技术',null,1);
insert into catalog (`name`, `father_id`, `level`) values('生活',null,1);
insert into catalog (`name`, `father_id`, `level`) values('文娱音像',null,1);
insert into catalog (`name`, `father_id`, `level`) values('教育培训',null,1);

insert into catalog (`name`, `father_id`, `level`) values('小说',1,2);
insert into catalog (`name`, `father_id`, `level`) values('散文随笔',1,2);
insert into catalog (`name`, `father_id`, `level`) values('青春文学',1,2);
insert into catalog (`name`, `father_id`, `level`) values('传记',1,2);
insert into catalog (`name`, `father_id`, `level`) values('动漫',1,2);
insert into catalog (`name`, `father_id`, `level`) values('悬疑推理',1,2);
insert into catalog (`name`, `father_id`, `level`) values('科幻',1,2);
insert into catalog (`name`, `father_id`, `level`) values('武侠',1,2);
insert into catalog (`name`, `father_id`, `level`) values('世界名著',1,2);

insert into catalog (`name`, `father_id`, `level`) values('儿童文学',2,2);
insert into catalog (`name`, `father_id`, `level`) values('绘本',2,2);
insert into catalog (`name`, `father_id`, `level`) values('科普百科',2,2);
insert into catalog (`name`, `father_id`, `level`) values('幼儿启蒙',2,2);
insert into catalog (`name`, `father_id`, `level`) values('智力开发',2,2);
insert into catalog (`name`, `father_id`, `level`) values('少儿英语',2,2);
insert into catalog (`name`, `father_id`, `level`) values('玩具书',2,2);

insert into catalog (`name`, `father_id`, `level`) values('教材',4,2);
insert into catalog (`name`, `father_id`, `level`) values('中小学教辅',4,2);
insert into catalog (`name`, `father_id`, `level`) values('考试',4,2);
insert into catalog (`name`, `father_id`, `level`) values('外语学习',4,2);
insert into catalog (`name`, `father_id`, `level`) values('字典词典',4,2);
insert into catalog (`name`, `father_id`, `level`) values('课外读物',4,2);
insert into catalog (`name`, `father_id`, `level`) values('英语四六级',4,2);
insert into catalog (`name`, `father_id`, `level`) values('会计类考试',4,2);
insert into catalog (`name`, `father_id`, `level`) values('国家公务员',4,2);

insert into catalog (`name`, `father_id`, `level`) values('历史',5,2);
insert into catalog (`name`, `father_id`, `level`) values('心理学',5,2);
insert into catalog (`name`, `father_id`, `level`) values('政治军事',5,2);
insert into catalog (`name`, `father_id`, `level`) values('传统文化',5,2);
insert into catalog (`name`, `father_id`, `level`) values('哲学宗教',5,2);
insert into catalog (`name`, `father_id`, `level`) values('社会科学',5,2);
insert into catalog (`name`, `father_id`, `level`) values('法律',5,2);
insert into catalog (`name`, `father_id`, `level`) values('文化',5,2);
insert into catalog (`name`, `father_id`, `level`) values('党政专区',5,2);

insert into catalog (`name`, `father_id`, `level`) values('管理',6,2);
insert into catalog (`name`, `father_id`, `level`) values('金融',6,2);
insert into catalog (`name`, `father_id`, `level`) values('经济',6,2);
insert into catalog (`name`, `father_id`, `level`) values('市场营销',6,2);
insert into catalog (`name`, `father_id`, `level`) values('领导力',6,2);
insert into catalog (`name`, `father_id`, `level`) values('股票',6,2);
insert into catalog (`name`, `father_id`, `level`) values('投资',6,2);
insert into catalog (`name`, `father_id`, `level`) values('励志与成功',6,2);
insert into catalog (`name`, `father_id`, `level`) values('自我完善',6,2);

insert into catalog (`name`, `father_id`, `level`) values('绘画',7,2);
insert into catalog (`name`, `father_id`, `level`) values('摄影',7,2);
insert into catalog (`name`, `father_id`, `level`) values('音乐',7,2);
insert into catalog (`name`, `father_id`, `level`) values('书法',7,2);
insert into catalog (`name`, `father_id`, `level`) values('连环画',7,2);
insert into catalog (`name`, `father_id`, `level`) values('设计',7,2);
insert into catalog (`name`, `father_id`, `level`) values('建筑艺术',7,2);
insert into catalog (`name`, `father_id`, `level`) values('艺术史',7,2);
insert into catalog (`name`, `father_id`, `level`) values('影视',7,2);

insert into catalog (`name`, `father_id`, `level`) values('计算机与互联网',8,2);
insert into catalog (`name`, `father_id`, `level`) values('科普',8,2);
insert into catalog (`name`, `father_id`, `level`) values('建筑',8,2);
insert into catalog (`name`, `father_id`, `level`) values('工业技术',8,2);
insert into catalog (`name`, `father_id`, `level`) values('电子通信',8,2);
insert into catalog (`name`, `father_id`, `level`) values('医学',8,2);
insert into catalog (`name`, `father_id`, `level`) values('科学与自然',8,2);
insert into catalog (`name`, `father_id`, `level`) values('农林',8,2);

insert into catalog (`name`, `father_id`, `level`) values('育儿家教',9,2);
insert into catalog (`name`, `father_id`, `level`) values('孕产胎教',9,2);
insert into catalog (`name`, `father_id`, `level`) values('健身保健',9,2);
insert into catalog (`name`, `father_id`, `level`) values('旅游地图',9,2);
insert into catalog (`name`, `father_id`, `level`) values('手工DIY',9,2);
insert into catalog (`name`, `father_id`, `level`) values('烹饪美食',9,2);

insert into catalog (`name`, `father_id`, `level`) values('音乐',10,2);
insert into catalog (`name`, `father_id`, `level`) values('影视',10,2);
insert into catalog (`name`, `father_id`, `level`) values('教育音像',10,2);
insert into catalog (`name`, `father_id`, `level`) values('游戏',10,2);
insert into catalog (`name`, `father_id`, `level`) values('影视、动漫周边',10,2);

insert into catalog (`name`, `father_id`, `level`) values('中小学教育',11,2);
insert into catalog (`name`, `father_id`, `level`) values('素质培养',11,2);
insert into catalog (`name`, `father_id`, `level`) values('出国留学',11,2);
insert into catalog (`name`, `father_id`, `level`) values('语言培训',11,2);
insert into catalog (`name`, `father_id`, `level`) values('学历教育',11,2);
insert into catalog (`name`, `father_id`, `level`) values('职业培训',11,2);

