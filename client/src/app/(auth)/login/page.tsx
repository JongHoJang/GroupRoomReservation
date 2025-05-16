import LoginForm from '@/app/(auth)/login/_component/LoginForm'
import SignUpButton from '@/app/(auth)/login/_component/SignUpButton'
import LoginPageContext from '@/app/(auth)/login/_component/LoginPageContext'

const LoginPage = async () => {
  return (
    <>
      <div className="w-full py-10">
        <div className="px-4 sm:px-0">
          <LoginPageContext />
          <div className="flex flex-col mx-auto gap-4 mt-10 sm:mt-14 max-w-[380px]">
            <LoginForm />
            <SignUpButton />
          </div>
        </div>
      </div>
    </>
  )
}

export default LoginPage
