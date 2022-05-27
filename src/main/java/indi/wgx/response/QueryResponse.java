package indi.wgx.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResponse {
    private Long projectId;
    private String projectName;
    private String projectVersion;

    private String sqlTemplate;
    private Double templateScore;
    private String tableName;

    // SQL模板下面性能最差的SQL语句
    private String minScoreSqlContext;

    // 最大耗时:SQl模板下面具体SQL的最大耗时
    private Integer maxSqlTime;

    // 平均耗时
    private double averageSqlTime;

    // 最小耗时：SQl模板下面具体SQL的最小耗时
    private Integer minSqlTime;
}
