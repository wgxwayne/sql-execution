package indi.wgx.service;

import indi.wgx.pojo.ProjectVersion;
import indi.wgx.request.QueryRequest;
import indi.wgx.response.QueryResponse;
import indi.wgx.vo.AllFieldVo;
import indi.wgx.vo.HighTimeSqlVo;
import indi.wgx.vo.OrderedScoreVo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface SqlService {

    // 通过项目版本id, 获取该版本项目的SQL分数排序
    List<OrderedScoreVo> getAllOrderedScore(Long projectVersionId);

    // 获取一个项目中高耗时的sql，
    List<HighTimeSqlVo> getHighTimeSQL(Long projectVersionId, Integer sqlTime);

    List<QueryResponse> getSqlScoreByCondition(@RequestBody QueryRequest queryRequest);

    List<String> getSqlTemplateList();
}
