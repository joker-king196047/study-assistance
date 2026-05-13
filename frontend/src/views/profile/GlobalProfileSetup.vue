<template>
  <div class="global-profile-setup">
    <div class="setup-container">
      <div class="progress-bar">
        <div class="step" :class="{ active: currentStep >= 1, completed: currentStep > 1 }">
          <div class="step-number">1</div>
          <span class="step-label">基本信息</span>
        </div>
        <div class="step-line" :class="{ active: currentStep > 1 }"></div>
        <div class="step" :class="{ active: currentStep >= 2, completed: currentStep > 2 }">
          <div class="step-number">2</div>
          <span class="step-label">学习习惯</span>
        </div>
        <div class="step-line" :class="{ active: currentStep > 2 }"></div>
        <div class="step" :class="{ active: currentStep >= 3, completed: currentStep > 3 }">
          <div class="step-number">3</div>
          <span class="step-label">偏好设置</span>
        </div>
      </div>

      <div class="content-area">
        <div v-if="currentStep === 1" class="step-content">
          <h2>欢迎！让我们了解一下你</h2>
          <p class="subtitle">填写基本信息，帮助我们更好地了解你</p>
          
          <el-form :model="formData.basicInfo" label-position="top" class="setup-form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="姓名">
                  <el-input v-model="formData.basicInfo.name" placeholder="请输入姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="性别">
                  <el-radio-group v-model="formData.basicInfo.gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="年龄">
                  <el-input-number v-model="formData.basicInfo.age" :min="1" :max="100" placeholder="年龄" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="学号">
                  <el-input v-model="formData.basicInfo.studentId" placeholder="请输入学号" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="学校">
                  <el-input v-model="formData.basicInfo.school" placeholder="请输入学校" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="专业">
                  <el-input v-model="formData.basicInfo.major" placeholder="请输入专业" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="年级">
                  <el-select v-model="formData.basicInfo.grade" placeholder="请选择年级">
                    <el-option label="大一" value="大一" />
                    <el-option label="大二" value="大二" />
                    <el-option label="大三" value="大三" />
                    <el-option label="大四" value="大四" />
                    <el-option label="研究生" value="研究生" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="学历">
                  <el-select v-model="formData.basicInfo.educationLevel" placeholder="请选择学历">
                    <el-option label="本科" value="本科" />
                    <el-option label="硕士" value="硕士" />
                    <el-option label="博士" value="博士" />
                    <el-option label="其他" value="其他" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="邮箱">
                  <el-input v-model="formData.basicInfo.email" placeholder="请输入邮箱" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="手机号">
                  <el-input v-model="formData.basicInfo.phone" placeholder="请输入手机号" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div v-if="currentStep === 2" class="step-content">
          <h2>你的学习习惯是怎样的？</h2>
          <p class="subtitle">了解你的学习偏好，为你推荐更合适的学习方式</p>

          <div class="question-section">
            <h4>1. 你通常在什么时段学习效率最高？</h4>
            <div class="option-grid">
              <div
                v-for="item in timePeriodOptions"
                :key="item.value"
                class="option-card"
                :class="{ selected: formData.learningHabits.timePreference?.preferredPeriod === item.value }"
                @click="selectTimePeriod(item.value)"
              >
                <el-icon :size="28">{{ item.icon }}</el-icon>
                <span>{{ item.label }}</span>
              </div>
            </div>
          </div>

          <div class="question-section">
            <h4>2. 你喜欢在什么样的环境中学习？</h4>
            <div class="option-grid">
              <div
                v-for="item in environmentOptions"
                :key="item.value"
                class="option-card"
                :class="{ selected: formData.learningHabits.environment?.type === item.value }"
                @click="selectEnvironment(item.value)"
              >
                <el-icon :size="28">{{ item.icon }}</el-icon>
                <span>{{ item.label }}</span>
              </div>
            </div>
          </div>

          <div class="question-section">
            <h4>3. 你通常的学习策略是？</h4>
            <div class="option-grid">
              <div
                v-for="item in strategyOptions"
                :key="item.value"
                class="option-card"
                :class="{ selected: formData.learningHabits.method?.strategy === item.value }"
                @click="selectStrategy(item.value)"
              >
                <el-icon :size="28">{{ item.icon }}</el-icon>
                <span>{{ item.label }}</span>
              </div>
            </div>
          </div>
        </div>

        <div v-if="currentStep === 3" class="step-content">
          <h2>选择你的学习偏好</h2>
          <p class="subtitle">这将帮助我们为你推荐更符合你口味的学习内容</p>

          <div class="question-section">
            <h4>1. 你更喜欢的学习资源类型（可多选）</h4>
            <div class="option-grid">
              <div
                v-for="item in contentTypeOptions"
                :key="item.value"
                class="option-card"
                :class="{ selected: selectedContentTypes.includes(item.value) }"
                @click="toggleContentType(item.value)"
              >
                <el-icon :size="28">{{ item.icon }}</el-icon>
                <span>{{ item.label }}</span>
              </div>
            </div>
          </div>

          <div class="question-section">
            <h4>2. 你的主要学习动机是？（可多选）</h4>
            <div class="option-grid">
              <div
                v-for="item in motivationOptions"
                :key="item.value"
                class="option-card"
                :class="{ selected: selectedMotivations.includes(item.value) }"
                @click="toggleMotivation(item.value)"
              >
                <el-icon :size="28">{{ item.icon }}</el-icon>
                <span>{{ item.label }}</span>
              </div>
            </div>
          </div>

          <div class="question-section">
            <h4>3. 请对自己的能力进行评估（0-100分）</h4>
            <div class="rating-grid">
              <div v-for="item in selfRatingItems" :key="item.key" class="rating-item">
                <span class="rating-label">{{ item.label }}</span>
                <el-slider :model-value="formData.personalTraits?.selfAssessment?.[item.key] ?? 50" @update:model-value="(val: number) => { if (formData.personalTraits!.selfAssessment) formData.personalTraits!.selfAssessment[item.key] = val }" :min="0" :max="100" show-input />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="button-area">
        <el-button v-if="currentStep > 1" @click="prevStep">上一步</el-button>
        <el-button v-if="currentStep < 3" type="primary" @click="nextStep">下一步</el-button>
        <el-button v-if="currentStep === 3" type="primary" :loading="loading" @click="submitGlobalProfile">
          完成并继续
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  Document,
  Sunrise,
  Sunny,
  Cloudy,
  Moon,
  Night,
  Reading,
  Microphone,
  Monitor,
  House,
  CoffeeCup,
  Baseball,
  Connection,
  ChatDotRound,
  Search,
  EditPen,
  TrendCharts,
  Trophy,
  Star,
  StarFilled,
  DataAnalysis,
  Briefcase,
  UserFilled,
  VideoPlay,
  Presenter
} from '@element-plus/icons-vue'
import type { GlobalProfile } from '@/api/profile'
import { profileApi } from '@/api/profile'

const router = useRouter()
const currentStep = ref(1)
const loading = ref(false)

const timePeriodOptions = [
  { value: '早晨型', label: '早晨 (6:00-10:00)', icon: Sunrise },
  { value: '上午型', label: '上午 (10:00-14:00)', icon: Cloudy },
  { value: '下午型', label: '下午 (14:00-18:00)', icon: Sunny },
  { value: '晚上型', label: '晚上 (18:00-24:00)', icon: Moon },
  { value: '深夜型', label: '深夜 (0:00-6:00)', icon: Night }
]

const environmentOptions = [
  { value: '安静环境', label: '安静的图书馆/自习室', icon: Reading },
  { value: '有背景音乐', label: '有点背景音乐', icon: Microphone },
  { value: '咖啡店', label: '咖啡店/公共空间', icon: CoffeeCup },
  { value: '家里', label: '家里/宿舍', icon: House },
  { value: '户外', label: '户外学习', icon: Baseball }
]

const strategyOptions = [
  { value: '系统学习型', label: '系统学习 - 从基础到深入', icon: Document },
  { value: '问题驱动型', label: '问题驱动 - 遇到问题再学', icon: Search },
  { value: '实践导向型', label: '实践导向 - 边做边学', icon: EditPen },
  { value: '理论研究型', label: '理论研究 - 深入原理', icon: Connection },
  { value: '混合策略型', label: '混合策略 - 多种方式结合', icon: TrendCharts }
]

const contentTypeOptions = [
  { value: '视频课程', label: '视频课程', icon: VideoPlay },
  { value: '文字文档', label: '文字文档', icon: Document },
  { value: '实战练习', label: '实战练习', icon: EditPen },
  { value: '思维导图', label: '思维导图', icon: Connection },
  { value: '音频讲解', label: '音频讲解', icon: Microphone },
  { value: 'PPT', label: 'PPT课件', icon: Presenter }
]

const motivationOptions = [
  { value: '学术成绩', label: '提升学术成绩', icon: Star },
  { value: '就业准备', label: '为就业做准备', icon: Briefcase },
  { value: '兴趣爱好', label: '纯粹兴趣爱好', icon: StarFilled },
  { value: '技能提升', label: '技能提升', icon: TrendCharts },
  { value: '竞赛准备', label: '参加竞赛', icon: Trophy },
  { value: '自我提升', label: '自我提升', icon: UserFilled }
]

const selfRatingItems = [
  { key: 'programming', label: '编程能力' },
  { key: 'math', label: '数学基础' },
  { key: 'english', label: '英语水平' },
  { key: 'learningAbility', label: '学习能力' },
  { key: 'problemSolving', label: '解决问题能力' }
]

const selectedContentTypes = ref<string[]>([])
const selectedMotivations = ref<string[]>([])

const formData = reactive<GlobalProfile>({
  basicInfo: {
    name: '',
    gender: '',
    age: undefined,
    studentId: '',
    school: '',
    major: '',
    grade: '',
    educationLevel: '',
    email: '',
    phone: ''
  },
  learningHabits: {
    timePreference: {
      preferredPeriod: '',
      frequency: '每天学习',
      focusDuration: '长',
      focusMinutes: 60,
      pattern: '劳逸结合型'
    },
    environment: {
      type: '',
      devicePreference: '电脑为主'
    },
    method: {
      strategy: '',
      noteTaking: '要点记录型',
      reviewHabit: '定期复习'
    }
  },
  behaviorPreferences: {
    contentType: {
      ranking: [],
      style: '讲解详细型'
    },
    interaction: {
      questionStyle: '思考后提问',
      feedbackPreference: '即时反馈'
    },
    progress: {
      pace: '稳扎稳打型',
      completeness: '兼顾平衡'
    }
  },
  personalTraits: {
    personality: {
      type: '中间型',
      cognitiveStyle: '视觉型'
    },
    motivation: [],
    selfAssessment: {
      programming: 50,
      math: 50,
      english: 50,
      learningAbility: 50,
      problemSolving: 50
    }
  }
})

const selectTimePeriod = (value: string) => {
  if (formData.learningHabits.timePreference) {
    formData.learningHabits.timePreference.preferredPeriod = value
  }
}

const selectEnvironment = (value: string) => {
  if (formData.learningHabits.environment) {
    formData.learningHabits.environment.type = value
  }
}

const selectStrategy = (value: string) => {
  if (formData.learningHabits.method) {
    formData.learningHabits.method.strategy = value
  }
}

const toggleContentType = (value: string) => {
  const index = selectedContentTypes.value.indexOf(value)
  if (index > -1) {
    selectedContentTypes.value.splice(index, 1)
  } else {
    selectedContentTypes.value.push(value)
  }
  if (formData.behaviorPreferences?.contentType) {
    formData.behaviorPreferences.contentType.ranking = selectedContentTypes.value
  }
}

const toggleMotivation = (value: string) => {
  const index = selectedMotivations.value.indexOf(value)
  if (index > -1) {
    selectedMotivations.value.splice(index, 1)
  } else {
    selectedMotivations.value.push(value)
  }
  if (formData.personalTraits) {
    formData.personalTraits.motivation = selectedMotivations.value
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

const submitGlobalProfile = async () => {
  loading.value = true
  try {
    await profileApi.saveGlobalProfile(formData)
    ElMessage.success('全局画像保存成功！')
    router.push('/profile/direction-select')
  } catch (error) {
    ElMessage.error('保存失败，请重试')
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  try {
    const existingProfile = await profileApi.getGlobalProfile()
    if (existingProfile) {
      Object.assign(formData, existingProfile)
    }
  } catch (error) {
    console.log('No existing profile found')
  }
})
</script>

<style scoped>
.global-profile-setup {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.setup-container {
  width: 100%;
  max-width: 900px;
  background: white;
  border-radius: 20px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
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
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #e5e7eb;
  color: #9ca3af;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 16px;
  transition: all 0.3s;
}

.step.active .step-number,
.step.completed .step-number {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.step-label {
  font-size: 14px;
  color: #9ca3af;
}

.step.active .step-label,
.step.completed .step-label {
  color: #667eea;
  font-weight: 500;
}

.step-line {
  width: 80px;
  height: 2px;
  background: #e5e7eb;
  margin: 0 10px 20px;
}

.step-line.active {
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
}

.content-area {
  min-height: 400px;
}

.step-content h2 {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 8px;
  text-align: center;
}

.subtitle {
  text-align: center;
  color: #6b7280;
  margin: 0 0 32px;
  font-size: 16px;
}

.setup-form {
  max-width: 600px;
  margin: 0 auto;
}

.question-section {
  margin-bottom: 32px;
}

.question-section h4 {
  font-size: 18px;
  font-weight: 600;
  color: #374151;
  margin: 0 0 16px;
}

.option-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 16px;
}

.option-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 20px 16px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
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

.option-card span {
  font-size: 14px;
  color: #4b5563;
}

.rating-grid {
  display: flex;
  flex-direction: column;
  gap: 24px;
  max-width: 500px;
  margin: 0 auto;
}

.rating-item {
  display: flex;
  align-items: center;
  gap: 20px;
}

.rating-label {
  width: 120px;
  font-size: 14px;
  color: #4b5563;
  font-weight: 500;
  flex-shrink: 0;
}

.rating-item .el-slider {
  flex: 1;
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
