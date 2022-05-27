package indi.wgx.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TableMapper {

    /**
     * 返回表的名称做筛选控件
     */
    List<String> getTableNameList();
}
