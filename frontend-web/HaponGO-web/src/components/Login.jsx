import { useState } from 'react'
import reactLogo from "../assets/react.svg";
import viteLogo from '/vite.svg'
import "../templates/App.css"; 


function Login() {
  return (
    <>
      {/* Header */}
      <header className="App-header">
        <h1 className="title">HaponGO</h1>
      </header>
      
    {/* Login Container (Flexbox for side-by-side layout) */}
    <div className="login-container">
        {/* Login Section */}
        <div className="login-section">
          <h1 className="second-title">Start your Japanese <br />Journey Today</h1>
          <p>Learn Japanese the fun and easy way with HaponGO.</p>

          {/* Login Box */}
          <div className="login-form">
            <form className="form">
              <label className="label">Email</label>
              <input type="text" className="input" /> <br />
              <label className="label">Password</label>
              <input type="password" className="input" /> <br />
              <button type="submit" className="button">Sign in</button>

              {/* Sign up section (inline) */}
              <div className="signup-container">
                <p className="signup-desc">
                  Don't have an account? <a href="/register" className="signup-link">Sign up Here</a>
                </p>
              </div>
            </form>
          </div>
        </div>

        {/* SVG Image on Right Side */}
        <img src="/haponGO-image.svg" alt="HaponGO" className="haponGO-image" />
      </div>
    </>
  );
}

export default Login
