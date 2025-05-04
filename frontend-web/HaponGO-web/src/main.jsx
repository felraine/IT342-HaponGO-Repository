import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login.jsx';
import SignUp from './components/SignUp.jsx';
import Dashboard from './components/Dashboard.jsx';
import LessonView from './components/LessonView.jsx';
import AdminDashboard from './components/AdminDashboard.jsx';
import AdminLessonPage from './components/AdminLessonPage.jsx';
import PaymentScreen from './components/PaymentScreen.jsx';
import AdminQuizPage from './components/AdminQuizPage.jsx';
import QuizView from './components/QuizView.jsx';
import Leaderboard from './components/LeaderboardView.jsx';
import Dictionary from './components/Dictionary.jsx';


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<SignUp />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/lesson/:lessonId" element={<LessonView />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        <Route path="/admin-lesson/:id" element={<AdminLessonPage />} />
        <Route path="/payment" element={<PaymentScreen />} />
        <Route path="/admin-quiz/:id" element={<AdminQuizPage />} />
        <Route path="/quiz/:lessonId" element={<QuizView />} />
        <Route path="/leaderboard" element={<Leaderboard />} />
        <Route path="/dictionary" element={<Dictionary />} />
      </Routes>
    </Router>
  </StrictMode>
);
