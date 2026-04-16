<template>
  <div class="addEdit-block">
    <el-form
      class="detail-form-content"
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="80px"
	  :style="{backgroundColor:addEditForm.addEditBoxColor}"
    >
      <el-row :gutter="16">
      <el-col :span="24" :md="12">
        <el-form-item class="input" v-if="type!='info'"  label="设备编号" prop="shebeibianhao">
          <el-input v-model="ruleForm.shebeibianhao" 
              placeholder="设备编号" clearable  :readonly="ro.shebeibianhao"></el-input>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="设备编号" prop="shebeibianhao">
              <el-input v-model="ruleForm.shebeibianhao" 
                placeholder="设备编号" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24" :md="12">
        <el-form-item class="input" v-if="type!='info'"  label="设备名称" prop="shebeimingcheng">
          <el-input v-model="ruleForm.shebeimingcheng" 
              placeholder="设备名称" clearable  :readonly="ro.shebeimingcheng"></el-input>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="设备名称" prop="shebeimingcheng">
              <el-input v-model="ruleForm.shebeimingcheng" 
                placeholder="设备名称" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24" :md="12">
        <el-form-item class="input" v-if="type!='info'"  label="型号" prop="xinghao">
          <el-input v-model="ruleForm.xinghao" 
              placeholder="型号" clearable  :readonly="ro.xinghao"></el-input>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="型号" prop="xinghao">
              <el-input v-model="ruleForm.xinghao" 
                placeholder="型号" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24" :md="12">
        <el-form-item class="input" v-if="type!='info'"  label="单位" prop="danwei">
          <el-input v-model="ruleForm.danwei" 
              placeholder="单位" clearable  :readonly="ro.danwei"></el-input>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="单位" prop="danwei">
              <el-input v-model="ruleForm.danwei" 
                placeholder="单位" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24" :md="12">
        <el-form-item class="select" v-if="type!='info'"  label="分类" prop="category">
          <el-checkbox-group v-model="ruleForm.categories">
            <el-checkbox
              v-for="(item,index) in categoryOptions"
              :key="index"
              :label="item"
            >{{ item }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="分类" prop="category">
              <el-input v-model="ruleForm.category"
                placeholder="分类" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24">  
        <el-form-item class="upload" v-if="type!='info' && !ro.tupian" label="图片" prop="tupian">
          <file-upload
          tip="点击上传图片"
          action="file/upload"
          :limit="3"
          :multiple="true"
          :fileUrls="ruleForm.tupian?ruleForm.tupian:''"
          @change="tupianUploadChange"
          ></file-upload>
        </el-form-item>
        <div v-else>
          <el-form-item v-if="ruleForm.tupian" label="图片" prop="tupian">
            <img style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.tupian.split(',')" :src="item" width="100" height="100">
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24" :md="12">
        <el-form-item class="input" v-if="type!='info'"  label="设备数量" prop="shebeishuliang">
          <el-input v-model="ruleForm.shebeishuliang" 
              placeholder="设备数量" clearable  :readonly="ro.shebeishuliang"></el-input>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="设备数量" prop="shebeishuliang">
              <el-input v-model="ruleForm.shebeishuliang" 
                placeholder="设备数量" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      <el-col :span="24" :md="12">
        <el-form-item class="select" v-if="type!='info'"  label="设备状态" prop="shebeizhuangtai">
          <el-select v-model="ruleForm.shebeizhuangtai" placeholder="请选择设备状态">
            <el-option
                v-for="(item,index) in shebeizhuangtaiOptions"
                v-bind:key="index"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <div v-else>
          <el-form-item class="input" label="设备状态" prop="shebeizhuangtai">
	      <el-input v-model="ruleForm.shebeizhuangtai"
                placeholder="设备状态" readonly></el-input>
          </el-form-item>
        </div>
      </el-col>
      </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item class="textarea" v-if="type!='info'" label="使用方法" prop="shiyongfangfa">
                <el-input
                  type="textarea"
                  :rows="8"
                  placeholder="使用方法"
                  v-model="ruleForm.shiyongfangfa" >
                </el-input>
              </el-form-item>
              <div v-else>
                <el-form-item v-if="ruleForm.shiyongfangfa" label="使用方法" prop="shiyongfangfa">
                    <span>{{ruleForm.shiyongfangfa}}</span>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
      <el-form-item class="btn">
        <el-button v-if="type!='info'" type="primary" class="btn-success" @click="onSubmit">提交</el-button>
        <el-button v-if="type!='info'" class="btn-close" @click="back()">取消</el-button>
        <el-button v-if="type=='info'" type="success" class="btn-success" @click="reserveDevice">预约</el-button>
        <el-button v-if="type=='info'" class="btn-close" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
    

  </div>
</template>
<script>
// 数字，邮件，手机，url，身份证校验
import { isNumber,isIntNumer,isEmail,isPhone, isMobile,isURL,checkIdCard } from "@/utils/validate";
export default {
  data() {
    let self = this
    var validateIdCard = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!checkIdCard(value)) {
        callback(new Error("请输入正确的身份证号码"));
      } else {
        callback();
      }
    };
    var validateUrl = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isURL(value)) {
        callback(new Error("请输入正确的URL地址"));
      } else {
        callback();
      }
    };
    var validateMobile = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isMobile(value)) {
        callback(new Error("请输入正确的手机号码"));
      } else {
        callback();
      }
    };
    var validatePhone = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isPhone(value)) {
        callback(new Error("请输入正确的电话号码"));
      } else {
        callback();
      }
    };
    var validateEmail = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isEmail(value)) {
        callback(new Error("请输入正确的邮箱地址"));
      } else {
        callback();
      }
    };
    var validateNumber = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isNumber(value)) {
        callback(new Error("请输入数字"));
      } else {
        callback();
      }
    };
    var validateIntNumber = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isIntNumer(value)) {
        callback(new Error("请输入整数"));
      } else {
        callback();
      }
    };
    return {
	  addEditForm: {"btnSaveFontColor":"rgba(34, 32, 32, 1)","selectFontSize":"14px","btnCancelBorderColor":"#DCDFE6","inputBorderRadius":"16px","inputFontSize":"14px","textareaBgColor":"rgba(207, 199, 199, 0.13)","btnSaveFontSize":"14px","textareaBorderRadius":"16px","uploadBgColor":"rgba(207, 199, 199, 0.13)","textareaBorderStyle":"solid","btnCancelWidth":"88px","textareaHeight":"120px","dateBgColor":"rgba(207, 199, 199, 0.13)","btnSaveBorderRadius":"16px","uploadLableFontSize":"14px","textareaBorderWidth":"1px","inputLableColor":"#606266","addEditBoxColor":"rgba(238, 221, 221, 0.32)","dateIconFontSize":"14px","btnSaveBgColor":"#409EFF","uploadIconFontColor":"#8c939d","textareaBorderColor":"#DCDFE6","btnCancelBgColor":"rgba(84, 244, 185, 1)","selectLableColor":"#606266","btnSaveBorderStyle":"solid","dateBorderWidth":"1px","dateLableFontSize":"14px","dateBorderRadius":"16px","btnCancelBorderStyle":"solid","selectLableFontSize":"14px","selectBorderStyle":"solid","selectIconFontColor":"#C0C4CC","btnCancelHeight":"44px","inputHeight":"40px","btnCancelFontColor":"rgba(23, 23, 24, 1)","dateBorderColor":"#DCDFE6","dateIconFontColor":"#C0C4CC","uploadBorderStyle":"solid","dateBorderStyle":"solid","dateLableColor":"#606266","dateFontSize":"14px","inputBorderWidth":"1px","uploadIconFontSize":"28px","selectHeight":"40px","inputFontColor":"rgba(25, 26, 27, 1)","uploadHeight":"148px","textareaLableColor":"#606266","textareaLableFontSize":"14px","btnCancelFontSize":"14px","inputBorderStyle":"solid","btnCancelBorderRadius":"16px","inputBgColor":"rgba(207, 199, 199, 0.13)","inputLableFontSize":"14px","uploadLableColor":"#606266","uploadBorderRadius":"16px","btnSaveHeight":"44px","selectBgColor":"rgba(207, 199, 199, 0.13)","btnSaveWidth":"88px","selectIconFontSize":"14px","dateHeight":"40px","selectBorderColor":"#DCDFE6","inputBorderColor":"#DCDFE6","uploadBorderColor":"#DCDFE6","textareaFontColor":"rgba(25, 26, 27, 1)","selectBorderWidth":"1px","dateFontColor":"rgba(255, 69, 0, 0.66)","btnCancelBorderWidth":"1px","uploadBorderWidth":"1px","textareaFontSize":"14px","selectBorderRadius":"16px","selectFontColor":"rgba(25, 26, 27, 1)","btnSaveBorderColor":"#409EFF","btnSaveBorderWidth":"1px"},
      id: '',
      type: '',
      ro:{
	shebeibianhao : false,
	shebeimingcheng : false,
	xinghao : false,
	danwei : false,
	category : false,
	tupian : false,
	shebeishuliang : false,
	shiyongfangfa : false,
	shebeizhuangtai : false,
      },
      ruleForm: {
        id: '',
        shebeibianhao: '',
        shebeimingcheng: '',
        xinghao: '',
        danwei: '',
        category: '',
        categories: [],
        tupian: '',
        shebeishuliang: '',
        shiyongfangfa: '',
        shebeizhuangtai: '',
      },
      reserveForm: {
        id: '',
        shiyanshebeiId: '',
        shebeibianhao: '',
        shebeimingcheng: '',
        yuyuefangshi: '借离实验室',
        yuyueren: '',
        yuyueshijian: '',
        guihaishijian: '',
        yuyuezhuangtai: '借出中'
      },
          categoryOptions: [],
          shebeizhuangtaiOptions: [],
      rules: {
          shebeibianhao: [
                { required: true, message: '设备编号不能为空', trigger: 'blur' },
          ],
          shebeimingcheng: [
                { required: true, message: '设备名称不能为空', trigger: 'blur' },
          ],
          xinghao: [
                { required: true, message: '型号不能为空', trigger: 'blur' },
          ],
          danwei: [
          ],
          category: [
                { validator: (rule, value, callback) => {
                  if (!this.ruleForm.categories || this.ruleForm.categories.length === 0) {
                    callback(new Error('分类不能为空'));
                    return;
                  }
                  callback();
                }, trigger: 'change' },
          ],
          tupian: [
          ],
          shebeishuliang: [
                { required: true, message: '设备数量不能为空', trigger: 'blur' },
                { validator: validateIntNumber, trigger: 'blur' },
          ],
          shiyongfangfa: [
          ],
          shebeizhuangtai: [
          ],
      }
    };
  },
  props: ["parent"],
  computed: {
  },
  created() {
	this.addEditStyleChange()
	this.addEditUploadStyleChange()
  },
  methods: {
    // Vue2 compatibility shim for templates compiled with ref checks.
    _v_isRef(val) {
      return !!(val && typeof val === "object" && Object.prototype.hasOwnProperty.call(val, "value"));
    },
    // 下载
    download(file){
      window.open(`${file}`)
    },
    // 初始化
    init(id,type) {
      this.id = id || '';
      this.type = type || '';
      this.ro = {
        shebeibianhao : false,
        shebeimingcheng : false,
        xinghao : false,
        danwei : false,
        category : false,
        tupian : false,
        shebeishuliang : false,
        shiyongfangfa : false,
        shebeizhuangtai : false,
      };
      this.ruleForm = {
        id: '',
        shebeibianhao: '',
        shebeimingcheng: '',
        xinghao: '',
        danwei: '',
        category: '',
        categories: [],
        tupian: '',
        shebeishuliang: '',
        shiyongfangfa: '',
        shebeizhuangtai: ''
      };
      if(this.type=='info'||this.type=='else'){
        this.info(id);
      }else if(this.type=='cross'){
        var obj = this.$storage.getObj('crossObj');
        for (var o in obj){
          if(o=='shebeibianhao'){
            this.ruleForm.shebeibianhao = obj[o];
	    this.ro.shebeibianhao = true;
            continue;
          }
          if(o=='shebeimingcheng'){
            this.ruleForm.shebeimingcheng = obj[o];
	    this.ro.shebeimingcheng = true;
            continue;
          }
          if(o=='xinghao'){
            this.ruleForm.xinghao = obj[o];
	    this.ro.xinghao = true;
            continue;
          }
          if(o=='danwei'){
            this.ruleForm.danwei = obj[o];
	    this.ro.danwei = true;
            continue;
          }
          if(o=='category'){
            this.ruleForm.category = obj[o];
            this.ruleForm.categories = obj[o] ? obj[o].split(',') : [];
	    this.ro.category = true;
            continue;
          }
          if(o=='tupian'){
            this.ruleForm.tupian = obj[o];
	    this.ro.tupian = true;
            continue;
          }
          if(o=='shebeishuliang'){
            this.ruleForm.shebeishuliang = obj[o];
	    this.ro.shebeishuliang = true;
            continue;
          }
          if(o=='shiyongfangfa'){
            this.ruleForm.shiyongfangfa = obj[o];
	    this.ro.shiyongfangfa = true;
            continue;
          }
          if(o=='shebeizhuangtai'){
            this.ruleForm.shebeizhuangtai = obj[o];
	    this.ro.shebeizhuangtai = true;
            continue;
          }
        }
      }
      // 获取用户信息
      this.$http({
        url: `${this.$storage.get('sessionTable')}/session`,
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          var json = data.data;
        } else {
          this.$message.error(data.msg);
        }
      });
            this.shebeizhuangtaiOptions = "正常,维修,报废".split(',')
            this.getCategoryOptions()
    },
    getCategoryOptions() {
      this.$http({
        url: "shiyanshebei/categoryOptions",
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0 && Array.isArray(data.data)) {
          this.categoryOptions = this.appendReagentOption(data.data)
        } else {
          this.categoryOptions = this.appendReagentOption([
            "生物类",
            "化学类",
            "物理类",
            "计算机与电子信息类",
            "机械与工程类",
            "环境与检测类",
            "医学与药学类",
            "农林牧渔类",
            "地质与资源类"
          ])
        }
      }).catch(() => {
        this.categoryOptions = this.appendReagentOption([
          "生物类",
          "化学类",
          "物理类",
          "计算机与电子信息类",
          "机械与工程类",
          "环境与检测类",
          "医学与药学类",
          "农林牧渔类",
          "地质与资源类"
        ])
      })
    },
    appendReagentOption(options) {
      const nextOptions = Array.isArray(options) ? [...options] : []
      if (nextOptions.includes("试剂耗材")) {
        return nextOptions
      }
      const insertAfter = "医学与药学类"
      const insertIndex = nextOptions.indexOf(insertAfter)
      if (insertIndex !== -1) {
        nextOptions.splice(insertIndex + 1, 0, "试剂耗材")
      } else {
        nextOptions.push("试剂耗材")
      }
      return nextOptions
    },
    // 多级联动参数
    info(id) {
      this.$http({
        url: `shiyanshebei/info/${id}`,
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
        this.ruleForm = data.data;
        this.ruleForm.categories = this.ruleForm.category ? this.ruleForm.category.split(',') : [];
	//解决前台上传图片后台不显示的问题
	let reg=new RegExp('../../../upload','g')//g代表全部
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    reserveDevice() {
      const sessionTable = this.$storage.get('sessionTable');
      this.$http({
        url: `${sessionTable}/session`,
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          const user = data.data || {};
          const payload = {
            shiyanshebeiId: this.ruleForm.id,
            shebeibianhao: this.ruleForm.shebeibianhao,
            shebeimingcheng: this.ruleForm.shebeimingcheng,
            yuyuefangshi: '借离实验室',
            yuyueren: sessionTable === 'jiaoshi' ? (user.jiaoshixingming || user.gonghao || '') : (user.xueshengxingming || user.xuehao || ''),
            yuyueshijian: new Date().toISOString().slice(0, 19).replace('T', ' '),
            guihaishijian: '',
            yuyuezhuangtai: '借出中',
            xuehao: sessionTable === 'jiaoshi' ? (user.gonghao || '') : (user.xuehao || ''),
            xueshengxingming: sessionTable === 'jiaoshi' ? (user.jiaoshixingming || '') : (user.xueshengxingming || ''),
            banji: user.banji || '',
            shouji: user.shouji || user.dianhua || ''
          };
          this.$http({
            url: 'shiyanshebeiBorrow/save',
            method: 'post',
            data: payload
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message.success('预约成功');
            } else {
              this.$message.error(data.msg || '预约失败');
            }
          });
        }
      });
    },
    // 提交
    onSubmit() {
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}








      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          const payload = {
            ...this.ruleForm,
            categories: [...(this.ruleForm.categories || [])]
          };
          this.$http({
            url: `shiyanshebei/${!this.ruleForm.id ? "save" : "update"}`,
            method: "post",
            data: payload
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.parent.showFlag = true;
                  this.parent.addOrUpdateFlag = false;
                  this.parent.shiyanshebeiCrossAddOrUpdateFlag = false;
                  this.parent.search();
                  this.parent.contentStyleChange();
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    // 获取uuid
    getUUID () {
      return new Date().getTime();
    },
    // 返回
    back() {
      this.parent.showFlag = true;
      this.parent.addOrUpdateFlag = false;
      this.parent.shiyanshebeiCrossAddOrUpdateFlag = false;
      this.parent.contentStyleChange();
    },
    tupianUploadChange(fileUrls) {
	this.ruleForm.tupian = fileUrls;
			this.addEditUploadStyleChange()
    },
	addEditStyleChange() {
	  this.$nextTick(()=>{
	    // input
	    document.querySelectorAll('.addEdit-block .input .el-input__inner').forEach(el=>{
	      el.style.height = this.addEditForm.inputHeight
	      el.style.color = this.addEditForm.inputFontColor
	      el.style.fontSize = this.addEditForm.inputFontSize
	      el.style.borderWidth = this.addEditForm.inputBorderWidth
	      el.style.borderStyle = this.addEditForm.inputBorderStyle
	      el.style.borderColor = this.addEditForm.inputBorderColor
	      el.style.borderRadius = this.addEditForm.inputBorderRadius
	      el.style.backgroundColor = this.addEditForm.inputBgColor
	    })
	    document.querySelectorAll('.addEdit-block .input .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.inputHeight
	      el.style.color = this.addEditForm.inputLableColor
	      el.style.fontSize = this.addEditForm.inputLableFontSize
	    })
	    // select
	    document.querySelectorAll('.addEdit-block .select .el-input__inner').forEach(el=>{
	      el.style.height = this.addEditForm.selectHeight
	      el.style.color = this.addEditForm.selectFontColor
	      el.style.fontSize = this.addEditForm.selectFontSize
	      el.style.borderWidth = this.addEditForm.selectBorderWidth
	      el.style.borderStyle = this.addEditForm.selectBorderStyle
	      el.style.borderColor = this.addEditForm.selectBorderColor
	      el.style.borderRadius = this.addEditForm.selectBorderRadius
	      el.style.backgroundColor = this.addEditForm.selectBgColor
	    })
	    document.querySelectorAll('.addEdit-block .select .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.selectHeight
	      el.style.color = this.addEditForm.selectLableColor
	      el.style.fontSize = this.addEditForm.selectLableFontSize
	    })
	    document.querySelectorAll('.addEdit-block .select .el-select__caret').forEach(el=>{
	      el.style.color = this.addEditForm.selectIconFontColor
	      el.style.fontSize = this.addEditForm.selectIconFontSize
	    })
	    // date
	    document.querySelectorAll('.addEdit-block .date .el-input__inner').forEach(el=>{
	      el.style.height = this.addEditForm.dateHeight
	      el.style.color = this.addEditForm.dateFontColor
	      el.style.fontSize = this.addEditForm.dateFontSize
	      el.style.borderWidth = this.addEditForm.dateBorderWidth
	      el.style.borderStyle = this.addEditForm.dateBorderStyle
	      el.style.borderColor = this.addEditForm.dateBorderColor
	      el.style.borderRadius = this.addEditForm.dateBorderRadius
	      el.style.backgroundColor = this.addEditForm.dateBgColor
	    })
	    document.querySelectorAll('.addEdit-block .date .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.dateHeight
	      el.style.color = this.addEditForm.dateLableColor
	      el.style.fontSize = this.addEditForm.dateLableFontSize
	    })
	    document.querySelectorAll('.addEdit-block .date .el-input__icon').forEach(el=>{
	      el.style.color = this.addEditForm.dateIconFontColor
	      el.style.fontSize = this.addEditForm.dateIconFontSize
	      el.style.lineHeight = this.addEditForm.dateHeight
	    })
	    // upload
	    let iconLineHeight = parseInt(this.addEditForm.uploadHeight) - parseInt(this.addEditForm.uploadBorderWidth) * 2 + 'px'
	    document.querySelectorAll('.addEdit-block .upload .el-upload--picture-card').forEach(el=>{
	      el.style.width = this.addEditForm.uploadHeight
	      el.style.height = this.addEditForm.uploadHeight
	      el.style.borderWidth = this.addEditForm.uploadBorderWidth
	      el.style.borderStyle = this.addEditForm.uploadBorderStyle
	      el.style.borderColor = this.addEditForm.uploadBorderColor
	      el.style.borderRadius = this.addEditForm.uploadBorderRadius
	      el.style.backgroundColor = this.addEditForm.uploadBgColor
	    })
	    document.querySelectorAll('.addEdit-block .upload .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.uploadHeight
	      el.style.color = this.addEditForm.uploadLableColor
	      el.style.fontSize = this.addEditForm.uploadLableFontSize
	    })
	    document.querySelectorAll('.addEdit-block .upload .el-icon-plus').forEach(el=>{
	      el.style.color = this.addEditForm.uploadIconFontColor
	      el.style.fontSize = this.addEditForm.uploadIconFontSize
	      el.style.lineHeight = iconLineHeight
	      el.style.display = 'block'
	    })
	    // 多文本输入框
	    document.querySelectorAll('.addEdit-block .textarea .el-textarea__inner').forEach(el=>{
	      el.style.height = this.addEditForm.textareaHeight
	      el.style.color = this.addEditForm.textareaFontColor
	      el.style.fontSize = this.addEditForm.textareaFontSize
	      el.style.borderWidth = this.addEditForm.textareaBorderWidth
	      el.style.borderStyle = this.addEditForm.textareaBorderStyle
	      el.style.borderColor = this.addEditForm.textareaBorderColor
	      el.style.borderRadius = this.addEditForm.textareaBorderRadius
	      el.style.backgroundColor = this.addEditForm.textareaBgColor
	    })
	    document.querySelectorAll('.addEdit-block .textarea .el-form-item__label').forEach(el=>{
	      // el.style.lineHeight = this.addEditForm.textareaHeight
	      el.style.color = this.addEditForm.textareaLableColor
	      el.style.fontSize = this.addEditForm.textareaLableFontSize
	    })
	    // 保存
	    document.querySelectorAll('.addEdit-block .btn .btn-success').forEach(el=>{
	      el.style.width = this.addEditForm.btnSaveWidth
	      el.style.height = this.addEditForm.btnSaveHeight
	      el.style.color = this.addEditForm.btnSaveFontColor
	      el.style.fontSize = this.addEditForm.btnSaveFontSize
	      el.style.borderWidth = this.addEditForm.btnSaveBorderWidth
	      el.style.borderStyle = this.addEditForm.btnSaveBorderStyle
	      el.style.borderColor = this.addEditForm.btnSaveBorderColor
	      el.style.borderRadius = this.addEditForm.btnSaveBorderRadius
	      el.style.backgroundColor = this.addEditForm.btnSaveBgColor
	    })
	    // 返回
	    document.querySelectorAll('.addEdit-block .btn .btn-close').forEach(el=>{
	      el.style.width = this.addEditForm.btnCancelWidth
	      el.style.height = this.addEditForm.btnCancelHeight
	      el.style.color = this.addEditForm.btnCancelFontColor
	      el.style.fontSize = this.addEditForm.btnCancelFontSize
	      el.style.borderWidth = this.addEditForm.btnCancelBorderWidth
	      el.style.borderStyle = this.addEditForm.btnCancelBorderStyle
	      el.style.borderColor = this.addEditForm.btnCancelBorderColor
	      el.style.borderRadius = this.addEditForm.btnCancelBorderRadius
	      el.style.backgroundColor = this.addEditForm.btnCancelBgColor
	    })
	  })
	},
	addEditUploadStyleChange() {
		this.$nextTick(()=>{
		  document.querySelectorAll('.addEdit-block .upload .el-upload-list--picture-card .el-upload-list__item').forEach(el=>{
			el.style.width = this.addEditForm.uploadHeight
			el.style.height = this.addEditForm.uploadHeight
			el.style.borderWidth = this.addEditForm.uploadBorderWidth
			el.style.borderStyle = this.addEditForm.uploadBorderStyle
			el.style.borderColor = this.addEditForm.uploadBorderColor
			el.style.borderRadius = this.addEditForm.uploadBorderRadius
			el.style.backgroundColor = this.addEditForm.uploadBgColor
		  })
	  })
	},
  }
};
</script>
<style lang="scss">
.editor{
  height: 500px;
  
  & ::v-deep .ql-container {
	  height: 310px;
  }
}
.amap-wrapper {
  width: 100%;
  height: 500px;
}
.search-box {
  position: absolute;
}
.addEdit-block {
	margin: 0;
}
.detail-form-content {
	padding: 14px;
}
.btn .el-button {
  padding: 0;
}

/* Form spacing & responsive */
.addEdit-block .el-row {
	margin-left: 0 !important;
	margin-right: 0 !important;
}

.addEdit-block .el-col {
	margin-bottom: 8px;
}

.addEdit-block .el-checkbox-group {
	display: flex;
	flex-wrap: wrap;
	gap: 8px 12px;
}

.addEdit-block .el-textarea__inner {
	min-height: 160px;
}

@media (max-width: 768px) {
	.detail-form-content {
		padding: 12px;
	}
}
</style>
