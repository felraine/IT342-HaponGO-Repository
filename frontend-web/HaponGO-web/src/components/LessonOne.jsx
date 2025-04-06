import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

export default function LessonOne() {

  //lesson images
  const images = [
    "/page-one.png",
    "/page-two.png", 
    "/page-three.png",
    "/page-four.png",
    "/page-five.png",
  ];

  const [currentIndex, setCurrentIndex] = useState(0);

  //function to go to the next slide
  const nextSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  //function to go to the previous slide
  const prevSlide = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === 0 ? images.length - 1 : prevIndex - 1
    );
  };
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
            {/*Placeholder for the navgation bar */}
            <a  href="/dashboard"className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</a>
            <h2 className="text-black text-[20px] lg:text-[22px] pl-10">Dictionary</h2>
            {/*temporary logout link */}
            <a href="/" className="text-xl text-black hover:text-[#9a0024] pl-8">Logout</a>
            
        </div>

        
         {/* Lesson One Container */}
        <div className="bg-[#FFE79B] p-8 shadow-xl w-full min-h-screen text-left mt-4">
          <h2 className="text-black text-center text-[18px] lg:text-[22px] pl-10 mb-6 ">Lesson 1: Basic Greetings</h2>

           {/* Lesson Carousel */}
          <div className="relative w-full max-w-4xl mx-auto">
            {/* Image */}
            <div className="relative overflow-hidden">
            <AnimatePresence mode="wait">
              <motion.img
                key={images[currentIndex]}
                src={images[currentIndex]}
                alt={`Slide ${currentIndex + 1}`}
                initial={{ opacity: 0, x: 50 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -50 }}
                transition={{ duration: 0.5 }}
                className="w-full h-[500px] sm:h-[400px] md:h-[500px] object-cover bg-white shadow-2xl"
              />
            </AnimatePresence>
          </div>
            {/* Prev Button */}
            <button
              onClick={prevSlide}
              className="absolute top-1/2 left-2 sm:left-4 transform -translate-y-1/2 bg-[#BC002D] text-white text-2xl sm:text-5xl w-12 h-12  rounded-full hover:bg-red-800 shadow-md flex items-center justify-center pb-4 pr-1"
            >
              ‹
            </button>

            {/* Next Button */}
            <button
              onClick={nextSlide}
              className="absolute top-1/2 right-2 transform -translate-y-1/2 bg-[#BC002D] text-white text-2xl sm:text-5xl w-12 h-12 rounded-full hover:bg-red-800 shadow-md flex items-center justify-center pb-4 pl-1"
            >
              ›
            </button>
          </div>
           {/* Take Quiz Button */}
           <div className="flex justify-center mt-8">
            <button
              className="bg-[#BC002D] text-white text-lg sm:text-xl px-6 py-3 rounded-md hover:bg-red-800 shadow-md transition-all duration-300"
              onClick={() => alert("Quiz coming soon!")}
            >
              Take Quiz
            </button>
          </div>
          </div>
          
      </>
    );
  }