import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Landing',
    component: () => import('@/views/Landing.vue'),
    meta: { requiresAuth: false }
  },


  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    meta: { requiresAuth: true }
  },

  
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/profile/start',
    name: 'ProfileFlow',
    component: () => import('@/views/profile/ProfileFlow.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile/global-setup',
    name: 'GlobalProfileSetup',
    component: () => import('@/views/profile/GlobalProfileSetup.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile/direction-select',
    name: 'DirectionSelect',
    component: () => import('@/views/profile/DirectionSelect.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile/local-setup/:directionId',
    name: 'LocalProfileSetup',
    component: () => import('@/views/profile/LocalProfileSetup.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/question-bank',
    name: 'QuestionBank',
    component: () => import('@/views/questionBank/QuestionBank.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/question-bank/problems',
    name: 'ProblemList',
    component: () => import('@/views/questionBank/ProblemList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/question-bank/:bankId/problem/:id',
    name: 'ProblemDetail',
    component: () => import('@/views/questionBank/ProblemDetail.vue'),
    meta: { requiresAuth: true }
  }
]




const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const token = userStore.token

  if (token && !userStore.userInfo) {
    try {
      await userStore.fetchUserInfo()
    } catch {
      userStore.logout()
    }
  }

  if (to.meta.requiresAuth && !token) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
  } else if ((to.name === 'Login' || to.name === 'Register') && token) {
    next({ name: 'Home' })
  } else {
    next()
  }
})

export default router
