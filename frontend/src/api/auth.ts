import request from './request'
import type { AxiosPromise } from 'axios'

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
}

export interface LoginResponse {
  token: string
}

export interface UserInfo {
  id: number
  username: string
  email: string
  createTime: string
}

export const login = (data: LoginRequest): AxiosPromise<LoginResponse> => {
  return request({
    url: '/api/auth/login',
    method: 'post',
    data
  })
}

export const register = (data: RegisterRequest): AxiosPromise => {
  return request({
    url: '/api/auth/register',
    method: 'post',
    data
  })
}

export const getUserInfo = (): AxiosPromise<UserInfo> => {
  return request({
    url: '/api/auth/me',
    method: 'get'
  })
}

export const logout = (): AxiosPromise => {
  return request({
    url: '/api/auth/logout',
    method: 'post'
  })
<<<<<<< HEAD
}
=======
}
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
