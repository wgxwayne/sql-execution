package indi.wgx.service.strategy;

import indi.wgx.vo.FieldMap;

public class CalculateRowsScore implements FieldStrategy{

    private static final Integer LINE_1 = 5;
    private static final Integer LINE_2 = 10;
    private static final Integer LINE_3 = 15;
    private static final Integer LINE_4 = 20;

    private static final Double LINE_1_SCORE = 0.0;
    private static final Double LINE_2_SCORE = 20.0;
    private static final Double LINE_3_SCORE = 40.0;
    private static final Double LINE_4_SCORE = 60.0;
    private static final Double LINE_5_SCORE = 100.0;


    @Override
    public Double calculateFieldScore(String fieldName, String value) {
        double score = 0.0;
        int rows = Integer.parseInt(value);
        score = rows <= LINE_1 ? LINE_1_SCORE : (rows <= LINE_2 ? LINE_2_SCORE : (rows <= LINE_3 ? LINE_3_SCORE : (rows <= LINE_4 ? LINE_4_SCORE : LINE_5_SCORE)));
        // 乘以rows的系数
        score = score * FieldMap.singleFieldRatio.get(fieldName);
        return score;
    }
}
