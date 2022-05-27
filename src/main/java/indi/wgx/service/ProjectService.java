package indi.wgx.service;

import indi.wgx.pojo.ProjectVersion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    /**
     * 测试，获取所有项目版本信息
     */
    List<ProjectVersion> getProjectList();

    List<String> getProjectNameList();

    List<Long> getProjectVersionIdList();
}
