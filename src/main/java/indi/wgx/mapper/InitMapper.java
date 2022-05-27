package indi.wgx.mapper;

import indi.wgx.pojo.ProjectVersion;
import indi.wgx.pojo.SqlDimension;
import indi.wgx.pojo.SqlExecution;
import indi.wgx.pojo.SqlTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InitMapper {

    Long initProjectVersionTable(ProjectVersion projectVersion);

    Long initSqlTemplateTable(SqlTemplate sqlTemplate);

    Long initSqlExecutionTable(SqlExecution sqlExecution);

    Long initSqlDimensionTable(List<SqlDimension> list);
}
