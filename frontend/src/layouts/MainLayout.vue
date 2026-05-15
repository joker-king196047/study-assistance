<template>
  <div class="layout-container">
    <!-- 顶部横向导航栏 -->
    <header class="header">
      <div class="header-content">
        <div class="logo-section">
          <el-icon :size="28" color="#667eea"><Document /></el-icon>
          <span class="logo-text">智能学习平台</span>
        </div>
        
        <nav class="nav-menu">
          <div 
            class="nav-item" 
            :class="{ active: $route.path === '/home' }"
            @click="$router.push('/home')"
          >
            <el-icon><House /></el-icon>
            <span>首页</span>
          </div>
          <div 
            class="nav-item" 
            :class="{ active: $route.path.startsWith('/question-bank') }"
            @click="$router.push('/question-bank')"
          >
            <el-icon><Reading /></el-icon>
            <span>题库</span>
          </div>
        </nav>
        
        <div class="user-section">
          <div class="user-avatar" @click="showUserCenter = true">
            <el-avatar :size="40" :src="userAvatar" />
          </div>
        </div>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="main-content">
      <slot />
    </main>

    <!-- 个人中心弹出窗口 -->
    <div v-if="showUserCenter" class="user-center-overlay" @click="showUserCenter = false">
      <div class="user-center-panel" @click.stop>
        <div class="user-center-header">
          <div class="user-center-avatar">
            <el-avatar :size="80" :src="userAvatar" />
          </div>
          <div class="user-center-info">
            <h3>{{ userStore.userInfo?.username }}</h3>
            <p>{{ userStore.userInfo?.email }}</p>
            <div class="user-level">
              <el-tag type="success" size="small">
                <el-icon><Trophy /></el-icon>
                学习达人 Lv.5
              </el-tag>
            </div>
          </div>
        </div>
        
        <div class="user-stats">
          <div class="user-stat">
            <span class="stat-number">128</span>
            <span class="stat-name">学习天数</span>
          </div>
          <div class="user-stat">
            <span class="stat-number">1,234</span>
            <span class="stat-name">完成题目</span>
          </div>
          <div class="user-stat">
            <span class="stat-number">89%</span>
            <span class="stat-name">正确率</span>
          </div>
        </div>

        <div class="user-menu">
          <div class="menu-item" @click="handleMenu('profile')">
            <el-icon><User /></el-icon>
            <span>个人资料</span>
            <el-icon class="menu-arrow"><ArrowRight /></el-icon>
          </div>
          <div class="menu-item" @click="handleMenu('settings')">
            <el-icon><Setting /></el-icon>
            <span>系统设置</span>
            <el-icon class="menu-arrow"><ArrowRight /></el-icon>
          </div>
          <div class="menu-item" @click="handleMenu('history')">
            <el-icon><Clock /></el-icon>
            <span>学习历史</span>
            <el-icon class="menu-arrow"><ArrowRight /></el-icon>
          </div>
          <div class="menu-item" @click="handleMenu('achievement')">
            <el-icon><Medal /></el-icon>
            <span>成就徽章</span>
            <el-icon class="menu-arrow"><ArrowRight /></el-icon>
          </div>
        </div>

        <div class="user-center-footer">
          <el-button type="danger" @click="handleLogout">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document, House, Reading, Trophy, User, Setting, Clock, Medal, SwitchButton, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const showUserCenter = ref(false)

const userAvatar = computed(() => {
  const username = userStore.userInfo?.username || 'user'
  return `https://api.dicebear.com/7.x/avataaars/svg?seed=${username}`
})

function handleMenu(menu: string) {
  showUserCenter.value = false
  ElMessage.info(`${menu === 'profile' ? '个人资料' : menu === 'settings' ? '系统设置' : menu === 'history' ? '学习历史' : '成就徽章'} 页面开发中...`)
}

async function handleLogout() {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/')
  } catch {}
}
</script>

<style scoped>
.layout-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background: #f8f9ff;
}

.header {
  width: 100%;
  height: 64px;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  height: 100%;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo-text {
  font-size: 18px;
  font-weight: 700;
  color: #1a1a2e;
}

.nav-menu {
  display: flex;
  gap: 8px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 20px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 14px;
  font-weight: 500;
  color: #666;
}

.nav-item:hover {
  background: #f5f7fa;
  color: #667eea;
}

.nav-item.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.user-section {
  cursor: pointer;
}

.user-avatar {
  cursor: pointer;
  transition: transform 0.2s;
  padding: 4px;
  border-radius: 50%;
}

.user-avatar:hover {
  background: #f5f7fa;
  transform: scale(1.05);
}

.main-content {
  flex: 1;
  overflow-y: auto;
  padding: 0;
}

/* 个人中心弹出窗口 */
.user-center-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 2000;
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  padding: 80px 40px 0 0;
  animation: fadeIn 0.2s ease;
}

.user-center-panel {
  background: #fff;
  border-radius: 20px;
  width: 360px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
  animation: slideInRight 0.3s ease;
}

@keyframes slideInRight {
  from {
    opacity: 0;
    transform: translateX(40px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.user-center-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 32px 24px 24px;
  border-radius: 20px 20px 0 0;
  text-align: center;
  color: #fff;
}

.user-center-avatar {
  margin-bottom: 16px;
}

.user-center-avatar :deep(.el-avatar) {
  border: 4px solid rgba(255, 255, 255, 0.3);
}

.user-center-info h3 {
  margin: 0 0 4px;
  font-size: 20px;
  font-weight: 700;
}

.user-center-info p {
  margin: 0 0 12px;
  opacity: 0.9;
  font-size: 14px;
}

.user-stats {
  display: flex;
  padding: 24px;
  border-bottom: 1px solid #f1f5f9;
}

.user-stat {
  flex: 1;
  text-align: center;
}

.stat-number {
  display: block;
  font-size: 24px;
  font-weight: 700;
  color: #667eea;
  margin-bottom: 4px;
}

.stat-name {
  font-size: 13px;
  color: #64748b;
}

.user-menu {
  padding: 8px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 16px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s;
  color: #1e293b;
}

.menu-item:hover {
  background: #f1f5f9;
}

.menu-arrow {
  margin-left: auto;
  color: #cbd5e1;
}

.user-center-footer {
  padding: 20px 24px 24px;
}
</style>
