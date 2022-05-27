package indi.wgx.service.strategy;

import org.springframework.stereotype.Service;

/**
 * Strategy角色负责决定实现策略所必需的接口（API）
 */
@Service
public interface FieldStrategy {
    Double calculateFieldScore(String fieldName, String value);
}
