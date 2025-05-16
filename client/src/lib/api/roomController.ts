// 방 정보 api

import api from '@/lib/api/axios'
import { authStore } from '@/store/useAuthStore'

const accessToken = authStore.getState().accessToken

export const fetchRoomInfoClient = async () => {
  try {
    const res = await api.get(
      `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/room/reservation/info`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    )

    return res.data
  } catch {
    throw new Error('방 정보 불러오기 실패')
  }
}

// 이번주에 당첨된 사람들 리스트 api
export const usingRoomUserList = async () => {
  const res = await api.get('/api/room/users', {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  })
  return res.data
}

// 방 예약 api
export const reservationRoom = async (roomId: number) => {
  const res = await api.post('/api/room/reserve', null, {
    params: {
      roomId,
    },
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  })
  return res.data
}

// [
//   {
//     "roomId": 0,
//     "name": "string",
//     "floor": "string",
//     "personAffordableCount": 0,
//     "groupAffordableCount": 0,
//     "availableStatus": "UNAVAILABLE",
//     "sittingType": "CHAIR",
//     "reservationCount": 0
//   }
// ]
