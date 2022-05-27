package indi.wgx.controller;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import indi.wgx.config.ResultResponse;
import indi.wgx.service.InitService;
import indi.wgx.service.ScoreService;
import indi.wgx.vo.InitTableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/init")
public class InitController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    InitService initService;

    /**
     * 从其他项目中获取所有的信息到数据库中
     */
    @RequestMapping("/getAllData")
    public void getAllData(@RequestBody InitTableVo initTableVo) {

        System.out.println("接收到的数据为============");
        System.out.println(initTableVo.getProjectVersion());
        System.out.println(initTableVo.getSqlTemplate());
        System.out.println(initTableVo.getSqlExecution());
        initTableVo.getSqlDimensionList().forEach(System.out::println);

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        initService.initAllTable(initTableVo);
        log.info("成功初始化数据......");
    }

    /**
     * 在一个项目接入SQL打分服务时生成，此后无论修改名称或者版本都不发生变化
     */
    @RequestMapping("/generateProjectId")
    public ResultResponse<Object> generateProjectId() {
        // 雪花漂移算法
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
        Long projectId = YitIdHelper.nextId();
        return ResultResponse.ok().data(projectId);
    }
}
