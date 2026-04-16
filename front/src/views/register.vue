<template>
  <div>
    <div class="container">
      <div class="login-form">
        <h1 class="h1">实验室管理系统注册</h1>
		<el-form ref="rgsForm" class="rgs-form" :model="rgsForm" label-width="120px">
			<!-- <div v-if="tableName=='xuesheng'" class="input-group">
			   <div class="label">学号</div>
			   <div class="input-container">
			     <input v-model="ruleForm.xuehao" class="input" type="text" placeholder="学号">
			   </div>
			 </div> -->
			<el-form-item label="学号" class="input" v-if="tableName=='xuesheng'">
			  <el-input v-model="ruleForm.xuehao" autocomplete="off" placeholder="学号" maxlength="6" />
			</el-form-item>
			<!-- <div v-if="tableName=='xuesheng'" class="input-group">
			   <div class="label">密码</div>
			   <div class="input-container">
			     <input v-model="ruleForm.mima" class="input" type="text" placeholder="密码">
			   </div>
			 </div> -->
			<el-form-item label="密码" class="input" v-if="tableName=='xuesheng'">
			  <el-input v-model="ruleForm.mima" autocomplete="off" placeholder="密码" type="password"#elsetype="text" />
			</el-form-item>
			<!-- <div v-if="tableName=='xuesheng'" class="input-group">
			   <div class="label">学生姓名</div>
			   <div class="input-container">
			     <input v-model="ruleForm.xueshengxingming" class="input" type="text" placeholder="学生姓名">
			   </div>
			 </div> -->
			<el-form-item label="学生姓名" class="input" v-if="tableName=='xuesheng'">
			  <el-input v-model="ruleForm.xueshengxingming" autocomplete="off" placeholder="学生姓名"  />
			</el-form-item>
			<!-- <div v-if="tableName=='xuesheng'" class="input-group">
			   <div class="label">班级</div>
			   <div class="input-container">
			     <input v-model="ruleForm.banji" class="input" type="text" placeholder="班级">
			   </div>
			 </div> -->
			<el-form-item label="班级" class="input" v-if="tableName=='xuesheng'">
			  <el-input v-model="ruleForm.banji" autocomplete="off" placeholder="班级"  />
			</el-form-item>
			<!-- <div v-if="tableName=='xuesheng'" class="input-group">
			   <div class="label">手机</div>
			   <div class="input-container">
			     <input v-model="ruleForm.shouji" class="input" type="text" placeholder="手机">
			   </div>
			 </div> -->
			<el-form-item label="手机" class="input" v-if="tableName=='xuesheng'">
			  <el-input v-model="ruleForm.shouji" autocomplete="off" placeholder="手机"  />
			</el-form-item>
			<!-- <div v-if="tableName=='jiaoshi'" class="input-group">
			   <div class="label">工号</div>
			   <div class="input-container">
			     <input v-model="ruleForm.gonghao" class="input" type="text" placeholder="工号">
			   </div>
			 </div> -->
			<el-form-item label="工号" class="input" v-if="tableName=='jiaoshi'">
			  <el-input v-model="ruleForm.gonghao" autocomplete="off" placeholder="工号" maxlength="6" />
			</el-form-item>
			<!-- <div v-if="tableName=='jiaoshi'" class="input-group">
			   <div class="label">密码</div>
			   <div class="input-container">
			     <input v-model="ruleForm.mima" class="input" type="text" placeholder="密码">
			   </div>
			 </div> -->
			<el-form-item label="密码" class="input" v-if="tableName=='jiaoshi'">
			  <el-input v-model="ruleForm.mima" autocomplete="off" placeholder="密码" type="password"#elsetype="text" />
			</el-form-item>
			<!-- <div v-if="tableName=='jiaoshi'" class="input-group">
			   <div class="label">教师姓名</div>
			   <div class="input-container">
			     <input v-model="ruleForm.jiaoshixingming" class="input" type="text" placeholder="教师姓名">
			   </div>
			 </div> -->
			<el-form-item label="教师姓名" class="input" v-if="tableName=='jiaoshi'">
			  <el-input v-model="ruleForm.jiaoshixingming" autocomplete="off" placeholder="教师姓名"  />
			</el-form-item>
			<!-- <div v-if="tableName=='jiaoshi'" class="input-group">
			   <div class="label">学院</div>
			   <div class="input-container">
			     <input v-model="ruleForm.xueyuan" class="input" type="text" placeholder="学院">
			   </div>
			 </div> -->
			<el-form-item label="学院" class="input" v-if="tableName=='jiaoshi'">
			  <el-input v-model="ruleForm.xueyuan" autocomplete="off" placeholder="学院"  />
			</el-form-item>
			<!-- <div v-if="tableName=='jiaoshi'" class="input-group">
			   <div class="label">职称</div>
			   <div class="input-container">
			     <input v-model="ruleForm.zhicheng" class="input" type="text" placeholder="职称">
			   </div>
			 </div> -->
			<el-form-item label="职称" class="input" v-if="tableName=='jiaoshi'">
			  <el-input v-model="ruleForm.zhicheng" autocomplete="off" placeholder="职称"  />
			</el-form-item>
			<!-- <div v-if="tableName=='jiaoshi'" class="input-group">
			   <div class="label">电话</div>
			   <div class="input-container">
			     <input v-model="ruleForm.dianhua" class="input" type="text" placeholder="电话">
			   </div>
			 </div> -->
			<el-form-item label="电话" class="input" v-if="tableName=='jiaoshi'">
			  <el-input v-model="ruleForm.dianhua" autocomplete="off" placeholder="电话"  />
			</el-form-item>
			<div class="form-actions">
				<el-button class="btn" type="primary" @click="login()">注册</el-button>
				<el-button class="btn close" type="primary" @click="close()">取消</el-button>
			</div>
		</el-form>
      </div>
      <!-- <div class="nk-navigation">
        <a href="#">
          <div @click="login()">注册</div>
        </a>
      </div> -->
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
      },
      tableName:"",
      rules: {}
    };
  },
  mounted(){
    let table = this.$storage.get("loginTable");
    if (table !== "xuesheng" && table !== "jiaoshi") {
      this.$message.error("仅支持学生和教师注册");
      this.$router.replace({ path: "/login" });
      return;
    }
    this.tableName = table;
  },
  methods: {
    // 获取uuid
    getUUID () {
      return new Date().getTime();
    },
    close(){
	this.$router.push({ path: "/login" });
    },
    // 注册
    login() {
      if((!this.ruleForm.xuehao) && `xuesheng` == this.tableName){
        this.$message.error(`学号不能为空`);
        return
      }
      if(`xuesheng` == this.tableName && this.ruleForm.xuehao && this.ruleForm.xuehao.trim().length !== 6){
        this.$message.error(`学号长度必须为6位`);
        return
      }
      if((!this.ruleForm.mima) && `xuesheng` == this.tableName){
        this.$message.error(`密码不能为空`);
        return
      }
      if((!this.ruleForm.xueshengxingming) && `xuesheng` == this.tableName){
        this.$message.error(`学生姓名不能为空`);
        return
      }
      if(`xuesheng` == this.tableName && this.ruleForm.shouji&&(!this.$validate.isMobile(this.ruleForm.shouji))){
        this.$message.error(`手机应输入手机格式`);
        return
      }
      if((!this.ruleForm.gonghao) && `jiaoshi` == this.tableName){
        this.$message.error(`工号不能为空`);
        return
      }
      if(`jiaoshi` == this.tableName && this.ruleForm.gonghao && this.ruleForm.gonghao.trim().length !== 6){
        this.$message.error(`工号长度必须为6位`);
        return
      }
      if((!this.ruleForm.mima) && `jiaoshi` == this.tableName){
        this.$message.error(`密码不能为空`);
        return
      }
      if(`jiaoshi` == this.tableName && this.ruleForm.dianhua&&(!this.$validate.isMobile(this.ruleForm.dianhua))){
        this.$message.error(`电话应输入手机格式`);
        return
      }
      this.$http({
        url: `${this.tableName}/register`,
        method: "post",
        data:this.ruleForm
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "注册成功",
            type: "success",
            duration: 1500,
            onClose: () => {
              this.$router.replace({ path: "/login" });
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
  .container {
    min-height: 100vh;
    background-image: url("http://codegen.caihongy.cn/20201126/ff6024f5009f4e9497b897831b3b157d.jpg");
    background-position: center center;
    background-size: cover;
    background-repeat: no-repeat;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 18px;
  }

  .container::before {
    content: "";
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, rgba(19, 42, 72, 0.38), rgba(27, 59, 98, 0.22));
  }

  .container .login-form {
    position: relative;
    z-index: 1;
    height: auto;
    border-radius: 16px;
    background-color: rgba(247, 252, 255, 0.86);
    border: 1px solid rgba(212, 229, 246, 0.92);
    width: 460px;
    max-width: 94vw;
    padding: 26px 24px 30px;
    font-size: 14px;
    font-weight: 500;
    box-shadow: 0 16px 34px rgba(23, 49, 85, 0.24);
    backdrop-filter: blur(8px);
  }

  .container .login-form .h1 {
    margin: 0 0 14px;
    text-align: center;
    line-height: 1.4;
    font-size: 26px;
    color: #1f3f62;
  }

  .container .login-form .rgs-form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .container .login-form .rgs-form .input {
    width: 100%;
    margin-bottom: 2px;
  }

  .container .login-form .rgs-form .input ::v-deep .el-form-item__label {
    line-height: 40px;
    color: #4f6783;
    font-size: 14px;
    font-weight: 500;
  }

  .container .login-form .rgs-form .input ::v-deep .el-input__inner {
    height: 42px;
    color: #182b40;
    font-size: 14px;
    border: 1px solid #cfe0f3;
    border-radius: 10px;
    background-color: rgba(255, 255, 255, 0.95);
  }

  .form-actions {
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    justify-content: center;
    margin-top: 8px;
    gap: 10px;
  }

  .btn {
    width: 104px;
    height: 42px;
    color: #f4f8ff;
    font-size: 14px;
    border: none;
    border-radius: 10px;
    background: linear-gradient(135deg, #2b6cb0, #3b82c4);
  }

  .close {
    color: #2b6cb0;
    border: 1px solid #9dbce0;
    background: #f6fbff;
  }

  @media (max-width: 768px) {
    .container .login-form {
      width: 92vw;
      max-width: 390px;
      padding: 22px 14px 26px;
    }

    .container .login-form .h1 {
      font-size: 22px;
    }

    .btn {
      width: calc(50% - 6px);
      max-width: 140px;
    }
  }
</style>
