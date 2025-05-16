import axios from 'axios'
import { authStore } from '@/store/useAuthStore'
import { refreshAccessToken } from '@/lib/api/auth'
import { setCookie } from 'cookies-next'

const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
  withCredentials: true, // 쿠키 기반 인증 시 true 설정
})

// 응답 인터셉터 (accessToken 만료 시 자동 재발급)
api.interceptors.response.use(
  res => res,
  async err => {
    const originalRequest = err.config
    if (
      err.response?.status === 401 &&
      !originalRequest._retry &&
      !originalRequest.url?.includes('/api/refresh-token')
    ) {
      originalRequest._retry = true

      try {
        const newAccessToken = await refreshAccessToken()

        authStore.getState().setAccessToken(newAccessToken)
        setCookie('accessToken', newAccessToken, {
          path: '/',
          maxAge: 60 * 60,
          sameSite: 'lax',
        })

        originalRequest.headers = originalRequest.headers || {}
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`
        return api(originalRequest)
      } catch (refreshErr) {
        console.error('토큰 재발급 실패:', refreshErr)

        window.location.href = '/login'
        return Promise.reject(refreshErr)
      }
    }

    return Promise.reject(err)
  }
)

export default api
