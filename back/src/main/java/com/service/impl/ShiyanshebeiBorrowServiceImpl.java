package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ShiyanshebeiBorrowDao;
import com.entity.ShiyanshebeiBorrowEntity;
import com.service.ShiyanshebeiBorrowService;
import com.utils.PageUtils;
import com.utils.Query;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("shiyanshebeiBorrowService")
public class ShiyanshebeiBorrowServiceImpl extends ServiceImpl<ShiyanshebeiBorrowDao, ShiyanshebeiBorrowEntity> implements ShiyanshebeiBorrowService {
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<ShiyanshebeiBorrowEntity> page = this.selectPage(
			new Query<ShiyanshebeiBorrowEntity>(params).getPage(),
			new EntityWrapper<ShiyanshebeiBorrowEntity>()
		);
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ShiyanshebeiBorrowEntity> wrapper) {
		Page<ShiyanshebeiBorrowEntity> page = this.selectPage(
			new Query<ShiyanshebeiBorrowEntity>(params).getPage(),
			wrapper
		);
		return new PageUtils(page);
	}
}
