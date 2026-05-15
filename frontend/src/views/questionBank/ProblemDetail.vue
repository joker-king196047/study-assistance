<template>
  <MainLayout>
    <div class="problem-detail-page">
      <div v-if="loading" class="loading-state">
        <el-icon :size="32" class="is-loading"><Loading /></el-icon>
        <p>正在加载题目...</p>
      </div>

      <template v-else-if="question">
        <div class="page-header">
          <div class="back-btn" @click="goBack">
            <el-icon><ArrowLeft /></el-icon>
            <span>返回题目列表</span>
          </div>
        </div>

        <div class="question-container">
          <div class="question-main">
            <div class="question-header">
              <span class="type-tag" :class="question.type">{{ typeLabel(question.type) }}</span>
              <span class="difficulty-tag" :class="question.difficulty">{{ difficultyLabel(question.difficulty) }}</span>
              <span class="score-tag">{{ question.score }}分</span>
            </div>

            <h2 class="question-content">{{ question.content }}</h2>

            <div class="answer-area">
              <template v-if="question.type === 'single'">
                <div class="option-label">请选择正确答案：</div>
                <div
                  v-for="(opt, idx) in parsedOptions"
                  :key="idx"
                  class="option-item"
                  :class="{
                    selected: userAnswer === String.fromCharCode(65 + idx),
                    correct: submitted && String.fromCharCode(65 + idx) === question.answer,
                    wrong: submitted && userAnswer === String.fromCharCode(65 + idx) && userAnswer !== question.answer
                  }"
                  @click="!submitted && (userAnswer = String.fromCharCode(65 + idx))"
                >
                  <span class="option-letter">{{ String.fromCharCode(65 + idx) }}</span>
                  <span class="option-text">{{ opt }}</span>
                  <el-icon v-if="submitted && String.fromCharCode(65 + idx) === question.answer" class="result-icon correct"><Check /></el-icon>
                  <el-icon v-if="submitted && userAnswer === String.fromCharCode(65 + idx) && userAnswer !== question.answer" class="result-icon wrong"><Close /></el-icon>
                </div>
              </template>

              <template v-else-if="question.type === 'multiple'">
                <div class="option-label">请选择所有正确答案（多选）：</div>
                <div
                  v-for="(opt, idx) in parsedOptions"
                  :key="idx"
                  class="option-item"
                  :class="{
                    selected: multipleAnswers.includes(String.fromCharCode(65 + idx)),
                    correct: submitted && question.answer.split(',').map((s: string) => s.trim()).includes(String.fromCharCode(65 + idx)),
                    wrong: submitted && multipleAnswers.includes(String.fromCharCode(65 + idx)) && !question.answer.split(',').map((s: string) => s.trim()).includes(String.fromCharCode(65 + idx))
                  }"
                  @click="!submitted && toggleMultiple(String.fromCharCode(65 + idx))"
                >
                  <span class="option-letter">{{ String.fromCharCode(65 + idx) }}</span>
                  <span class="option-text">{{ opt }}</span>
                  <el-icon v-if="submitted && question.answer.split(',').map((s: string) => s.trim()).includes(String.fromCharCode(65 + idx))" class="result-icon correct"><Check /></el-icon>
                  <el-icon v-if="submitted && multipleAnswers.includes(String.fromCharCode(65 + idx)) && !question.answer.split(',').map((s: string) => s.trim()).includes(String.fromCharCode(65 + idx))" class="result-icon wrong"><Close /></el-icon>
                </div>
              </template>

              <template v-else-if="question.type === 'judge'">
                <div class="option-label">请判断对错：</div>
                <div class="judge-options">
                  <div
                    class="option-item judge-item"
                    :class="{
                      selected: userAnswer === '对',
                      correct: submitted && question.answer === '对',
                      wrong: submitted && userAnswer === '对' && question.answer !== '对'
                    }"
                    @click="!submitted && (userAnswer = '对')"
                  >
                    <span class="option-letter">T</span>
                    <span class="option-text">正确</span>
                  </div>
                  <div
                    class="option-item judge-item"
                    :class="{
                      selected: userAnswer === '错',
                      correct: submitted && question.answer === '错',
                      wrong: submitted && userAnswer === '错' && question.answer !== '错'
                    }"
                    @click="!submitted && (userAnswer = '错')"
                  >
                    <span class="option-letter">F</span>
                    <span class="option-text">错误</span>
                  </div>
                </div>
              </template>

              <template v-else-if="question.type === 'fill'">
                <div class="option-label">请填写答案：</div>
                <el-input
                  v-model="userAnswer"
                  placeholder="请输入答案"
                  :disabled="submitted"
                  size="large"
                />
              </template>

              <template v-else-if="question.type === 'essay'">
                <div class="option-label">请写出你的解答过程：</div>
                <el-input
                  v-model="userAnswer"
                  type="textarea"
                  :rows="8"
                  placeholder="请输入详细解答过程..."
                  :disabled="submitted"
                />
              </template>
            </div>

            <div class="action-bar" v-if="!submitted">
              <el-button type="primary" size="large" :disabled="!hasAnswer" @click="handleSubmit" :loading="submitting">
                提交答案
              </el-button>
            </div>

            <div class="result-card" v-if="submitted">
              <div class="result-header" :class="submitResult?.isCorrect ? 'correct' : 'wrong'">
                <el-icon :size="24">
                  <Check v-if="submitResult?.isCorrect" />
                  <Close v-else />
                </el-icon>
                <span>{{ submitResult?.isCorrect ? '回答正确！' : '回答错误' }}</span>
                <span class="result-score">+{{ submitResult?.scoreEarned }}分</span>
              </div>
              <div class="result-answer">
                <span class="label">正确答案：</span>
                <span class="value">{{ submitResult?.correctAnswer }}</span>
              </div>
              <div class="result-explanation" v-if="submitResult?.explanation">
                <span class="label">解析：</span>
                <span class="value">{{ submitResult.explanation }}</span>
              </div>
              <div class="result-actions">
                <el-button @click="goBack">返回题目列表</el-button>
                <el-button type="primary" @click="nextQuestion">下一题</el-button>
              </div>
            </div>
          </div>
        </div>
      </template>
    </div>
  </MainLayout>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getQuestionById, submitAnswer, type QuestionItem, type SubmitResult } from '@/api/questionBank'
import MainLayout from '@/layouts/MainLayout.vue'
import { ArrowLeft, Loading, Check, Close } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const bankId = Number(route.params.bankId)
const questionId = Number(route.params.id)

const question = ref<QuestionItem | null>(null)
const loading = ref(true)
const userAnswer = ref('')
const multipleAnswers = ref<string[]>([])
const submitted = ref(false)
const submitting = ref(false)
const submitResult = ref<SubmitResult | null>(null)

const parsedOptions = computed(() => {
  if (!question.value?.options) return []
  try {
    return JSON.parse(question.value.options)
  } catch {
    return []
  }
})

const hasAnswer = computed(() => {
  if (question.value?.type === 'multiple') return multipleAnswers.value.length > 0
  return userAnswer.value.trim() !== ''
})

onMounted(async () => {
  try {
    const res = await getQuestionById(questionId)
    question.value = res as any
  } catch (e) {
    console.error('加载题目失败', e)
  } finally {
    loading.value = false
  }
})

function toggleMultiple(letter: string) {
  const idx = multipleAnswers.value.indexOf(letter)
  if (idx >= 0) {
    multipleAnswers.value.splice(idx, 1)
  } else {
    multipleAnswers.value.push(letter)
  }
}

async function handleSubmit() {
  if (!hasAnswer.value || submitted.value) return
  submitting.value = true

  const finalAnswer = question.value?.type === 'multiple'
    ? multipleAnswers.value.sort().join(',')
    : userAnswer.value

  const userId = getUserId()
  try {
    const res = await submitAnswer(userId, questionId, finalAnswer)
    submitResult.value = res as any
    submitted.value = true
  } catch (e) {
    console.error('提交失败', e)
  } finally {
    submitting.value = false
  }
}

function getUserId(): number {
  try {
    const userStr = localStorage.getItem('user')
    if (userStr) {
      const user = JSON.parse(userStr)
      return user.id || 1
    }
  } catch {}
  return 1
}

function goBack() {
  router.push({ name: 'ProblemList', query: { subCategory: `bank-${bankId}` } })
}

function nextQuestion() {
  router.push({ name: 'ProblemList', query: { subCategory: `bank-${bankId}` } })
}

function typeLabel(t: string) {
  const map: Record<string, string> = {
    single: '单选题', multiple: '多选题', judge: '判断题',
    fill: '填空题', essay: '解答题'
  }
  return map[t] || t
}

function difficultyLabel(d: string) {
  const map: Record<string, string> = { easy: '简单', medium: '中等', hard: '较难' }
  return map[d] || d
}
</script>

<style scoped>
.problem-detail-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 28px 24px;
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

.question-container {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
  padding: 28px;
}

.question-header {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
}

.type-tag, .difficulty-tag, .score-tag {
  font-size: 12px;
  padding: 3px 10px;
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
  font-size: 18px;
  font-weight: 600;
  color: #1a1a2e;
  line-height: 1.6;
  margin: 0 0 24px;
}

.answer-area {
  margin-bottom: 24px;
}

.option-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 12px;
  font-weight: 500;
}

.option-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 16px;
  border-radius: 10px;
  border: 2px solid #e8e8e8;
  margin-bottom: 10px;
  cursor: pointer;
  transition: all 0.2s;
}

.option-item:hover:not(.correct):not(.wrong) {
  border-color: #667eea;
  background: #f8f9ff;
}

.option-item.selected:not(.correct):not(.wrong) {
  border-color: #667eea;
  background: #eef0ff;
}

.option-item.correct {
  border-color: #4caf50;
  background: #e8f5e9;
}

.option-item.wrong {
  border-color: #f44336;
  background: #fce4ec;
}

.option-letter {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 600;
  color: #666;
  flex-shrink: 0;
}

.selected .option-letter {
  background: #667eea;
  color: #fff;
}

.correct .option-letter {
  background: #4caf50;
  color: #fff;
}

.wrong .option-letter {
  background: #f44336;
  color: #fff;
}

.option-text {
  flex: 1;
  font-size: 14px;
  color: #333;
}

.result-icon {
  flex-shrink: 0;
}

.result-icon.correct { color: #4caf50; }
.result-icon.wrong { color: #f44336; }

.judge-options {
  display: flex;
  gap: 16px;
}

.judge-item {
  flex: 1;
  justify-content: center;
}

.action-bar {
  display: flex;
  justify-content: center;
  padding-top: 8px;
}

.action-bar .el-button {
  min-width: 200px;
  height: 44px;
  font-size: 16px;
  border-radius: 22px;
}

.result-card {
  margin-top: 20px;
  background: #f8f9ff;
  border-radius: 12px;
  padding: 20px;
  border: 1px solid #e8e8e8;
}

.result-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 16px;
}

.result-header.correct { color: #4caf50; }
.result-header.wrong { color: #f44336; }

.result-score {
  margin-left: auto;
  font-size: 16px;
  padding: 2px 12px;
  border-radius: 12px;
  background: #e8f5e9;
  color: #4caf50;
}

.result-answer, .result-explanation {
  margin-bottom: 10px;
  font-size: 14px;
  line-height: 1.6;
}

.result-answer .label, .result-explanation .label {
  font-weight: 600;
  color: #333;
}

.result-answer .value, .result-explanation .value {
  color: #555;
}

.result-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid #e8e8e8;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 14px;
  min-height: 300px;
}

.loading-state p {
  font-size: 14px;
  color: #999;
  margin: 0;
}
</style>
