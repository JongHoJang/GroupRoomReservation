'use client'

import React, { useState } from 'react'
import InputField from '../../_component/InputField'
import SubmitButton from '@/app/_component/button/SubmitButton'
import { login } from '@/lib/api/auth'
import { useRouter } from 'next/navigation'

const LoginForm = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [isLoading, setIsLoading] = useState(false)

  const router = useRouter()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)
    try {
      const result = await login({ email, password })
      if (result.success) {
        setIsLoading(false)
        console.log('로그인 성공성공!!')
        // await new Promise(res => setTimeout(res, 800))
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
        <SubmitButton
          buttonLabel={isLoading ? '로그인 중...' : '로그인'}
          disabled={isLoading}
        />
      </div>
    </form>
  )
}

export default LoginForm
