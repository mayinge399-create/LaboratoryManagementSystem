<template>
  <div>
    <div v-if="flag=='xuesheng' || flag=='jiaoshi'" class="stats-card">
      <div class="stats-title">我的预约统计</div>
      <el-row :gutter="12">
        <el-col :span="6" :xs="12"><div class="stat-item"><div>总预约</div><strong>{{ reservationStats.total }}</strong></div></el-col>
        <el-col :span="6" :xs="12"><div class="stat-item"><div>待审核</div><strong>{{ reservationStats.pending }}</strong></div></el-col>
        <el-col :span="6" :xs="12"><div class="stat-item"><div>已通过</div><strong>{{ reservationStats.approved }}</strong></div></el-col>
        <el-col :span="6" :xs="12"><div class="stat-item"><div>未通过</div><strong>{{ reservationStats.rejected }}</strong></div></el-col>
      </el-row>
    </div>
    <el-form
      class="detail-form-content"
      ref="ruleForm"
      :model="ruleForm"
      label-width="80px"
    >  
     <el-row>
      <el-col :span="12">
        <el-form-item   v-if="flag=='xuesheng'"  label="学号" prop="xuehao">
          <el-input v-model="ruleForm.xuehao" readonly              placeholder="学号" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='xuesheng'"  label="学生姓名" prop="xueshengxingming">
          <el-input v-model="ruleForm.xueshengxingming"               placeholder="学生姓名" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item v-if="flag=='xuesheng'"  label="性别" prop="xingbie">
          <el-select v-model="ruleForm.xingbie" placeholder="请选择性别">
            <el-option
                v-for="(item,index) in xueshengxingbieOptions"
                v-bind:key="index"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="24">  
        <el-form-item v-if="flag=='xuesheng'" label="头像" prop="touxiang">
          <file-upload
          tip="点击上传头像"
          action="file/upload"
          :limit="3"
          :multiple="true"
          :fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
          @change="xueshengtouxiangUploadChange"
          ></file-upload>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='xuesheng'"  label="班级" prop="banji">
          <el-input v-model="ruleForm.banji"               placeholder="班级" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='xuesheng'"  label="手机" prop="shouji">
          <el-input v-model="ruleForm.shouji"               placeholder="手机" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='jiaoshi'"  label="工号" prop="gonghao">
          <el-input v-model="ruleForm.gonghao" readonly              placeholder="工号" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='jiaoshi'"  label="教师姓名" prop="jiaoshixingming">
          <el-input v-model="ruleForm.jiaoshixingming"               placeholder="教师姓名" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item v-if="flag=='jiaoshi'"  label="性别" prop="xingbie">
          <el-select v-model="ruleForm.xingbie" placeholder="请选择性别">
            <el-option
                v-for="(item,index) in jiaoshixingbieOptions"
                v-bind:key="index"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="24">  
        <el-form-item v-if="flag=='jiaoshi'" label="头像" prop="touxiang">
          <file-upload
          tip="点击上传头像"
          action="file/upload"
          :limit="3"
          :multiple="true"
          :fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
          @change="jiaoshitouxiangUploadChange"
          ></file-upload>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='jiaoshi'"  label="学院" prop="xueyuan">
          <el-input v-model="ruleForm.xueyuan"               placeholder="学院" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='jiaoshi'"  label="职称" prop="zhicheng">
          <el-input v-model="ruleForm.zhicheng"               placeholder="职称" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item   v-if="flag=='jiaoshi'"  label="电话" prop="dianhua">
          <el-input v-model="ruleForm.dianhua"               placeholder="电话" clearable></el-input>
        </el-form-item>
      </el-col>
      <el-form-item v-if="flag=='users'" label="用户名" prop="username">
        <el-input v-model="ruleForm.username" 
        placeholder="用户名"></el-input>
      </el-form-item>
      <el-col :span="24">
      <el-form-item class="center-actions">
        <el-button type="primary" class="center-primary-btn" @click="onUpdateHandler">修 改</el-button>
        <el-button v-if="flag=='xuesheng' || flag=='jiaoshi'" type="success" class="center-secondary-btn" @click="goBorrowList">我的设备借阅</el-button>
      </el-form-item>
      </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
// 数字，邮件，手机，url，身份证校验
import { isNumber,isIntNumer,isEmail,isMobile,isPhone,isURL,checkIdCard } from "@/utils/validate";

export default {
  data() {
    return {
      ruleForm: {},
      flag: '',
      usersFlag: false,
      xueshengxingbieOptions: [],
      jiaoshixingbieOptions: [],
      reservationStats: {
        total: 0,
        pending: 0,
        approved: 0,
        rejected: 0
      }
    };
  },
  mounted() {
    var table = this.$storage.get("sessionTable");
    this.flag = table;
    this.$http({
      url: `${this.$storage.get("sessionTable")}/session`,
      method: "get"
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.ruleForm = data.data;
        this.loadReservationStats();
      } else {
        this.$message.error(data.msg);
      }
    });
    this.xueshengxingbieOptions = "男,女".split(',')
    this.jiaoshixingbieOptions = "男,女".split(',')
  },
  methods: {
    xueshengtouxiangUploadChange(fileUrls) {
        this.ruleForm.touxiang = fileUrls;
    },
    jiaoshitouxiangUploadChange(fileUrls) {
        this.ruleForm.touxiang = fileUrls;
    },
    goBorrowList() {
      this.$router.push('/shiyanshebeiBorrow')
    },
    loadReservationStats() {
      if (!(this.flag === 'xuesheng' || this.flag === 'jiaoshi')) return;
      this.$http({
        url: "shiyanshiyuyue/page",
        method: "get",
        params: { page: 1, limit: 1000, sort: 'id' }
      }).then(({ data }) => {
        const list = data && data.code === 0 && data.data && data.data.list ? data.data.list : [];
        this.reservationStats.total = list.length;
        this.reservationStats.pending = list.filter(item => item.sfsh === '待审核').length;
        this.reservationStats.approved = list.filter(item => item.sfsh === '是').length;
        this.reservationStats.rejected = list.filter(item => item.sfsh === '否').length;
      });
    },
    onUpdateHandler() {
      if((!this.ruleForm.xuehao)&& 'xuesheng'==this.flag){
        this.$message.error('学号不能为空');
        return
      }
      if((!this.ruleForm.mima)&& 'xuesheng'==this.flag){
        this.$message.error('密码不能为空');
        return
      }
      if((!this.ruleForm.xueshengxingming)&& 'xuesheng'==this.flag){
        this.$message.error('学生姓名不能为空');
        return
      }
      if( 'xuesheng' ==this.flag && this.ruleForm.shouji&&(!isMobile(this.ruleForm.shouji))){
        this.$message.error(`手机应输入手机格式`);
        return
      }
      if((!this.ruleForm.gonghao)&& 'jiaoshi'==this.flag){
        this.$message.error('工号不能为空');
        return
      }
      if((!this.ruleForm.mima)&& 'jiaoshi'==this.flag){
        this.$message.error('密码不能为空');
        return
      }
      if( 'jiaoshi' ==this.flag && this.ruleForm.dianhua&&(!isMobile(this.ruleForm.dianhua))){
        this.$message.error(`电话应输入手机格式`);
        return
      }
      if('users'==this.flag && this.ruleForm.username.trim().length<1) {
	this.$message.error(`用户名不能为空`);
        return	
      }
      this.$http({
        url: `${this.$storage.get("sessionTable")}/update`,
        method: "post",
        data: this.ruleForm
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "修改信息成功",
            type: "success",
            duration: 1500,
            onClose: () => {
            }
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.stats-card {
  margin-bottom: 14px;
  background: linear-gradient(120deg, #eef5ff, #f6faff);
  border: 1px solid #d3e3f5;
  border-radius: 14px;
  padding: 14px;
  box-shadow: 0 10px 24px rgba(30, 64, 124, 0.08);
}
.stats-title {
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: 600;
  color: #26486b;
}
.stat-item {
  background: rgba(255, 255, 255, 0.9);
  border: 1px solid #dde9f8;
  border-radius: 12px;
  padding: 12px 10px;
  text-align: center;
  color: #5b7088;
  min-height: 84px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.stat-item strong {
  display: block;
  margin-top: 6px;
  font-size: 24px;
  color: #244a71;
}

.detail-form-content {
  border-radius: 12px;
}

.detail-form-content ::v-deep .el-input__inner,
.detail-form-content ::v-deep .el-select .el-input__inner {
  height: 42px;
  line-height: 42px;
  border-radius: 10px;
}

.center-actions {
  margin-top: 8px;
}

.center-primary-btn,
.center-secondary-btn {
  min-width: 120px;
}

@media (max-width: 768px) {
  .stats-card {
    padding: 10px;
    margin-bottom: 10px;
  }

  .stat-item {
    margin-bottom: 10px;
  }

  .stat-item strong {
    font-size: 20px;
  }

  .center-actions ::v-deep .el-button {
    width: 100%;
    margin: 0 0 10px 0 !important;
  }
}
</style>
