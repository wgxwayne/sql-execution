package indi.wgx.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TableService {
    List<String> getTableNameList();
}
