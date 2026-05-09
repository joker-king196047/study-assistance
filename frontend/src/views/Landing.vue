<template>
  <div class="landing-container">
    <div class="hero-section">
      <div class="hero-content">
        <h1 class="title">基于多智能体的个性化学习辅助系统</h1>
        <p class="subtitle">AI驱动，因材施教，让学习更高效</p>
        <div class="cta-buttons">
          <el-button type="primary" size="large" @click="router.push('/login')">
            立即登录
          </el-button>
          <el-button size="large" @click="router.push('/register')">
            注册账号
          </el-button>
        </div>
      </div>
    </div>
    
    <div class="features-section">
      <div class="section-header">
        <h2>核心功能</h2>
        <p>六大模块，打造个性化学习体验</p>
      </div>
      <el-row :gutter="30" class="features-grid">
        <el-col :xs="24" :sm="12" :md="8" v-for="(feature, index) in features" :key="index">
          <el-card class="feature-card" shadow="hover">
            <el-icon class="feature-icon" :size="56" :color="feature.color">
              <component :is="feature.icon" />
            </el-icon>
            <h3>{{ feature.title }}</h3>
            <p>{{ feature.description }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="how-section">
      <div class="section-header">
        <h2>使用流程</h2>
        <p>三步开启个性化学习之旅</p>
      </div>
      <el-row :gutter="30" class="steps-grid">
        <el-col :xs="24" :sm="8" v-for="(step, index) in steps" :key="index">
          <div class="step-card">
            <div class="step-number">{{ index + 1 }}</div>
            <h3>{{ step.title }}</h3>
            <p>{{ step.description }}</p>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { 
  Reading, 
  Connection, 
  Document, 
  ChatDotRound, 
  DataAnalysis, 
  TrendCharts 
} from '@element-plus/icons-vue'

const router = useRouter()

const features = [
  {
    title: '对话式画像构建',
    description: '通过AI对话自动构建您的学习画像，了解您的知识基础、学习风格和目标',
    icon: Reading,
    color: '#667eea'
  },
  {
    title: '智能技能树',
    description: '根据您的画像生成个性化技能树，可视化学习路径，清晰规划学习方向',
    icon: Connection,
    color: '#764ba2'
  },
  {
    title: '个性化资源生成',
    description: 'AI多智能体协同工作，为您生成专属学习资源、教程文档和练习题',
    icon: Document,
    color: '#11998e'
  },
  {
    title: 'AI智能答疑',
    description: '遇到问题随时提问，AI学霸为您详细解答，支持举一反三练习',
    icon: ChatDotRound,
    color: '#38ef7d'
  },
  {
    title: '学习效果评估',
    description: '多维度跟踪学习进度，生成评估报告，实时掌握学习效果',
    icon: DataAnalysis,
    color: '#ee0979'
  },
  {
    title: '学习报告',
    description: '全面分析学习数据，生成可视化报告，帮助优化学习策略',
    icon: TrendCharts,
    color: '#ff6a00'
  }
]

const steps = [
  {
    title: '注册登录',
    description: '创建账号，登录系统开始您的学习之旅'
  },
  {
    title: '构建画像',
    description: '通过AI对话完成学习画像构建，让系统了解您的需求'
  },
  {
    title: '开始学习',
    description: '探索技能树，开始个性化学习，获得AI实时辅导'
  }
]
</script>

<style scoped>
.landing-container {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.hero-section {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
}

.hero-content {
  text-align: center;
  max-width: 800px;
}

.title {
  color: white;
  font-size: 42px;
  font-weight: 700;
  margin-bottom: 20px;
  line-height: 1.4;
}

.subtitle {
  color: rgba(255, 255, 255, 0.9);
  font-size: 20px;
  margin-bottom: 40px;
}

.cta-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.cta-buttons .el-button {
  padding: 15px 40px;
  font-size: 16px;
}

.features-section,
.how-section {
  background: white;
  padding: 80px 20px;
}

.section-header {
  text-align: center;
  margin-bottom: 50px;
}

.section-header h2 {
  font-size: 32px;
  color: #333;
  margin-bottom: 10px;
}

.section-header p {
  font-size: 16px;
  color: #666;
}

.features-grid,
.steps-grid {
  max-width: 1200px;
  margin: 0 auto;
}

.feature-card {
  text-align: center;
  padding: 40px 20px;
  margin-bottom: 30px;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.feature-card:hover {
  transform: translateY(-10px);
  border-color: #667eea;
}

.feature-icon {
  margin-bottom: 20px;
}

.feature-card h3 {
  font-size: 20px;
  color: #333;
  margin-bottom: 15px;
}

.feature-card p {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
}

.step-card {
  text-align: center;
  padding: 40px 20px;
}

.step-number {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-size: 36px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
}

.step-card h3 {
  font-size: 20px;
  color: #333;
  margin-bottom: 10px;
}

.step-card p {
  color: #666;
  font-size: 14px;
}

@media (max-width: 768px) {
  .title {
    font-size: 28px;
  }
  
  .subtitle {
    font-size: 16px;
  }
  
  .cta-buttons {
    flex-direction: column;
  }
  
  .cta-buttons .el-button {
    width: 100%;
  }
}
</style>
