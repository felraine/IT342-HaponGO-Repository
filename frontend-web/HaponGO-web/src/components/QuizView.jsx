import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const QuizView = () => {
  const { lessonId } = useParams();
  const navigate = useNavigate();
  const [quizzes, setQuizzes] = useState([]);
  const [lessonName, setLessonName] = useState('');
  const [currentIndex, setCurrentIndex] = useState(0);
  const [selectedAnswer, setSelectedAnswer] = useState('');
  const [showResult, setShowResult] = useState(false);
  const [isCorrect, setIsCorrect] = useState(false);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [score, setScore] = useState(0);
  const [answeredQuestions, setAnsweredQuestions] = useState([]);

  const currentQuiz = quizzes[currentIndex];

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const logout = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('user');
    navigate('/');
  };

  // Function to handle answer selection
  const handleAnswer = (answer) => {
    setSelectedAnswer(answer);
    const selectedChoiceText = currentQuiz[answer];
    const correct = selectedChoiceText === currentQuiz.answer;
    setIsCorrect(correct);
    setShowResult(true);
    setAnsweredQuestions((prev) => [
      ...prev,
      { question: currentQuiz.question, selected: selectedChoiceText, correct: currentQuiz.answer }
    ]);
    if (correct) setScore((prev) => prev + 1);

    // Automatically go to the next question after a short delay
    setTimeout(() => {
      nextQuestion();
    }, 1500); // 1.5 seconds delay
  };

  const nextQuestion = () => {
    setCurrentIndex((prev) => prev + 1);
    setSelectedAnswer('');
    setShowResult(false);
  };

  const restartQuiz = () => {
    setCurrentIndex(0);
    setSelectedAnswer('');
    setShowResult(false);
    setIsCorrect(false);
    setScore(0);
    setAnsweredQuestions([]);
  };

  // Function to submit the score to the leaderboard
  const submitScore = async () => {
    try {
      const userId = localStorage.getItem('userId'); // Assuming user is logged in
      if (!userId) {
        alert("User is not logged in!");
        return;
      }

      // Logging the parameters b efore submitting
      console.log('Submitting:', { userId, lessonId, points: score });

      const url = new URL('http://localhost:8080/api/leaderboards/update');
      url.searchParams.append('userId', userId);
      url.searchParams.append('lessonId', lessonId);
      url.searchParams.append('points', score);

      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // You may not need this header for a GET/POST with query params
        },
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Leaderboard updated:', data);
        alert("Score submitted successfully!");
      } else {
        console.error('Failed to submit score');
        alert('Failed to submit score');
      }
    } catch (error) {
      console.error('Error submitting score:', error);
      alert('Error submitting score');
    }
  };

  useEffect(() => {
    const fetchQuiz = async () => {
      const res = await fetch(`http://localhost:8080/api/lesson-quizzes/lesson/${lessonId}`);
      const data = await res.json();
      setQuizzes(data);
    };

    const fetchLesson = async () => {
      const res = await fetch(`http://localhost:8080/api/lessons/${lessonId}`);
      const data = await res.json();
      setLessonName(data.lessonName);
    };

    fetchQuiz();
    fetchLesson();
  }, [lessonId]);

  useEffect(() => {
    if (currentIndex === quizzes.length && quizzes.length > 0) {
      submitScore(); // Only submit if there are quizzes and the user has reached the end
    }
  }, [currentIndex, quizzes.length]);

  return (
    <>
      <title>HaponGO</title>
      <header className="w-full font-sans relative bg-[#BC002D]">
        <h1 className="m-0 text-[40px] text-white font-bold py-3 pl-20 text-left">HaponGO</h1>
        <div className="absolute top-5 right-10">
          <div className="relative">
            <button
              onClick={toggleDropdown}
              className="w-12 h-12 rounded-full overflow-hidden border-2 border-white focus:outline-none cursor-pointer"
            >
              <img src="/icon-shib.png" alt="profile" className="w-full h-full object-cover" />
            </button>
            {dropdownOpen && (
              <div className="flex flex-col absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-3 z-10">
                <div className="flex flex-col items-center px-4 py-2">
                  <img src="/icon-shib.png" alt="Profile" className="w-16 h-16 object-cover border-2 border-red-800 shadow-lg rounded-full" />
                  <p className="font-semibold text-gray-800">Ferenu</p>
                </div>
                <hr className="my-2" />
                <a href="/payment" className="px-4 py-2 text-gray-700 hover:bg-gray-100 text-sm text-center">Payment</a>
                <button onClick={logout} className="px-4 py-2 text-gray-700 hover:bg-gray-100 text-sm text-center">Logout</button>
              </div>
            )}
          </div>
        </div>
      </header>

      <div className="flex flex-row items-center gap-4 mx-auto mt-12 text-left">
        <a href="/dashboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</a>
        <h2 className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Dictionary</h2>
        <a href="/leaderboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Leaderboards</a>
      </div>

      <div className="bg-[#FFE79B] p-8 shadow-xl w-full min-h-screen text-left mt-4">
        <div className="max-w-3xl mx-auto px-4 py-2">
          <div className="mb-10 text-center">
            <h2 className="text-3xl font-bold text-black-700">{lessonName} Quiz</h2>
          </div>

          {quizzes.length === 0 ? (
            <p className="text-center text-gray-600">No quiz questions found for this lesson.</p>
          ) : currentIndex < quizzes.length ? (
            <div className="bg-white shadow-xl rounded-2xl px-8 py-10 px-16">
              <p className="text-xl font-medium mb-6 text-gray-800 text-center">
                Question {currentIndex + 1} of {quizzes.length}
              </p>
              <p className="text-2xl font-bold text-gray-900 mb-6">{currentQuiz.question}</p>
              
              {/* Displaying the choices*/}
              <div className="grid gap-4">
                {['choice1', 'choice2', 'choice3', 'choice4'].map((choiceKey) => {
                  const optionText = currentQuiz[choiceKey];
                  const isSelected = selectedAnswer === choiceKey;
                  const isAnswerCorrect = currentQuiz.answer === optionText;

                  return (
                    <button
                      key={choiceKey}
                      disabled={showResult}
                      onClick={() => handleAnswer(choiceKey)}
                      className={`py-3 px-6 rounded-xl text-left text-lg border transition
                        ${isSelected
                          ? isAnswerCorrect
                            ? 'bg-green-300 border-green-500'  // Correct answer: green
                            : 'bg-red-300 border-red-500'      // Incorrect answer: red
                          : 'bg-gray-100 hover:bg-yellow-200'  // Default state: gray
                        }`}
                    >
                      {optionText}
                    </button>
                  );
                })}
              </div>
              {/* Displaying the result after answering */}
              {showResult && (
                <div className="mt-6 text-center">
                  <p className={`text-xl font-semibold ${isCorrect ? 'text-green-700' : 'text-red-700'}`}>
                    {isCorrect ? 'Correct!' : `Wrong. Correct Answer: ${currentQuiz.answer}`}
                  </p>
                </div>
              )}
            </div>
          ) : (
            <div className="bg-white shadow-xl rounded-2xl px-8 py-10 text-center">
              <h3 className="text-2xl font-bold text-gray-800 mb-4">You've completed the quiz!</h3>
              <p className="text-lg text-gray-700 mb-6">Score: {score} / {quizzes.length}</p>

              <div className="text-left mb-6">
                <h4 className="text-xl font-semibold mb-2">Review:</h4>
                {answeredQuestions.map((q, index) => (
                  <div key={index} className="mb-4">
                    <p className="font-medium">{index + 1}. {q.question}</p>
                    <p className={`text-sm ${q.selected === q.correct ? 'text-green-600' : 'text-red-600'}`}>
                      Your Answer: {q.selected} {q.selected !== q.correct && ` (Correct: ${q.correct})`}
                    </p>
                  </div>
                ))}
              </div>

              <button
                onClick={restartQuiz}
                className="bg-[#BC002D] text-white py-2 px-6 rounded-xl hover:bg-red-800 transition"
              >
                Restart Quiz
              </button>
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default QuizView;