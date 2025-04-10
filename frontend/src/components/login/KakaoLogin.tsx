import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useKakaoLogin } from "../../hooks/auth/userKakaoLogin";

const KakaoLogin: React.FC = () => {
  const navigate = useNavigate();
  const { loginWithKakao } = useKakaoLogin();

  const code = new URL(window.location.href).searchParams.get("code");

  useEffect(() => {
    const handleLogin = async () => {
      if (!code) {
        console.error("No code found in URL.");
        return;
      }

      const success = await loginWithKakao(code);
      if (success) {
        navigate("/main");
      }
    };

    handleLogin();
  }, [code, loginWithKakao, navigate]);

  return (
    <div className="LoginHandeler">
      <div className="notice">
        <p>로그인 중입니다.</p>
        <p>잠시만 기다려주세요.</p>
        <div className="spinner"></div>
      </div>
    </div>
  );
};

export default KakaoLogin;
