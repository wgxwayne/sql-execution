package indi.wgx.mapper;

import indi.wgx.pojo.ProjectVersion;
import indi.wgx.pojo.SqlExecution;
import indi.wgx.request.QueryRequest;
import indi.wgx.response.QueryResponse;
import indi.wgx.vo.HighTimeSqlVo;
import indi.wgx.vo.OrderedScoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface SqlMapper {

    // 获取 sql_execution 表的信息
    SqlExecution getSqlExecution(Long executionId);

    // 通过项目版本id。获取该版本项目的SQL分数，SQL分数从高到低
    List<OrderedScoreVo> getAllOrderedScore(Long projectVersionId);

    // 获取一个项目中高耗时的sql
    List<HighTimeSqlVo> getHighTimeSQL(Long projectVersionId, int sqlTime);

    /**
     * 下面三个和初始化表格数据有关
     */

    // 根据项目id 和项目版本 获取 项目版本id
    Long getProjectVersionId(Long projectId, String projectVersion);

    // 根据项目版本id 和 sql模板来获取 sql模板id
    Long getSqlTemplateId(Long projectVersionId, String sqlTemplate);

    // 根据项目模板id 和 具体sql获取 具体sql的执行id
    Long getExecutionId(Long sqlTemplateId, String sqlContent);

    /**
     * 条件查询
     */
    List<QueryResponse> getSqlScoreByCondition(@RequestBody QueryRequest queryRequest);

    /**
     * 查询Sql模板给前端做筛选控件
     */
    List<String> getSqlTemplateList();
}
