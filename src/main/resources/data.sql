
-- ================== departments table ==================
INSERT INTO departments (id, dept_name) VALUES
    (1, '计算机科学系'),
    (2, '电子工程系'),
    (3, '信息管理系');


-- ================== teachers table ==================
-- 计算机科学系 (dept_id = 1) 的 20 名教师
INSERT INTO teachers (id, employee_id, teacher_name, teacher_email, dept_id) VALUES
    (1, 'T1001', '张教授', 'lk08wfocux@bltiwd.com', 1),
    (2, 'T1002', '李副教授', 'lif90@bid.com', 1),
    (3, 'T1003', '王讲师', 'lk08wfocux@twdas.com', 1),
--     (4, 'T1004', '赵高级讲师', 'zhao.seniorlect@cs.edu', 1),
--     (5, 'T1005', '孙博士', 'sun.dr@cs.edu', 1),
--     (6, 'T1006', '周教授', 'zhou.prof@cs.edu', 1),
--     (7, 'T1007', '吴副教授', 'wu.assocprof@cs.edu', 1),
--     (8, 'T1008', '郑讲师', 'zheng.lecturer@cs.edu', 1),
--     (9, 'T1009', '冯高级讲师', 'feng.seniorlect@cs.edu', 1),
--     (10, 'T1010', '陈博士', 'chen.dr@cs.edu', 1),
--     (11, 'T1011', '魏教授', 'wei.prof@cs.edu', 1),
--     (12, 'T1012', '江副教授', 'jiang.assocprof@cs.edu', 1),
--     (13, 'T1013', '秦讲师', 'qin.lecturer@cs.edu', 1),
--     (14, 'T1014', '韩高级讲师', 'han.seniorlect@cs.edu', 1),
--     (15, 'T1015', '朱博士', 'zhu.dr@cs.edu', 1),
--     (16, 'T1016', '夏教授', 'xia.prof@cs.edu', 1),
--     (17, 'T1017', '方副教授', 'fang.assocprof@cs.edu', 1),
--     (18, 'T1018', '顾讲师', 'gu.lecturer@cs.edu', 1),
--     (19, 'T1019', '孟高级讲师', 'meng.seniorlect@cs.edu', 1),
    (20, 'T1020', '高博士', 'gao.dr@cs.edu', 1);

-- 电子工程系 (dept_id = 2) 的 20 名教师
INSERT INTO teachers (id, employee_id, teacher_name, teacher_email, dept_id) VALUES
    (21, 'T2001', '刘教授', 'liu.prof@ee.edu', 2),
--     (22, 'T2002', '黄副教授', 'huang.assocprof@ee.edu', 2),
--     (23, 'T2003', '郭讲师', 'guo.lecturer@ee.edu', 2),
--     (24, 'T2004', '马高级讲师', 'ma.seniorlect@ee.edu', 2),
--     (25, 'T2005', '程博士', 'cheng.dr@ee.edu', 2),
--     (26, 'T2006', '许教授', 'xu.prof@ee.edu', 2),
--     (27, 'T2007', '唐副教授', 'tang.assocprof@ee.edu', 2),
--     (28, 'T2008', '林讲师', 'lin.lecturer@ee.edu', 2),
--     (29, 'T2009', '潘高级讲师', 'pan.seniorlect@ee.edu', 2),
--     (30, 'T2010', '杜博士', 'du.dr@ee.edu', 2),
--     (31, 'T2011', '景教授', 'jing.prof@ee.edu', 2),
--     (32, 'T2012', '童副教授', 'tong.assocprof@ee.edu', 2),
--     (33, 'T2013', '苏讲师', 'su.lecturer@ee.edu', 2),
--     (34, 'T2014', '卢高级讲师', 'lu.seniorlect@ee.edu', 2),
--     (35, 'T2015', '任博士', 'ren.dr@ee.edu', 2),
--     (36, 'T2016', '华教授', 'hua.prof@ee.edu', 2),
--     (37, 'T2017', '石副教授', 'shi.assocprof@ee.edu', 2),
--     (38, 'T2018', '金讲师', 'jin.lecturer@ee.edu', 2),
--     (39, 'T2019', '邱高级讲师', 'qiu.seniorlect@ee.edu', 2),
    (40, 'T2020', '鲍博士', 'bao.dr@ee.edu', 2);

-- 信息管理系 (dept_id = 3) 的 20 名教师
INSERT INTO teachers (id, employee_id, teacher_name, teacher_email, dept_id) VALUES
--     (41, 'T3001', '曹教授', 'cao.prof@im.edu', 3),
--     (42, 'T3002', '薛副教授', 'xue.assocprof@im.edu', 3),
--     (43, 'T3003', '黎讲师', 'li.lecturer@im.edu', 3),
--     (44, 'T3004', '段高级讲师', 'duan.seniorlect@im.edu', 3),
--     (45, 'T3005', '雷博士', 'lei.dr@im.edu', 3),
--     (46, 'T3006', '杨教授', 'yang.prof@im.edu', 3),
--     (47, 'T3007', '俞副教授', 'yu.assocprof@im.edu', 3),
--     (48, 'T3008', '钟讲师', 'zhong.lecturer@im.edu', 3),
--     (49, 'T3009', '童高级讲师', 'tong.seniorlect@im.edu', 3),
--     (50, 'T3010', '姜博士', 'jiang.dr@im.edu', 3),
--     (51, 'T3011', '钱教授', 'qian.prof@im.edu', 3),
--     (52, 'T3012', '谭副教授', 'tan.assocprof@im.edu', 3),
--     (53, 'T3013', '史讲师', 'shi.lecturer@im.edu', 3),
--     (54, 'T3014', '郝高级讲师', 'hao.seniorlect@im.edu', 3),
--     (55, 'T3015', '毛博士', 'mao.dr@im.edu', 3),
--     (56, 'T3016', '孔教授', 'kong.prof@im.edu', 3),
--     (57, 'T3017', '辛副教授', 'xin.assocprof@im.edu', 3),
--     (58, 'T3018', '廖讲师', 'liao.lecturer@im.edu', 3),
    (59, 'T3019', '万高级讲师', 'wan.seniorlect@im.edu', 3),
    (60, 'T3020', '翟博士', 'zhai.dr@im.edu', 3);

-- ================== assistants table ==================
INSERT INTO assistants (id, employee_id, assistant_name, assistant_email, email_app_password) VALUES
    (1, 'A001', '科研秘书-小李', null, null),
    (2, 'A002', '科研秘书-小王', 'wang.assistant@example.com', 'app_pwd_wang'),
    (3, 'A003', '科研秘书-小张', 'zhang.assistant@example.com', 'app_pwd_zhang');