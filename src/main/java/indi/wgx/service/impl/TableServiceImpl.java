package indi.wgx.service.impl;

import indi.wgx.mapper.TableMapper;
import indi.wgx.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    TableMapper tableMapper;

    /**
     * 从数据库中获取所有的表名，返回给前端做控件
     */
    @Override
    public List<String> getTableNameList() {
        List<String> tableNameList = tableMapper.getTableNameList();
        // 需要对表名称进行切分,如"project_version;sql_template;sql_execution;sql_dimension"需要提取出单独的表名称列表

        List<String> singleTableList = new ArrayList<>();
        for (String tableName : tableNameList) {
            String[] strArr = tableName.trim().split(";");
            singleTableList.addAll(Arrays.asList(strArr));
        }
        return singleTableList;
    }
}
