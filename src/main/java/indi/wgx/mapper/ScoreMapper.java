package indi.wgx.mapper;

import indi.wgx.vo.AllFieldVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * 获取所有字段的信息（已经从日志存到数据库中了）
     */
    List<AllFieldVo> getAllFieldInformation(Long projectVersionId);

    /**
     * 更新所有表中关于分数的字段
     *
     * 场景：更改计分规则后更新分数，或者插入执行计划字段之后计算分数
     * @param fieldVoList
     */
    void updateAllScore(List<AllFieldVo> fieldVoList);

}
