import request from './request'

export interface BasicInfo {
  name?: string
  gender?: string
  age?: number
  studentId?: string
  school?: string
  major?: string
  grade?: string
  educationLevel?: string
  enrollmentDate?: string
  expectedGraduation?: string
  email?: string
  phone?: string
}

export interface LearningHabits {
  timePreference?: {
    preferredPeriod?: string
    periodDetail?: string
    frequency?: string
    focusDuration?: string
    focusMinutes?: number
    pattern?: string
  }
  environment?: {
    type?: string
    location?: string
    devicePreference?: string
  }
  method?: {
    strategy?: string
    noteTaking?: string
    noteTool?: string
    reviewHabit?: string
  }
}

export interface BehaviorPreferences {
  contentType?: {
    ranking?: string[]
    style?: string
  }
  interaction?: {
    questionStyle?: string
    feedbackPreference?: string
  }
  progress?: {
    pace?: string
    completeness?: string
  }
}

export interface PersonalTraits {
  personality?: {
    type?: string
    cognitiveStyle?: string
  }
  motivation?: string[]
  selfAssessment?: {
    programming?: number
    math?: number
    english?: number
    learningAbility?: number
    problemSolving?: number
  }
}

export interface GlobalProfile {
  basicInfo?: BasicInfo
  learningHabits?: LearningHabits
  behaviorPreferences?: BehaviorPreferences
  personalTraits?: PersonalTraits
}

<<<<<<< HEAD
export interface DirectionSkill {
  name: string
  level: string
  confidence: number
}

export interface LocalProfile {
  overallLevel?: string
  skills?: DirectionSkill[]
  knowledgeDistribution?: Record<string, number>
  prerequisites?: Record<string, string>
  completedNodes?: number
  totalNodes?: number
  completeness?: number
  totalHours?: number
  startDate?: string
  currentStage?: string
  pace?: string
  strengths?: string[]
  weaknesses?: string[]
  preferredStack?: string[]
  avoidedTech?: string[]
  growthTrend?: string
  learningGoals?: {
    shortTerm?: {
      description?: string
      deadline?: string
      targetLevel?: string
    }
    midTerm?: {
      description?: string
      deadline?: string
      projectsPlan?: number
    }
    longTerm?: {
      description?: string
      careerTarget?: string
    }
  }
  resourcePreferences?: {
    typeRanking?: string[]
    preferredDifficulty?: string
    stylePreference?: string
    platforms?: string[]
    projectTypes?: string[]
  }
  achievements?: {
    projects?: Array<{
      name: string
      description?: string
      link?: string
      date?: string
    }>
    certificates?: any[]
    competitions?: any[]
    contributions?: any[]
    otherAchievements?: any[]
  }
}

=======
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
export interface LearningDirection {
  id: string
  name: string
  description: string
  icon: string
}

export interface CheckFirstLearningResponse {
  isFirstTime: boolean
  hasGlobalProfile: boolean
  activeDirections: string[]
}

const API_BASE = '/api/profile'

export const profileApi = {
  checkFirstLearning() {
    return request.get<CheckFirstLearningResponse>(`${API_BASE}/check-first`)
  },

  getGlobalProfile() {
    return request.get<GlobalProfile>(`${API_BASE}/global`)
  },

  saveGlobalProfile(data: GlobalProfile) {
    return request.post(`${API_BASE}/global`, data)
  },

  getLearningDirections() {
    return request.get<LearningDirection[]>(`${API_BASE}/directions`)
  },

<<<<<<< HEAD
  getLocalProfile(directionId: string) {
    return request.get<LocalProfile>(`${API_BASE}/local/${directionId}`)
  },

  saveLocalProfile(directionId: string, data: LocalProfile) {
    return request.post(`${API_BASE}/local/${directionId}`, data)
  },

  completeProfileSetup(directionId: string) {
    return request.post(`${API_BASE}/complete-setup`, { directionId })
  }
}
=======
  completeProfileSetup(directionId: string) {
    return request.post(`${API_BASE}/complete-setup`, { directionId })
  }
}
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
