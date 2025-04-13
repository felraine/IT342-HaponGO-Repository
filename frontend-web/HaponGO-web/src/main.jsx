import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login.jsx';
import SignUp from './components/SignUp.jsx';
import Dashboard from './components/Dashboard.jsx';
import LessonOne from './components/LessonOne.jsx';
import AdminDashboard from './components/AdminDashboard.jsx';
import AdminLessonPage from './components/AdminLessonPage.jsx';


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<SignUp />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/lesson-one" element={<LessonOne />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        <Route path="/admin-lesson/:id" element={<AdminLessonPage />} />
      </Routes>
    </Router>
  </StrictMode>
);
