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

export const getCategories = (): AxiosPromise<CategoryGroup[]> => {
  return request({
    url: '/api/question-bank/categories',
    method: 'get'
  })
}

export const getQuestions = (bankId: string): AxiosPromise<Question[]> => {
  return request({
    url: `/api/question-bank/questions/${bankId}`,
    method: 'get'
  })
}

export const getQuestionById = (questionId: number): AxiosPromise<Question> => {
  return request({
    url: `/api/question-bank/question/${questionId}`,
    method: 'get'
  })
}
