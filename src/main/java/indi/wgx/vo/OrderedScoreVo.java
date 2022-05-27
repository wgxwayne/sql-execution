package indi.wgx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedScoreVo {

    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目版本
     */
    private String projectVersion;

    /**
     * 表的名称
     */
    private String tableName;

    /**
     * sql模板内容
     */
    private String sqlTemplate;

    /**
     * 具体sql内容
     */
    private String sqlContent;

    /**
     * sql模板得分
     */
    private Double templateScore;

    /**
     * 具体sql得分
     */
    private Double contentScore;
}
