import type { Metadata } from 'next'
import { Geist, Geist_Mono } from 'next/font/google'
import './globals.css'
import Footer from '@/app/_component/Footer'
import { pretendard } from '@/app/_fonts/pretendard'
import Header from '@/app/_component/Header'
import { ReactQueryProvider } from '@/app/providers/ReactProvider'
import { AuthInitializer } from '@/app/_component/AuthInitializer'

const geistSans = Geist({
  variable: '--font-geist-sans',
  subsets: ['latin'],
})

const geistMono = Geist_Mono({
  variable: '--font-geist-mono',
  subsets: ['latin'],
})

export const metadata: Metadata = {
  title: '여기서 만나',
  description: '만나교회 청년부 소그룹실 예약 시스템입니다.',
  openGraph: {
    title: '여기서 만나',
    description: '만나교회 청년부 소그룹실 예약 시스템입니다.',
    url: 'https://group-room-reservation.vercel.app/',
    siteName: '여기서 만나',
    images: [
      {
        url: 'https://group-room-reservation.vercel.app/thumbnail.png',
        width: 1200,
        height: 630,
        alt: '만청 소그룹실 예약 시스템 썸네일',
      },
    ],
    type: 'website',
  },
}

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="ko" className={pretendard.className}>
      <head>
        <title>여기서 만나</title>
        <meta
          name="description"
          content="만나교회 청년부 소그룹실 예약 시스템입니다."
        />
        <meta property="og:title" content="여기서 만나" />
        <meta
          property="og:description"
          content="만나교회 청년부 소그룹실 예약 시스템입니다."
        />
        <meta
          property="og:url"
          content="https://group-room-reservation.vercel.app/"
        />
        <meta property="og:site_name" content="여기서 만나" />
        <meta
          property="og:image"
          content="https://group-room-reservation.vercel.app/thumbnail.png"
        />
        <meta property="og:image:width" content="1200" />
        <meta property="og:image:height" content="630" />
        <meta property="og:type" content="website" />
      </head>
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased font-pretendard min-h-screen flex flex-col`}
      >
        <ReactQueryProvider>
          <AuthInitializer>
            <Header />
            <main className="flex-grow">{children}</main>
            <Footer />
          </AuthInitializer>
        </ReactQueryProvider>
      </body>
    </html>
  )
}
