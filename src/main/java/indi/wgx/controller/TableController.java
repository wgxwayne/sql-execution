package indi.wgx.controller;

import indi.wgx.config.ResultResponse;
import indi.wgx.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    TableService tableService;

    @RequestMapping("/getTableNameList")
    public ResultResponse<Object> getTableNameList() {
        List<String> tableNameList = tableService.getTableNameList();
        return ResultResponse.ok().data(tableNameList);
    }
}
