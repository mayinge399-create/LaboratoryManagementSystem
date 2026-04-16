import Vue from 'vue';
//配置路由
import VueRouter from 'vue-router'
Vue.use(VueRouter);
//1.创建组件
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import pay from '@/views/pay'
import register from '@/views/register'
import center from '@/views/center'
    import xuesheng from '@/views/modules/xuesheng/list'
    import weixiujilu from '@/views/modules/weixiujilu/list'
    import jiaoshi from '@/views/modules/jiaoshi/list'
    import storeup from '@/views/modules/storeup/list'
    import shiyanshixinxi from '@/views/modules/shiyanshixinxi/list'
    import shiyanshebei from '@/views/modules/shiyanshebei/list'
    import shiyanshebeiBorrow from '@/views/modules/shiyanshebeiBorrow/list'
    import config from '@/views/modules/config/list'


//2.配置路由   注意：名字
const routes = [{
    path: '/index',
    name: 'IndexLayout',
    component: Index,
    children: [{
      path: '/',
      name: '首页',
      component: Home,
      meta: {icon:'', title:'center'}
    }, {
      path: '/updatePassword',
      name: '修改密码',
      component: UpdatePassword,
      meta: {icon:'', title:'updatePassword'}
    }, {
      path: '/pay',
      name: '支付',
      component: pay,
      meta: {icon:'', title:'pay'}
    }, {
      path: '/center',
      name: '个人信息',
      component: center,
      meta: {icon:'', title:'center'}
    }
      ,{
	path: '/xuesheng',
        name: '学生',
        component: xuesheng
      }
      ,{
	path: '/weixiujilu',
        name: '维修记录',
        component: weixiujilu
      }
      ,{
	path: '/jiaoshi',
        name: '教师',
        component: jiaoshi
      }
      ,{
	path: '/storeup',
        name: '我的收藏管理',
        component: storeup
      }
      ,{
	path: '/shiyanshixinxi',
        name: '实验室信息',
        component: shiyanshixinxi
      }
      ,{
	path: '/shiyanshebei',
        name: '实验设备',
        component: shiyanshebei
      }
      ,{
	path: '/shiyanshebeiBorrow',
        name: '设备借阅',
        component: shiyanshebeiBorrow
      }
      ,{
	path: '/config',
        name: '轮播图管理',
        component: config
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: {icon:'', title:'login'}
  },
  {
    path: '/register',
    name: 'register',
    component: register,
    meta: {icon:'', title:'register'}
  },
  {
    path: '/',
    name: 'RootRedirect',
    redirect: '/index'
  },
  {
    path: '*',
    component: NotFound
  }
]
const router = new VueRouter({
  mode: 'hash',
  routes
})

export default router;
