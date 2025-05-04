import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import API_BASE_URL from "../config";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // User login function
  // Send a POST request to the server with the email and password
  const handleLogin = async (e) => {
    e.preventDefault();
    //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
    //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
    try {
      const response = await fetch(
        `https://hapongo-backend-819908927275.asia-southeast1.run.app/api/users/login?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`,
        {
          method: "POST",
        }
      );
  
      if (!response.ok) {
        const message = await response.text();
        setError(message || "Login failed.");
        return;
      }
  
      const data = await response.json();
      console.log("Login successful!", data);
  
      // ✅ Save the user ID (and the full user object if you like)
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("user", JSON.stringify(data));
  
      // Redirect based on role
      if (data.admin === true || data.admin === 1) {
        navigate("/admin-dashboard");
      } else {
        navigate("/dashboard");
      }
      
    } catch (err) {
      console.error("Login error:", err);
      setError("An unexpected error occurred.");
    }
  };      

  return (
    <>
      <title>HaponGO</title>
      <header className="w-full font-sans">
        <h1 className="m-0 text-[40px] text-white bg-[#BC002D] font-bold py-3 pl-20 text-left">
          HaponGO
        </h1>
      </header>

      <div className="flex flex-col lg:flex-row items-center justify-between w-4/5 mx-auto mt-12 text-center lg:text-left">
        <div className="lg:pl-20 flex flex-col items-center lg:items-start">
          <h1 className="text-black text-[36px] lg:text-[44px] mb-1 font-bold">
            Start your Japanese <br className="hidden lg:block" /> Journey Today
          </h1>
          <p className="text-12">
            Learn Japanese the fun and easy way with HaponGO.</p>
          
          {/* Login form here */}
          <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-[400px] text-left mt-4">
            <form className="space-y-3" onSubmit={handleLogin}>
              <label className="block">Email</label>
              <input
                type="text"
                className="w-full h-10 p-2 border border-gray-300 rounded-md text-lg"
                value={email}
                onChange={(e) => setEmail(e.target.value)}/>
              <label className="block">Password</label>
              <input
                type="password"
                className="w-full h-10 p-2 border border-gray-300 rounded-md text-lg"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              {error && (
                <p className="text-red-600 text-sm mt-1">{error}</p>
              )}
              <button
                type="submit"
                className="w-full h-10 py-1 bg-[#BC002D] text-white text-lg rounded-md mt-2 hover:bg-[#9a0024]">Sign in</button>
              
              {/*Register link here */}
              <div className="text-center mt-3">
                <p className="text-sm text-black">
                  Don't have an account?{" "}
                  <a href="/register" className="font-bold underline hover:text-[#9a0024]"> Sign up here.</a>
                </p>
              </div>
            </form>
          </div>

        </div>
        {/* Image here */}        
        <img src="/haponGO-image.svg"alt="HaponGO" className="max-w-[80%] lg:max-w-[440px] h-auto mt-5 mr-10"/>
      </div>
    </>
  );
}
