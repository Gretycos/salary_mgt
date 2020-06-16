--- 操作之前务必请先将数据库进行备份！！！！！
--- 操作之前务必请先将数据库进行备份！！！！！
--- 操作之前务必请先将数据库进行备份！！！！！
--- 操作之前务必请先将数据库进行备份！！！！！
--- 操作之前务必请先将数据库进行备份！！！！！

--组织类型
DELETE SYS_ORGAN_TYPE WHERE ORGAN_TYPE NOT IN ('1','2','3','9');
--删除组织机构规则
DELETE SYS_STRU_RULE  WHERE ORGAN_TYPE NOT IN ('1','2','3','9');
DELETE SYS_STRU_RULE  WHERE SYS_ORGAN_TYPE NOT IN ('1','2','3','9');
--用户组织机构类型访问权限
DELETE SYS_STRU_TYPE_REF;
--用户审计表
DELETE SYS_USER_AUDIT;
--用户IP表
DELETE SYS_USER_IP;
--用户登录时间限制表 
DELETE SYS_USER_TIME;
--用户代理表
DELETE SYS_USER_PROXY WHERE PROXY_USER_ID IN (SELECT USER_ID FROM SYS_USERS WHERE USER_ID NOT IN ('superadmin','reviewadmin','auditadmin','systemadmin','businessadmin'));
--用户角色关联表
DELETE SYS_USER_ROLE WHERE USER_ID IN (SELECT USER_ID FROM SYS_USERS WHERE USER_ID NOT IN ('superadmin','reviewadmin','auditadmin','systemadmin','businessadmin'));
--用户历史密码
DELETE SYS_PASSWORD_HIST ;
--账户密码找回表
DELETE SYS_GET_BACK_PASSWORD ;
--用户
DELETE SYS_USERS WHERE USER_ID IN (SELECT USER_ID FROM SYS_USERS WHERE USER_ID NOT IN ('superadmin','reviewadmin','auditadmin','systemadmin','businessadmin'));
--用户代理表
DELETE SYS_USER_PROXY WHERE USER_ID IN (SELECT STRU_ID FROM SYS_STRU WHERE STRU_ID != '1');
--用户组织机构类型访问权限
DELETE SYS_STRU_TYPE_REF WHERE USER_ID IN (SELECT STRU_ID FROM SYS_STRU);
--用户
DELETE SYS_USERS WHERE USER_ID IN (SELECT STRU_ID FROM SYS_STRU WHERE STRU_ID != '1');
--角色
DELETE SYS_ROLES WHERE ROLE_ID NOT  IN ('systemadmin_role','reviewadmin_role','superadmin_role','auditadmin_role','public_role','businessadmin_role');
--用户角色
DELETE SYS_USER_ROLE WHERE USER_ID  NOT IN ('superadmin','reviewadmin','auditadmin','systemadmin','businessadmin');
--角色分组表
DELETE SYS_ROLE_GROUP WHERE GROUP_ID  <> 'sys_role_group';
--用户组织机构类型访问权限
DELETE SYS_STRU_TYPE_REF WHERE STRU_ID IN (SELECT STRU_ID FROM SYS_STRU);
--组织机构
DELETE SYS_STRU WHERE STRU_ID != '1';
--组织机构
DELETE SYS_ORGAN WHERE ORGAN_ID != '1';
--职工
DELETE SYS_STAFF ;
--不相容角色;
DELETE SYS_CONF_ROLES ;
--不相容角色集
DELETE SYS_CONF_ROLESET ;
---审核表
DELETE SYS_STRU_AUDIT;
DELETE SYS_STAFF_AUDIT;
DELETE SYS_ORGAN_AUDIT;
DELETE SYS_OFFICE_AUDIT;
DELETE SYS_USERS_AUDIT;
DELETE SYS_USER_IP_AUDIT;
DELETE SYS_USERROLE_AUDIT;

-- COMMIT; 