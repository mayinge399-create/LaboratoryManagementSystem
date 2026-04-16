<template>
  <div class="main-content">
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row :gutter="20" class="slt">
          <el-form-item>
            <el-input v-model="searchForm.shebeimingcheng" placeholder="设备名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchForm.yuyueren" placeholder="借阅人" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="searchForm.yuyueshijianRange"
              type="daterange"
              range-separator="至"
              start-placeholder="预约开始日期"
              end-placeholder="预约结束日期"
              value-format="yyyy-MM-dd"
              clearable
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="success" icon="el-icon-search" @click="search()">查询</el-button>
            <el-button type="warning" icon="el-icon-download" @click="exportCurrentList">导出当前列表</el-button>
          </el-form-item>
        </el-row>
      </el-form>
      <el-table :data="dataList" v-loading="dataListLoading" border style="width:100%">
        <el-table-column prop="shebeibianhao" label="设备编号" />
        <el-table-column prop="shebeimingcheng" label="设备名称" />
        <el-table-column prop="yuyuefangshi" label="预约方式" />
        <el-table-column prop="yuyueren" label="预约人" />
        <el-table-column prop="yuyueshijian" label="预约时间" />
        <el-table-column prop="guihaishijian" label="归还时间" />
        <el-table-column prop="yuyuezhuangtai" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.yuyuezhuangtai === '已归还' ? 'success' : 'warning'">
              {{ scope.row.yuyuezhuangtai || '借出中' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <el-button v-if="scope.row.yuyuezhuangtai!=='已归还'" size="mini" type="primary" @click="returnDevice(scope.row.id)">归还</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchForm: { shebeimingcheng: '', yuyueren: '', yuyueshijianRange: [] },
      dataList: [],
      dataListLoading: false,
      showFlag: true
    }
  },
  created() { this.getDataList() },
  methods: {
    search() { this.getDataList() },
    getDataList() {
      this.dataListLoading = true
      const params = {}
      if (this.searchForm.shebeimingcheng) params.shebeimingcheng = `%${this.searchForm.shebeimingcheng}%`
      if (this.searchForm.yuyueren) params.yuyueren = `%${this.searchForm.yuyueren}%`
      if (this.searchForm.yuyueshijianRange && this.searchForm.yuyueshijianRange.length === 2) {
        params.yuyueshijianstart = this.searchForm.yuyueshijianRange[0] + ' 00:00:00'
        params.yuyueshijianend = this.searchForm.yuyueshijianRange[1] + ' 23:59:59'
      }
      this.$http({ url: 'shiyanshebeiBorrow/list', method: 'get', params }).then(({ data }) => {
        this.dataList = data && data.data && data.data.list ? data.data.list : []
        this.dataListLoading = false
      }).catch(() => { this.dataListLoading = false })
    },
    returnDevice(id) {
      this.$http({ url: `shiyanshebeiBorrow/returnDevice/${id}`, method: 'post' }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message.success('归还成功')
          this.getDataList()
        } else {
          this.$message.error(data.msg || '归还失败')
        }
      })
    },
    exportCurrentList() {
      const header = ['设备编号', '设备名称', '预约方式', '预约人', '预约时间', '归还时间', '状态']
      const rows = this.dataList.map(item => [
        item.shebeibianhao || '',
        item.shebeimingcheng || '',
        item.yuyuefangshi || '',
        item.yuyueren || '',
        item.yuyueshijian || '',
        item.guihaishijian || '',
        item.yuyuezhuangtai || ''
      ])
      const csv = [header, ...rows].map(row => row.map(val => `"${String(val).replace(/"/g, '""')}"`).join(',')).join('\n')
      const blob = new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8;' })
      const link = document.createElement('a')
      link.href = URL.createObjectURL(blob)
      link.download = `设备借阅列表_${new Date().getTime()}.csv`
      link.click()
      URL.revokeObjectURL(link.href)
    }
  }
}
</script>

<style lang="scss" scoped>
.slt {
  margin: 0 !important;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px 12px;
}
</style>
