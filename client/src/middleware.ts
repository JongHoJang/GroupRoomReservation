import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export function middleware(request: NextRequest) {
  const accessToken = request.cookies.get('accessToken')?.value
  const { pathname } = request.nextUrl

  // 1. 로그인 상태인데 / auth 관련 페이지로 접근하면 리디렉션
  if (accessToken && (pathname === '/login' || pathname === '/signup')) {
    return NextResponse.redirect(new URL('/main', request.url))
  }

  // 2. 비로그인 상태로 접근하면 로그인으로 리디렉션 > login 으로
  const protectedPaths = [
    '/main',
    '/apply',
    '/application-overview',
    '/check-winner',
  ]
  const isProtected = protectedPaths.some(path => pathname.startsWith(path))

  if (!accessToken && isProtected) {
    return NextResponse.redirect(new URL('/login', request.url))
  }

  // 통과
  return NextResponse.next()
}

export const config = {
  matcher: [
    '/main/:path*',
    '/apply/:path*',
    '/application-overview/:path*',
    '/check-winner/:path*',
    '/login/:path*',
    '/signup/:path*',
  ],
}
