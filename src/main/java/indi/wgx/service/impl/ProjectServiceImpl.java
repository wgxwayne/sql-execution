package indi.wgx.service.impl;

import indi.wgx.mapper.ProjectMapper;
import indi.wgx.pojo.ProjectVersion;
import indi.wgx.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<ProjectVersion> getProjectList() {
        return projectMapper.getProjectList();
    }

    @Override
    public List<String> getProjectNameList() {
        return projectMapper.getProjectNameList();
    }

    @Override
    public List<Long> getProjectVersionIdList() {
        return projectMapper.getProjectVersionIdList();
    }
}
