package indi.wgx.vo;

import indi.wgx.pojo.ProjectVersion;
import indi.wgx.pojo.SqlDimension;
import indi.wgx.pojo.SqlExecution;
import indi.wgx.pojo.SqlTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitTableVo {
    ProjectVersion projectVersion;

    SqlTemplate sqlTemplate;

    SqlExecution sqlExecution;

    List<SqlDimension> sqlDimensionList;
}
