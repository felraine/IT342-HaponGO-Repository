import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Leaderboard = () => {
  const navigate = useNavigate();
  const [leaderboards, setLeaderboards] = useState([]);
  const [lessons, setLessons] = useState([]);
  const [selectedLesson, setSelectedLesson] = useState('');
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const logout = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('user');
    navigate('/');
  };

  useEffect(() => {
    const fetchLessons = async () => {
      const res = await fetch('http://localhost:8080/api/lessons');
      const data = await res.json();
      const sortedLessons = [...data].sort((a, b) => a.lessonOrder - b.lessonOrder);
      setLessons(sortedLessons);

      if (sortedLessons.length > 0) {
        setSelectedLesson(sortedLessons[0].lessonId); // Default to first lesson
      }
    };
    fetchLessons();
  }, []);

  useEffect(() => {
    if (!selectedLesson) return;

    const fetchLeaderboard = async () => {
      const url = `http://localhost:8080/api/leaderboards/lesson/${selectedLesson}/top`;
      const res = await fetch(url);
      const data = await res.json();
      
      // SORT THE LEADERBOARD BASED ON POINTS (Descending)
      const sortedData = [...data].sort((a, b) => b.points - a.points);

      setLeaderboards(sortedData);
    };
    fetchLeaderboard();
  }, [selectedLesson]);

  return (
    <>
      {/* Header */}
      <header className="w-full font-sans relative bg-[#BC002D]">
        <h1 className="m-0 text-[40px] text-white font-bold py-3 pl-20 text-left">HaponGO</h1>

        {/* Profile Dropdown */}
        <div className="absolute top-5 right-10">
          <div className="relative">
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

      {/* Navigation */}
      <div className="flex flex-row items-center gap-4 mx-auto mt-12 text-left">
        <a href="/dashboard" className="text-black text-[20px] lg:text-[22px] pl-20">Lessons</a>
        <h2 className="text-black text-[20px] lg:text-[22px] pl-20">Dictionary</h2>
        <a href="/leaderboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Leaderboards</a>
      </div>

      {/* Leaderboard Container */}
      <div className="bg-[#FFE79B] min-h-screen py-10 px-6 mt-4">
        <div className="max-w-4xl mx-auto">
          <h2 className="text-3xl font-bold text-center mb-8">Leaderboard</h2>

          <div className="mb-6 text-center">
            <label className="font-medium mr-2">Sort by Lesson:</label>
            <select
              value={selectedLesson}
              onChange={(e) => setSelectedLesson(e.target.value)}
              className="px-4 py-2 border border-grey-300 focus:outline-none focus:ring-2 bg-white bg-[url('data:image/svg+xml;utf8,<svg fill=\'%2364A91B\' height=\'20\' viewBox=\'0 0 24 24\' width=\'20\' xmlns=\'http://www.w3.org/2000/svg\'><path d=\'M7 10l5 5 5-5H7z\'/></svg>')] bg-no-repeat bg-[right_1rem_center] bg-[length:1.25rem_1.25rem]"
            >
              {lessons.map((lesson) => (
                <option key={lesson.lessonId} value={lesson.lessonId}>
                  {lesson.lessonName}
                </option>
              ))}
            </select>
          </div>

          <div className="bg-white shadow-xl rounded-2xl p-6 overflow-x-auto">
            <table className="w-full text-left">
              <thead>
                <tr className="border-b border-gray-300">
                  <th className="pb-2">Rank</th>
                  <th className="pb-2">User</th>
                  <th className="pb-2">Points</th>
                </tr>
              </thead>
              <tbody>
                {leaderboards.length === 0 ? (
                  <tr>
                    <td colSpan="3" className="text-center py-4">No data available</td>
                  </tr>
                ) : (
                  leaderboards.map((entry, index) => (
                    <tr key={index} className="border-b border-gray-200 hover:bg-yellow-100">
                      <td className="py-2 font-medium">{index + 1}</td>
                      <td className="py-2">{entry.user.name}</td>
                      <td className="py-2 font-semibold text-red-700">{entry.points}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default Leaderboard;
