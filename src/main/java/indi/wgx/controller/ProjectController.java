package indi.wgx.controller;

import indi.wgx.config.ResultResponse;
import indi.wgx.pojo.ProjectVersion;
import indi.wgx.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    /**
     * 测试：获取所有项目以及版本信息
     */
    @RequestMapping("/getProjectList")
    public ResultResponse<Object> getProjectList() {
        List<ProjectVersion> projectList = projectService.getProjectList();
        return ResultResponse.ok().data(projectList);
    }

    @RequestMapping("/getProjectNameList")
    public ResultResponse<Object> getProjectNameList() {
        List<String> projectNameList = projectService.getProjectNameList();
        return ResultResponse.ok().data(projectNameList);
    }

    @PostMapping("/getProjectVersionIdList")
    public ResultResponse<Object> getProjectVersionIdList() {
        List<Long> projectVersionIdList = projectService.getProjectVersionIdList();
        return ResultResponse.ok().data(projectVersionIdList);
    }
}
