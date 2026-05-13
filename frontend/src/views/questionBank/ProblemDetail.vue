<template>
  <div class="problem-detail-page">
    <div class="page-header">
      <div class="back-btn" @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon>
        <span>返回题目列表</span>
      </div>
      <div class="header-main">
        <div class="header-left">
          <span class="pid-badge">{{ problem?.pid }}</span>
          <h1>{{ problem?.title }}</h1>
          <span class="difficulty-tag" :class="getDifficultyClass(problem?.difficulty || '')">
            {{ problem?.difficulty }}
          </span>
        </div>
        <div class="header-right">
          <span class="source-tag">来源：{{ problem?.source }}</span>
        </div>
      </div>
    </div>

    <div class="content-area">
      <div class="panel-card">
        <h3>题目描述</h3>
        <div class="description-text">{{ problem?.description }}</div>
      </div>

      <div class="panel-card" v-if="problem?.sampleInput">
        <h3>输入样例</h3>
        <pre class="sample-block">{{ problem.sampleInput }}</pre>
      </div>

      <div class="panel-card" v-if="problem?.sampleOutput">
        <h3>输出样例</h3>
        <pre class="sample-block">{{ problem.sampleOutput }}</pre>
      </div>

      <div class="panel-card hint-card">
        <div class="hint-content">
          <el-icon :size="20" color="#667eea"><InfoFilled /></el-icon>
          <span>本题来自洛谷开放平台，可在洛谷官网在线提交评测</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getProblemDetail, type Question } from '@/api/questionBank'
import { ArrowLeft, InfoFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const pid = route.params.pid as string

const problem = ref<Question | null>(null)

onMounted(async () => {
  try {
    const res = await getProblemDetail(pid)
    problem.value = res.data as unknown as Question
  } catch (e) {
    console.error('获取题目详情失败', e)
    ElMessage.error('获取题目详情失败')
  }
})

function getDifficultyClass(difficulty: string) {
  const map: Record<string, string> = {
    '入门': 'easy',
    '普及-': 'normal',
    '普及/提高-': 'medium',
    '提高+/省选-': 'hard'
  }
  return map[difficulty] || 'normal'
}
</script>

<style scoped>
.problem-detail-page {
  max-width: 900px;
  margin: 0 auto;
  padding: 32px 24px;
}

.page-header {
  margin-bottom: 28px;
}

.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #667eea;
  cursor: pointer;
  font-size: 14px;
  margin-bottom: 14px;
  transition: color 0.2s;
}

.back-btn:hover {
  color: #764ba2;
}

.header-main {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 12px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.pid-badge {
  font-family: 'Consolas', monospace;
  font-size: 14px;
  color: #667eea;
  background: rgba(102, 126, 234, 0.1);
  padding: 4px 12px;
  border-radius: 6px;
}

.header-left h1 {
  font-size: 24px;
  font-weight: 700;
  color: #1a1a2e;
  margin: 0;
}

.difficulty-tag {
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 10px;
}

.difficulty-tag.easy { background: #e8f5e9; color: #4caf50; }
.difficulty-tag.normal { background: #fff3e0; color: #ff9800; }
.difficulty-tag.medium { background: #fce4ec; color: #e91e63; }
.difficulty-tag.hard { background: #f3e5f5; color: #9c27b0; }

.source-tag {
  font-size: 13px;
  color: #999;
}

.content-area {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.panel-card {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
  padding: 24px;
}

.panel-card h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0 0 14px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.description-text {
  font-size: 15px;
  line-height: 1.9;
  color: #444;
  white-space: pre-wrap;
}

.sample-block {
  background: #f8f9fa;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  padding: 14px 18px;
  font-family: 'Consolas', 'Monaco', monospace;
  font-size: 14px;
  color: #333;
  margin: 0;
  white-space: pre-wrap;
  overflow-x: auto;
}

.hint-card {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.04) 0%, rgba(118, 75, 162, 0.04) 100%);
  border-color: rgba(102, 126, 234, 0.15);
}

.hint-content {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  color: #888;
}
</style>