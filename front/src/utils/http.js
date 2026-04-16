import axios from 'axios'
import router from '@/router/router-static'
import storage from '@/utils/storage'
import base from '@/utils/base'

const http = axios.create({
    timeout: 1000 * 86400,
    withCredentials: true,
    // 开发环境可能运行在 8081/8082 等端口，使用绝对地址避免代理缺失导致接口请求到错误端口
    // 生产环境（打包后）仍保持相对路径，适配后端同域部署
    baseURL: process.env.NODE_ENV === 'production' ? '/springboote51e2' : (base.get().url || 'http://localhost:8080/springboote51e2/'),
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})
// 请求拦截
http.interceptors.request.use(config => {
    config.headers['Token'] = storage.get('Token') // 请求头带上token
    return config
}, error => {
    return Promise.reject(error)
})
// 响应拦截
http.interceptors.response.use(response => {
    if (response.data && response.data.code === 401) { // 401, token失效
        router.push({ name: 'login' })
    }
    return response
}, error => {
    return Promise.reject(error)
})
export default http