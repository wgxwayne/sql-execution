package indi.wgx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.wgx.vo.AllFieldVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlExecution {
    private Long executionId;
    private Long sqlTemplateId;
    private String sqlContent;
    private Double contentScore;
    private Integer sqlTime;
    private Double timeScore;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtCreate;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtModified;


    // 这里的字段占比和耗时占比是最后具体sql总分的时候进行相乘
    // 字段占比
    private static final double TIME_RATIO = 0.6;
    // 耗时占比
    private static final double FIELD_RATIO = 0.4;

    private static final double FULL_SCORE = 100.0;


    public Double calculateForTimeScore(Integer sqlTime) {
        if (sqlTime <= 10) {
            timeScore = 0.0;
        } else if (sqlTime <= 20) {
            timeScore = 20.0;
        } else if (sqlTime <= 100){
            timeScore = 40.0;
        } else {
            timeScore = 100.0;
        }
        return timeScore;
    }

    /**
     * 计算具体sql的得分
     */
    public Double calculateForContentScore(List<AllFieldVo> allFields, Long executionId) {
        List<AllFieldVo> sqlDimensionList = allFields.stream().filter(s -> s.getExecutionId().equals(executionId)).collect(Collectors.toList());
        timeScore = sqlDimensionList.get(0).getTimeScore();
        double fieldScoreSum = sqlDimensionList.stream().mapToDouble(AllFieldVo::getFieldScore).sum();
        contentScore = fieldScoreSum * FIELD_RATIO + timeScore * TIME_RATIO;
        if (contentScore > FULL_SCORE) {
            contentScore = FULL_SCORE;
        }
        contentScore = FULL_SCORE - contentScore;
        return  contentScore;
    }
}
