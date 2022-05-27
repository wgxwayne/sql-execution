package indi.wgx;

import indi.wgx.mapper.ScoreMapper;
import indi.wgx.mapper.SqlMapper;
import indi.wgx.service.SqlService;
import indi.wgx.service.impl.ScoreServiceImpl;
import indi.wgx.vo.AllFieldVo;
import indi.wgx.vo.FieldMap;
import indi.wgx.vo.HighTimeSqlVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class SqlExecutionApplicationTests {

    @Autowired
    SqlService sqlService;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    SqlMapper sqlMapper;

    @Autowired
    ScoreServiceImpl scoreService;


//    @Test
//    void contextLoads() {
//        List<ProjectVersion> projectList = sqlService.getProjectList();
//        System.out.println(projectList);
//    }

//    @Test
//    void test1() {
//        Long projectVersionId = 10001L;
//        Integer sqlTime = 20;
//        List<HighTimeSqlVo> highTimeSQL = sqlService.getHighTimeSQL(projectVersionId, sqlTime);
//        System.out.println(highTimeSQL);
//    }

//    @Test
//    void test2() {
//        Long projectVersionId = 10002L;
//        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);
//        System.out.println(allFields);
//        int size = allFields.size();
//
//        for (AllFieldVo fieldVo : allFields) {
//            // 计算关键字段的扣分
//            String fieldName = fieldVo.getExecutionField();// 字段名称
//            Integer rowsNum = fieldVo.getRowsNum();// 第几行
//            Double rowFactor = FieldMap.rowsNumMap.get(rowsNum);
//            String value = fieldVo.getValue();
//            double score = 0.0;

//            if (!fieldName.equals("rows")) {
//                fieldName = fieldName + "Map";
//
//                try {
//                    Class<?> clazz = Class.forName("indi.wgx.vo.FieldMap");
//                    Field fieldMap = clazz.getDeclaredField(fieldName);
//                    System.out.println(fieldMap);
//                    fieldMap.setAccessible(true);   //去除私有权限
//                    score = (Double) fieldMap.get(value);
//                    System.out.println(score);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


//            System.out.println("value:" + value);
//            if (fieldName.equals("Extra")) {
//                score = FieldMap.extraMap.get(value).doubleValue();
//            }
//
//            if (fieldName.equals("type")){
//                score = FieldMap.typeMap.get(value).doubleValue();
//            }
//
//            if (fieldName.equals("rows")){
//                int rows = Integer.parseInt(value);
//                if (rows <= 5) {
//                    score = 0.0;
//                } else if (rows <= 10){
//                    score = 20.0;
//                } else if (rows <= 20) {
//                    score = 40.0;
//                } else if (rows <= 40) {
//                    score = 60.0;
//                } else {
//                    score = 100.0;
//                }
//            }
//
//            // 当前字段的扣分
//            score = score * rowFactor;
//            // 赋值
//            fieldVo.setFieldScore(score);
//
//            System.out.println(fieldVo);
//
//        }
//
//    }


//     具体sql的得分
//    double contentScore = 0.0;
    // 总扣分
//    double totalDeductions = 0.0;
    //        System.out.println("具体sql的总扣分" + totalDeductions);
//
//        if (totalDeductions > 100) {
//            totalDeductions = 100;
//        }
//        contentScore = 100 - totalDeductions;
//        System.out.println("具体sql的总得分" + contentScore);


//    @Test
//    void test3() {
//        Long projectVersionId = 10002L;
//        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);
//
//        List<SqlExecution> sqlExecutions = sqlMapper.getSqlExecution();
//        sqlExecutions.forEach(System.out::println);
//
//        // 先提取出同一个具体sql的信息，根据executionId来提取（sql_execution表的executionId代表每一个执行的具体sql）
//
//        // 所有执行的具体sql
//        for (SqlExecution sqlExecution : sqlExecutions) {
//            // allFields为该项目版本所有的sql
//            List<AllFieldVo> tempList = allFields;
//            Long executionId = sqlExecution.getExecutionId();
//            List<AllFieldVo> sameContentSql = tempList.stream().filter(s -> s.getExecutionId().equals(executionId)).collect(Collectors.toList());
//            if (sameContentSql != null) {
//                System.out.println(sameContentSql);
//            }
//
//        }
//    }


    /**
     * 计算具体sql分数
     */
    @Test
    void test04() {
        Long projectVersionId = 10002L;
        // 该项目所有的具体sql执行
        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);
//        allFields.forEach(System.out::println);

        // List 集合 根据 executionId 进行去重
        // 查询出该项目包含的具体sql执行id
        List<Long> executionIds = allFields.stream().map(AllFieldVo::getExecutionId).distinct().collect(Collectors.toList());
        System.out.println(executionIds);

        // 根据id再去统计该具体sql执行的分数
        for (Long id : executionIds) {
            List<AllFieldVo> resultField = allFields.stream().filter(s -> s.getExecutionId().equals(id)).collect(Collectors.toList());
            System.out.println("=============");

            // 对result集合进行处理，统计出所有字段的扣分总和
            double score = resultField.stream().mapToDouble(AllFieldVo::getFieldScore).sum();
            if (score > 100.0) {
                score = 100.0;
            }
            score = 100.0 - score;

            double finalScore = score;
            // 这里不能用过滤，不然后面操作就没有符合条件的元素了
            // allFields = allFields.stream().filter(item -> item.getExecutionId().equals(id)).peek(item -> item.setContentScore(finalScore)).collect(Collectors.toList());

            allFields.forEach(item -> {
                if (item.getExecutionId().equals(id)) {
                    item.setContentScore(finalScore);
                }
            });
        }
        allFields.forEach(System.out::println);
    }

    /**
     * 计算具体sql的分数
     */
//    @Test
//    void test004() {
//        Long projectVersionId = 10002L;
//        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);
//        List<AllFieldVo> allFieldVos = scoreService.calculateContentSqlScore(allFields);
//        allFieldVos.forEach(System.out::println);
//    }

    /**
     * 耗时扣分
     */
    @Test
    void test05() {
        Long projectVersionId = 10002L;
        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);

        allFields = allFields.stream().peek(item -> {
            if (item.getSqlTime() <= 10) {
                item.setTimeScore(0.0);
            } else if (item.getSqlTime() <= 20) {
                item.setTimeScore(20.0);
            } else if (item.getSqlTime() <= 100) {
                item.setTimeScore(40.0);
            } else {
                item.setTimeScore(100.0);
            }
        }).collect(Collectors.toList());
        allFields.forEach(System.out::println);
    }

    /**
     * 计算耗时扣分，调用服务
     */
//    @Test
//    void test005() {
//        Long projectVersionId = 10002L;
//        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);
//        scoreService.calculateTimeScore(allFields);
//        allFields.forEach(System.out::println);
//    }

    /**
     * 得到完整赋值分数的集合
     */
    @Test
    void test01() {
        Long projectVersionId = 10002L;
        List<AllFieldVo> allFieldVos = scoreService.calculateTotalScore(projectVersionId);
        allFieldVos.forEach(System.out::println);
    }

    /**
     * 测试计算模板分数
     */
    @Test
    void test02() {
        Long projectVersionId = 10002L;
        List<AllFieldVo> allFields = scoreMapper.getAllFieldInformation(projectVersionId);
        List<Long> templateIds = allFields.stream().map(AllFieldVo::getSqlTemplateId).distinct().collect(Collectors.toList());

        // 根据sql模板id去找sql_execution中找对应的具体sql
        for (Long id : templateIds) {
            List<AllFieldVo> resultField = allFields.stream().filter(s -> s.getSqlTemplateId().equals(id)).collect(Collectors.toList());
            // 对result集合进行处理，统计出所有字段的扣分总和
            int length = resultField.size();
            double score = resultField.stream().mapToDouble(AllFieldVo::getContentScore).sum();
            double finalScore = score / length;

            allFields.stream().forEach(item -> {
                if (item.getSqlTemplateId().equals(id)) {
                    item.setTemplateScore(finalScore);
                }
            });
        }
        allFields.forEach(System.out::println);
    }

    /**
     * 测试更新表分数
     */
    @Test
    void test03() {
        Long projectVersionId = 10002L;
        List<AllFieldVo> allFieldVos = scoreService.calculateTotalScore(projectVersionId);
        allFieldVos.forEach(System.out::println);

        scoreMapper.updateAllScore(allFieldVos);
        List<AllFieldVo> allFieldInformation = scoreMapper.getAllFieldInformation(projectVersionId);
        allFieldInformation.forEach(System.out::println);
    }

    /**
     * 统计项目中代码的行数
     */
    @Test
    void countCode() {

    }

}
