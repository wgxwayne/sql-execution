package indi.wgx.service.strategy;

import indi.wgx.vo.FieldMap;

public class CalculateTypeScore implements FieldStrategy{

    @Override
    public Double calculateFieldScore(String fieldName, String value) {
            // 乘以type的系数
            return FieldMap.typeMap.get(value).doubleValue() * FieldMap.singleFieldRatio.get(fieldName);
    }
}
