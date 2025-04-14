import React from 'react';
import { Outlet } from 'react-router';
import Footer from '../common/Footer';
import ItemHeader from '../common/ItemHeader';
const Layout: React.FC = () => {

  return (
    <div className="flex flex-col items-center w-full bg-gray-100 min-h-screen">
      <div className="flex flex-col w-full bg-white shadow-md flex-grow">
        {/* 상단바 고정 */}
        <div className="fixed top-0 left-0 w-full z-10">
          <ItemHeader />
        </div>

        {/* 본문 영역: 상단바 크기만큼 여백 추가 */}
				<div className="flex-grow overflow-y-auto mt-16 mb-16">
          <Outlet />
        </div>

        {/* 하단바 고정 */}
        <div className="fixed bottom-0 left-0 w-full z-10">
          <Footer />
        </div>
      </div>
    </div>
  );
};

export default Layout;