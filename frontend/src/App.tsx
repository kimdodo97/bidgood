import React from 'react';
import { Route, Routes, Navigate } from 'react-router';
import Login from './pages/Login';
import Layout from './components/login/Layout';

const App: React.FC = () => {
  return (
    <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route
          path="/login"
          element={
            <Layout>
              <Login />
            </Layout>
          }
        />
      </Routes>
  );
};

export default App
