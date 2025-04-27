import React, { useEffect, useState } from 'react';
import { useParams, useNavigate} from 'react-router-dom';

const LessonView = () => {
  const { lessonId } = useParams();
  const [lessonContents, setLessonContents] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [loading, setLoading] = useState(true);
  const [lessonName, setLessonName] = useState("");
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const navigate = useNavigate(); 

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  // Logout function to clear user data from localStorage
  const logout = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('user');
    navigate('/'); 
  };

  //pop sound effect
  const playClickSound = () => {
    new Audio('/click-sound.mp3').play(); 
  };

  useEffect(() => {
    const fetchLessonContents = async () => {
      //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
      //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
      try {
        const response = await fetch(`http://localhost:8080/api/lesson-contents/lesson/${lessonId}`);
        if (!response.ok) throw new Error('Failed to fetch lesson content');
        const data = await response.json();
      
        setLessonContents(data);
      } catch (error) {
        console.error('Error:', error);
      } finally {
        setLoading(false);
      }
    };

    const fetchLessonName = async () => {
      try {
        //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
        //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
        const response = await fetch(`http://localhost:8080/api/lessons/${lessonId}`);
        if (!response.ok) throw new Error('Failed to fetch lesson name');
        const data = await response.json();
        setLessonName(data.lessonName);
      } catch (error) {
        console.error('Error fetching lesson name:', error);
      }
    };

    fetchLessonName();
    fetchLessonContents();
  }, [lessonId]);

  const goToNext = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % lessonContents.length);
  };

  const goToPrev = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + lessonContents.length) % lessonContents.length);
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <p className="text-lg font-medium text-gray-600">Loading lesson content...</p>
      </div>
    );
  }

  const content = lessonContents[currentIndex];

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
                <a
                  href="/"
                  className="px-4 py-2 text-gray-700 hover:bg-gray-100 text-sm text-center"
                >
                  Logout
                </a>
              </div>
            )}
          </div>
        </div>
      </header>

      {/* Navigation Links */}
      <div className="flex flex-row items-center gap-4 mx-auto mt-12 text-left">
        <a href="/dashboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</a>
        <h2 className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Dictionary</h2>
        <a href="/leaderboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Leaderboards</a>
      </div>

      {/* Lesson Content Container */}
      <div className="bg-[#FFE79B] p-8 shadow-xl w-full min-h-screen text-left mt-4">
        <div className="max-w-3xl mx-auto px-4 py-2">

          {/* Lesson Title */}
          <div className="mb-10 text-center">
            <h2 className="text-3xl font-bold text-black-700">
              {lessonName}
            </h2>
          </div>

          {lessonContents.length === 0 ? (
            <p className="text-gray-500 text-center">No content found for this lesson.</p>
          ) : (
            <>
              {/* Flashcard Display */}
              <div className="bg-white shadow-2xl border border-gray-300 rounded-3xl px-16 py-30 text-center text-xl transition-all duration-300 ease-in-out max-w-screen-xl mx-auto">
                <p className="text-4xl font-bold text-gray-800 mb-8">🇯🇵 {content.japaneseWord}</p>
                <p className="text-2xl text-gray-700 mb-6">
                  Pronunciation: <span className="italic">{content.pronunciation}</span>
                </p>
                <p className="text-2xl text-gray-700">
                  Translation: <span className="font-semibold">{content.englishWord}</span>
                </p>
              </div>

              {/* Navigation Buttons */}
              <div className="flex justify-between items-center mt-8">
                <button
                  onClick={() => { goToPrev(); playClickSound(); }} 
                  className="px-5 py-3 bg-[#BC002D] text-white rounded-xl font-semibold hover:bg-red-800 transition"
                >
                  Back
                </button>

                <p className="text-sm text-gray-600">
                  Card {currentIndex + 1} of {lessonContents.length}
                </p>

                <button
                  onClick={() => { goToNext(); playClickSound(); }} 
                  className="px-5 py-3 bg-[#BC002D] text-white rounded-xl font-semibold hover:bg-red-800 transition"
                >
                  Next
                </button>
              </div>
              <div className="flex justify-center mt-8">
              <button
                className="bg-[#BC002D] text-white text-lg sm:text-xl px-6 py-3 rounded-md hover:bg-red-800 shadow-md transition-all duration-300"
                onClick={() => navigate(`/quiz/${lessonId}`)}
              >
                Start Quiz
              </button>
            </div>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default LessonView;
