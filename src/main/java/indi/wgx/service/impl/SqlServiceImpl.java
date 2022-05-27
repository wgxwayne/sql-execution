package indi.wgx.service.impl;

import com.github.pagehelper.PageHelper;
import indi.wgx.enums.OrderWayType;
import indi.wgx.mapper.SqlMapper;
import indi.wgx.request.QueryRequest;
import indi.wgx.response.QueryResponse;
import indi.wgx.service.SqlService;
import indi.wgx.vo.AllFieldVo;
import indi.wgx.vo.HighTimeSqlVo;
import indi.wgx.vo.OrderedScoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SqlServiceImpl implements SqlService {

    @Autowired
    SqlMapper sqlMapper;

    @Override
    public List<OrderedScoreVo> getAllOrderedScore(Long projectVersionId) {
        return sqlMapper.getAllOrderedScore(projectVersionId);
    }

    @Override
    public List<HighTimeSqlVo> getHighTimeSQL(Long projectVersionId, Integer sqlTime) {
        return sqlMapper.getHighTimeSQL(projectVersionId, sqlTime);
    }

    @Override
    public List<QueryResponse> getSqlScoreByCondition(QueryRequest queryRequest) {
        handleTableNames(queryRequest);
        handlePage(queryRequest);
        handleSort(queryRequest);

        if (queryRequest.getOrderBy() != null){
            // 分页 + 排序
            PageHelper.startPage(queryRequest.getCurrentPage(), queryRequest.getPageSize(), queryRequest.getOrderBy());
        } else {
            // 分页
            PageHelper.startPage(queryRequest.getCurrentPage(), queryRequest.getPageSize());
        }
        return sqlMapper.getSqlScoreByCondition(queryRequest);
    }

    /**
     * 处理表的名称
     */
    public void handleTableNames(QueryRequest queryRequest) {
        List<String> tableNamesList = queryRequest.getTableNames();
        // 转化为正则表达式的字符串,去匹配表的名称REGEXP('(value1)|(value2)|(value3)')
        // 注意：QueryRequest中的tableNames是一个List集合
        List<String> result = new ArrayList<>();
        // 转换后的格式：(www|ggg|xxx)

        // (sql_template|sql_execution)
        String tableNames = tableNamesList.stream().collect(Collectors.joining("|", "(", ")"));
        result.add(tableNames);
        queryRequest.setTableNames(result);
    }

    /**
     * 分页处理，分页必须指定
     */
    public void handlePage(QueryRequest queryRequest) {
        Integer currentPage = queryRequest.getCurrentPage();
        Integer pageSize = queryRequest.getPageSize();
        currentPage = (currentPage - 1) * pageSize;
        queryRequest.setCurrentPage(currentPage);
    }

    /**
     * 排序处理，排序可以不指定
     */
    public void handleSort(QueryRequest queryRequest) {
        String orderBy = queryRequest.getOrderBy();
        Integer orderWayField = queryRequest.getOrderWay();
        // 没有指定排序时
        if (orderBy == null || orderWayField == null || orderBy.equals("")) {
            queryRequest.setOrderBy(null);
            return;
        }
        String orderWay = orderWayField == 0 ? OrderWayType.ASCENDING_ORDER.getInfo() : OrderWayType.DESCENDING_ORDER.getInfo();
        orderBy = orderBy + " " + orderWay;
        queryRequest.setOrderBy(orderBy);
    }

    /**
     * 获取Sql模板列表
     */
    @Override
    public List<String> getSqlTemplateList() {
        return sqlMapper.getSqlTemplateList();
    }
}
