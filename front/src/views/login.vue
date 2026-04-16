<template>
  <div>
    <div
      class="loginIn"
      :style="{ backgroundImage: 'url(' + loginBg + ')' }"
    >
      <div class="login-card">
        <el-form class="login-form-box" label-position="left" :label-width="1 == 3 ? '56px' : '0px'">
          <div class="title-container"><h3 class="title">实验室管理系统登录</h3></div>
          <el-form-item :label="1 == 3 ? '用户名' : ''" :class="'style'+1">
            <span v-if="1 != 3" class="svg-container"><svg-icon icon-class="user" /></span>
            <el-input placeholder="请输入用户名" name="username" type="text" v-model="rulesForm.username" />
          </el-form-item>
          <el-form-item :label="1 == 3 ? '密码' : ''" :class="'style'+1">
            <span v-if="1 != 3" class="svg-container"><svg-icon icon-class="password" /></span>
            <el-input placeholder="请输入密码" name="password" type="password" v-model="rulesForm.password" />
          </el-form-item>
          <el-form-item v-if="0 == '1'" class="code" :label="1 == 3 ? '验证码' : ''" :class="'style'+1">
            <span v-if="1 != 3" class="svg-container"><svg-icon icon-class="code" /></span>
            <el-input placeholder="请输入验证码" name="code" type="text" v-model="rulesForm.code" />
            <div class="getCodeBt" @click="getRandCode(4)">
              <span v-for="(item, index) in codes" :key="index" :style="{color:item.color,transform:item.rotate,fontSize:item.size}">{{ item.num }}</span>
            </div>
          </el-form-item>
          <el-form-item label="角色" prop="loginInRole" class="role">
            <el-radio
              v-for="item in menus"
              v-if="item.hasBackLogin=='是'"
              v-bind:key="item.roleName"
              v-model="rulesForm.role"
              :label="item.roleName"
            >{{item.roleName}}</el-radio>
          </el-form-item>
          <el-button type="primary" @click="login()" class="loginInBt">{{'1' == '1' ? '登录' : 'login'}}</el-button>
          <div class="register-entry">
            <span class="register-tip">没有账号？立即注册：</span>
            <el-button type="text" class="register-link" @click="goRegister('xuesheng')">学生</el-button>
            <el-button type="text" class="register-link" @click="goRegister('jiaoshi')">教师</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import menu from "@/utils/menu";
export default {
  data() {
    return {
      loginBg: require("@/pic/background.jpg"),
      rulesForm: { username: "", password: "", role: "", code: '' },
      menus: [],
      tableName: "",
      codes: [{ num: 1, color: '#000', rotate: '10deg', size: '16px' },{ num: 2, color: '#000', rotate: '10deg', size: '16px' },{ num: 3, color: '#000', rotate: '10deg', size: '16px' },{ num: 4, color: '#000', rotate: '10deg', size: '16px' }],
    };
  },
  mounted() { this.menus = menu.list(); },
  created() { this.getRandCode(); },
  methods: {
    login() {
      let code = ''
      for(let i in this.codes) code += this.codes[i].num
      if (!this.rulesForm.username) return this.$message.error("请输入用户名")
      if (!this.rulesForm.password) return this.$message.error("请输入密码")
      if (!this.rulesForm.role) return this.$message.error("请选择角色")
      let menus = this.menus
      for (let i = 0; i < menus.length; i++) if (menus[i].roleName == this.rulesForm.role) this.tableName = menus[i].tableName
      this.$http({ url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`, method: "post" }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$storage.set("Token", data.token)
          this.$storage.set("role", this.rulesForm.role)
          this.$storage.set("sessionTable", this.tableName)
          this.$storage.set("adminName", this.rulesForm.username)
          if (this.tableName === "xuesheng" || this.tableName === "jiaoshi") {
            window.location.href = this.$base.indexUrl
            return
          }
          this.$router.replace({ path: "/index" })
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    goRegister(tableName) {
      this.$storage.set("loginTable", tableName);
      this.$router.push({ path: "/register" });
    },
    getRandCode(len = 4){ this.randomString(len) },
    randomString(len = 4) { let chars = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"]; let colors = ["0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"]; let sizes = ['14','15','16','17','18']; for (let i = 0; i < len; i++) { let key = Math.floor(Math.random()*chars.length); this.codes[i].num = chars[key]; let code = '#'; for (let j = 0; j < 6; j++) { let key = Math.floor(Math.random()*colors.length); code += colors[key]; } this.codes[i].color = code; let rotate = Math.floor(Math.random()*60); let plus = Math.floor(Math.random()*2); if(plus == 1) rotate = '-'+rotate; this.codes[i].rotate = 'rotate('+rotate+'deg)'; let size = Math.floor(Math.random()*sizes.length); this.codes[i].size = sizes[size]+'px'; } },
  }
};
</script>
<style lang="scss" scoped>
.loginIn {
  min-height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  margin: 0;
  background-position: center center;
  background-size: cover;
  background-repeat: no-repeat;
  position: relative;
}

.loginIn::before {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, rgba(22, 43, 73, 0.38), rgba(34, 69, 111, 0.22));
}

.loginIn .login-card {
  width: 380px !important;
  max-width: 92vw !important;
  min-width: 0 !important;
  margin: 0 auto !important;
  box-sizing: border-box;
  padding: 28px 24px 22px;
  border-radius: 18px;
  background-color: rgba(246, 251, 255, 0.82);
  border: 1px solid rgba(213, 230, 248, 0.92);
  box-shadow: 0 16px 34px rgba(22, 49, 86, 0.24);
  backdrop-filter: blur(8px);
  position: relative;
  z-index: 1;
}

.loginIn .login-form-box {
  width: 100%;
  position: static;
  height: auto;
}

.register-entry {
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 2px;
}

.register-tip {
  color: rgba(20, 20, 20, 0.88);
  font-size: 13px;
}

.register-link {
  padding: 0 4px;
  font-size: 13px;
}

.loginIn .title {
  color: #244a71;
  letter-spacing: 0.2px;
  margin-bottom: 20px;
  text-align: center;
}

.loginIn .svg-container {
  color: rgba(16, 15, 15, 0.97);
  line-height: 44px;
}

.loginIn .getCodeBt {
  height: 44px;
  line-height: 44px;
}

.loginIn .loginInBt {
  width: 100%;
  height: 44px;
  padding: 0;
  line-height: 44px;
  border-radius: 12px;
  font-size: 16px;
  color: #f4f8ff;
  background: linear-gradient(135deg, #2b6cb0, #3b82c4);
  border-color: transparent;
}

.loginIn ::v-deep .el-input__inner {
  height: 44px;
  line-height: 44px;
  color: rgba(51, 51, 51, 1);
  border-radius: 12px;
  background-color: rgba(255, 255, 255, 0.96);
  border-color: #d3e3f5;
}

.loginIn ::v-deep .el-form-item__label {
  color: rgba(16, 15, 15, 0.97);
}

.loginIn ::v-deep .role .el-radio__label {
  color: rgba(6, 5, 5, 0.97);
}

@media (max-width: 768px) {
  .loginIn .login-card {
    width: 92vw !important;
    max-width: 360px !important;
    padding: 22px 16px;
  }
}
</style>
