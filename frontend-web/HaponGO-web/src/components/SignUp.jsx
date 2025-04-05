import { useState } from 'react';

export default function SignUp() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  
  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };


  // Register a new user
  // Send a POST request to the server with the form data
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert("Passwords do not match.");
      return;
    }

    try {
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
        alert("Registration successful!");
      } else {
        alert(`Registration failed: ${text}`);
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
