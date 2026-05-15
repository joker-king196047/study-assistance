import request from './request'
import type { AxiosPromise } from 'axios'

export interface CategoryGroup {
  name: string
  icon: string
  subCategories: SubCategory[]
}

export interface SubCategory {
  id: string
  name: string
  description: string
  questionCount: number
}

export interface Question {
  id: number
  bankId: number
  type: string
  content: string
  options: string
  answer: string
  explanation: string
  difficulty: string
  score: number
}

export type QuestionItem = Question

export interface SubmitResult {
  isCorrect: boolean
  scoreEarned: number
  correctAnswer: string
  explanation?: string
}

export interface BankDetail {
  bank: {
    id: number
    name: string
    description: string
  }
  totalQuestions: number
  singleCount: number
  multipleCount: number
  judgeCount: number
  fillCount: number
  essayCount: number
}

export const getCategories = (): AxiosPromise<CategoryGroup[]> => {
  return request({
    url: '/api/question-bank/categories',
    method: 'get'
  })
}

export const getQuestions = (bankId: number, type?: string): AxiosPromise<Question[]> => {
  const params = type ? { type } : {}
  return request({
    url: `/api/question-bank/questions/${bankId}`,
    method: 'get',
    params
  })
}

export const getBankDetail = (bankId: number): AxiosPromise<BankDetail> => {
  return request({
    url: `/api/question-bank/bank/${bankId}`,
    method: 'get'
  })
}

export const submitAnswer = (userId: number, questionId: number, answer: string): AxiosPromise<SubmitResult> => {
  return request({
    url: '/api/question-bank/submit',
    method: 'post',
    data: { userId, questionId, answer }
  })
}

export const getQuestionById = (questionId: number): AxiosPromise<Question> => {
  return request({
    url: `/api/question-bank/question/${questionId}`,
    method: 'get'
  })
}
