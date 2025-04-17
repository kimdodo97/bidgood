import React from 'react';
import { Route, Routes, Navigate } from 'react-router';
import Login from './pages/LoginPage';
import KakaoLogin from './components/login/KakaoLogin';
import MainPage from './pages/MainPage';
import BidPageWrapper from './pages/BIdPageWrapper';
import Layout from './components/layout/Layout';
import ItemLayout from './components/layout/ItemLayout';
import ProductAddPage from './pages/ProductAddPage';
import ProductAddImagePage from './pages/ProductAddImagePage';

const App: React.FC = () => {
  return (
    <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route
          path="/login/oauth2/callback/kakao" //redirect_url
          element={<KakaoLogin />} //당신이 redirect_url에 맞춰 꾸밀 컴포넌트
        />
        <Route
          path="/login"
          element={
            <Login />
          }
        />
        <Route element={<Layout />}>
          <Route path='/main' element={<MainPage/>}/>
        </Route>
        <Route element={<ItemLayout/>}>
          <Route path="/auction/:auctionId" element={<BidPageWrapper />} />
          <Route path="/product" element={<ProductAddPage/>}/>
        </Route>
        
      </Routes>
  );
};

export default App
