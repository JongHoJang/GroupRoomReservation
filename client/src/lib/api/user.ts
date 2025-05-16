// // 유저 관련 api

// 로그인 한 유저 정보
import api from '@/lib/api/axios'

export const getUserUsage = async () => {
  try {
    const res = await api.get('/api/user/usage')
    return res.data
  } catch (err) {
    console.error('사용자 정보 불러오기 실패:', err)
    return null
  }
}

//  "userName": "string",
// "applicationStartTime": "2025-04-07T10:31:15.589Z",
//  "applicationDeadline": "2025-04-01T23:52:00.247Z",
//  "announcementTime": "2025-04-01T23:52:00.247Z",
//  "useDate": "2025-04-01",
//  "status": "NOT_APPLIED",
//  "roomName": "string"
