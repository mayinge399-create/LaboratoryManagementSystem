package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ShiyanshebeiEntity;
import com.entity.view.ShiyanshebeiView;

import com.service.ShiyanshebeiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 实验设备
 * 后端接口
 * @author 
 * @email 
 * @date 2021-05-17 08:45:46
 */
@RestController
@RequestMapping("/shiyanshebei")
public class ShiyanshebeiController {
    @Autowired
    private ShiyanshebeiService shiyanshebeiService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final AtomicBoolean categoryTableReady = new AtomicBoolean(false);
    private final AtomicBoolean mainCategoryColumnReady = new AtomicBoolean(false);

    private static final List<String> FIXED_CATEGORIES = Arrays.asList(
        "生物类",
        "化学类",
        "物理类",
        "计算机与电子信息类",
        "机械与工程类",
        "环境与检测类",
        "医学与药学类",
        "农林牧渔类",
        "地质与资源类"
    );
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShiyanshebeiEntity shiyanshebei,
		HttpServletRequest request){
        EntityWrapper<ShiyanshebeiEntity> ew = new EntityWrapper<ShiyanshebeiEntity>();
        shiyanshebei.setCategories(null);
        applyCategoryFilter(ew, params.get("categories"));
		PageUtils page = shiyanshebeiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shiyanshebei), params), params));
        fillEntityCategories(page.getList());

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShiyanshebeiEntity shiyanshebei, 
		HttpServletRequest request){
        EntityWrapper<ShiyanshebeiEntity> ew = new EntityWrapper<ShiyanshebeiEntity>();
        shiyanshebei.setCategories(null);
        applyCategoryFilter(ew, params.get("categories"));
		PageUtils page = shiyanshebeiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shiyanshebei), params), params));
        fillEntityCategories(page.getList());
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShiyanshebeiEntity shiyanshebei){
       	EntityWrapper<ShiyanshebeiEntity> ew = new EntityWrapper<ShiyanshebeiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shiyanshebei, "shiyanshebei")); 
        return R.ok().put("data", shiyanshebeiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShiyanshebeiEntity shiyanshebei){
        EntityWrapper< ShiyanshebeiEntity> ew = new EntityWrapper< ShiyanshebeiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shiyanshebei, "shiyanshebei")); 
		ShiyanshebeiView shiyanshebeiView =  shiyanshebeiService.selectView(ew);
		return R.ok("查询实验设备成功").put("data", shiyanshebeiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShiyanshebeiEntity shiyanshebei = shiyanshebeiService.selectById(id);
        if (shiyanshebei != null) {
            List<String> categories = getCategoriesByDeviceId(id);
            shiyanshebei.setCategories(categories);
            shiyanshebei.setCategory(String.join(",", categories));
        }
        return R.ok().put("data", shiyanshebei);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShiyanshebeiEntity shiyanshebei = shiyanshebeiService.selectById(id);
        if (shiyanshebei != null) {
            List<String> categories = getCategoriesByDeviceId(id);
            shiyanshebei.setCategories(categories);
            shiyanshebei.setCategory(String.join(",", categories));
        }
        return R.ok().put("data", shiyanshebei);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShiyanshebeiEntity shiyanshebei, HttpServletRequest request){
        ensureMainCategoryColumnReady();
    	shiyanshebei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
        List<String> categories = normalizeCategories(shiyanshebei);
        shiyanshebei.setCategory(String.join(",", categories));
    	//ValidatorUtils.validateEntity(shiyanshebei);
        shiyanshebeiService.insert(shiyanshebei);
        safeSaveDeviceCategories(shiyanshebei.getId(), categories);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShiyanshebeiEntity shiyanshebei, HttpServletRequest request){
        ensureMainCategoryColumnReady();
    	shiyanshebei.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
        List<String> categories = normalizeCategories(shiyanshebei);
        shiyanshebei.setCategory(String.join(",", categories));
    	//ValidatorUtils.validateEntity(shiyanshebei);
        shiyanshebeiService.insert(shiyanshebei);
        safeSaveDeviceCategories(shiyanshebei.getId(), categories);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShiyanshebeiEntity shiyanshebei, HttpServletRequest request){
        ensureMainCategoryColumnReady();
        List<String> categories = normalizeCategories(shiyanshebei);
        shiyanshebei.setCategory(String.join(",", categories));
        //ValidatorUtils.validateEntity(shiyanshebei);
        shiyanshebeiService.updateById(shiyanshebei);//全部更新
        safeSaveDeviceCategories(shiyanshebei.getId(), categories);
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shiyanshebeiService.deleteBatchIds(Arrays.asList(ids));
        if (ids != null && ids.length > 0) {
            safeDeleteDeviceCategories(ids);
        }
        return R.ok();
    }

    /**
     * 首页设备统计
     */
    @RequestMapping("/dashboard")
    public R dashboard() {
        Map<String, Object> result = new HashMap<String, Object>();

        int total = shiyanshebeiService.selectCount(new EntityWrapper<ShiyanshebeiEntity>());

        Map<String, Integer> categoryStats = new LinkedHashMap<String, Integer>();
        for (String category : FIXED_CATEGORIES) {
            Integer count = safeCategoryCount(category);
            categoryStats.put(category, count == null ? 0 : count);
        }

        Map<String, Integer> statusStats = new LinkedHashMap<String, Integer>();
        statusStats.put("正常", shiyanshebeiService.selectCount(new EntityWrapper<ShiyanshebeiEntity>().eq("shebeizhuangtai", "正常")));
        statusStats.put("维修", shiyanshebeiService.selectCount(new EntityWrapper<ShiyanshebeiEntity>().eq("shebeizhuangtai", "维修")));
        statusStats.put("报废", shiyanshebeiService.selectCount(new EntityWrapper<ShiyanshebeiEntity>().eq("shebeizhuangtai", "报废")));

        List<ShiyanshebeiEntity> recentList = shiyanshebeiService.selectList(
            new EntityWrapper<ShiyanshebeiEntity>().orderBy("addtime", false).last("limit 5")
        );

        result.put("total", total);
        result.put("categoryStats", categoryStats);
        result.put("statusStats", statusStats);
        result.put("recentList", recentList);
        return R.ok().put("data", result);
    }

    /**
     * 分类选项（默认分类 + 已存在分类）
     */
    @RequestMapping("/categoryOptions")
    public R categoryOptions() {
        return R.ok().put("data", FIXED_CATEGORIES);
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ShiyanshebeiEntity> wrapper = new EntityWrapper<ShiyanshebeiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = shiyanshebeiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}

    private List<String> normalizeCategories(ShiyanshebeiEntity entity) {
        List<String> categories = new ArrayList<String>();
        if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
            categories.addAll(entity.getCategories());
        } else if (StringUtils.isNotBlank(entity.getCategory())) {
            categories.addAll(Arrays.asList(entity.getCategory().split(",")));
        }
        return categories.stream()
            .map(String::trim)
            .filter(StringUtils::isNotBlank)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<String> parseCategories(Object categoriesParam) {
        if (categoriesParam == null) {
            return new ArrayList<String>();
        }
        String raw = categoriesParam.toString().trim();
        if (StringUtils.isBlank(raw)) {
            return new ArrayList<String>();
        }
        if (raw.startsWith("[") && raw.endsWith("]")) {
            raw = raw.substring(1, raw.length() - 1);
        }
        List<String> categories = new ArrayList<String>();
        for (String item : raw.split(",")) {
            String normalized = item.replace("\"", "").trim();
            if (StringUtils.isNotBlank(normalized)) {
                categories.add(normalized);
            }
        }
        return categories.stream().distinct().collect(Collectors.toList());
    }

    private void applyCategoryFilter(EntityWrapper<ShiyanshebeiEntity> ew, Object categoriesParam) {
        List<String> categories = parseCategories(categoriesParam);
        if (categories.isEmpty()) {
            return;
        }
        if (!ensureCategoryTableReady()) {
            ew.andNew();
            for (int i = 0; i < categories.size(); i++) {
                if (i > 0) {
                    ew.or();
                }
                ew.like("category", categories.get(i));
            }
            return;
        }
        String placeholders = String.join(",", Collections.nCopies(categories.size(), "?"));
        List<Long> ids = jdbcTemplate.queryForList(
            "SELECT DISTINCT shiyanshebei_id FROM shiyanshebei_category WHERE category IN (" + placeholders + ")",
            categories.toArray(),
            Long.class
        );
        if (ids == null || ids.isEmpty()) {
            ew.eq("id", -1L);
            return;
        }
        ew.in("id", ids);
    }

    private List<String> getCategoriesByDeviceId(Long deviceId) {
        if (deviceId == null) {
            return new ArrayList<String>();
        }
        if (!ensureCategoryTableReady()) {
            ShiyanshebeiEntity entity = shiyanshebeiService.selectById(deviceId);
            if (entity == null || StringUtils.isBlank(entity.getCategory())) {
                return new ArrayList<String>();
            }
            return Arrays.stream(entity.getCategory().split(","))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
        }
        return jdbcTemplate.queryForList(
            "SELECT category FROM shiyanshebei_category WHERE shiyanshebei_id = ? ORDER BY id ASC",
            new Object[]{deviceId},
            String.class
        );
    }

    private void saveDeviceCategories(Long deviceId, List<String> categories) {
        if (deviceId == null) {
            return;
        }
        if (!ensureCategoryTableReady()) {
            return;
        }
        jdbcTemplate.update("DELETE FROM shiyanshebei_category WHERE shiyanshebei_id = ?", deviceId);
        for (String category : categories) {
            jdbcTemplate.update(
                "INSERT INTO shiyanshebei_category (id, shiyanshebei_id, category, addtime) VALUES (?, ?, ?, NOW())",
                new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue(),
                deviceId,
                category
            );
        }
    }

    private void safeSaveDeviceCategories(Long deviceId, List<String> categories) {
        try {
            saveDeviceCategories(deviceId, categories);
        } catch (Exception ignored) {
            // 回退为主表 category 字段，不中断主流程，避免前端出现 500
        }
    }

    private void safeDeleteDeviceCategories(Long[] ids) {
        try {
            if (!ensureCategoryTableReady()) {
                return;
            }
            String placeholders = String.join(",", Collections.nCopies(ids.length, "?"));
            jdbcTemplate.update("DELETE FROM shiyanshebei_category WHERE shiyanshebei_id IN (" + placeholders + ")", (Object[]) ids);
        } catch (Exception ignored) {
            // 关联表删除失败不影响主表删除
        }
    }

    private Integer safeCategoryCount(String category) {
        try {
            if (!ensureCategoryTableReady()) {
                return shiyanshebeiService.selectCount(new EntityWrapper<ShiyanshebeiEntity>().like("category", category));
            }
            return jdbcTemplate.queryForObject(
                "SELECT COUNT(DISTINCT shiyanshebei_id) FROM shiyanshebei_category WHERE category = ?",
                new Object[]{category},
                Integer.class
            );
        } catch (Exception e) {
            return shiyanshebeiService.selectCount(new EntityWrapper<ShiyanshebeiEntity>().like("category", category));
        }
    }

    private boolean ensureCategoryTableReady() {
        if (categoryTableReady.get()) {
            return true;
        }
        try {
            jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS shiyanshebei_category (" +
                    "id BIGINT NOT NULL COMMENT '主键'," +
                    "shiyanshebei_id BIGINT NOT NULL COMMENT '设备ID'," +
                    "category VARCHAR(200) NOT NULL COMMENT '分类名称'," +
                    "addtime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'," +
                    "PRIMARY KEY (id)," +
                    "KEY idx_shiyanshebei_id (shiyanshebei_id)," +
                    "KEY idx_category (category)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-分类关联表'"
            );
            categoryTableReady.set(true);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private boolean ensureMainCategoryColumnReady() {
        if (mainCategoryColumnReady.get()) {
            return true;
        }
        try {
            jdbcTemplate.execute("ALTER TABLE shiyanshebei ADD COLUMN category VARCHAR(200) DEFAULT NULL COMMENT '分类' AFTER danwei");
        } catch (Exception ignored) {
            // 字段已存在或数据库不支持 AFTER 语法时忽略，后续再做探测
        }
        try {
            Integer exists = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM information_schema.COLUMNS " +
                    "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'shiyanshebei' AND COLUMN_NAME = 'category'",
                Integer.class
            );
            boolean ready = exists != null && exists > 0;
            if (ready) {
                mainCategoryColumnReady.set(true);
            }
            return ready;
        } catch (Exception e) {
            return false;
        }
    }

    private void fillEntityCategories(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (Object row : list) {
            if (row instanceof ShiyanshebeiEntity) {
                ShiyanshebeiEntity entity = (ShiyanshebeiEntity) row;
                List<String> categories = getCategoriesByDeviceId(entity.getId());
                entity.setCategories(categories);
                entity.setCategory(String.join(",", categories));
            } else if (row instanceof ShiyanshebeiView) {
                ShiyanshebeiView view = (ShiyanshebeiView) row;
                List<String> categories = getCategoriesByDeviceId(view.getId());
                view.setCategory(String.join(",", categories));
            }
        }
    }
	


}
