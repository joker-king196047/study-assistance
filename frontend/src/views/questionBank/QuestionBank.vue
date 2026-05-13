<template>
  <div class="question-bank">
    <div class="page-header">
      <h1>题库中心</h1>
      <p>选择专业方向和知识点分类，开始刷题练习</p>
    </div>

    <div class="bank-layout">
      <aside class="major-sidebar">
        <div class="sidebar-title">学科门类</div>
        <div class="major-list">
          <div
            v-for="group in categories"
            :key="group.name"
            class="major-item"
            :class="{ active: activeMajor === group.name }"
            @click="selectMajor(group.name)"
          >
            <div class="major-icon">
              <el-icon :size="18">
                <component :is="getMajorIcon(group.icon)" />
              </el-icon>
            </div>
            <div class="major-info">
              <span class="major-name">{{ group.name }}</span>
              <span class="major-count">{{ group.subCategories.length }} 个专业类</span>
            </div>
          </div>
        </div>
      </aside>

      <main class="subject-area">
        <div v-if="loading" class="empty-hint">
          <el-icon :size="32" class="is-loading"><Loading /></el-icon>
          <p>正在加载学科分类...</p>
        </div>

        <div v-else-if="loadError" class="empty-hint error-hint">
          <el-icon :size="64" color="#f56c6c"><WarningFilled /></el-icon>
          <p>加载失败：{{ loadError }}</p>
          <el-button size="small" type="primary" @click="retryLoad">重新加载</el-button>
        </div>

        <div v-else-if="!activeMajor" class="empty-hint">
          <el-icon :size="80" color="#e0e0e0"><ArrowLeft /></el-icon>
          <p>请先在左侧选择一个学科门类</p>
        </div>

        <template v-else>
          <div class="subject-area-header">
            <h2>{{ activeMajor }}</h2>
            <span class="subject-count">{{ currentSubjects.length }} 个专业类</span>
          </div>

          <div class="subject-grid">
            <div
              v-for="sub in currentSubjects"
              :key="sub.id"
              class="subject-card"
              @click="goToProblemList(sub.id, sub.name)"
            >
              <div class="subject-card-icon">
                <el-icon :size="24">
                  <Document v-if="sub.id.startsWith('cs-') || sub.id === 'philosophy' || sub.id === 'law' || sub.id === 'history'" />
                  <DataAnalysis v-else-if="sub.id.startsWith('econ-') || sub.id.startsWith('mgmt-') || sub.id === 'statistics'" />
                  <Connection v-else-if="sub.id.startsWith('edu-') || sub.id === 'journalism'" />
                  <Cpu v-else-if="sub.id.startsWith('eng-') || sub.id === 'math' || sub.id === 'physics'" />
                  <Setting v-else-if="sub.id.startsWith('med-') || sub.id === 'chemistry' || sub.id === 'biology'" />
                  <Monitor v-else-if="sub.id.startsWith('agri-') || sub.id === 'geography'" />
                  <EditPen v-else-if="sub.id.startsWith('art-') || sub.id === 'chinese-lang' || sub.id === 'foreign-lang'" />
                  <EditPen v-else />
                </el-icon>
              </div>
              <div class="subject-card-body">
                <h3>{{ sub.name }}</h3>
                <p>{{ sub.description }}</p>
              </div>
              <div class="subject-card-footer">
                <span class="question-count">{{ sub.questionCount }} 题</span>
                <el-icon :size="16"><ArrowRight /></el-icon>
              </div>
            </div>
          </div>
        </template>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategories, type CategoryGroup } from '@/api/questionBank'
import {
  Monitor, EditPen, Document, Cpu, Connection, Setting,
  DataAnalysis, ArrowRight, ArrowLeft, Loading, WarningFilled
} from '@element-plus/icons-vue'

const router = useRouter()
const categories = ref<CategoryGroup[]>([])
const activeMajor = ref('')
const loading = ref(true)
const loadError = ref('')

const currentSubjects = computed(() => {
  const group = categories.value.find(g => g.name === activeMajor.value)
  return group ? group.subCategories : []
})

function getMajorIcon(icon: string) {
  const map: Record<string, any> = {
    'philosophy': DataAnalysis,
    'economics': DataAnalysis,
    'law': Document,
    'education': Connection,
    'literature': EditPen,
    'history': Document,
    'science': Cpu,
    'engineering': Monitor,
    'agriculture': Setting,
    'medicine': Setting,
    'management': DataAnalysis,
    'art': EditPen
  }
  return map[icon] || Document
}

onMounted(() => {
  fetchCategories()
})

function selectMajor(name: string) {
  activeMajor.value = name
}

function retryLoad() {
  loadError.value = ''
  loading.value = true
  fetchCategories()
}

function fetchCategories() {
  getCategories()
    .then(res => {
      const data = (res && res.data) ? res.data : res
      if (Array.isArray(data)) {
        categories.value = data as unknown as CategoryGroup[]
      } else {
        throw new Error('数据格式不正确')
      }
      if (categories.value.length > 0) {
        activeMajor.value = categories.value[0].name
      }
    })
    .catch((e: any) => {
      loadError.value = e?.message || '获取分类失败'
    })
    .finally(() => {
      loading.value = false
    })
}

function goToProblemList(subCategoryId: string, subCategoryName: string) {
  router.push({
    name: 'ProblemList',
    query: { subCategory: subCategoryId, name: subCategoryName }
  })
}
</script>

<style scoped>
.question-bank {
  max-width: 1200px;
  margin: 0 auto;
  padding: 28px 24px;
}

.page-header {
  text-align: center;
  margin-bottom: 28px;
}

.page-header h1 {
  font-size: 28px;
  font-weight: 700;
  color: #1a1a2e;
  margin: 0 0 6px;
}

.page-header p {
  font-size: 14px;
  color: #888;
  margin: 0;
}

.bank-layout {
  display: flex;
  gap: 20px;
  align-items: flex-start;
}

.major-sidebar {
  width: 210px;
  flex-shrink: 0;
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
  overflow: hidden;
}

.sidebar-title {
  padding: 14px 18px 10px;
  font-size: 13px;
  font-weight: 600;
  color: #999;
  border-bottom: 1px solid #f0f0f0;
}

.major-list {
  max-height: calc(100vh - 220px);
  overflow-y: auto;
  padding: 6px;
}

.major-list::-webkit-scrollbar {
  width: 4px;
}

.major-list::-webkit-scrollbar-thumb {
  background: #e0e0e0;
  border-radius: 2px;
}

.major-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 11px 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.15s;
  margin-bottom: 1px;
}

.major-item:hover {
  background: #f8f9ff;
}

.major-item.active {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.08) 100%);
  border: 1px solid rgba(102, 126, 234, 0.18);
}

.major-icon {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  flex-shrink: 0;
}

.major-item.active .major-icon {
  box-shadow: 0 3px 10px rgba(102, 126, 234, 0.3);
}

.major-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 1px;
}

.major-name {
  font-size: 13px;
  font-weight: 600;
  color: #333;
}

.major-count {
  font-size: 11px;
  color: #999;
}

.subject-area {
  flex: 1;
  min-height: 400px;
}

.subject-area-header {
  display: flex;
  align-items: baseline;
  gap: 12px;
  margin-bottom: 18px;
  padding-bottom: 10px;
  border-bottom: 2px solid #f0f0f0;
}

.subject-area-header h2 {
  font-size: 19px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.subject-count {
  font-size: 13px;
  color: #999;
}

.subject-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 12px;
}

.subject-card {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 18px;
  background: #fff;
  border-radius: 10px;
  border: 1px solid #e8e8e8;
  cursor: pointer;
  transition: all 0.2s ease;
}

.subject-card:hover {
  border-color: #667eea;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.1);
  transform: translateY(-1px);
}

.subject-card-icon {
  width: 42px;
  height: 42px;
  border-radius: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.subject-card-body h3 {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin: 0 0 3px;
}

.subject-card-body p {
  font-size: 12px;
  color: #999;
  margin: 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.subject-card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 8px;
  border-top: 1px solid #f5f5f5;
}

.question-count {
  font-size: 12px;
  color: #667eea;
  background: rgba(102, 126, 234, 0.08);
  padding: 2px 10px;
  border-radius: 8px;
  font-weight: 500;
}

.subject-card-footer .el-icon {
  color: #ccc;
  transition: transform 0.2s;
}

.subject-card:hover .subject-card-footer .el-icon {
  transform: translateX(2px);
  color: #667eea;
}

.empty-hint {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 14px;
  min-height: 400px;
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e8e8e8;
}

.empty-hint p {
  font-size: 14px;
  color: #bbb;
  margin: 0;
}

.error-hint p {
  color: #f56c6c;
  font-size: 14px;
  margin: 8px 0;
}
</style>