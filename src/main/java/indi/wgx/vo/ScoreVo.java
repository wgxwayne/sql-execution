package indi.wgx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVo {
    /**
     * 字段扣分
     */
    private Double fieldScore;
    /**
     * 耗时扣分
     */
    private Double timeScore;

    /**
     * 具体SQL分数
     */
    private Double contentScore;
    /**
     * 模板分数
     */
    private Double templateScore;
}
