package indi.wgx.service.strategy;

import java.util.HashMap;
import java.util.Map;

public abstract class FieldFactory {

    private static final Map<String, FieldStrategy> contextMap = new HashMap<>();

    static {
        contextMap.put("Extra", new CalculateExtraScore());
        contextMap.put("type", new CalculateTypeScore());
        contextMap.put("rows", new CalculateRowsScore());
    }

    public static FieldStrategy getFiledStrategy(String field) {
        return contextMap.get(field);
    }
}
