package indi.wgx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.wgx.service.strategy.FieldFactory;
import indi.wgx.service.strategy.FieldStrategy;
import indi.wgx.vo.FieldMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlDimension {
    private Long dimensionId;
    private Long executionId;
    private String executionField;
    private Integer rowsNum;
    private String value;
    private Double fieldScore;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtCreate;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtModified;


    /**
     * 根据关键字段的值计算字段得分
     */
    public Double calculateForKeyFieldScore(String executionField, Integer rowsNum, String value) {
        FieldStrategy filedStrategy = FieldFactory.getFiledStrategy(executionField);
        fieldScore = filedStrategy.calculateFieldScore(executionField, value);
        Double rowFactor = FieldMap.rowsNumMap.get(rowsNum);
        fieldScore = fieldScore * rowFactor;
        return fieldScore;
    }
}
