package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实验设备借阅
 */
@TableName("shiyanshebei_borrow")
public class ShiyanshebeiBorrowEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public ShiyanshebeiBorrowEntity() {}

	@TableId
	private Long id;
	private Long shiyanshebeiId;
	private String shebeibianhao;
	private String shebeimingcheng;
	private String yuyuefangshi;
	private String yuyueren;
	private String yuyueshijian;
	private String guihaishijian;
	private String yuyuezhuangtai;
	private String xuehao;
	private String xueshengxingming;
	private String banji;
	private String shouji;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Long getShiyanshebeiId() { return shiyanshebeiId; }
	public void setShiyanshebeiId(Long shiyanshebeiId) { this.shiyanshebeiId = shiyanshebeiId; }
	public String getShebeibianhao() { return shebeibianhao; }
	public void setShebeibianhao(String shebeibianhao) { this.shebeibianhao = shebeibianhao; }
	public String getShebeimingcheng() { return shebeimingcheng; }
	public void setShebeimingcheng(String shebeimingcheng) { this.shebeimingcheng = shebeimingcheng; }
	public String getYuyuefangshi() { return yuyuefangshi; }
	public void setYuyuefangshi(String yuyuefangshi) { this.yuyuefangshi = yuyuefangshi; }
	public String getYuyueren() { return yuyueren; }
	public void setYuyueren(String yuyueren) { this.yuyueren = yuyueren; }
	public String getYuyueshijian() { return yuyueshijian; }
	public void setYuyueshijian(String yuyueshijian) { this.yuyueshijian = yuyueshijian; }
	public String getGuihaishijian() { return guihaishijian; }
	public void setGuihaishijian(String guihaishijian) { this.guihaishijian = guihaishijian; }
	public String getYuyuezhuangtai() { return yuyuezhuangtai; }
	public void setYuyuezhuangtai(String yuyuezhuangtai) { this.yuyuezhuangtai = yuyuezhuangtai; }
	public String getXuehao() { return xuehao; }
	public void setXuehao(String xuehao) { this.xuehao = xuehao; }
	public String getXueshengxingming() { return xueshengxingming; }
	public void setXueshengxingming(String xueshengxingming) { this.xueshengxingming = xueshengxingming; }
	public String getBanji() { return banji; }
	public void setBanji(String banji) { this.banji = banji; }
	public String getShouji() { return shouji; }
	public void setShouji(String shouji) { this.shouji = shouji; }
	public Date getAddtime() { return addtime; }
	public void setAddtime(Date addtime) { this.addtime = addtime; }
}
