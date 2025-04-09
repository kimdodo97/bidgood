import React from 'react';
import LogoImg from '../../assets/bidgood_logo.png'; // 이미지 경로는 프로젝트 구조에 따라 다름
import KakaoImg from '../../assets/kakao_login.png';


interface ImportMetaEnv {
    VITE_KAKAO_CLIENT_ID: string;
    VITE_KAKAO_REDIRECT_URI: string;
}

interface LoginProps {
    
}

const Login: React.FC<LoginProps> = () => {
    const KAKAO_CLIENT_ID = import.meta.env.VITE_KAKAO_CLIENT_ID;
    const KAKAO_REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI;    
    
    const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
    
    const handleKakaoLogin = () => {
        
    }

    return (
      <div className="w-full min-h-screen flex flex-col justify-between items-center bg-white px-4 py-10">
        {/* 상단 로고 */}
        <div className='flex-col justify-center text-center'>
          <div className="w-[250px] h-[250px] flex items-center justify-center mt-40">
            <img src={LogoImg} alt="logo" className="w-full h-full object-cover" />
          </div>
          <div className='mb-4 text-2xl font-extrabold'>누구나 쉽게 비드굿</div>
          <div className='flex-col justify-center text-center text-sm space-y-1'>
            <div>건담,포토카드,앨범 무엇이든,</div>
            <div>지금부터 나만의 굿즈를 판매해보세요</div>
          </div>
        </div>

        {/* 하단 카카오 버튼 */}
        <div className=''>
        <a href={KAKAO_AUTH_URL}>
          <img src={KakaoImg} alt="카카오 로그인" className="w-[300px]" />
        </a>
        <div className='mt-3 text-center text-sm'>
          <div className='flex justify-center gap-2'><span className='text-gray-500'>계정이 없으신가요?</span><span className='font-bold'>둘러보기</span></div>
        </div>
        </div>
      </div>

    );
};

export default Login;
