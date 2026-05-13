import request from './request'
import type { AxiosPromise } from 'axios'

export interface SubCategory {
  id: string
  name: string
  description: string
  questionCount: number
}

export interface CategoryGroup {
  name: string
  icon: string
  subCategories: SubCategory[]
}

export interface Question {
  id: number
  pid: string
  title: string
  description: string
  category: string
  subCategory: string
  difficulty: string
  tags: string
  sampleInput: string
  sampleOutput: string
  acceptedCount: number
  submissionCount: number
  source: string
}

export interface SubmitCodeRequest {
  pid: string
  lang: string
  code: string
  o2: boolean
  trackId?: string
}

export interface SubmitResult {
  requestId: string
  status: string
}

export interface CompileResult {
  success: boolean
  message: string
  opt2: boolean
}

export interface TestCase {
  id: number
  status: number
  score: number
  time: number
  memory: number
  description: string
}

export interface SubTask {
  id: number
  status: number
  score: number
  cases: TestCase[]
}

export interface JudgeData {
  id: number
  status: number
  score: number
  time: number
  memory: number
  subtasks: SubTask[]
}

export interface JudgeResultData {
  compile: CompileResult
  judge: JudgeData
}

export interface JudgeResult {
  status: string
  type: string
  requestId: string
  trackId: string
  data: JudgeResultData
}

export const getCategories = (): AxiosPromise<CategoryGroup[]> => {
  return request({
    url: '/api/question-bank/categories',
    method: 'get'
  })
}

export const getProblems = (subCategory: string): AxiosPromise<Question[]> => {
  return request({
    url: '/api/question-bank/problems',
    method: 'get',
    params: { subCategory }
  })
}

export const getProblemDetail = (pid: string): AxiosPromise<Question> => {
  return request({
    url: `/api/question-bank/problem/${pid}`,
    method: 'get'
  })
}

export const submitCode = (data: SubmitCodeRequest): AxiosPromise<SubmitResult> => {
  return request({
    url: '/api/question-bank/submit',
    method: 'post',
    data
  })
}

export const getJudgeResult = (requestId: string): AxiosPromise<JudgeResult> => {
  return request({
    url: '/api/question-bank/judge-result',
    method: 'get',
    params: { requestId }
  })
}