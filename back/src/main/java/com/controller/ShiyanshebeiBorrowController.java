package com.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ShiyanshebeiBorrowEntity;
import com.service.ShiyanshebeiBorrowService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实验设备借阅
 */
@RestController
@RequestMapping("/shiyanshebeiBorrow")
public class ShiyanshebeiBorrowController {
	@Autowired
	private ShiyanshebeiBorrowService shiyanshebeiBorrowService;

	@RequestMapping("/page")
	public R page(@RequestParam Map<String, Object> params, ShiyanshebeiBorrowEntity shiyanshebeiBorrow, HttpServletRequest request) {
		String tableName = (String) request.getSession().getAttribute("tableName");
		String username = (String) request.getSession().getAttribute("username");
		if ("xuesheng".equals(tableName)) {
			shiyanshebeiBorrow.setXuehao(username);
		}
		if ("jiaoshi".equals(tableName)) {
			shiyanshebeiBorrow.setYuyueren(username);
		}
		EntityWrapper<ShiyanshebeiBorrowEntity> ew = new EntityWrapper<ShiyanshebeiBorrowEntity>();
		PageUtils page = shiyanshebeiBorrowService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shiyanshebeiBorrow), params), params));
		return R.ok().put("data", page);
	}

	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params, ShiyanshebeiBorrowEntity shiyanshebeiBorrow, HttpServletRequest request) {
		String tableName = (String) request.getSession().getAttribute("tableName");
		String username = (String) request.getSession().getAttribute("username");
		if ("xuesheng".equals(tableName)) {
			shiyanshebeiBorrow.setXuehao(username);
		}
		if ("jiaoshi".equals(tableName)) {
			shiyanshebeiBorrow.setYuyueren(username);
		}
		EntityWrapper<ShiyanshebeiBorrowEntity> ew = new EntityWrapper<ShiyanshebeiBorrowEntity>();
		PageUtils page = shiyanshebeiBorrowService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shiyanshebeiBorrow), params), params));
		return R.ok().put("data", page);
	}

	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		return R.ok().put("data", shiyanshebeiBorrowService.selectById(id));
	}

	@RequestMapping("/save")
	public R save(@RequestBody ShiyanshebeiBorrowEntity shiyanshebeiBorrow, HttpServletRequest request) {
		shiyanshebeiBorrow.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
		if (shiyanshebeiBorrow.getYuyuezhuangtai() == null || shiyanshebeiBorrow.getYuyuezhuangtai().trim().isEmpty()) {
			shiyanshebeiBorrow.setYuyuezhuangtai("借出中");
		}
		shiyanshebeiBorrowService.insert(shiyanshebeiBorrow);
		return R.ok();
	}

	@RequestMapping("/update")
	public R update(@RequestBody ShiyanshebeiBorrowEntity shiyanshebeiBorrow, HttpServletRequest request) {
		shiyanshebeiBorrowService.updateById(shiyanshebeiBorrow);
		return R.ok();
	}

	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		shiyanshebeiBorrowService.deleteBatchIds(Arrays.asList(ids));
		return R.ok();
	}

	@RequestMapping("/returnDevice/{id}")
	public R returnDevice(@PathVariable("id") Long id, HttpServletRequest request) {
		ShiyanshebeiBorrowEntity entity = shiyanshebeiBorrowService.selectById(id);
		if (entity == null) {
			return R.error("记录不存在");
		}
		entity.setYuyuezhuangtai("已归还");
		entity.setGuihaishijian(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		shiyanshebeiBorrowService.updateById(entity);
		return R.ok();
	}
}
