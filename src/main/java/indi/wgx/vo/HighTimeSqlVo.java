package indi.wgx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighTimeSqlVo {

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 表的名称
     */
    private String tableName;

    /**
     * 具体sql内容
     */
    private String sqlContent;

    /**
     * sql执行耗时
     */
    private Long sqlTime;
}
