<template>
  <div class="local-profile-setup">
    <div class="container">
      <div class="header">
        <div class="direction-badge" :style="{ background: directionInfo.color }">
          <el-icon :size="24" color="white">{{ directionInfo.iconComponent }}</el-icon>
          <span>{{ directionInfo.name }}</span>
        </div>
        <h1>完善你的学习画像</h1>
        <p class="subtitle">让我们了解你在{{ directionInfo.name }}方向的基础情况</p>
      </div>

      <div class="progress-bar">
        <div class="step" :class="{ active: currentStep >= 1, completed: currentStep > 1 }">
          <div class="step-number">1</div>
          <span>基础评估</span>
        </div>
        <div class="step-line" :class="{ active: currentStep > 1 }"></div>
        <div class="step" :class="{ active: currentStep >= 2, completed: currentStep > 2 }">
          <div class="step-number">2</div>
          <span>学习目标</span>
        </div>
        <div class="step-line" :class="{ active: currentStep > 2 }"></div>
        <div class="step" :class="{ active: currentStep >= 3, completed: currentStep > 3 }">
          <div class="step-number">3</div>
          <span>资源偏好</span>
        </div>
      </div>

      <div class="content-area">
        <div v-if="currentStep === 1" class="step-content">
          <h2>你的基础水平如何？</h2>

          <div class="question-section">
            <h4>1. 你在{{ directionInfo.name }}方向的整体水平是？</h4>
            <div class="level-options">
              <div
                v-for="level in levelOptions"
                :key="level.value"
                class="level-card"
                :class="{ selected: formData.overallLevel === level.value }"
                @click="selectLevel(level.value)"
              >
                <div class="level-icon" :style="{ background: level.color }">
                  <el-icon :size="28" color="white">{{ level.icon }}</el-icon>
                </div>
                <h4>{{ level.label }}</h4>
                <p>{{ level.description }}</p>
              </div>
            </div>
          </div>

          <div class="question-section">
            <h4>2. 请评估你对以下技能的掌握程度（0-100分）</h4>
            <div class="skills-rating">
              <div v-for="skill in currentSkills" :key="skill.name" class="skill-item">
                <div class="skill-info">
                  <span class="skill-name">{{ skill.name }}</span>
                  <span class="skill-score">{{ skill.confidence }}%</span>
                </div>
                <el-slider v-model="skill.confidence" :min="0" :max="100" />
              </div>
            </div>
          </div>
        </div>

        <div v-if="currentStep === 2" class="step-content">
          <h2>设定你的学习目标</h2>

          <div class="question-section">
            <h4>1. 短期目标（1-3个月）</h4>
            <el-input
              :model-value="formData.learningGoals?.shortTerm?.description"
              @update:model-value="(val: string) => { if (formData.learningGoals!.shortTerm) formData.learningGoals!.shortTerm.description = val }"
              type="textarea"
              :rows="3"
              placeholder="例如：掌握Vue3全家桶，完成2个实战项目"
            />
            <div class="goal-meta">
              <el-date-picker
                v-model="shortTermDeadline"
                type="date"
                placeholder="目标完成日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
              />
              <el-select :model-value="formData.learningGoals?.shortTerm?.targetLevel" @update:model-value="(val: string) => { if (formData.learningGoals!.shortTerm) formData.learningGoals!.shortTerm.targetLevel = val }" placeholder="目标水平">
                <el-option label="入门" value="入门" />
                <el-option label="初级" value="初级" />
                <el-option label="中级" value="中级" />
                <el-option label="中级+" value="中级+" />
                <el-option label="高级" value="高级" />
              </el-select>
            </div>
          </div>

          <div class="question-section">
            <h4>2. 中期目标（3-6个月）</h4>
            <el-input
              :model-value="formData.learningGoals?.midTerm?.description"
              @update:model-value="(val: string) => { if (formData.learningGoals!.midTerm) formData.learningGoals!.midTerm.description = val }"
              type="textarea"
              :rows="3"
              placeholder="例如：成为能够独立开发完整应用的前端工程师"
            />
            <el-input-number
              :model-value="formData.learningGoals?.midTerm?.projectsPlan"
              @update:model-value="(val: number) => { if (formData.learningGoals!.midTerm) formData.learningGoals!.midTerm.projectsPlan = val }"
              :min="1"
              :max="20"
              placeholder="计划完成项目数"
              style="width: 100%; margin-top: 16px;"
            />
          </div>

          <div class="question-section">
            <h4>3. 长期目标（6个月以上）</h4>
            <el-input
              :model-value="formData.learningGoals?.longTerm?.description"
              @update:model-value="(val: string) => { if (formData.learningGoals!.longTerm) formData.learningGoals!.longTerm.description = val }"
              type="textarea"
              :rows="3"
              placeholder="例如：成为高级前端工程师，向全栈发展"
            />
            <el-input
              :model-value="formData.learningGoals?.longTerm?.careerTarget"
              @update:model-value="(val: string) => { if (formData.learningGoals!.longTerm) formData.learningGoals!.longTerm.careerTarget = val }"
              placeholder="职业目标，例如：前端架构师"
              style="margin-top: 16px;"
            />
          </div>
        </div>

        <div v-if="currentStep === 3" class="step-content">
          <h2>选择你的学习偏好</h2>

          <div class="question-section">
            <h4>1. 你更喜欢的资源类型（请按喜好排序）</h4>
            <div class="sortable-list">
              <div
                v-for="(item, index) in resourceTypeOptions"
                :key="item.value"
                class="sort-item"
                draggable="true"
                @dragstart="dragStart($event, index)"
                @dragover="dragOver($event, index)"
                @drop="drop($event, index)"
              >
                <el-icon class="drag-handle"><Rank /></el-icon>
                <span class="order-number">{{ index + 1 }}</span>
                <el-icon class="type-icon">{{ item.icon }}</el-icon>
                <span>{{ item.label }}</span>
              </div>
            </div>
            <p class="hint">拖拽调整顺序，排在前面的更优先推荐</p>
          </div>

          <div class="question-section">
            <h4>2. 你偏好的学习内容难度？</h4>
            <div class="option-grid">
              <div
                v-for="diff in difficultyOptions"
                :key="diff.value"
                class="option-card"
                :class="{ selected: formData.resourcePreferences?.preferredDifficulty === diff.value }"
                @click="selectDifficulty(diff.value)"
              >
                <el-icon :size="28">{{ diff.icon }}</el-icon>
                <span>{{ diff.label }}</span>
              </div>
            </div>
          </div>

          <div class="question-section">
            <h4>3. 你喜欢什么类型的实战项目？（可多选）</h4>
            <div class="option-grid">
              <div
                v-for="type in projectTypeOptions"
                :key="type.value"
                class="option-card"
                :class="{ selected: selectedProjectTypes.includes(type.value) }"
                @click="toggleProjectType(type.value)"
              >
                <el-icon :size="28">{{ type.icon }}</el-icon>
                <span>{{ type.label }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="button-area">
        <el-button v-if="currentStep > 1" @click="prevStep">上一步</el-button>
        <el-button v-if="currentStep < 3" type="primary" @click="nextStep">下一步</el-button>
        <el-button v-if="currentStep === 3" type="primary" :loading="loading" @click="submitLocalProfile">
          开始学习之旅
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
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
  Medal,
  Star,
  Trophy,
  VideoPlay,
  Reading,
  EditPen,
  Rank,
  IceTea,
  VanillaLatte,
  Orange,
  CoffeeCup,
  TrendCharts as Trend,
  DataLine,
  ShoppingCart,
  Tools
} from '@element-plus/icons-vue'
import { profileApi } from '@/api/profile'
import type { LocalProfile, LearningDirection } from '@/api/profile'

const router = useRouter()
const route = useRoute()

const currentStep = ref(1)
const loading = ref(false)
const directionId = computed(() => route.params.directionId as string)
const shortTermDeadline = ref('')

const dragIndex = ref(-1)

const directionMap: Record<string, any> = {
  frontend: {
    id: 'frontend',
    name: '前端开发',
    iconComponent: Monitor,
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    skills: [
      { name: 'HTML/CSS', confidence: 50 },
      { name: 'JavaScript', confidence: 50 },
      { name: 'Vue3/React', confidence: 30 },
      { name: '工程化工具', confidence: 20 }
    ]
  },
  backend: {
    id: 'backend',
    name: '后端开发',
    iconComponent: Server,
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    skills: [
      { name: '编程语言', confidence: 50 },
      { name: '数据库', confidence: 40 },
      { name: 'API设计', confidence: 30 },
      { name: '系统设计', confidence: 20 }
    ]
  },
  algorithm: {
    id: 'algorithm',
    name: '算法',
    iconComponent: TrendCharts,
    color: 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
    skills: [
      { name: '数据结构', confidence: 50 },
      { name: '基础算法', confidence: 40 },
      { name: '动态规划', confidence: 20 },
      { name: '高级算法', confidence: 10 }
    ]
  },
  ai: {
    id: 'ai',
    name: '人工智能',
    iconComponent: ChatDotRound,
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    skills: [
      { name: '机器学习基础', confidence: 30 },
      { name: '深度学习', confidence: 20 },
      { name: 'Python编程', confidence: 50 },
      { name: '数学基础', confidence: 40 }
    ]
  }
}

const directionInfo = computed(() => {
  return directionMap[directionId.value] || directionMap.frontend
})

const currentSkills = computed(() => {
  return directionInfo.value.skills
})

const levelOptions = [
  { value: '入门', label: '入门', description: '零基础，刚开始接触', icon: Star, color: 'linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)' },
  { value: '初级', label: '初级', description: '了解基础，可以实现简单功能', icon: Medal, color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' },
  { value: '中级', label: '中级', description: '能够独立完成常见开发任务', icon: Trophy, color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)' },
  { value: '高级', label: '高级', description: '深入理解，能够解决复杂问题', icon: Trend, color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)' }
]

const resourceTypeOptions = [
  { value: '实战项目', label: '实战项目', icon: EditPen },
  { value: '视频课程', label: '视频课程', icon: VideoPlay },
  { value: '官方文档', label: '官方文档', icon: Document },
  { value: '技术博客', label: '技术博客', icon: Reading }
]

const difficultyOptions = [
  { value: '简单', label: '循序渐进', icon: IceTea },
  { value: '中等', label: '适中难度', icon: VanillaLatte },
  { value: '困难', label: '挑战自我', icon: Orange }
]

const projectTypeOptions = [
  { value: '管理后台', label: '管理后台', icon: Document },
  { value: '个人博客', label: '个人博客', icon: Reading },
  { value: '电商应用', label: '电商应用', icon: ShoppingCart },
  { value: '工具类应用', label: '工具应用', icon: Tools },
  { value: '社交应用', label: '社交应用', icon: ChatDotRound },
  { value: '游戏开发', label: '游戏开发', icon: Box }
]

const selectedProjectTypes = ref<string[]>([])

const formData = reactive<LocalProfile>({
  overallLevel: '入门',
  skills: [],
  knowledgeDistribution: {},
  prerequisites: {},
  learningGoals: {
    shortTerm: {
      description: '',
      targetLevel: '中级'
    },
    midTerm: {
      description: '',
      projectsPlan: 3
    },
    longTerm: {
      description: '',
      careerTarget: ''
    }
  },
  resourcePreferences: {
    typeRanking: ['实战项目', '视频课程', '官方文档', '技术博客'],
    preferredDifficulty: '中等',
    stylePreference: '案例驱动型',
    platforms: ['B站', '掘金', '官方文档'],
    projectTypes: []
  },
  achievements: {
    projects: [],
    certificates: [],
    competitions: [],
    contributions: [],
    otherAchievements: []
  }
})

const selectLevel = (value: string) => {
  formData.overallLevel = value
}

const selectDifficulty = (value: string) => {
  if (formData.resourcePreferences) {
    formData.resourcePreferences.preferredDifficulty = value
  }
}

const toggleProjectType = (value: string) => {
  const index = selectedProjectTypes.value.indexOf(value)
  if (index > -1) {
    selectedProjectTypes.value.splice(index, 1)
  } else {
    selectedProjectTypes.value.push(value)
  }
  if (formData.resourcePreferences) {
    formData.resourcePreferences.projectTypes = selectedProjectTypes.value
  }
}

const dragStart = (e: DragEvent, index: number) => {
  dragIndex.value = index
}

const dragOver = (e: DragEvent, index: number) => {
  e.preventDefault()
}

const drop = (e: DragEvent, index: number) => {
  e.preventDefault()
  if (dragIndex.value !== index && formData.resourcePreferences?.typeRanking) {
    const ranking = [...formData.resourcePreferences.typeRanking]
    const item = ranking.splice(dragIndex.value, 1)[0]
    ranking.splice(index, 0, item)
    formData.resourcePreferences.typeRanking = ranking
    dragIndex.value = -1
  }
}

const nextStep = () => {
  if (currentStep.value < 3) {
    currentStep.value++
  }
}

const prevStep = () => {
  if (currentStep.value > 1) {
    currentStep.value--
  }
}

const submitLocalProfile = async () => {
  loading.value = true
  try {
    formData.skills = currentSkills.value
    if (shortTermDeadline.value && formData.learningGoals?.shortTerm) {
      formData.learningGoals.shortTerm.deadline = shortTermDeadline.value
    }
    if (formData.resourcePreferences) {
      formData.resourcePreferences.projectTypes = selectedProjectTypes.value
    }
    await profileApi.saveLocalProfile(directionId.value, formData)
    await profileApi.completeProfileSetup(directionId.value)
    ElMessage.success('学习画像建立完成！')
    router.push('/home')
  } catch (error) {
    ElMessage.error('保存失败，请重试')
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  try {
    const existing = await profileApi.getLocalProfile(directionId.value)
    if (existing) {
      Object.assign(formData, existing)
    }
  } catch (error) {
    console.log('No existing local profile')
  }
})
</script>

<style scoped>
.local-profile-setup {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.container {
  width: 100%;
  max-width: 800px;
  background: white;
  border-radius: 20px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
}

.header {
  text-align: center;
  margin-bottom: 32px;
}

.direction-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 20px;
  border-radius: 50px;
  color: white;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 16px;
}

.header h1 {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 8px;
}

.subtitle {
  color: #6b7280;
  margin: 0;
}

.progress-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 40px;
}

.step {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.step-number {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #e5e7eb;
  color: #9ca3af;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 14px;
  transition: all 0.3s;
}

.step.active .step-number,
.step.completed .step-number {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.step span {
  font-size: 13px;
  color: #9ca3af;
}

.step.active span,
.step.completed span {
  color: #667eea;
  font-weight: 500;
}

.step-line {
  width: 60px;
  height: 2px;
  background: #e5e7eb;
  margin: 0 10px 20px;
}

.step-line.active {
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
}

.step-content h2 {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 24px;
  text-align: center;
}

.question-section {
  margin-bottom: 32px;
}

.question-section h4 {
  font-size: 16px;
  font-weight: 600;
  color: #374151;
  margin: 0 0 16px;
}

.level-options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.level-card {
  padding: 24px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
  text-align: center;
}

.level-card:hover {
  border-color: #c7d2fe;
  background: #f5f3ff;
}

.level-card.selected {
  border-color: #667eea;
  background: linear-gradient(135deg, #f5f3ff 0%, #ede9fe 100%);
}

.level-icon {
  width: 56px;
  height: 56px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16px;
}

.level-card h4 {
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 4px;
}

.level-card p {
  font-size: 13px;
  color: #6b7280;
  margin: 0;
}

.skills-rating {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.skill-item {
  padding: 16px;
  background: #f9fafb;
  border-radius: 12px;
}

.skill-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.skill-name {
  font-size: 15px;
  font-weight: 600;
  color: #374151;
}

.skill-score {
  font-size: 14px;
  font-weight: 600;
  color: #667eea;
}

.goal-meta {
  display: flex;
  gap: 12px;
  margin-top: 16px;
}

.goal-meta .el-date-picker,
.goal-meta .el-select {
  flex: 1;
}

.sortable-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.sort-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 16px;
  background: #f9fafb;
  border-radius: 10px;
  border: 2px solid transparent;
  transition: all 0.3s;
}

.sort-item:hover {
  border-color: #c7d2fe;
  background: #f5f3ff;
}

.drag-handle {
  color: #9ca3af;
  cursor: grab;
}

.drag-handle:active {
  cursor: grabbing;
}

.order-number {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: #667eea;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 600;
}

.type-icon {
  color: #667eea;
}

.hint {
  font-size: 13px;
  color: #9ca3af;
  margin-top: 12px;
  text-align: center;
}

.option-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  gap: 12px;
}

.option-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 16px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s;
  text-align: center;
}

.option-card:hover {
  border-color: #c7d2fe;
  background: #f5f3ff;
}

.option-card.selected {
  border-color: #667eea;
  background: linear-gradient(135deg, #f5f3ff 0%, #ede9fe 100%);
}

.option-card .el-icon {
  color: #6b7280;
}

.option-card.selected .el-icon {
  color: #667eea;
}

.button-area {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 40px;
  padding-top: 24px;
  border-top: 1px solid #f3f4f6;
}
</style>
