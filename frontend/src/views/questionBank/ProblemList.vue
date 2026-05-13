<template>
  <div class="problem-list-page">
    <div class="page-header">
      <div class="back-btn" @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon>
        <span>返回题库</span>
      </div>
      <h1>{{ categoryName }}</h1>
      <p>共 {{ problems.length }} 道题目</p>
    </div>

    <div class="filter-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索题目名称或PID..."
        :prefix-icon="Search"
        clearable
        class="search-input"
      />
      <el-select v-model="difficultyFilter" placeholder="难度筛选" clearable class="filter-select">
        <el-option label="入门" value="入门" />
        <el-option label="普及-" value="普及-" />
        <el-option label="普及/提高-" value="普及/提高-" />
        <el-option label="提高+/省选-" value="提高+/省选-" />
      </el-select>
    </div>

    <div class="problem-table-wrapper">
      <table class="problem-table" v-if="filteredProblems.length > 0">
        <thead>
          <tr>
            <th class="col-pid">题号</th>
            <th class="col-title">题目名称</th>
            <th class="col-difficulty">难度</th>
            <th class="col-source">来源</th>
            <th class="col-action">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="problem in filteredProblems"
            :key="problem.id"
            @click="goToDetail(problem.pid)"
            class="problem-row"
          >
            <td class="col-pid">
              <span class="pid-tag">{{ problem.pid }}</span>
            </td>
            <td class="col-title">{{ problem.title }}</td>
            <td class="col-difficulty">
              <span class="difficulty-tag" :class="getDifficultyClass(problem.difficulty)">
                {{ problem.difficulty }}
              </span>
            </td>
            <td class="col-source">{{ problem.source }}</td>
            <td class="col-action">
              <el-button type="primary" size="small" @click.stop="goToDetail(problem.pid)">
                开始刷题
              </el-button>
            </td>
          </tr>
        </tbody>
      </table>

      <div v-else class="empty-state">
        <el-icon :size="64" color="#ddd"><Document /></el-icon>
        <p>暂无题目数据</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProblems, type Question } from '@/api/questionBank'
import { ArrowLeft, Search, Document } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const categoryName = ref(route.query.name as string || '题目列表')
const subCategory = route.query.subCategory as string
const problems = ref<Question[]>([])
const searchKeyword = ref('')
const difficultyFilter = ref('')

const filteredProblems = computed(() => {
  let result = problems.value
  if (searchKeyword.value) {
    const kw = searchKeyword.value.toLowerCase()
    result = result.filter(p =>
      p.title.toLowerCase().includes(kw) || p.pid.toLowerCase().includes(kw)
    )
  }
  if (difficultyFilter.value) {
    result = result.filter(p => p.difficulty === difficultyFilter.value)
  }
  return result
})

onMounted(async () => {
  try {
    const res = await getProblems(subCategory)
    problems.value = res.data as unknown as Question[]
  } catch (e) {
    console.error('获取题目列表失败', e)
  }
})

function goToDetail(pid: string) {
  router.push({ name: 'ProblemDetail', params: { pid } })
}

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
.problem-list-page {
  max-width: 1100px;
  margin: 0 auto;
  padding: 32px 24px;
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

.back-btn:hover {
  color: #764ba2;
}

.page-header h1 {
  font-size: 28px;
  font-weight: 700;
  color: #1a1a2e;
  margin: 0 0 4px;
}

.page-header p {
  font-size: 14px;
  color: #999;
  margin: 0;
}

.filter-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.search-input {
  width: 320px;
}

.filter-select {
  width: 160px;
}

.problem-table-wrapper {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
  overflow: hidden;
}

.problem-table {
  width: 100%;
  border-collapse: collapse;
}

.problem-table thead {
  background: #fafafa;
}

.problem-table th {
  padding: 14px 16px;
  text-align: left;
  font-size: 13px;
  font-weight: 600;
  color: #666;
  border-bottom: 1px solid #e8e8e8;
}

.problem-table td {
  padding: 14px 16px;
  font-size: 14px;
  color: #333;
  border-bottom: 1px solid #f0f0f0;
}

.problem-row {
  cursor: pointer;
  transition: background 0.2s;
}

.problem-row:hover {
  background: #f8f9ff;
}

.col-pid { width: 120px; }
.col-title { }
.col-difficulty { width: 120px; }
.col-source { width: 100px; }
.col-action { width: 120px; }

.pid-tag {
  font-family: 'Consolas', 'Monaco', monospace;
  font-size: 13px;
  color: #667eea;
  background: rgba(102, 126, 234, 0.08);
  padding: 2px 8px;
  border-radius: 4px;
}

.difficulty-tag {
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 10px;
}

.difficulty-tag.easy {
  background: #e8f5e9;
  color: #4caf50;
}

.difficulty-tag.normal {
  background: #fff3e0;
  color: #ff9800;
}

.difficulty-tag.medium {
  background: #fce4ec;
  color: #e91e63;
}

.difficulty-tag.hard {
  background: #f3e5f5;
  color: #9c27b0;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  color: #ccc;
}

.empty-state p {
  margin-top: 16px;
  font-size: 16px;
}
</style>