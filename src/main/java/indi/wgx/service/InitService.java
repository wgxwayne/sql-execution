package indi.wgx.service;

import indi.wgx.vo.InitTableVo;
import org.springframework.stereotype.Service;

@Service
public interface InitService {
    void initAllTable(InitTableVo initTableVo);
}
