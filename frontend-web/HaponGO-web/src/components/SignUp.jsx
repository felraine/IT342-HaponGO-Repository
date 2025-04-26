import { useState } from 'react';
import { useNavigate } from "react-router-dom";

export default function SignUp() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  // Function to show toast notifications
  function showToast(message, type = "success") {
    // Create or get the toast container
    let container = document.getElementById("toast-container");
    if (!container) {
      container = document.createElement("div");
      container.id = "toast-container";
      container.className = "fixed bottom-4 right-4 z-50 flex flex-col gap-2";
      document.body.appendChild(container);
    }
  
    const toast = document.createElement("div");
  
    const isSuccess = type === "success";
  
    const icon = isSuccess
      ? `<svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20"><path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/></svg>`
      : `<svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20"><path d="M18 10a8 8 0 1 1-16 0 8 8 0 0 1 16 0ZM9 9V5h2v4H9Zm0 2h2v2H9v-2Z"/></svg>`;
  
    const iconColorClass = isSuccess ? "text-green-500 bg-green-100 dark:bg-green-800 dark:text-green-200" : "text-red-500 bg-red-100 dark:bg-red-800 dark:text-red-200";
  
    toast.innerHTML = `
      <div class="flex items-center w-full max-w-xs p-4 text-gray-500 bg-white rounded-lg shadow-sm dark:text-gray-400 dark:bg-gray-800" role="alert">
        <div class="inline-flex items-center justify-center shrink-0 w-8 h-8 ${iconColorClass} rounded-lg">
          ${icon}
          <span class="sr-only">Icon</span>
        </div>
        <div class="ms-3 text-sm font-normal">${message}</div>
        <button type="button" class="ms-auto -mx-1.5 -my-1.5 bg-white text-gray-400 hover:text-gray-900 rounded-lg focus:ring-2 focus:ring-gray-300 p-1.5 hover:bg-gray-100 inline-flex items-center justify-center h-8 w-8 dark:text-gray-500 dark:hover:text-white dark:bg-gray-800 dark:hover:bg-gray-700" aria-label="Close">
          <span class="sr-only">Close</span>
          <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
          </svg>
        </button>
      </div>
    `;
  
    const closeBtn = toast.querySelector("button");
    closeBtn.addEventListener("click", () => toast.remove());
  
    container.appendChild(toast);
  
    setTimeout(() => {
      toast.remove();
      if (container.children.length === 0) {
        container.remove();
      }
    }, 5000);
  }  
  
  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const navigate = useNavigate();


  // Register a new user
  // Send a POST request to the server with the form data
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      //alert("Passwords do not match.");
      showToast("Passwords do not match, please try again.", "error");
      return;
    }

    try {
      //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
      //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
      const response = await fetch('http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: formData.username,
          email: formData.email,
          password: formData.password
        })
      });

      const text = await response.text();

      if (response.ok) {
        showToast("Registration successful!", "success");
        setTimeout(()=> navigate("/"), 500); // Redirect to login page after successful registration
      } else {
        showToast(`Registration failed: ${text}`, "error");
      }            

    } catch (error) {
      console.error("Error during registration:", error);
     alert("Something went wrong. Please try again later.");
     
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
          <p className="text-12">Learn Japanese the fun and easy way with HaponGO.</p>

          {/* Sign up form here */}
          <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-[400px] text-left mt-4 mb-10">
            <form className="space-y-3" onSubmit={handleSubmit}>
              <label className="block">Username</label>
              <input
                type="text"
                name="username"
                className="w-full h-8 p-2 border border-gray-300 rounded-md text-lg"
                value={formData.username}
                onChange={handleChange}
                required
              />
              <label className="block">Email</label>
              <input
                type="email"
                name="email"
                className="w-full h-8 p-2 border border-gray-300 rounded-md text-lg"
                value={formData.email}
                onChange={handleChange}
                required
              />
              <label className="block">Password</label>
              <input
                type="password"
                name="password"
                className="w-full h-8 p-2 border border-gray-300 rounded-md text-lg"
                value={formData.password}
                onChange={handleChange}
                required
              />
              <label className="block">Confirm Password</label>
              <input
                type="password"
                name="confirmPassword"
                className="w-full h-8 p-2 border border-gray-300 rounded-md text-lg"
                value={formData.confirmPassword}
                onChange={handleChange}
                required
              />
              <button
                type="submit"
                className="w-full h-10 py-1 bg-[#BC002D] text-white text-lg rounded-md mt-2 hover:bg-[#9a0024]"
              >
                Sign up
              </button>

              {/* Login link here */}
              <div className="text-center mt-3">
                <p className="text-sm text-black">
                  Already have an account? <a href="/" className="font-bold underline hover:text-[#9a0024]">Sign in here.</a>
                </p>
              </div>
            </form>
          </div>
        </div>

        {/* Image here */}
        <img src="/haponGO-image.svg" alt="HaponGO" className="max-w-[80%] lg:max-w-[440px] h-auto mt-5 mr-10" />
      </div>
    </>
  );
}
