<template>
  <div class="direction-select">
    <div class="container">
      <div class="header">
        <h1>选择你的学习方向</h1>
        <p class="subtitle">选择一个你想要深入学习的方向，我们将为你定制专属学习路径</p>
      </div>

      <div class="directions-grid">
        <div
          v-for="dir in directions"
          :key="dir.id"
          class="direction-card"
          :class="{ selected: selectedDirection === dir.id }"
          @click="selectDirection(dir.id)"
        >
          <div class="card-icon" :style="{ background: dir.color }">
            <el-icon :size="36" color="white">{{ dir.iconComponent }}</el-icon>
          </div>
          <h3>{{ dir.name }}</h3>
          <p>{{ dir.description }}</p>
          <div v-if="dir.features" class="features">
            <span v-for="feat in dir.features" :key="feat" class="feature-tag">{{ feat }}</span>
          </div>
          <div v-if="selectedDirection === dir.id" class="selected-badge">
            <el-icon><Check /></el-icon>
            已选择
          </div>
        </div>
      </div>

      <div class="button-area">
        <el-button @click="goBack">返回</el-button>
        <el-button
          type="primary"
          size="large"
          :disabled="!selectedDirection"
          :loading="loading"
          @click="confirmDirection"
        >
          确认选择并继续
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  Monitor,
  Server,
  Connection,
  TrendCharts,
  DataAnalysis,
  ChatDotRound,
  Box,
  Document,
  Shield,
  Cloudy,
  Check
} from '@element-plus/icons-vue'
import { profileApi } from '@/api/profile'
import type { LearningDirection } from '@/api/profile'

const router = useRouter()
const selectedDirection = ref('')
const loading = ref(false)

const directions = [
  {
    id: 'frontend',
    name: '前端开发',
    description: 'Web前端、移动端、跨端应用开发',
    iconComponent: Monitor,
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    features: ['Vue/React', '响应式', '小程序']
  },
  {
    id: 'backend',
    name: '后端开发',
    description: '服务端开发、API设计、数据库管理',
    iconComponent: Server,
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    features: ['Java/Python', '微服务', '数据库']
  },
  {
    id: 'fullstack',
    name: '全栈开发',
    description: '前后端一体化开发',
    iconComponent: Connection,
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    features: ['前端', '后端', '全栈']
  },
  {
    id: 'ai',
    name: '人工智能',
    description: '机器学习、深度学习、自然语言处理',
    iconComponent: ChatDotRound,
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    features: ['机器学习', '深度学习', 'NLP']
  },
  {
    id: 'algorithm',
    name: '算法',
    description: '数据结构、算法设计、竞赛准备',
    iconComponent: TrendCharts,
    color: 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
    features: ['数据结构', '算法', 'LeetCode']
  },
  {
    id: 'mobile',
    name: '移动端开发',
    description: 'iOS、Android、跨端应用',
    iconComponent: Box,
    color: 'linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)',
    features: ['iOS', 'Android', 'Flutter']
  },
  {
    id: 'game',
    name: '游戏开发',
    description: '游戏引擎、Unity、Unreal开发',
    iconComponent: Document,
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    features: ['Unity', 'Unreal', '游戏引擎']
  },
  {
    id: 'data',
    name: '数据分析',
    description: '数据处理、可视化、大数据技术',
    iconComponent: DataAnalysis,
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    features: ['Python', '数据可视化', '大数据']
  },
  {
    id: 'security',
    name: '网络安全',
    description: '网络安全、渗透测试、攻防技术',
    iconComponent: Shield,
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    features: ['渗透测试', '网络安全', '攻防']
  },
  {
    id: 'cloud',
    name: '云计算',
    description: '云原生、容器、DevOps技术',
    iconComponent: Cloudy,
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    features: ['Docker', 'K8s', '云原生']
  }
]

const selectDirection = (id: string) => {
  selectedDirection.value = id
}

const goBack = () => {
  router.back()
}

const confirmDirection = async () => {
  if (!selectedDirection.value) {
    ElMessage.warning('请选择一个学习方向')
    return
  }
  loading.value = true
  try {
    router.push(`/profile/local-setup/${selectedDirection.value}`)
  } catch (error) {
    ElMessage.error('操作失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.direction-select {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.container {
  width: 100%;
  max-width: 1100px;
}

.header {
  text-align: center;
  color: white;
  margin-bottom: 40px;
}

.header h1 {
  font-size: 32px;
  font-weight: 700;
  margin: 0 0 12px;
}

.subtitle {
  font-size: 16px;
  opacity: 0.9;
  margin: 0;
}

.directions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 32px;
}

.direction-card {
  background: white;
  border-radius: 16px;
  padding: 28px 24px;
  cursor: pointer;
  transition: all 0.3s;
  position: relative;
  border: 3px solid transparent;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.direction-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.direction-card.selected {
  border-color: #667eea;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
}

.card-icon {
  width: 72px;
  height: 72px;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.direction-card h3 {
  font-size: 20px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 8px;
}

.direction-card p {
  font-size: 14px;
  color: #6b7280;
  margin: 0 0 16px;
  line-height: 1.5;
}

.features {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.feature-tag {
  padding: 4px 12px;
  background: #f3f4f6;
  color: #6b7280;
  font-size: 12px;
  border-radius: 20px;
}

.selected-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 6px 14px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-size: 13px;
  font-weight: 600;
  border-radius: 20px;
}

.button-area {
  display: flex;
  justify-content: center;
  gap: 16px;
}
</style>
