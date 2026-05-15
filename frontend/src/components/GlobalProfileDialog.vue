<template>
  <el-dialog
    v-model="dialogVisible"
    title="完善个人信息"
    width="70%"
    :close-on-click-modal="false"
    :show-close="false"
    @close="handleClose"
  >
    <el-steps :active="currentStep" align-center class="step-bar">
      <el-step title="基本信息" />
      <el-step title="学习习惯" />
      <el-step title="行为偏好" />
      <el-step title="个人特质" />
    </el-steps>

    <el-form :model="formData" label-width="120px" class="profile-form">
      <div v-show="currentStep === 0">
        <el-divider content-position="left">个人基础信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="formData.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-radio-group v-model="formData.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年龄">
              <el-input-number v-model="formData.age" :min="1" :max="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学号/工号">
              <el-input v-model="formData.studentId" placeholder="请输入学号或工号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学校/单位">
              <el-input v-model="formData.school" placeholder="请输入学校或单位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业">
              <el-input v-model="formData.major" placeholder="请输入专业" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年级">
              <el-select v-model="formData.grade" placeholder="请选择年级">
                <el-option label="大一" value="大一" />
                <el-option label="大二" value="大二" />
                <el-option label="大三" value="大三" />
                <el-option label="大四" value="大四" />
                <el-option label="研一" value="研一" />
                <el-option label="研二" value="研二" />
                <el-option label="研三" value="研三" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学历层次">
              <el-select v-model="formData.educationLevel" placeholder="请选择学历">
                <el-option label="专科" value="专科" />
                <el-option label="本科" value="本科" />
                <el-option label="硕士" value="硕士" />
                <el-option label="博士" value="博士" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="入学时间">
              <el-date-picker
                v-model="formData.enrollmentDate"
                type="month"
                placeholder="选择入学月份"
                format="YYYY-MM"
                value-format="YYYY-MM"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预计毕业">
              <el-date-picker
                v-model="formData.expectedGraduation"
                type="month"
                placeholder="选择毕业月份"
                format="YYYY-MM"
                value-format="YYYY-MM"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="formData.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="formData.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div v-show="currentStep === 1">
        <el-divider content-position="left">学习时间习惯</el-divider>
        <el-form-item label="学习时段">
          <el-radio-group v-model="formData.preferredPeriod">
            <el-radio label="早晨型（6:00-10:00）" />
            <el-radio label="上午型（10:00-14:00）" />
            <el-radio label="下午型（14:00-18:00）" />
            <el-radio label="晚上型（18:00-24:00）" />
            <el-radio label="深夜型（0:00-6:00）" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="时段详情">
          <el-input v-model="formData.periodDetail" placeholder="例如：19:00-23:00" />
        </el-form-item>
        <el-form-item label="学习频率">
          <el-radio-group v-model="formData.learningFrequency">
            <el-radio label="每天学习" />
            <el-radio label="工作日学习" />
            <el-radio label="周末学习" />
            <el-radio label="间歇性学习" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="单次专注时长">
          <el-radio-group v-model="formData.focusDuration">
            <el-radio label="短（<30分钟）" />
            <el-radio label="中（30-60分钟）" />
            <el-radio label="长（60-90分钟）" />
            <el-radio label="超长（>90分钟）" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="专注时长">
          <el-input-number v-model="formData.focusMinutes" :min="1" :max="180" /> 分钟
        </el-form-item>
        <el-form-item label="学习周期规律">
          <el-radio-group v-model="formData.learningPattern">
            <el-radio label="持续稳定型" />
            <el-radio label="突击冲刺型" />
            <el-radio label="循序渐进型" />
            <el-radio label="劳逸结合型" />
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">学习环境偏好</el-divider>
        <el-form-item label="环境类型">
          <el-radio-group v-model="formData.environmentType">
            <el-radio label="安静环境（图书馆/自习室）" />
            <el-radio label="有背景音乐" />
            <el-radio label="咖啡店/公共空间" />
            <el-radio label="家里/宿舍" />
            <el-radio label="户外学习" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="具体地点">
          <el-input v-model="formData.location" placeholder="请描述具体地点" />
        </el-form-item>
        <el-form-item label="设备偏好">
          <el-radio-group v-model="formData.devicePreference">
            <el-radio label="电脑为主" />
            <el-radio label="平板为主" />
            <el-radio label="手机为主" />
            <el-radio label="多设备切换" />
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">学习方法偏好</el-divider>
        <el-form-item label="学习策略">
          <el-radio-group v-model="formData.learningStrategy">
            <el-radio label="系统学习型（从基础到深入）" />
            <el-radio label="问题驱动型（遇到问题再学）" />
            <el-radio label="实践导向型（边做边学）" />
            <el-radio label="理论研究型（深入原理）" />
            <el-radio label="混合策略型" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="记笔记习惯">
          <el-radio-group v-model="formData.noteTaking">
            <el-radio label="详细笔记型" />
            <el-radio label="思维导图型" />
            <el-radio label="要点记录型" />
            <el-radio label="电子笔记" />
            <el-radio label="纸质笔记" />
            <el-radio label="不记笔记" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="笔记工具">
          <el-input v-model="formData.noteTool" placeholder="请输入常用的笔记工具" />
        </el-form-item>
        <el-form-item label="复习习惯">
          <el-radio-group v-model="formData.reviewHabit">
            <el-radio label="定期复习" />
            <el-radio label="考前复习" />
            <el-radio label="按需复习" />
            <el-radio label="很少复习" />
          </el-radio-group>
        </el-form-item>
      </div>

      <div v-show="currentStep === 2">
        <el-divider content-position="left">内容类型偏好</el-divider>
        <el-form-item label="资源类型偏好">
          <el-checkbox-group v-model="contentRankingList">
            <el-checkbox
              v-for="(item, index) in contentOptions"
              :key="index"
              :label="item"
              :disabled="contentRankingList.length >= 6 && !contentRankingList.includes(item)"
            />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="内容风格偏好">
          <el-radio-group v-model="formData.contentStyle">
            <el-radio label="讲解详细型" />
            <el-radio label="简洁高效型" />
            <el-radio label="案例驱动型" />
            <el-radio label="理论深入型" />
            <el-radio label="趣味生动型" />
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">交互偏好</el-divider>
        <el-form-item label="提问方式">
          <el-radio-group v-model="formData.questionStyle">
            <el-radio label="频繁提问" />
            <el-radio label="思考后提问" />
            <el-radio label="先查资料再提问" />
            <el-radio label="很少提问" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="反馈偏好">
          <el-radio-group v-model="formData.feedbackPreference">
            <el-radio label="即时反馈" />
            <el-radio label="阶段性反馈" />
            <el-radio label="自主探索" />
            <el-radio label="引导式学习" />
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">进度偏好</el-divider>
        <el-form-item label="学习节奏">
          <el-radio-group v-model="formData.learningPace">
            <el-radio label="快速推进型" />
            <el-radio label="稳扎稳打型" />
            <el-radio label="快慢结合型" />
            <el-radio label="随性学习型" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="完成度要求">
          <el-radio-group v-model="formData.completeness">
            <el-radio label="完美主义（100%掌握）" />
            <el-radio label="实用为主（够用就行）" />
            <el-radio label="兼顾平衡" />
            <el-radio label="快速浏览" />
          </el-radio-group>
        </el-form-item>
      </div>

      <div v-show="currentStep === 3">
        <el-divider content-position="left">性格与学习风格</el-divider>
        <el-form-item label="性格倾向">
          <el-radio-group v-model="formData.personalityType">
            <el-radio label="外向型（喜欢讨论、协作）" />
            <el-radio label="内向型（喜欢独立思考）" />
            <el-radio label="中间型" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="认知风格">
          <el-radio-group v-model="formData.cognitiveStyle">
            <el-radio label="视觉型（图表、图像）" />
            <el-radio label="听觉型（听讲解、讨论）" />
            <el-radio label="动觉型（动手实践）" />
            <el-radio label="阅读型（文字、文档）" />
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">学习动机</el-divider>
        <el-form-item label="主要动机">
          <el-checkbox-group v-model="motivationList">
            <el-checkbox label="学术成绩" />
            <el-checkbox label="就业准备" />
            <el-checkbox label="兴趣爱好" />
            <el-checkbox label="技能提升" />
            <el-checkbox label="竞赛准备" />
            <el-checkbox label="自我提升" />
          </el-checkbox-group>
        </el-form-item>

        <el-divider content-position="left">自我认知（能力自评）</el-divider>
        <el-form-item label="编程能力">
          <el-slider v-model="formData.programmingLevel" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="数学基础">
          <el-slider v-model="formData.mathLevel" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="英语水平">
          <el-slider v-model="formData.englishLevel" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="学习能力">
          <el-slider v-model="formData.learningAbility" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="解决问题能力">
          <el-slider v-model="formData.problemSolvingLevel" :min="0" :max="100" show-input />
        </el-form-item>
      </div>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button v-if="currentStep > 0" @click="previousStep">上一步</el-button>
        <el-button v-if="currentStep < 3" type="primary" @click="nextStep">下一步</el-button>
        <el-button v-if="currentStep === 3" type="primary" :loading="loading" @click="submitForm">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { ElMessage } from 'element-plus';
import { globalProfileAPI } from '@/api/globalProfile';

interface ProfileData {
  name?: string;
  gender?: string;
  age?: number;
  studentId?: string;
  school?: string;
  major?: string;
  grade?: string;
  educationLevel?: string;
  enrollmentDate?: string;
  expectedGraduation?: string;
  email?: string;
  phone?: string;
  preferredPeriod?: string;
  periodDetail?: string;
  learningFrequency?: string;
  focusDuration?: string;
  focusMinutes?: number;
  learningPattern?: string;
  environmentType?: string;
  location?: string;
  devicePreference?: string;
  learningStrategy?: string;
  noteTaking?: string;
  noteTool?: string;
  reviewHabit?: string;
  contentRanking?: string;
  contentStyle?: string;
  questionStyle?: string;
  feedbackPreference?: string;
  learningPace?: string;
  completeness?: string;
  personalityType?: string;
  cognitiveStyle?: string;
  motivation?: string;
  programmingLevel?: number;
  mathLevel?: number;
  englishLevel?: number;
  learningAbility?: number;
  problemSolvingLevel?: number;
}

const props = defineProps<{
  visible: boolean;
}>();

const emit = defineEmits<{
  (e: 'update:visible', value: boolean): void;
  (e: 'success'): void;
}>();

const dialogVisible = ref(false);
const currentStep = ref(0);
const loading = ref(false);
const contentOptions = [
  '视频课程',
  '文字文档',
  '实战练习',
  '思维导图',
  '音频讲解',
  'PPT/课件'
];

const contentRankingList = ref<string[]>([]);
const motivationList = ref<string[]>([]);

const formData = ref<ProfileData>({
  programmingLevel: 50,
  mathLevel: 50,
  englishLevel: 50,
  learningAbility: 50,
  problemSolvingLevel: 50
});

watch(() => props.visible, (val) => {
  dialogVisible.value = val;
  if (val) {
    loadProfile();
  }
});

watch(dialogVisible, (val) => {
  emit('update:visible', val);
});

watch(contentRankingList, (val) => {
  formData.value.contentRanking = val.join(',');
}, { deep: true });

watch(motivationList, (val) => {
  formData.value.motivation = val.join(',');
}, { deep: true });

const loadProfile = async () => {
  try {
    const res = await globalProfileAPI.getProfile();
    if (res.data) {
      Object.assign(formData.value, res.data);
      if (res.data.contentRanking) {
        contentRankingList.value = res.data.contentRanking.split(',');
      }
      if (res.data.motivation) {
        motivationList.value = res.data.motivation.split(',');
      }
    }
  } catch (error) {
    console.error('加载画像失败', error);
  }
};

const nextStep = () => {
  if (currentStep.value < 3) {
    currentStep.value++;
  }
};

const previousStep = () => {
  if (currentStep.value > 0) {
    currentStep.value--;
  }
};

const submitForm = async () => {
  try {
    loading.value = true;
    const res = await globalProfileAPI.saveProfile(formData.value);
    if (res.code === 200) {
      ElMessage.success('画像保存成功');
      emit('success');
      handleClose();
    } else {
      ElMessage.error(res.msg || '保存失败');
    }
  } catch (error) {
    ElMessage.error('保存失败');
  } finally {
    loading.value = false;
  }
};

const handleClose = () => {
  dialogVisible.value = false;
  currentStep.value = 0;
};
</script>

<style scoped>
.step-bar {
  margin-bottom: 30px;
}

.profile-form {
  max-height: 500px;
  overflow-y: auto;
  padding-right: 10px;
}

:deep(.el-form-item) {
  margin-bottom: 18px;
}

:deep(.el-divider) {
  margin: 24px 0 18px 0;
}
</style>
