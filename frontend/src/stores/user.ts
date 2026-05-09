import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, getUserInfo as getUserInfoApi, logout as logoutApi } from '@/api/auth'
import type { LoginRequest, UserInfo } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)

  const setToken = (newToken: string) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const login = async (data: LoginRequest) => {
    const res = await loginApi(data)
    setToken(res.token)
    await fetchUserInfo()
  }

  const fetchUserInfo = async () => {
    try {
      const res = await getUserInfoApi()
      userInfo.value = res
    } catch (error) {
      userInfo.value = null
    }
  }

  const logout = async () => {
    try {
      await logoutApi()
    } catch {
    }
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  return {
    token,
    userInfo,
    login,
    fetchUserInfo,
    logout,
    setToken
  }
})
