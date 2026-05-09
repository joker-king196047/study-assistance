<template>
  <div class="home-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>学习辅助系统</h2>
          <div class="user-info">
            <span>欢迎，{{ userStore.userInfo?.username }}</span>
            <el-button type="danger" size="small" @click="handleLogout">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <el-card>
          <template #header>
            <h3>功能模块</h3>
          </template>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-card shadow="hover" class="feature-card">
                <el-icon :size="48" color="#667eea"><Reading /></el-icon>
                <h4>画像构建</h4>
                <p>通过AI对话自动构建学习画像</p>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="feature-card">
                <el-icon :size="48" color="#667eea"><Connection /></el-icon>
                <h4>技能树</h4>
                <p>可视化技能树，导航学习路径</p>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="feature-card">
                <el-icon :size="48" color="#667eea"><Document /></el-icon>
                <h4>资源学习</h4>
                <p>个性化学习资源，精准推送</p>
              </el-card>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 20px">
            <el-col :span="8">
              <el-card shadow="hover" class="feature-card">
                <el-icon :size="48" color="#667eea"><ChatDotRound /></el-icon>
                <h4>智能答疑</h4>
                <p>AI智能辅导，即问即答</p>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="feature-card">
                <el-icon :size="48" color="#667eea"><DataAnalysis /></el-icon>
                <h4>效果评估</h4>
                <p>多维度评估，持续跟踪</p>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="feature-card">
                <el-icon :size="48" color="#667eea"><TrendCharts /></el-icon>
                <h4>学习报告</h4>
                <p>全面分析学习情况</p>
              </el-card>
            </el-col>
          </el-row>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const handleLogout = async () => {
  await userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.home-container {
  width: 100%;
  height: 100vh;
}

.el-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.header-content h2 {
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-info span {
  color: white;
}

.el-main {
  background-color: #f5f5f5;
  padding: 20px;
}

.feature-card {
  text-align: center;
  cursor: pointer;
  transition: transform 0.3s;
}

.feature-card:hover {
  transform: translateY(-5px);
}

.feature-card h4 {
  margin: 15px 0 10px;
}

.feature-card p {
  color: #666;
  font-size: 14px;
}
</style>
