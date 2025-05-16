//로그인, 로그아웃, 회원가입
import { LoginForm } from '@/types/auth'
import api from '@/lib/api/axios'
import { AxiosError } from 'axios'
import { authStore } from '@/store/useAuthStore'
import { setCookie } from 'cookies-next'

// 로그인 axios
export const login = async ({ email, password }: LoginForm) => {
  try {
    const res = await api.post(
      '/api/login',
      { email, password },
      {
        withCredentials: true, // 쿠키 포함 필수
      }
    )
    const { accessToken } = res.data

    // zustand 메모리에 저장
    authStore.getState().setAccessToken(accessToken)

    // 쿠키 저장 (ssr 대응 일반 쿠키)
    setCookie('accessToken', accessToken, {
      path: '/',
      maxAge: 60 * 60, // 1시간
      sameSite: 'lax',
    })

    return { success: true }
  } catch (err: unknown) {
    let errorMessage = '로그인 실패'
    console.error('로그인 실패:', err)

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
export const refreshAccessToken = async (): Promise<string> => {
  try {
    const res = await api.post(
      `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/refresh-token`,
      {}
    )
    return res.data.accessToken
  } catch (err) {
    console.error('refreshToken 재발급 실패:', err)
    throw err // 상위에서 catch 가능하도록 rethrow
  }
}
