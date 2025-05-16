import { create } from 'zustand'
import { UserInfo } from '@/types/auth'

interface UserState {
  userInfo: UserInfo | null
  setUserInfo: (user: UserInfo) => void
  clearUserInfo: () => void
}

export const useUserStore = create<UserState>(set => ({
  userInfo: null,
  setUserInfo: user => set({ userInfo: user }),
  clearUserInfo: () => set({ userInfo: null }),
}))

// useUserStore.subscribe(state => {
//   console.log('📦 Zustand 상태 변경됨:', state.userInfo)
// })
