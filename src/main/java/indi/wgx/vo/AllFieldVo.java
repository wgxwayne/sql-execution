package indi.wgx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 将所有的字段都放在这个实体类中
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllFieldVo {
    private Long projectVersionId;
    private Long projectId;
    private String projectName;
    private String projectVersion;

    private Long sqlTemplateId;
    private String sqlTemplate;
    private Double templateScore;
    private String databaseName;
    private String tableName;
    private String minScoreSqlContext;
    private Double averageSqlTime;
    private Integer maxSqlTime;
    private Integer minSqlTime;

    private Long executionId;
    private String sqlContent;
    private Double contentScore;
    private Integer sqlTime;
    private Double timeScore;

    private Long dimensionId;
    private String executionField;
    private Integer rowsNum;
    private String value;
    private Double fieldScore;
}

//class SqlExecution{
//    private Long sqlTemplateId;
//    private Long executionId;
//    private String sqlContent;
//    private Double contentScore;
//    private Integer sqlTime;
//    private Double timeScore;
//
//    public void calculate(List<Dimension> Dimensions){
//         contentScore = dimensions.stream().map()
//    }
//}
//
//abstract class Dimension{
//    private Long dimensionId;
//    private String executionField;
//    private Integer rowsNum;
//    private String value;
//    private Double fieldScore;
//
//    public abstract BigDecimal calculate();
//}
//
//class ADimension{
//    public BigDecimal calculate(){
//
//    }
//}
