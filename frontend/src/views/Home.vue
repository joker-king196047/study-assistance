<template>
  <div class="home-container">
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
      <!-- 欢迎横幅 -->
        <div class="welcome-banner">
          <div class="banner-content">
            <div class="banner-text">
              <h1>你好，{{ userStore.userInfo?.username }}！</h1>
              <p>继续今天的学习，向着目标前进吧！</p>
              <div class="banner-stats">
                <div class="stat-item">
                  <span class="stat-num">{{ todayData.studyTime }}</span>
                  <span class="stat-label">今日学习时长</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                  <span class="stat-num">{{ todayData.completed }}</span>
                  <span class="stat-label">完成题目</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                  <span class="stat-num">{{ todayData.streak }}</span>
                  <span class="stat-label">连续打卡</span>
                </div>
              </div>
            </div>
            <div class="banner-image">
              <el-icon :size="120" color="rgba(255,255,255,0.3)"><TrendCharts /></el-icon>
            </div>
          </div>
        </div>

        <!-- 快捷入口 -->
        <div class="section">
          <h2 class="section-title">快捷入口</h2>
          <div class="quick-actions">
            <div class="action-card" @click="handleAction('new')">
              <div class="action-icon" style="background: linear-gradient(135deg, #ff9a56 0%, #ff6b6b 100%);">
                <el-icon :size="32" color="#fff"><Plus /></el-icon>
              </div>
              <h3>新的学习</h3>
              <p>开始新的知识点学习</p>
            </div>
            <div class="action-card" @click="handleAction('continue')">
              <div class="action-icon" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                <el-icon :size="32" color="#fff"><VideoPlay /></el-icon>
              </div>
              <h3>继续学习</h3>
              <p>从上次结束的地方继续</p>
            </div>
            <div class="action-card" @click="handleAction('practice')">
              <div class="action-icon" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                <el-icon :size="32" color="#fff"><Edit /></el-icon>
              </div>
              <h3>开始练习</h3>
              <p>选择知识点进行练习</p>
            </div>
            <div class="action-card" @click="handleAction('exam')">
              <div class="action-icon" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);">
                <el-icon :size="32" color="#fff"><Document /></el-icon>
              </div>
              <h3>模拟考试</h3>
              <p>检验你的学习成果</p>
            </div>
            <div class="action-card" @click="handleAction('review')">
              <div class="action-icon" style="background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);">
                <el-icon :size="32" color="#fff"><Clock /></el-icon>
              </div>
              <h3>错题回顾</h3>
              <p>复习答错的题目</p>
            </div>
          </div>
        </div>

        <!-- 学习进度 -->
        <div class="section">
          <div class="section-header">
            <h2 class="section-title">学习进度</h2>
            <el-link type="primary">查看详情</el-link>
          </div>
          <div class="progress-grid">
            <div class="progress-card">
              <div class="progress-header">
                <span class="progress-title">JavaScript 基础</span>
                <span class="progress-percent">75%</span>
              </div>
              <el-progress :percentage="75" :stroke-width="8" :show-text="false" />
              <p class="progress-desc">已完成 15/20 个知识点</p>
            </div>
            <div class="progress-card">
              <div class="progress-header">
                <span class="progress-title">Vue.js 框架</span>
                <span class="progress-percent">45%</span>
              </div>
              <el-progress :percentage="45" :stroke-width="8" :show-text="false" status="active" />
              <p class="progress-desc">已完成 9/20 个知识点</p>
            </div>
            <div class="progress-card">
              <div class="progress-header">
                <span class="progress-title">算法基础</span>
                <span class="progress-percent">20%</span>
              </div>
              <el-progress :percentage="20" :stroke-width="8" :show-text="false" />
              <p class="progress-desc">已完成 4/20 个知识点</p>
            </div>
          </div>
        </div>

        <!-- 最近学习 -->
        <div class="section">
          <div class="section-header">
            <h2 class="section-title">最近学习</h2>
            <el-link type="primary">查看全部</el-link>
          </div>
          <div class="recent-list">
            <div class="recent-item" v-for="(item, index) in recentLearn" :key="index" @click="handleRecent(item)">
              <div class="recent-icon" :style="{ background: item.color }">
                <el-icon :size="24" color="#fff"><component :is="item.icon" /></el-icon>
              </div>
              <div class="recent-info">
                <h4>{{ item.title }}</h4>
                <p>{{ item.desc }}</p>
              </div>
              <div class="recent-meta">
                <span>{{ item.time }}</span>
                <el-icon><ArrowRight /></el-icon>
              </div>
            </div>
          </div>
        </div>
    </main>

    <!-- AI唤醒悬浮按钮 -->
    <div class="ai-float-btn" @click="showAIPanel = true">
      <el-icon :size="28" color="#fff"><ChatDotRound /></el-icon>
      <div class="ai-pulse"></div>
    </div>

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

    <!-- AI面板 -->
    <div v-if="showAIPanel" class="ai-panel-overlay" @click="showAIPanel = false">
      <div class="ai-panel" @click.stop>
        <div class="ai-panel-header">
          <div class="ai-panel-title">
            <el-icon :size="24" color="#667eea"><ChatDotRound /></el-icon>
            <span>AI学习助手</span>
          </div>
          <el-button class="ai-panel-close" text @click="showAIPanel = false">
            <el-icon><Close /></el-icon>
          </el-button>
        </div>
        <div class="ai-panel-body">
          <div class="ai-welcome">
            <el-icon :size="48" color="#667eea"><ChatDotRound /></el-icon>
            <h3>你好！我是AI学习助手</h3>
            <p>我可以帮你解答问题、讲解知识点、分析错题...</p>
          </div>
          <div class="ai-suggestions">
            <div class="ai-suggestion" @click="handleAIQuestion('这道题怎么做？')">
              <el-icon><Document /></el-icon>
              <span>这道题怎么做？</span>
            </div>
            <div class="ai-suggestion" @click="handleAIQuestion('讲解这个知识点')">
              <el-icon><Reading /></el-icon>
              <span>讲解这个知识点</span>
            </div>
            <div class="ai-suggestion" @click="handleAIQuestion('帮我分析错题')">
              <el-icon><DataAnalysis /></el-icon>
              <span>帮我分析错题</span>
            </div>
          </div>
        </div>
        <div class="ai-panel-footer">
          <el-input v-model="aiQuestion" placeholder="输入你的问题..." />
          <el-button type="primary" @click="handleAISend">发送</el-button>
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
import { 
  Document,
  House,
  Reading,
  VideoPlay,
  Edit,
  Clock,
  TrendCharts,
  ArrowRight,
  User,
  Setting,
  Trophy,
  Medal,
  SwitchButton,
  ChatDotRound,
  Close,
  DataAnalysis,
  Plus
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const showUserCenter = ref(false)
const showAIPanel = ref(false)
const aiQuestion = ref('')

const userAvatar = computed(() => `https://api.dicebear.com/7.x/avataaars/svg?seed=${userStore.userInfo?.username}`)

const todayData = ref({
  studyTime: '2.5h',
  completed: 18,
  streak: 7
})

const recentLearn = ref([
  { 
    title: 'JavaScript 闭包深入理解', 
    desc: '前端基础 · 已完成', 
    time: '10分钟前',
    icon: 'Document',
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'
  },
  { 
    title: 'Vue3 响应式原理', 
    desc: '框架核心 · 进行中', 
    time: '30分钟前',
    icon: 'Connection',
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)'
  },
  { 
    title: '算法：二分查找', 
    desc: '算法入门 · 已完成', 
    time: '1小时前',
    icon: 'TrendCharts',
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)'
  }
])

const handleAction = (action: string) => {
  if (action === 'new') {
    router.push('/profile/start')
    return
  }
  const messages: Record<string, string> = {
    continue: '即将继续上次的学习...',
    practice: '即将进入练习模式...',
    exam: '即将开始模拟考试...',
    review: '即将打开错题本...'
  }
  ElMessage.info(messages[action] || '功能开发中...')
}

const handleRecent = (item: any) => {
  ElMessage.info(`即将打开「${item.title}」...`)
}

const handleMenu = (menu: string) => {
  showUserCenter.value = false
  ElMessage.info(`${menu === 'profile' ? '个人资料' : menu === 'settings' ? '系统设置' : menu === 'history' ? '学习历史' : '成就徽章'} 页面开发中...`)
}

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/')
  } catch {
  }
}

const handleAIQuestion = (question: string) => {
  aiQuestion.value = question
  ElMessage.info('AI助手正在思考...')
}

const handleAISend = () => {
  if (!aiQuestion.value.trim()) {
    ElMessage.warning('请输入你的问题')
    return
  }
  ElMessage.info('AI助手正在回复中...')
  aiQuestion.value = ''
}
</script>

<style scoped>
.home-container {
  width: 100%;
  min-height: 100vh;
  background: #f5f7fa;
  display: flex;
  flex-direction: column;
}

/* 顶部导航栏 */
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-text {
  font-size: 20px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.nav-menu {
  display: flex;
  gap: 8px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 20px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 15px;
  color: #64748b;
  transition: all 0.3s;
}

.nav-item:hover {
  background: #f1f5f9;
  color: #334155;
}

.nav-item.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.user-section {
  display: flex;
  align-items: center;
}

.user-avatar {
  cursor: pointer;
  transition: all 0.3s;
  padding: 4px;
  border-radius: 50%;
}

.user-avatar:hover {
  background: #f1f5f9;
  transform: scale(1.05);
}

/* 主内容区 */
.main-content {
  flex: 1;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  padding: 24px;
}

.tab-content {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 欢迎横幅 */
.welcome-banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 20px;
  padding: 40px;
  color: #fff;
  margin-bottom: 32px;
  overflow: hidden;
  position: relative;
}

.banner-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.banner-text h1 {
  margin: 0 0 8px;
  font-size: 28px;
  font-weight: 700;
}

.banner-text p {
  margin: 0 0 24px;
  opacity: 0.9;
  font-size: 16px;
}

.banner-stats {
  display: flex;
  gap: 32px;
}

.stat-item {
  display: flex;
  flex-direction: column;
}

.stat-num {
  font-size: 28px;
  font-weight: 700;
  line-height: 1.2;
}

.stat-label {
  font-size: 13px;
  opacity: 0.8;
  margin-top: 4px;
}

.stat-divider {
  width: 1px;
  background: rgba(255, 255, 255, 0.3);
}

.banner-image {
  opacity: 0.8;
}

/* 通用区域样式 */
.section {
  margin-bottom: 32px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
}

/* 快捷入口 */
.quick-actions {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
}

.action-card {
  background: #fff;
  border-radius: 16px;
  padding: 24px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
}

.action-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.2);
}

.action-icon {
  width: 72px;
  height: 72px;
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.action-card h3 {
  margin: 0 0 6px;
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.action-card p {
  margin: 0;
  font-size: 13px;
  color: #64748b;
}

/* 学习进度 */
.progress-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.progress-card {
  background: #fff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
}

.progress-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.progress-title {
  font-size: 15px;
  font-weight: 600;
  color: #1e293b;
}

.progress-percent {
  font-size: 18px;
  font-weight: 700;
  color: #667eea;
}

.progress-desc {
  margin: 12px 0 0;
  font-size: 13px;
  color: #64748b;
}

/* 最近学习 */
.recent-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.recent-item {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.recent-item:hover {
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.15);
  transform: translateX(4px);
}

.recent-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.recent-info {
  flex: 1;
}

.recent-info h4 {
  margin: 0 0 4px;
  font-size: 15px;
  font-weight: 600;
  color: #1e293b;
}

.recent-info p {
  margin: 0;
  font-size: 13px;
  color: #64748b;
}

.recent-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #94a3b8;
  font-size: 13px;
}

/* AI悬浮按钮 */
.ai-float-btn {
  position: fixed;
  bottom: 40px;
  right: 40px;
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.4);
  z-index: 1000;
  transition: all 0.3s;
}

.ai-float-btn:hover {
  transform: scale(1.1);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.5);
}

.ai-pulse {
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: rgba(102, 126, 234, 0.3);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(1.8);
    opacity: 0;
  }
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

/* AI面板 */
.ai-panel-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 2000;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: fadeIn 0.2s ease;
}

.ai-panel {
  background: #fff;
  border-radius: 20px;
  width: 500px;
  max-height: 80vh;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  animation: scaleIn 0.3s ease;
}

@keyframes scaleIn {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.ai-panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #f1f5f9;
}

.ai-panel-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
}

.ai-panel-close {
  color: #94a3b8;
  font-size: 20px;
}

.ai-panel-body {
  flex: 1;
  padding: 32px 24px;
  overflow-y: auto;
}

.ai-welcome {
  text-align: center;
  margin-bottom: 32px;
}

.ai-welcome h3 {
  margin: 16px 0 8px;
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
}

.ai-welcome p {
  margin: 0;
  color: #64748b;
  font-size: 14px;
}

.ai-suggestions {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.ai-suggestion {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: #f8fafc;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.2s;
  color: #1e293b;
}

.ai-suggestion:hover {
  background: #f1f5f9;
  transform: translateX(4px);
}

.ai-suggestion .el-icon {
  color: #667eea;
}

.ai-panel-footer {
  display: flex;
  gap: 12px;
  padding: 16px 24px;
  border-top: 1px solid #f1f5f9;
}

.ai-panel-footer .el-input {
  flex: 1;
}
</style>
