'use client'

import React, { useEffect, useState } from 'react'
import InputField from '../../_component/InputField'
import SubmitButton from '@/app/_component/button/SubmitButton'
import { login } from '@/lib/api/auth'
import { useRouter } from 'next/navigation'
import { useUserStore } from '@/store/useUserStore'
import { deleteCookie } from 'cookies-next'

const LoginForm = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [isLoading, setIsLoading] = useState(false)

  const router = useRouter()

  const { clearUserInfo } = useUserStore()

  // 로그인화면 진입 시, 로그아웃 로직 실행
  useEffect(() => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    deleteCookie('accessToken', { path: '/' })
    clearUserInfo()
  }, [])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)
    try {
      const result = await login({ email, password })
      if (result.success) {
        router.replace('/main')
      } else {
        alert(result.error || '로그인에 실패했습니다.')
        setIsLoading(false)
      }
    } catch (err) {
      if (err instanceof Error) {
        alert(err.message)
      } else {
        alert('로그인 중 오류 발생')
      }
      setIsLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <InputField
        label="이메일(아이디)"
        id="email"
        name="email"
        type="email"
        placeholder="이메일을 입력해주세요."
        onChange={e => setEmail(e.target.value)}
      />

      <InputField
        label="비밀번호"
        id="password"
        name="password"
        type="password"
        placeholder="비밀번호를 입력해주세요."
        onChange={e => setPassword(e.target.value)}
      />

      <div className="mt-8">
        {/*<SubmitButton buttonLabel={'로그인'} />*/}
        <SubmitButton
          buttonLabel={isLoading ? '로그인 중...' : '로그인'}
          disabled={isLoading}
        />
      </div>
    </form>
  )
}

export default LoginForm
