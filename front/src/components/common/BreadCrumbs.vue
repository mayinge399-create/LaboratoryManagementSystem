<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb" class="box">
      <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
        <span v-if="item.redirect==='noRedirect'||index==levelList.length-1" class="no-redirect">{{ item.name }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ item.name }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import pathToRegexp from 'path-to-regexp'
import { generateTitle } from '@/utils/i18n'
export default {
  data() {
    return {
      levelList: null
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
    this.breadcrumbStyleChange()
  },
  methods: {
    generateTitle,
    getBreadcrumb() {
      // only show routes with meta.title
      let route = this.$route
      let matched = route.matched.filter(item => item.meta)
      const first = matched[0]
      matched = [{ path: '/index' }].concat(matched)

      this.levelList = matched.filter(item => item.meta)
    },
    isDashboard(route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim().toLocaleLowerCase() === 'Index'.toLocaleLowerCase()
    },
    pathCompile(path) {
      // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route
      var toPath = pathToRegexp.compile(path)
      return toPath(params)
    },
    handleLink(item) {
      const { redirect, path } = item
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      this.$router.push(path)
    },
    breadcrumbStyleChange(val) {
      this.$nextTick(()=>{
        document.querySelectorAll('.app-breadcrumb .el-breadcrumb__separator').forEach(el=>{
          el.innerText = "/"
          el.style.color = "rgba(122, 145, 173, 0.9)"
        })
        document.querySelectorAll('.app-breadcrumb .el-breadcrumb__inner a').forEach(el=>{
          el.style.color = "#4d6786"
        })
        document.querySelectorAll('.app-breadcrumb .el-breadcrumb__inner .no-redirect').forEach(el=>{
          el.style.color = "#2f4d6e"
        })

        let str = "vertical"
        if("vertical" === str) {
          let headHeight = "60px"
          headHeight = parseInt(headHeight) + 10 + 'px'
          document.querySelectorAll('.app-breadcrumb').forEach(el=>{
            el.style.marginTop = headHeight
          })
        }

      })
    },
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb {
  display: block;
  font-size: 14px;
  line-height: 50px;
  height: 50px;
  padding: 0 16px;
  border-radius: 16px;
  border: 1px solid #d5e2f1;
  background: linear-gradient(90deg, rgba(241, 247, 255, 0.94), rgba(234, 242, 252, 0.9));
  box-shadow: 0 8px 20px rgba(42, 56, 76, 0.1);
  backdrop-filter: blur(6px);
  box-sizing: border-box;

  .box {
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
    gap: 4px;
  }

  .no-redirect {
    color: #2f4d6e;
    cursor: text;
    font-weight: 600;
  }

  & ::v-deep .el-breadcrumb__inner a {
    transition: opacity 160ms ease;
  }

  & ::v-deep .el-breadcrumb__inner a:hover {
    opacity: 0.88;
  }
}

@media (max-width: 768px) {
  .app-breadcrumb {
    padding: 0 12px;
    border-radius: 14px;
    height: 46px;
    line-height: 46px;
  }
}
</style>
