INSERT INTO `user_role` (id, title, name, priority, state) VALUES (1, 'ROLE_admin', '管理员', 1, 1);
INSERT INTO `user_role` (id, title, name, priority, state) VALUES (2, 'ROLE_teachingsecretary', '教学秘书', 2, 1);
INSERT INTO `user_role` (id, title, name, priority, state) VALUES (3, 'ROLE_teacher', '教师', 3, 1);
COMMIT;
INSERT INTO `user_resource` (id, title, url, priority, resource_type, state) VALUES (1, '教师管理', '', 1, 1, 1);
INSERT INTO `user_resource` (id, title, url, priority, resource_type, state, parent_id) VALUES (2, '教师列表', '/teacher/list', 1, 1, 1, 1);
INSERT INTO `user_resource` (id, title, url, priority, resource_type, state, parent_id) VALUES (3, '个人中心', '/teacher/center', 2, 1, 1, 1);
INSERT INTO `user_resource` (id, title, url, priority, resource_type, state, parent_id) VALUES (4, '添加教师', '/teacher/add', 1, 1, 1, 1);
COMMIT;
-- INSERT INTO `bg_user` (id, name, password, real_name, regist_time, regist_ip, login_count, data_range, state)
--  VALUES (1, 'admin', '{noop}123456', 'administrator', '2022-7-26', '127.0.0.1', 0, 1, 1);
 INSERT INTO `user_teacher` (username, password, name, regist_time, login_success_count, login_failure_count, account_non_expired, account_non_locked, credentials_non_expired, enabled)
 VALUES ('admin', '{bcrypt}$2a$10$sv.ZgyVW3AANy.JB6GyQ/u0ibtaPDm7mGvSyu.V7GMjI/LqyrPMQW', '张三', '2022-7-26', 0, 0, true, true, true, true);
--## $2a$10$sv.ZgyVW3AANy.JB6GyQ/u0ibtaPDm7mGvSyu.V7GMjI/LqyrPMQW
COMMIT;
INSERT INTO  `user_rel_role_teacher` (role_id, teacher_username) VALUES (1, 'admin');
COMMIT;
INSERT INTO  `user_rel_role_resource` (role_id, resource_id) VALUES (1, 2);
INSERT INTO  `user_rel_role_resource` (role_id, resource_id) VALUES (1, 3);
INSERT INTO  `user_rel_role_resource` (role_id, resource_id) VALUES (1, 4);
COMMIT;
