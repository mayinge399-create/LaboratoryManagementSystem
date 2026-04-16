<template>
  <div class="portal-home">
    <template v-if="isAdmin">
      <div class="content">
        <div class="headline">欢迎使用 {{ this.$project.projectName }}</div>
        <div class="stat-grid">
          <div class="stat-card">
            <div class="label">设备总数</div>
            <div class="value">{{ stats.total }}</div>
          </div>
          <div class="stat-card" v-for="item in categoryCards" :key="item.key">
            <div class="label">{{ item.label }}</div>
            <div class="value">{{ item.value }}</div>
          </div>
          <div class="stat-card" v-for="item in statusCards" :key="item.key">
            <div class="label">{{ item.label }}</div>
            <div class="value">{{ item.value }}</div>
          </div>
        </div>
        <div class="trend-grid">
          <div class="trend-card">
            <div class="label">近7天新增预约</div>
            <div class="value">{{ recentStats.reserve7d }}</div>
          </div>
          <div class="trend-card">
            <div class="label">近7天新增借阅</div>
            <div class="value">{{ recentStats.borrow7d }}</div>
          </div>
        </div>
        <div class="toolbar">
          <el-date-picker
            v-model="adminDateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="设备录入开始"
            end-placeholder="设备录入结束"
            value-format="yyyy-MM-dd"
            clearable
            @change="getDashboardStats"
          />
          <el-button type="warning" icon="el-icon-download" @click="exportRecentDevices">导出最近设备</el-button>
        </div>

        <div class="recent-card">
          <div class="recent-title">最近新增设备（5条）</div>
          <el-table :data="stats.recentList" border size="small" style="width: 100%">
            <el-table-column prop="shebeimingcheng" label="设备名称" />
            <el-table-column prop="category" label="分类" width="140" />
            <el-table-column prop="shebeizhuangtai" label="状态" width="140" />
          </el-table>
        </div>
      </div>
    </template>

    <template v-else>
      <div class="portal-title">实验室管理系统</div>
      <el-row :gutter="16">
        <el-col :span="24" :md="12">
          <div class="panel-card">
            <div class="panel-header">
              <span>实验室信息列表</span>
              <el-button type="text" @click="$router.push('/shiyanshixinxi')">查看更多</el-button>
            </div>
            <el-table :data="labList" border size="small" v-loading="labLoading" style="width:100%">
              <el-table-column prop="shiyanshimingcheng" label="实验室名称" />
              <el-table-column prop="shiyanshiguimo" label="规模" width="100" />
              <el-table-column prop="shiyanshizhuangtai" label="状态" width="100" />
              <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                  <el-button type="success" size="mini" @click="$router.push('/shiyanshixinxi')">预约</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        <el-col :span="24" :md="12">
          <div class="panel-card">
            <div class="panel-header">
              <span>实验设备列表</span>
              <el-button type="text" @click="$router.push('/shiyanshebei')">查看更多</el-button>
            </div>
            <el-table :data="deviceList" border size="small" v-loading="deviceLoading" style="width:100%">
              <el-table-column prop="shebeimingcheng" label="设备名称" />
              <el-table-column prop="category" label="分类" width="100" />
              <el-table-column prop="shebeizhuangtai" label="状态" width="100" />
              <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                  <el-button type="success" size="mini" @click="$router.push('/shiyanshebei')">预约</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </template>
  </div>
</template>
<script>
import router from '@/router/router-static'
export default {
  data() {
    return {
      stats: { total: 0, categoryStats: {}, statusStats: {}, recentList: [] },
      labList: [],
      deviceList: [],
      labLoading: false,
      deviceLoading: false,
      isAdmin: false,
      adminDateRange: [],
      recentStats: {
        reserve7d: 0,
        borrow7d: 0
      }
    }
  },
  computed: {
    categoryCards() {
      return [
        { key: 'bio', label: '生物设备', value: this.stats.categoryStats['生物'] || 0 },
        { key: 'chem', label: '化学设备', value: this.stats.categoryStats['化学'] || 0 },
        { key: 'phy', label: '物理设备', value: this.stats.categoryStats['物理'] || 0 },
        { key: 'other', label: '其他设备', value: this.stats.categoryStats['其他'] || 0 }
      ]
    },
    statusCards() {
      return [
        { key: 'normal', label: '正常设备', value: this.stats.statusStats['正常'] || 0 },
        { key: 'repair', label: '维修设备', value: this.stats.statusStats['维修'] || 0 },
        { key: 'scrap', label: '报废设备', value: this.stats.statusStats['报废'] || 0 }
      ]
    }
  },
  mounted(){ this.init() },
  methods:{
    init(){
      if(this.$storage.get('Token')){
        this.$http({ url: `${this.$storage.get('sessionTable')}/session`, method: 'get' }).then(({ data }) => {
          if (data && data.code === 0) {
            this.isAdmin = this.$storage.get('sessionTable') === 'users'
            if (this.isAdmin) {
              this.getDashboardStats()
              this.getRecentTrendStats()
            } else {
              this.getPortalLists()
            }
          } else {
            router.push({ name: 'login' })
          }
        })
      }else{
        router.push({ name: 'login' })
      }
    },
    getDashboardStats() {
      this.$http({ url: 'shiyanshebei/dashboard', method: 'get' }).then(({ data }) => {
        if (data && data.code === 0) {
          this.stats = data.data || this.stats
          this.getRecentDevicesByDate()
        }
      })
    },
    getRecentDevicesByDate() {
      const params = { page: 1, limit: 5, sort: 'id', order: 'desc' }
      if (this.adminDateRange && this.adminDateRange.length === 2) {
        params.addtimestart = this.adminDateRange[0] + ' 00:00:00'
        params.addtimeend = this.adminDateRange[1] + ' 23:59:59'
      }
      this.$http({ url: 'shiyanshebei/list', method: 'get', params }).then(({ data }) => {
        if (data && data.code === 0 && data.data && data.data.list) {
          this.stats = { ...this.stats, recentList: data.data.list }
        }
      })
    },
    exportRecentDevices() {
      const header = ['设备名称', '分类', '状态']
      const rows = (this.stats.recentList || []).map(item => [
        item.shebeimingcheng || '',
        item.category || '',
        item.shebeizhuangtai || ''
      ])
      const csv = [header, ...rows].map(row => row.map(val => `"${String(val).replace(/"/g, '""')}"`).join(',')).join('\n')
      const blob = new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8;' })
      const link = document.createElement('a')
      link.href = URL.createObjectURL(blob)
      link.download = `最近设备_${new Date().getTime()}.csv`
      link.click()
      URL.revokeObjectURL(link.href)
    },
    getRecentTrendStats() {
      const now = new Date()
      const start = new Date(now.getTime() - 6 * 24 * 60 * 60 * 1000)
      const formatDate = (dateObj) => {
        const y = dateObj.getFullYear()
        const m = `${dateObj.getMonth() + 1}`.padStart(2, '0')
        const d = `${dateObj.getDate()}`.padStart(2, '0')
        return `${y}-${m}-${d}`
      }
      const startTime = `${formatDate(start)} 00:00:00`
      const endTime = `${formatDate(now)} 23:59:59`
      const reserveParams = { page: 1, limit: 1, addtimestart: startTime, addtimeend: endTime }
      const borrowParams = { page: 1, limit: 1, addtimestart: startTime, addtimeend: endTime }
      this.$http({ url: 'shiyanshiyuyue/page', method: 'get', params: reserveParams }).then(({ data }) => {
        this.recentStats.reserve7d = data && data.code === 0 && data.data ? (data.data.total || 0) : 0
      })
      this.$http({ url: 'shiyanshebeiBorrow/page', method: 'get', params: borrowParams }).then(({ data }) => {
        this.recentStats.borrow7d = data && data.code === 0 && data.data ? (data.data.total || 0) : 0
      })
    },
    getPortalLists() {
      this.labLoading = true
      this.deviceLoading = true
      this.$http({ url: 'shiyanshixinxi/list', method: 'get', params: { page: 1, limit: 5 } }).then(({ data }) => {
        this.labList = data && data.data && data.data.list ? data.data.list : []
        this.labLoading = false
      }).catch(() => { this.labLoading = false })
      this.$http({ url: 'shiyanshebei/list', method: 'get', params: { page: 1, limit: 5 } }).then(({ data }) => {
        this.deviceList = data && data.data && data.data.list ? data.data.list : []
        this.deviceLoading = false
      }).catch(() => { this.deviceLoading = false })
    }
  }
};
</script>

<style lang="scss" scoped>
.portal-home { width: 100%; }
.content { width: 100%; min-height: 500px; padding: 12px; box-sizing: border-box; background: #e7edf5; border-radius: 14px; }
.portal-title { font-size: 28px; font-weight: 700; color: #2f3e52; margin-bottom: 16px; }
.headline { font-size: 28px; font-weight: 700; color: #2f3e52; margin-bottom: 16px; text-align: left; }
.stat-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(160px, 1fr)); gap: 12px; }
.trend-grid { margin-top: 12px; display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 12px; }
.stat-card, .panel-card, .recent-card { background: #f8fbff; border: 1px solid #d8e4f3; border-radius: 12px; padding: 14px; }
.trend-card { background: #f8fbff; border: 1px solid #d8e4f3; border-radius: 12px; padding: 14px; }
.label { color: #63758a; font-size: 14px; }
.value { margin-top: 8px; font-size: 28px; color: #2f3e52; font-weight: 700; }
.recent-card { margin-top: 16px; }
.toolbar { margin-top: 14px; display: flex; gap: 12px; align-items: center; justify-content: flex-end; }
.recent-title, .panel-header { font-size: 16px; color: #2f3e52; font-weight: 600; margin-bottom: 10px; display:flex; justify-content:space-between; align-items:center; }
.panel-card { min-height: 360px; }
</style>
