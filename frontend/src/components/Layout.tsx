import React from 'react';
import { Outlet } from 'react-router';

const Layout: React.FC = () => {
  return (
    <div className="w-full min-h-screen flex flex-col justify-center items-center bg-white">
      <Outlet />
    </div>
  );
};

export default Layout;
