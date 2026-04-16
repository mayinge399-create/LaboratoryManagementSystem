<template>
  <div class="index-page">
    <el-container class="index-shell">
      <index-header></index-header>
      <el-container class="index-body">
        <index-aside></index-aside>
        <index-main></index-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import IndexHeader from '@/components/index/IndexHeader'
import IndexAside from '@/components/index/IndexAsideStatic'
import IndexMain from '@/components/index/IndexMain'

export default {
  components: {
    IndexHeader,
    IndexAside,
    IndexMain
  }
}
</script>

<style lang="scss" scoped>
.index-page {
  min-height: 100vh;
  height: 100vh;
  width: 100%;
  display: flex;
  background: linear-gradient(180deg, #f4f8ff 0%, #edf3fc 100%);
}

.index-shell {
  width: 100%;
  min-height: 100vh;
  overflow: hidden;
}

.index-body {
  flex: 1 1 auto;
  min-height: 0; // allow children to scroll correctly
}
</style>
