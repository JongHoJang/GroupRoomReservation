// import { NextApiRequest, NextApiResponse } from 'next'
//
// export default async function handler(req: NextApiRequest, res: NextApiResponse) {
//   const refreshToken = req.cookies['refreshToken']
//
//   if (!refreshToken) {
//     return res.status(401).json({ message: 'Refresh token not found' })
//   }
//
//   try {
//     const payload = verifyRefreshToken(refreshToken)
//     const newAccessToken = issueAccessToken(payload)
//     return res.status(200).json({ accessToken: newAccessToken })
//   } catch {
//     return res.status(401).json({ message: 'Invalid refresh token' })
//   }
// }
