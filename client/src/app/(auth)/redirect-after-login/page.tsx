// redirect-after-login.tsx
'use client'
import { useEffect } from 'react'
import { useRouter } from 'next/navigation'

const Page = () => {
  const router = useRouter()

  useEffect(() => {
    const timer = setTimeout(() => {
      router.replace('/main')
    }, 500) // 100~300ms 정도면 충분
    return () => clearTimeout(timer)
  }, [router])

  return <p>로그인 처리 중...</p>
}

export default Page
