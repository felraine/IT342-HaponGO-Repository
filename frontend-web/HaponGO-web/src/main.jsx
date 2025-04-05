import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login.jsx'
import SignUp from './components/SignUp.jsx'
import Dashboard from './components/Dashboard.jsx';
import LessonOne from './components/LessonOne.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path="/" element ={<Login/>}></Route>
        <Route path="/register" element ={<SignUp/>}></Route>
        <Route path="/dashboard" element ={<Dashboard/>}></Route>
        <Route path="/lesson-one" element ={<LessonOne/>}></Route>
      </Routes>
    </Router>
  </StrictMode>,
)
