package indi.wgx.service;

import indi.wgx.vo.AllFieldVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScoreService {
    /**
     * 根据项目版本id从数据库中提取出关键的字段
     * 根据关键字段来计算分数
     * @param projectVersionId 项目版本id
     */
    List<AllFieldVo> calculateTotalScore(Long projectVersionId);

    void updateAllScore(List<AllFieldVo> fieldVoList);

    List<AllFieldVo> getAllFieldInformation(Long projectVersionId);
}
