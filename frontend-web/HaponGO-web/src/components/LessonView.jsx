import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

const LessonView = () => {
  const { lessonId } = useParams();
  const [lessonContents, setLessonContents] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchLessonContents = async () => {
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
    <header className="w-full font-sans">
      <h1 className="m-0 text-[40px] text-white bg-[#BC002D] font-bold py-3 pl-20 text-left">
        HaponGO
      </h1>
    </header>
  
    {/* Dashboard Container */}
    <div className="flex flex-row items-center gap-4 mx-auto mt-12 text-left">
      <a href="/dashboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</a>
      <h2 className="text-black text-[20px] lg:text-[22px] pl-10">Dictionary</h2>
      <a href="/" className="text-xl text-black hover:text-[#9a0024] pl-8">Logout</a>
    </div>
  
    {/* Yellow background container */}
    <div className="bg-[#FFE79B] p-8 shadow-xl w-full min-h-screen text-left mt-4">
      
      {/* Lesson Content */}
      <div className="max-w-3xl mx-auto px-4 py-2">
        
        {/* Lesson Title */}
        <div className="mb-10 text-center">
          <h2 className="text-3xl font-bold text-black-700">
            Lesson {lessonId}
          </h2>
        </div>
  
        {lessonContents.length === 0 ? (
          <p className="text-gray-500 text-center">No content found for this lesson.</p>
        ) : (
          <>
            {/* Card */}
            <div className="bg-white shadow-2xl border border-gray-300 rounded-3xl px-16 py-30 text-center text-xl transition-all duration-300 ease-in-out max-w-screen-xl mx-auto">
            <p className="text-4xl font-bold text-gray-800 mb-8">🇯🇵 {content.japaneseWord}</p>
            <p className="text-2xl text-gray-700 mb-6">
                🔊 Pronunciation: <span className="italic">{content.pronunciation}</span>
            </p>
            <p className="text-2xl text-gray-700">
                English: <span className="font-semibold">{content.englishWord}</span>
            </p>
            </div>
  
            {/* Navigation Buttons */}
            <div className="flex justify-between items-center mt-8">
              <button
                onClick={goToPrev}
                className="px-5 py-3 bg-[#BC002D] text-white rounded-xl font-semibold hover:bg-red-800 transition"
              >
                Back
              </button>
  
              <p className="text-sm text-gray-600">
                Card {currentIndex + 1} of {lessonContents.length}
              </p>
  
              <button
                onClick={goToNext}
                className="px-5 py-3 bg-[#BC002D] text-white rounded-xl font-semibold hover:bg-red-800 transition"
              >
                Next
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
