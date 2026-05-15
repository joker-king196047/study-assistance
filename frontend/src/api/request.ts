import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'

const service: AxiosInstance = axios.create({
  timeout: 30000
})

service.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    if (res.code && res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res.data || res
  },
  (error) => {
    const status = error.response?.status
    const serverMsg = error.response?.data?.message

    if (status === 401) {
      if (window.location.pathname !== '/login' && window.location.pathname !== '/register') {
        localStorage.removeItem('token')
        window.location.href = '/login'
      } else {
        ElMessage.error(serverMsg || '用户名或密码错误')
      }
    } else if (status === 403) {
      ElMessage.error(serverMsg || '无权限访问')
    } else {
      ElMessage.error(serverMsg || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default service
