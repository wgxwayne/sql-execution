package indi.wgx.service.impl;

import indi.wgx.mapper.InitMapper;
import indi.wgx.mapper.SqlMapper;
import indi.wgx.pojo.ProjectVersion;
import indi.wgx.pojo.SqlDimension;
import indi.wgx.pojo.SqlExecution;
import indi.wgx.pojo.SqlTemplate;
import indi.wgx.service.InitService;
import indi.wgx.service.ScoreService;
import indi.wgx.vo.AllFieldVo;
import indi.wgx.vo.InitTableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class InitServiceImpl implements InitService {

    @Autowired
    InitMapper initMapper;

    @Autowired
    SqlMapper sqlMapper;

    @Autowired
    ScoreService scoreService;

    /**
     * 第一次 插入所有数据，分数字段插入为 NULL
     */
    @Override
    public void initAllTable(InitTableVo initTableVo) {
        // 初始插入数据
        insertAllData(initTableVo);
        // 获取项目id
        Long projectVersionId = initTableVo.getProjectVersion().getProjectVersionId();
        // 计算该项目所有sql的得分
        List<AllFieldVo> allFieldVos = scoreService.calculateTotalScore(projectVersionId);
        // 只更新分数字段，其它字段在初始插入已经更新
        scoreService.updateAllScore(allFieldVos);
    }

    /**
     * 如果插入的数据已经存在，会先删除存在的数据，然后插入新的数据，因为新的数据某些字段可能会变化
     * 开启事务
     */
    @Transactional
    public void insertAllData(InitTableVo initTableVo) {
        ProjectVersion projectVersion = initTableVo.getProjectVersion();
        SqlTemplate sqlTemplate = initTableVo.getSqlTemplate();
        SqlExecution sqlExecution = initTableVo.getSqlExecution();
        List<SqlDimension> sqlDimensionList = initTableVo.getSqlDimensionList();

        // 做一层处理，防止数据重复插入以及id不统一
        insertProjectVersion(projectVersion, sqlTemplate);
        insertSqlTemplate(projectVersion, sqlTemplate, sqlExecution);
        insertSqlExecutionAndDimension(sqlExecution,sqlDimensionList);
    }

    /**
     * 数据初始插入 project_version表
     */
    public void insertProjectVersion(ProjectVersion projectVersion, SqlTemplate sqlTemplate) {
        // 1、项目版本表初始插入，如果存在则更新
        // 根据项目id 和 项目版本标识(保证唯一性) 获取当前的项目版本id
        Long oldProjectVersionId = sqlMapper.getProjectVersionId(projectVersion.getProjectId(), projectVersion.getProjectVersion());
        log.info("oldProjectVersionId:" + oldProjectVersionId);
        // 说明第一次插入
        if (oldProjectVersionId != null) {
            // 说明这条sql的项目已经存在,更新后面所有关联的字段
            projectVersion.setProjectVersionId(oldProjectVersionId);
            sqlTemplate.setProjectVersionId(oldProjectVersionId);
            // 如果存在则更新数据之后再插入，之所以要在插入是因为其他字段可能变化了，比如名称，如果有相同的数据插入则先删除之前的，再插入现在的
        }
        initMapper.initProjectVersionTable(projectVersion);
    }

    /**
     * 数据初始插入 sql_template表
     */
    public void insertSqlTemplate(ProjectVersion projectVersion, SqlTemplate sqlTemplate, SqlExecution sqlExecution) {
        // 2、SQL模板表初始插入，如果存在则更新
        Long oldSqlTemplateId = sqlMapper.getSqlTemplateId(sqlTemplate.getProjectVersionId(), sqlTemplate.getSqlTemplate());
        log.info("oldSqlTemplateId:" + oldSqlTemplateId);
        if (oldSqlTemplateId != null) {
            //无论projectVersionId是否改变，如果上一步改变了，再设置也无妨，如果没有改变说明旧数据本身存在
            sqlTemplate.setProjectVersionId(projectVersion.getProjectVersionId());
            sqlTemplate.setSqlTemplateId(oldSqlTemplateId);
            sqlExecution.setSqlTemplateId(oldSqlTemplateId);
        }
        initMapper.initSqlTemplateTable(sqlTemplate);
    }

    public void insertSqlExecutionAndDimension(SqlExecution sqlExecution, List<SqlDimension> sqlDimensionList) {
        // 3、具体SQL表、执行计划表初始插入，如果存在则更新
        Long oldExecutionId = sqlMapper.getExecutionId(sqlExecution.getSqlTemplateId(), sqlExecution.getSqlContent());
        log.info("oldExecutionId:" + oldExecutionId);
        if (oldExecutionId != null) {
            sqlExecution.setExecutionId(oldExecutionId);
            sqlDimensionList.forEach(item -> item.setExecutionId(oldExecutionId));
        }
        // 具体的sql新插入，则维度表中的字段也是新插入
        initMapper.initSqlExecutionTable(sqlExecution);
        initMapper.initSqlDimensionTable(sqlDimensionList);
    }
}
