import React from 'react'
import { deleteCookie } from 'cookies-next'
import { useRouter } from 'next/navigation'

interface Props {
  modalTitle: string
  modalDescription: string
  onClose: () => void
}

export default function LogoutModal({
  modalTitle,
  modalDescription,
  onClose,
}: Props) {
  const router = useRouter()

  const handleLogout = () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    deleteCookie('accessToken', { path: '/' })

    onClose() // 모달 먼저 닫고
    router.push('/login') // 페이지 이동
  }

  return (
    <>
      <div className="text-center text-main-d-black">
        <h2 className="text-xl font-bold mb-6">{modalTitle}</h2>
        <div className="mb-4 w-[320px] mx-auto">
          <p className="mb-4">{modalDescription}</p>
        </div>
        <div className="w-full max-w-[300px] mx-auto">
          <div className="flex flex-col mt-5">
            <button
              type="button"
              className="w-full h-[60px] bg-main-d-black rounded-[4px] text-white text-sm font-bold hover:bg-[#444]"
              onClick={handleLogout}
            >
              확인
            </button>
          </div>
        </div>
      </div>
    </>
  )
}
