package indi.wgx.service.impl;

import indi.wgx.mapper.ScoreMapper;
import indi.wgx.pojo.SqlDimension;
import indi.wgx.pojo.SqlExecution;
import indi.wgx.pojo.SqlTemplate;
import indi.wgx.service.ScoreService;
import indi.wgx.vo.FieldMap;
import indi.wgx.vo.AllFieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    FieldMap fieldMap;

    @Override
    public void updateAllScore(List<AllFieldVo> fieldVoList) {
        scoreMapper.updateAllScore(fieldVoList);
    }

    @Override
    public List<AllFieldVo> getAllFieldInformation(Long projectVersionId) {
        return scoreMapper.getAllFieldInformation(projectVersionId);
    }


    /**
     * 更新一个项目中的所有字段
     */
    @Override
    public List<AllFieldVo> calculateTotalScore(Long projectVersionId) {
        // 获取所有字段(以项目为出发点)
        List<AllFieldVo> allFields = getAllFieldInformation(projectVersionId);
        // 计算具体sql的总得分 = 字段扣分总和 + 耗时扣分
        List<AllFieldVo> contentSqlScoreList = calculateContentSqlScore(allFields);
        // 计算sql模板的得分，所有具体sql取平均值
        List<AllFieldVo> sqlTemplateScore = calculateSqlTemplateScore(contentSqlScoreList);
        // 查询出所有的时间分数排序等 前提是已经计算出分数了
        return fillAllTime(sqlTemplateScore);
    }


    /**
     * 计算具体SQL的得分
     * 根据 executionId 来，写一个接口，根据  executionId 获取一句sql执行的所有信息，并计算该具体sql的得分
     */
    public List<AllFieldVo> calculateContentSqlScore(List<AllFieldVo> allFields){
        SqlExecution sqlExecution = new SqlExecution();
        allFields = calculateKeyFieldScore(allFields);
        allFields = calculateTimeScore(allFields);

        List<Long> executionIds = allFields.stream().map(AllFieldVo::getExecutionId).distinct().collect(Collectors.toList());
        for (Long id : executionIds) {
            Double finalScore = sqlExecution.calculateForContentScore(allFields, id);
            allFields.forEach(item -> {
                if (item.getExecutionId().equals(id)) {
                    item.setContentScore(finalScore);
                }
            });
        }
        return allFields;
    }


    /**
     * 计算耗时的扣分
     */
    public List<AllFieldVo> calculateTimeScore(List<AllFieldVo> allFields) {
        SqlExecution sqlExecution = new SqlExecution();
        return allFields.stream().peek(item -> {
            item.setTimeScore(sqlExecution.calculateForTimeScore(item.getSqlTime()));
        }).collect(Collectors.toList());
    }


    /**
    * 计算关键字段的扣分
    */
    public List<AllFieldVo> calculateKeyFieldScore(List<AllFieldVo> allFields) {
        SqlDimension sqlDimension = new SqlDimension();
        for (AllFieldVo fieldVo : allFields) {
            Double fieldScore = sqlDimension.calculateForKeyFieldScore(fieldVo.getExecutionField(), fieldVo.getRowsNum(), fieldVo.getValue());
            fieldVo.setFieldScore(fieldScore);
        }
        return allFields;
    }


    /**
     * 计算sql模板的得分
     */
    public List<AllFieldVo> calculateSqlTemplateScore(List<AllFieldVo> allFields){
        SqlTemplate sqlTemplate = new SqlTemplate();
        // 统计allFields中有多少个sql模板，列出模板id
        List<Long> templateIds = allFields.stream().map(AllFieldVo::getSqlTemplateId).distinct().collect(Collectors.toList());
        // 根据sql模板id去找sql_execution中找对应的具体sql
        for (Long id : templateIds) {
            Double finalScore = sqlTemplate.calculateForTemplateScore(allFields, id);
            allFields.forEach(item -> {
                if (item.getSqlTemplateId().equals(id)) {
                    item.setTemplateScore(finalScore);
                }
            });
        }
        return allFields;
    }

    public List<AllFieldVo> fillAllTime(List<AllFieldVo> allFields){
        SqlTemplate sqlTemplate = new SqlTemplate();

        // 有几个sql模板
        List<Long> templateIds = allFields.stream().map(AllFieldVo::getSqlTemplateId).distinct().collect(Collectors.toList());
        for (Long id : templateIds) {
            List<AllFieldVo> sameSqlTemplateIdList = allFields.stream().filter(s -> s.getSqlTemplateId().equals(id)).collect(Collectors.toList());
            String minScoreSqlContent = sqlTemplate.getMinScoreSqlContent(sameSqlTemplateIdList);
            Double averageSqlTime = sqlTemplate.getAverageSqlTime(sameSqlTemplateIdList);
            Integer maxSqlTime = sqlTemplate.getMaxSqlTime(sameSqlTemplateIdList);
            Integer minSqlTime = sqlTemplate.getMinSqlTime(sameSqlTemplateIdList);

            allFields.forEach(item -> {
                if (item.getSqlTemplateId().equals(id)) {
                    item.setMinScoreSqlContext(minScoreSqlContent);
                    item.setAverageSqlTime(averageSqlTime);
                    item.setMinSqlTime(minSqlTime);
                    item.setMaxSqlTime(maxSqlTime);
                }
            });
        }
        return allFields;
    }
}
