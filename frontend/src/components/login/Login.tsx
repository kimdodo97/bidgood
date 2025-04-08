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
        <div>
            <a href={KAKAO_AUTH_URL} className="kakaobtn">
                <img src={KakaoImg} />
            </a>    
        </div>
    );
};

export default Login;
