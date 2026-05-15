<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="card-header">
          <h2>注册</h2>
          <p>创建您的账号</p>
        </div>
      </template>
      <el-form :model="registerForm" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input 
          v-model="registerForm.username" 
          placeholder="用户名" 
          prefix-icon="User"
          size="large"
          />
        </el-form-item>
        <el-form-item prop="email">
          <el-input 
          v-model="registerForm.email" 
          placeholder="邮箱" 
          prefix-icon="Message"
          size="large"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
          v-model="registerForm.password" 
          type="password" 
          placeholder="密码" 
          prefix-icon="Lock" 
          show-password
          size="large"
          />
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input 
          v-model="registerForm.confirmPassword" 
          type="password" 
          placeholder="确认密码" 
          prefix-icon="Lock" 
          show-password
          size="large"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
          type="primary" 
          size="large"
          style="width: 100%" 
          @click="handleRegister" 
          :loading="loading"
          >
            注册
          </el-button>
        </el-form-item>
      </el-form>
      <div class="footer">
        <span>已有账号？</span>
        <router-link to="/login">立即登录</router-link>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { register } from '@/api/auth'
import axios from 'axios'

const router = useRouter()

const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (_rule: any, value: any, callback: any) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度应在3-20个字符之间', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const formRef = ref<FormInstance>()
const loading = ref(false)

const handleRegister = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await register({
          username: registerForm.username,
          email: registerForm.email,
          password: registerForm.password
        })
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (error) {
        let errorMsg = '注册失败，请稍后重试'
        let shouldShowLoginLink = false
        
        if (axios.isAxiosError(error) && error.response?.data) {
          const serverMsg = error.response.data.message || error.response.data.msg
          if (serverMsg) {
            errorMsg = serverMsg
            if (serverMsg.includes('用户名已存在') || serverMsg.includes('邮箱已被注册')) {
              shouldShowLoginLink = true
            }
          }
        }
        
        ElMessage({
          type: 'error',
          message: shouldShowLoginLink 
            ? `${errorMsg}，请直接登录或更换信息`
            : errorMsg,
          duration: 4000
        })
        
        if (shouldShowLoginLink) {
          setTimeout(() => {
            ElMessage({
              type: 'info',
              message: '已有账号？点击右上角"立即登录"',
              duration: 3000
            })
          }, 500)
        }
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.register-container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  width: 400px;
}

.card-header {
  text-align: center;
}

.card-header h2 {
  margin: 0 0 5px;
}

.card-header p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.footer {
  text-align: center;
  font-size: 14px;
  margin-top: 10px;
}

.footer a {
  color: #667eea;
  text-decoration: none;
  margin-left: 5px;
}
</style>
