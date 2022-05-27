package indi.wgx.service.strategy;

import indi.wgx.vo.FieldMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 具体的策略负责实现Strategy角色的接口（API），即负责实现具体的策略（战略、方向、方法和算法）。
 *
 * Extra字段计算分数
 */
@Slf4j
public class CalculateExtraScore implements FieldStrategy{

    @Override
    public Double calculateFieldScore(String fieldName, String value) {
        double score = 0.0;
        // Extra字段可能是一个字符串数组，用;进行分割
        String[] strArr = value.trim().split(";");
        if (strArr.length == 0) {
            log.info("Extra字段的value为空");
            return 0.0;
        }
        for (String s : strArr) {
            Integer extraValue = FieldMap.extraMap.get(s);
            if (extraValue != null) {
                score += extraValue.doubleValue() * FieldMap.singleFieldRatio.get(fieldName);
            }
        }
        return score;
    }
}
