'use client'

import { useEffect, useState } from 'react'
import { authStore } from '@/store/useAuthStore'
import { setCookie } from 'cookies-next'
import api from '@/lib/api/axios'

export const AuthInitializer = ({
  children,
}: {
  children: React.ReactNode
}) => {
  const [checked, setChecked] = useState(false)

  useEffect(() => {
    const restoreAccessToken = async () => {
      const token = authStore.getState().accessToken
      if (!token) {
        try {
          const res = await api.post('/api/refresh-token')
          const { accessToken } = res.data

          authStore.getState().setAccessToken(accessToken)

          setCookie('accessToken', accessToken, {
            path: '/',
            maxAge: 60 * 60,
            sameSite: 'lax',
          })
        } catch {
          console.warn('refreshToken 인증 실패')
        }
      }
      setChecked(true)
    }

    restoreAccessToken()
  }, [])

  if (!checked) return null
  return <>{children}</>
}
