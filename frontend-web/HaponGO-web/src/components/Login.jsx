

export default function Login() {
  return (
    <>
      {/* Header */}
      <title>HaponGO</title>
      <header className="w-full font-sans">
        <h1 className="m-0 text-[40px] text-white bg-[#BC002D] font-bold py-3 pl-20 text-left">
          HaponGO
        </h1>
      </header>

      <div className="flex flex-col lg:flex-row items-center justify-between w-4/5 mx-auto mt-12 text-center lg:text-left">
        {/* Title Section */}
        <div className="lg:pl-20 flex flex-col items-center lg:items-start">
          <h1 className="text-black text-[36px] lg:text-[44px] mb-1 font-bold">
            Start your Japanese <br className="hidden lg:block" /> Journey Today
          </h1>
          <p className="text-12">Learn Japanese the fun and easy way with HaponGO.</p>

          {/* Login Container */}
          <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-[400px] text-left mt-4">
            <form className="space-y-3">
              <label className="block">Email</label>
              <input type="text" className="w-full h-10 p-2 border border-gray-300 rounded-md text-lg" />
              <label className="block">Password</label>
              <input type="password" className="w-full h-10 p-2 border border-gray-300 rounded-md text-lg" />
              <button type="submit" className="w-full h-10 py-1 bg-[#BC002D] text-white text-lg rounded-md mt-2 hover:bg-[#9a0024]">
                Sign in
              </button>

              {/* Sign up section*/}
              <div className="text-center mt-3">
                <p className="text-sm text-black">
                  Don't have an account? <a href="/register" className="font-bold underline hover:text-[#9a0024]">Sign up here.</a>
                </p>
              </div>
            </form>
          </div>
        </div>

        {/* Image Section */}
        <img src="/haponGO-image.svg" alt="HaponGO" className="max-w-[80%] lg:max-w-[440px] h-auto mt-5 mr-10" />
      </div>
    </>
  );
}

