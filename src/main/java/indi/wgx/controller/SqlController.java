package indi.wgx.controller;

import indi.wgx.config.ResultResponse;
import indi.wgx.request.QueryRequest;
import indi.wgx.response.QueryResponse;
import indi.wgx.service.ScoreService;
import indi.wgx.service.SqlService;
import indi.wgx.vo.AllFieldVo;
import indi.wgx.vo.HighTimeSqlVo;
import indi.wgx.vo.OrderedScoreVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController  // 注意这里要用RestController
@RequestMapping("/sql")
public class SqlController {

    @Autowired
    SqlService sqlService;

    @Autowired
    ScoreService scoreService;

    /**
     * 通过项目版本id。获取该版本项目的SQL分数排序
     */
    @RequestMapping("/getAllOrderedScore")
    public ResultResponse<Object> getAllOrderedScore(Long projectVersionId) {
        List<OrderedScoreVo> allOrderedScore = sqlService.getAllOrderedScore(projectVersionId);
        return ResultResponse.ok().data(allOrderedScore);
    }

    /**
     * 通过项目版本id和时间阈值,获取高的耗时sql
     */
    @RequestMapping("/getHighTimeSql")
    public ResultResponse<Object> getHighTimeSql(Long projectVersionId, Integer sqlTime) {
        List<HighTimeSqlVo> highTimeSQL = sqlService.getHighTimeSQL(projectVersionId, sqlTime);
        return ResultResponse.ok().data(highTimeSQL);
    }

    /**
     * 复杂条件查询接口
     * 返回给前端的表名称用;隔开
     */
    @RequestMapping("/getSqlScoreByCondition")
    public ResultResponse<Object> getSqlScoreByCondition(@RequestBody QueryRequest queryRequest) {
        if (null == queryRequest) {
            log.info("queryRequest请求体为空");
            return ResultResponse.ok().data(null);
        }
        String projectName = queryRequest.getProjectName();
        if (projectName == null || projectName.equals("")) {
            log.info("没有选择项目名称");
            return ResultResponse.ok().data(null);
        }
        List<QueryResponse> sqlScoreByCondition = sqlService.getSqlScoreByCondition(queryRequest);
        return ResultResponse.ok().data(sqlScoreByCondition);
    }

    @RequestMapping("/getSqlTemplateList")
    public ResultResponse<Object> getSqlTemplateList(){
        List<String> sqlTemplateList = sqlService.getSqlTemplateList();
        return ResultResponse.ok().data(sqlTemplateList);
    }

    @RequestMapping("/getAllFieldInformation")
    public ResultResponse<Object> getAllFieldInformation(Long projectVersionId) {
        List<AllFieldVo> allFieldInformation = scoreService.getAllFieldInformation(projectVersionId);
        return ResultResponse.ok().data(allFieldInformation);
    }
}
