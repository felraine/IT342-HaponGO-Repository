import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
  const navigate = useNavigate();
  const [lessons, setLessons] = useState([]);
  const [error, setError] = useState(null);
  const [dropdownOpen, setDropdownOpen] = useState(false);

  // Function to handle dropdown toggle
  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  // Logout function to clear user data from localStorage
  const logout = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('user');
    navigate('/');
  };

  // Get list of existing lessons
  useEffect(() => {
    // Fetch lessons
    const fetchLessons = async () => {
      try {
        //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
        //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
        const response = await fetch('https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lessons');
        const data = await response.json();
        setLessons(data);
      } catch (err) {
        console.error('Failed to fetch lessons:', err);
        setError('Failed to fetch lessons');
      }
    };

    fetchLessons();
  }, []);

  // Sort lessons by lessonOrder before rendering
  const sortedLessons = lessons.sort((a, b) => a.lessonOrder - b.lessonOrder);

  return (
    <>
      {/* Header */}
      <title>HaponGO</title>
      <header className="w-full font-sans relative bg-[#BC002D]">
        <h1 className="m-0 text-[40px] text-white font-bold py-3 pl-20 text-left">
          HaponGO
        </h1>

        {/* Profile Dropdown */}
        <div className="absolute top-5 right-10">
          <div className="relative">
            {/* Profile Icon/Button */}
            <button
              onClick={toggleDropdown}
              className="w-12 h-12 rounded-full overflow-hidden border-2 border-white focus:outline-none cursor-pointer"
            >
              <img
                src="/icon-shib.png"
                alt="profile"
                className="w-full h-full object-cover"
              />
            </button>

            {/* Dropdown Menu */}
            {dropdownOpen && (
              <div className="flex flex-col absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-3 z-10">
                <div className="flex flex-col items-center px-4 py-2">
                  <img
                    src="/icon-shib.png"
                    alt="Profile"
                    className="w-16 h-16 object-cover border-2 border-red-800 shadow-lg rounded-full"
                  />
                  <p className="font-semibold text-gray-800">Ferenu</p>
                </div>
                <hr className="my-2" />
                <a
                  href="/payment"
                  className="px-4 py-2 text-gray-700 hover:bg-gray-100 text-sm text-center"
                >
                  Payment
                </a>
                <button
                  onClick={logout}
                  className="px-4 py-2 text-gray-700 hover:bg-gray-100 text-sm text-center"
                >
                  Logout
                </button>
              </div>
            )}
          </div>
        </div>
      </header>

      {/* Dashboard Container */}
      <div className="flex flex-row items-center gap-4 mx-auto mt-12 text-left">
        {/* Placeholder for the navigation bar */}
        <h2 className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</h2>
        <a href="/dictionary" className="text-black text-[20px] lg:text-[22px] pl-20">Dictionary</a>
        <a href="/leaderboard" className="text-black text-[20px] lg:text-[22px] pl-20">Leaderboards</a>
      </div>

      {/* Lessons Container */}
      <div className="bg-[#FFE79B] p-8 shadow-xl w-full min-h-screen text-left mt-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 pl-12 pr-12">
          {sortedLessons.length > 0 ? (
            sortedLessons.map((lesson) => (
              <div
                key={lesson.lessonId}
                onClick={() => navigate(`/lesson/${lesson.lessonId}`)}
                className="bg-white p-4 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300 cursor-pointer"
              >
                <img
                  src="/haponGO-image.svg"
                  alt={`Lesson ${lesson.lessonId}`}
                  className="w-full max-w-[300px] h-40 object-contain mb-4 mx-auto"
                />
                <h3 className="text-lg font-bold">{lesson.lessonName}</h3>
              </div>
            ))
          ) : (
            <p className="col-span-full text-center text-gray-500">No lessons available.</p>
          )}
        </div>
      </div>
    </>
  );
}
