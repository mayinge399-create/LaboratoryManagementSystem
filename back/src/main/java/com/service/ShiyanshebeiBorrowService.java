package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ShiyanshebeiBorrowEntity;
import com.utils.PageUtils;
import java.util.Map;

public interface ShiyanshebeiBorrowService extends IService<ShiyanshebeiBorrowEntity> {
	PageUtils queryPage(Map<String, Object> params);
	PageUtils queryPage(Map<String, Object> params, Wrapper<ShiyanshebeiBorrowEntity> wrapper);
}
