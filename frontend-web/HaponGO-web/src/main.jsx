import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './templates/index.css'
import Login from './components/Login.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Login />
  </StrictMode>,
)
