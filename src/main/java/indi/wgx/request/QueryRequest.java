package indi.wgx.request;

import indi.wgx.pojo.ProjectVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequest {

    // 项目名称，不选不展示数据
    String projectName;

    // 多选，不选展示全部数据
    List<String> projectVersions;

    // 多选，不选展示全部数据
    // 为多表关联语句时，只要有一张表有，则查询出来
    List<String> tableNames;

    // 多选，不选展示全部数据
    // Sql模板选择多少，最终展示的数据就有多少行
    List<String> sqlTemplates;

    // 排序方式：分数，最大耗时，最小耗时，平均耗时
    private String orderBy;

    // 0 : 升序  1 : 降序
    private Integer orderWay;

    // 当前页
    private Integer currentPage;

    // 页面大小
    private Integer pageSize;
}
