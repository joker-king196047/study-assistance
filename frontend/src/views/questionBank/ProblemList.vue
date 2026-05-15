<template>
  <MainLayout>
    <div class="problem-list-page">
      <div class="page-header">
        <div class="back-btn" @click="$router.push('/question-bank')">
          <el-icon><ArrowLeft /></el-icon>
          <span>返回课程列表</span>
        </div>
        <h1>{{ bankDetail?.bank?.name || '题目列表' }}</h1>
        <p>{{ bankDetail?.bank?.description }}</p>
      </div>

      <div v-if="loading" class="loading-state">
        <el-icon :size="32" class="is-loading"><Loading /></el-icon>
        <p>正在加载...</p>
      </div>

      <template v-else>
        <div class="type-selector" v-if="bankDetail">
          <div class="type-label">选择题型</div>
          <div class="type-options">
            <div
              class="type-chip"
              :class="{ active: selectedType === '' }"
              @click="selectType('')"
            >
              全部题目
              <span class="type-count">{{ bankDetail.totalQuestions }}</span>
            </div>
            <div
              v-if="bankDetail.singleCount > 0"
              class="type-chip"
              :class="{ active: selectedType === 'single' }"
              @click="selectType('single')"
            >
              单选题
              <span class="type-count">{{ bankDetail.singleCount }}</span>
            </div>
            <div
              v-if="bankDetail.multipleCount > 0"
              class="type-chip"
              :class="{ active: selectedType === 'multiple' }"
              @click="selectType('multiple')"
            >
              多选题
              <span class="type-count">{{ bankDetail.multipleCount }}</span>
            </div>
            <div
              v-if="bankDetail.judgeCount > 0"
              class="type-chip"
              :class="{ active: selectedType === 'judge' }"
              @click="selectType('judge')"
            >
              判断题
              <span class="type-count">{{ bankDetail.judgeCount }}</span>
            </div>
            <div
              v-if="bankDetail.fillCount > 0"
              class="type-chip"
              :class="{ active: selectedType === 'fill' }"
              @click="selectType('fill')"
            >
              填空题
              <span class="type-count">{{ bankDetail.fillCount }}</span>
            </div>
            <div
              v-if="bankDetail.essayCount > 0"
              class="type-chip"
              :class="{ active: selectedType === 'essay' }"
              @click="selectType('essay')"
            >
              解答题
              <span class="type-count">{{ bankDetail.essayCount }}</span>
            </div>
          </div>
        </div>

        <div class="question-list" v-if="questions.length > 0">
          <div
            v-for="(q, index) in questions"
            :key="q.id"
            class="question-card"
            @click="goToQuestion(q)"
          >
            <div class="question-index">{{ index + 1 }}</div>
            <div class="question-body">
              <div class="question-meta">
                <span class="type-tag" :class="q.type">{{ typeLabel(q.type) }}</span>
                <span class="difficulty-tag" :class="q.difficulty">{{ difficultyLabel(q.difficulty) }}</span>
                <span class="score-tag">{{ q.score }}分</span>
              </div>
              <h3 class="question-content">{{ q.content }}</h3>
            </div>
            <div class="question-action">
              <el-button type="primary" size="small" @click.stop="goToQuestion(q)">
                {{ q.type === 'essay' ? '开始作答' : '开始答题' }}
              </el-button>
            </div>
          </div>
        </div>

        <div v-else class="empty-state">
          <el-icon :size="64" color="#ddd"><Document /></el-icon>
          <p>暂无该类型的题目</p>
        </div>
      </template>
    </div>
  </MainLayout>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getBankDetail, getQuestions, type BankDetail, type QuestionItem } from '@/api/questionBank'
import MainLayout from '@/layouts/MainLayout.vue'
import { ArrowLeft, Loading, Document } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const subCategory = route.query.subCategory as string
const bankId = Number(subCategory.replace('bank-', ''))

const bankDetail = ref<BankDetail | null>(null)
const questions = ref<QuestionItem[]>([])
const selectedType = ref('')
const loading = ref(true)

onMounted(async () => {
  try {
    const detailRes = await getBankDetail(bankId)
    bankDetail.value = detailRes as any
    await loadQuestions()
  } catch (e) {
    console.error('加载失败', e)
  } finally {
    loading.value = false
  }
})

watch(selectedType, () => { loadQuestions() })

function selectType(type: string) {
  selectedType.value = type
}

async function loadQuestions() {
  try {
    const res = await getQuestions(bankId, selectedType.value || undefined)
    questions.value = res as any
  } catch (e) {
    console.error('获取题目失败', e)
  }
}

function goToQuestion(q: QuestionItem) {
  router.push({
    name: 'ProblemDetail',
    params: { bankId, id: q.id }
  })
}

function typeLabel(t: string) {
  const map: Record<string, string> = {
    single: '单选', multiple: '多选', judge: '判断',
    fill: '填空', essay: '解答'
  }
  return map[t] || t
}

function difficultyLabel(d: string) {
  const map: Record<string, string> = { easy: '简单', medium: '中等', hard: '较难' }
  return map[d] || d
}
</script>

<style scoped>
.problem-list-page {
  max-width: 900px;
  margin: 0 auto;
  padding: 28px 24px;
}

.page-header {
  margin-bottom: 24px;
}

.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #667eea;
  cursor: pointer;
  font-size: 14px;
  margin-bottom: 12px;
  transition: color 0.2s;
}

.back-btn:hover { color: #764ba2; }

.page-header h1 {
  font-size: 24px;
  font-weight: 700;
  color: #1a1a2e;
  margin: 0 0 4px;
}

.page-header p {
  font-size: 14px;
  color: #999;
  margin: 0;
}

.type-selector {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
  padding: 18px 20px;
  margin-bottom: 20px;
}

.type-label {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin-bottom: 12px;
}

.type-options {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.type-chip {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 13px;
  color: #666;
  background: #f5f7fa;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid transparent;
}

.type-chip:hover {
  background: #eef0ff;
  color: #667eea;
}

.type-chip.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border-color: transparent;
}

.type-chip.active .type-count {
  background: rgba(255,255,255,0.25);
  color: #fff;
}

.type-count {
  font-size: 11px;
  background: #e0e0e0;
  color: #666;
  padding: 1px 8px;
  border-radius: 10px;
  font-weight: 500;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.question-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 20px;
  background: #fff;
  border-radius: 10px;
  border: 1px solid #e8e8e8;
  cursor: pointer;
  transition: all 0.2s;
}

.question-card:hover {
  border-color: #667eea;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.08);
}

.question-index {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  flex-shrink: 0;
}

.question-body {
  flex: 1;
  min-width: 0;
}

.question-meta {
  display: flex;
  gap: 8px;
  margin-bottom: 6px;
}

.type-tag, .difficulty-tag, .score-tag {
  font-size: 11px;
  padding: 1px 8px;
  border-radius: 8px;
  font-weight: 500;
}

.type-tag.single { background: #e3f2fd; color: #1976d2; }
.type-tag.multiple { background: #f3e5f5; color: #7b1fa2; }
.type-tag.judge { background: #e8f5e9; color: #388e3c; }
.type-tag.fill { background: #fff3e0; color: #f57c00; }
.type-tag.essay { background: #fce4ec; color: #c62828; }

.difficulty-tag.easy { background: #e8f5e9; color: #4caf50; }
.difficulty-tag.medium { background: #fff3e0; color: #ff9800; }
.difficulty-tag.hard { background: #fce4ec; color: #e91e63; }

.score-tag { background: #f5f7fa; color: #999; }

.question-content {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.question-action {
  flex-shrink: 0;
}

.loading-state, .empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 14px;
  min-height: 300px;
}

.loading-state p, .empty-state p {
  font-size: 14px;
  color: #999;
  margin: 0;
}
</style>
