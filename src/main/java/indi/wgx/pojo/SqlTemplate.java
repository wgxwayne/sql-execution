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
public class SqlTemplate {
    private Long sqlTemplateId;
    private Long projectVersionId;
    private String sqlTemplate;
    private Double templateScore;
    private String databaseName;
    private String tableName;

    private String minScoreSqlContext;
    private Double averageSqlTime;
    private Integer maxSqlTime;
    private Integer minSqlTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtCreate;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtModified;

    List<AllFieldVo> allFields;


    /**
     * 计算模板sql的得分
     */
    public Double calculateForTemplateScore(List<AllFieldVo> allFields, Long sqlTemplateId) {
        List<AllFieldVo> sqlTemplateList = allFields.stream().filter(s -> s.getSqlTemplateId().equals(sqlTemplateId)).collect(Collectors.toList());
        // 对result集合进行处理，统计出所有字段的扣分总和
        int length = sqlTemplateList.size();
        double score = sqlTemplateList.stream().mapToDouble(AllFieldVo::getContentScore).sum();
        // 取平均值
        templateScore = score / length;
        return templateScore;
    }

    /**
     * 获取sql模板下面的具体sql中，得分最低的sql
     */
    public String getMinScoreSqlContent(List<AllFieldVo> sameSqlTemplateIdList) {
        // 具体sql得分最少的sql
        Double minScore = sameSqlTemplateIdList.stream().mapToDouble(AllFieldVo::getContentScore).min().getAsDouble();
        List<AllFieldVo> sameMinScoreCollect = sameSqlTemplateIdList.stream().filter(item -> item.getContentScore().equals(minScore)).collect(Collectors.toList());
        // 按照具体sql内容去重
        List<String> distinctContentList = sameMinScoreCollect.stream().map(AllFieldVo::getSqlContent).distinct().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (String s : distinctContentList) {
            sb.append(s);
        }
        minScoreSqlContext = sb.toString();
        return minScoreSqlContext;
    }

    public Double getAverageSqlTime(List<AllFieldVo> sameSqlTemplateIdList) {
        return sameSqlTemplateIdList.stream().mapToDouble(AllFieldVo::getSqlTime).average().getAsDouble();
    }

    public Integer getMaxSqlTime(List<AllFieldVo> sameSqlTemplateIdList) {
        return sameSqlTemplateIdList.stream().mapToInt(AllFieldVo::getSqlTime).max().getAsInt();
    }

    public Integer getMinSqlTime(List<AllFieldVo> sameSqlTemplateIdList) {
        return sameSqlTemplateIdList.stream().mapToInt(AllFieldVo::getSqlTime).min().getAsInt();
    }

}
