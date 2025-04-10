import axios from "axios";
import { useCallback } from "react";

export const useKakaoLogin = () => {
  const loginWithKakao = useCallback(async (code: string): Promise<boolean> => {
    try {
      const res = await axios.get("http://localhost:8080/oauth2/callback/kakao", {
        params: { code },
      });

      if (res.status === 200) {
        const accessToken = res.headers['authorization']; // 또는 'access-token', 백엔드 명명 방식에 따라 다름
        if (accessToken) {
            localStorage.setItem('accessToken', accessToken); // 또는 Recoil, Zustand 등
        }
        
        return true;
      }

      return false;
    } catch (error) {
      console.error("Kakao login failed:", error);
      return false;
    }
  }, []);

  return { loginWithKakao };
};
