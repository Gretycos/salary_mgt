
DELETE FROM SYS_ROLE_RESOURCE WHERE RESOURCE_ID = 'b6c51ea813804dc8a222fbae394b8550';
DELETE FROM SYS_RESOURCES WHERE RESOURCE_ID = 'b6c51ea813804dc8a222fbae394b8550';

DELETE FROM SYS_ROLE_RESOURCE WHERE RESOURCE_ID = 'b5f3e2bcbcf046129b0a43618d5cb704';
DELETE FROM SYS_RESOURCES WHERE RESOURCE_ID = 'b5f3e2bcbcf046129b0a43618d5cb704'; 

-- 创建sysreport表
DROP TABLE IF EXISTS `sys_report`;
CREATE TABLE `sys_report` (
  `ID_` varchar(32) NOT NULL COMMENT '报表',
  `NAME_` varchar(64) NOT NULL COMMENT '报表名称',
  `CONTENT_` mediumblob COMMENT '报表内容',
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增模块--工作报表
insert into sys_modules values 
	('d6796d678cf146678b69d4f9e8932e5e','工作报表','1',1,11,'superadmin','2019-10-21 03:02:30',null,null,'0082');
-- 新增功能--新增报表
insert into sys_functions values 
	('805ebd3a377f4d7ba5c767d5b8542089','d6796d678cf146678b69d4f9e8932e5e','00820095','新增报表',1,null,1,'superadmin','2019-10-21 03:02:48',null,null);
-- 新增功能--查看报表
insert into sys_functions values 
	('2c3fc1870bbc435097df9b8cd663b1ba','d6796d678cf146678b69d4f9e8932e5e','00820096','查看报表',1,null,2,'superadmin','2019-10-21 03:03:01',null,null);
-- 新增资源--新增报表
insert into sys_resources values 
	('59141570ab1e4ff49f33a872546aa400','新增报表','新增报表',0,0,'805ebd3a377f4d7ba5c767d5b8542089',1,1,0,'superadmin','2019-10-21 03:07:08',null,null,'008200950170','/ureport/designer###','ureportDemo:edit');
-- 新增资源--查看报表
insert into sys_resources values 
	('067e4c76c92146ffad6fb4080260d286','查看报表','查看报表',0,0,'2c3fc1870bbc435097df9b8cd663b1ba',1,1,0,'superadmin','2019-10-21 03:09:44',null,null,'008200960171','/ureportDemo/view','ureportDemo:view');
-- 新增菜单 --工作报表
insert into sys_menu(MENU_ID,TEXT,MENU_ALIAS,TITLE,TYPE_ID,PARENT_ID,SEQ,IS_LEAF,TYPE,VALUE,TARGET,ICONS,RESOURCE_ID,OPEN_TYPE) values 
		('c98792dc44934dccb123e8bdc438923c','工作报表','工作报表','工作报表',null,'1',11,0,null,null,null,'','','tab');
-- 新增菜单--编辑报表
insert into sys_menu(MENU_ID,TEXT,MENU_ALIAS,TITLE,TYPE_ID,PARENT_ID,SEQ,IS_LEAF,TYPE,VALUE,TARGET,ICONS,RESOURCE_ID,OPEN_TYPE) values 
		('81dac3986a0d45828202b5bffdc6ff9a','新增报表','新增报表','新增报表',null,'c98792dc44934dccb123e8bdc438923c',1,1,null,null,null,'','59141570ab1e4ff49f33a872546aa400','tab');
-- 新增菜单--查看报表
insert into sys_menu(MENU_ID,TEXT,MENU_ALIAS,TITLE,TYPE_ID,PARENT_ID,SEQ,IS_LEAF,TYPE,VALUE,TARGET,ICONS,RESOURCE_ID,OPEN_TYPE) values 
		('e282813cdc9d4d8e9e29116eccd490f5','查看报表','查看报表','查看报表',null,'c98792dc44934dccb123e8bdc438923c',2,1,null,null,null,'','067e4c76c92146ffad6fb4080260d286','tab');