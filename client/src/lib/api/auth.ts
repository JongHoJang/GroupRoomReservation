//로그인, 로그아웃, 회원가입
import { LoginForm } from '@/types/auth'
import api from '@/lib/api/axios'
import axios, { AxiosError } from 'axios'
import { setCookie } from 'cookies-next'

// 로그인 axios
export const login = async ({ email, password }: LoginForm) => {
  try {
    const res = await api.post('/api/login', { email, password })

    const { accessToken, refreshToken } = res.data

    // ✅ accessToken을 쿠키에 저장
    setCookie('accessToken', accessToken, {
      // maxAge: 60,
      path: '/',
    })

    localStorage.setItem('refreshToken', refreshToken) // 이건 필요하다면 유지

    return { success: true }
  } catch (err: unknown) {
    let errorMessage = '로그인 실패'

    if (err && typeof err === 'object' && 'response' in err) {
      const axiosErr = err as AxiosError<{ message: string }>
      errorMessage = axiosErr.response?.data?.message || errorMessage
    }

    return {
      success: false,
      error: errorMessage,
    }
  }
}

// 회원가입 axios
export const signUp = async (formData: {
  name: string
  birthday: string
  churchMemberId: number
  email: string
  password: string
  communityCode: string
}) => {
  const res = await api.post('/api/signUp', formData)
  return res.data
}

// refreshToken API
const rawAxios = axios.create()

export const refreshAccessToken = async () => {
  const refreshToken = localStorage.getItem('refreshToken')
  const res = await rawAxios.post(
    `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/refresh-token`,
    { refreshToken }
  )
  const { accessToken } = res.data
  localStorage.setItem('accessToken', accessToken)
  return accessToken
}
