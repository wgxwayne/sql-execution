package indi.wgx.mapper;

import indi.wgx.pojo.ProjectVersion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    // 测试，获取所有项目版本信息
    List<ProjectVersion> getProjectList();

    /**
     * 返回项目名称列表 (去重) 做筛选控件
     */
    List<String> getProjectNameList();

    /**
     * 返回项目版本id列表 做筛选控件
     */
    List<Long> getProjectVersionIdList();

}
