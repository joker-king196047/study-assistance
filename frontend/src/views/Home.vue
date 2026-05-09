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
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" v-for="(feature, index) in features" :key="index">
            <el-card shadow="hover" class="feature-card" @click="handleFeatureClick(feature)">
              <el-icon :size="48" :color="feature.color">
                <component :is="feature.icon" />
              </el-icon>
              <h4>{{ feature.title }}</h4>
              <p>{{ feature.description }}</p>
            </el-card>
          </el-col>
        </el-row>

        <el-divider content-position="left">快速开始</el-divider>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-card>
              <div class="quick-start">
                <div class="step">
                  <div class="step-number">1</div>
                  <div class="step-content">
                    <h4>构建学习画像</h4>
                    <p>点击上方"对话式画像构建"开始，与AI对话建立您的学习画像</p>
                  </div>
                </div>
                <div class="step">
                  <div class="step-number">2</div>
                  <div class="step-content">
                    <h4>制定学习计划</h4>
                    <p>根据您的学习画像，系统会自动生成个性化的技能树</p>
                  </div>
                </div>
                <div class="step">
                  <div class="step-number">3</div>
                  <div class="step-content">
                    <h4>开始学习</h4>
                    <p>访问技能树页面，选择要学习的节点开始个性化学习</p>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-divider content-position="left">学习进度</el-divider>

        <el-row :gutter="20">
          <el-col :xs="24" :sm="12">
            <el-card>
              <template #header>
                <div class="card-header">
                  <span>今日学习</span>
                </div>
              </template>
              <div class="stat-item">
                <el-statistic title="学习时长" :value="2.5" suffix="小时" />
              </div>
              <div class="stat-item">
                <el-statistic title="完成节点" :value="3" suffix="个" />
              </div>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-card>
              <template #header>
                <div class="card-header">
                  <span>总体进度</span>
                </div>
              </template>
              <el-progress :percentage="15" status="active" />
              <p style="margin-top: 20px; color: #666;">已完成 3 个节点，共 20 个节点</p>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import { 
  Reading, 
  Connection, 
  Document, 
  ChatDotRound, 
  DataAnalysis, 
  TrendCharts 
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const features = [
  {
    title: '对话式画像构建',
    description: '通过AI对话自动构建您的学习画像',
    icon: Reading,
    color: '#667eea'
  },
  {
    title: '技能树',
    description: '可视化技能树，导航学习路径',
    icon: Connection,
    color: '#764ba2'
  },
  {
    title: '资源学习',
    description: '个性化学习资源，精准推送',
    icon: Document,
    color: '#11998e'
  },
  {
    title: '智能答疑',
    description: 'AI智能辅导，即问即答',
    icon: ChatDotRound,
    color: '#38ef7d'
  },
  {
    title: '学习效果评估',
    description: '多维度评估，持续跟踪',
    icon: DataAnalysis,
    color: '#ee0979'
  },
  {
    title: '学习报告',
    description: '全面分析学习情况',
    icon: TrendCharts,
    color: '#ff6a00'
  }
]

const handleFeatureClick = (feature: any) => {
  ElMessage.info(`即将进入${feature.title}模块，敬请期待...`)
}

const handleLogout = async () => {
  await userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/')
}
</script>

<style scoped>
.home-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  flex-shrink: 0;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.header-content h2 {
  margin: 0;
  font-size: 20px;
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
  overflow-y: auto;
}

.feature-card {
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  margin-bottom: 20px;
  border: 2px solid transparent;
}

.feature-card:hover {
  transform: translateY(-5px);
  border-color: #667eea;
}

.feature-card h4 {
  margin: 15px 0 10px;
  font-size: 16px;
}

.feature-card p {
  color: #666;
  font-size: 14px;
}

.quick-start {
  padding: 10px 0;
}

.step {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.step:last-child {
  margin-bottom: 0;
}

.step-number {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-size: 20px;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  margin-right: 20px;
}

.step-content h4 {
  margin: 0 0 5px;
  font-size: 16px;
}

.step-content p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.card-header {
  font-weight: bold;
}

.stat-item {
  margin-bottom: 20px;
}

.stat-item:last-child {
  margin-bottom: 0;
}
</style>
