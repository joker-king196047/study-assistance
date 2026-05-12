<template>
  <div class="profile-flow">
    <div class="loading-container">
      <el-icon class="loading-icon" :size="60" color="#667eea"><Loading /></el-icon>
      <p class="loading-text">正在准备你的学习之旅...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'
import { profileApi } from '@/api/profile'

const router = useRouter()

onMounted(async () => {
  try {
    const { isFirstTime, hasGlobalProfile, activeDirections } = await profileApi.checkFirstLearning()
    
    if (isFirstTime || !hasGlobalProfile) {
      router.push('/profile/global-setup')
    } else if (activeDirections.length === 0) {
      router.push('/profile/direction-select')
    } else {
      router.push('/profile/direction-select')
    }
  } catch (error) {
    ElMessage.error('获取信息失败，请重试')
    router.push('/home')
  }
})
</script>

<style scoped>
.profile-flow {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.loading-container {
  text-align: center;
}

.loading-icon {
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.loading-text {
  color: white;
  font-size: 18px;
  margin: 0;
}
</style>
