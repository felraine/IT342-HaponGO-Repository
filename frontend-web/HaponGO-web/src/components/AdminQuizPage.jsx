import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function LessonQuizPage() {
  const { id } = useParams();
  const [lessonName, setLessonName] = useState("");
  const [quizzes, setQuizzes] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [editingId, setEditingId] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [newQuiz, setNewQuiz] = useState({
    question: "",
    choice1: "",
    choice2: "",
    choice3: "",
    choice4: "",
    answer: ""
  });

  // Function to handle dropdown toggle
  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const fetchQuizData = async () => {
    try {
      const lessonResponse = await fetch(`http://localhost:8080/api/lessons/${id}`);
      const lessonData = await lessonResponse.json();

      const quizResponse = await fetch(`http://localhost:8080/api/lesson-quizzes/lesson/${id}`);
      const quizData = await quizResponse.json();

      setLessonName(lessonData?.lessonName || "");
      setQuizzes(quizData);
    } catch (err) {
      console.error("Error fetching quiz data:", err);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchQuizData();
  }, [id]);

  const handleChange = (e, quizId, field) => {
    setQuizzes((prev) =>
      prev.map((quiz) =>
        quiz.questionId === quizId ? { ...quiz, [field]: e.target.value } : quiz
      )
    );
  };

  const handleSave = async (quizId) => {
    const quiz = quizzes.find((q) => q.questionId === quizId);
    if (!quiz) return;

    try {
      const response = await fetch(`http://localhost:8080/api/lesson-quizzes/${quizId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          lesson: quiz.lesson,
          question: quiz.question,
          choice1: quiz.choice1,
          choice2: quiz.choice2,
          choice3: quiz.choice3,
          choice4: quiz.choice4,
          answer: quiz.answer
        }),
      });

      if (response.ok) {
        console.log("Quiz updated successfully");
        setEditingId(null);
      } else {
        console.error("Failed to update quiz. Status:", response.status);
      }
    } catch (error) {
      console.error("Error saving quiz:", error);
    }
  };

  const handleDelete = async (quizId) => {
    try {
      await fetch(`http://localhost:8080/api/lesson-quizzes/${quizId}`, { method: "DELETE" });
      setQuizzes((prev) => prev.filter((quiz) => quiz.questionId !== quizId));
    } catch (error) {
      console.error("Error deleting quiz:", error);
    }
  };

  const toggleEdit = (quizId) => {
    if (editingId === quizId) {
      handleSave(quizId);
    } else {
      setEditingId(quizId);
    }
  };

  const handleAddQuiz = async () => {
    const newLessonQuiz = {
      lesson: { lessonId: Number(id) },
      ...newQuiz
    };

    try {
      const response = await fetch("http://localhost:8080/api/lesson-quizzes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newLessonQuiz),
      });

      if (response.ok) {
        const data = await response.json();
        setQuizzes((prev) => [...prev, data]);
        setIsModalOpen(false);
        setNewQuiz({ question: "", choice1: "", choice2: "", choice3: "", choice4: "", answer: "" });
      } else {
        console.error("Failed to add new quiz.");
      }
    } catch (error) {
      console.error("Error adding quiz:", error);
    }
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setNewQuiz({ question: "", choice1: "", choice2: "", choice3: "", choice4: "", answer: "" });
  };

  const handleModalChange = (e) => {
    const { name, value } = e.target;
    setNewQuiz((prev) => ({ ...prev, [name]: value }));
  };

  if (isLoading) return <div>Loading...</div>;

  return (
    <>
      <title>HaponGO Quiz</title>
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
                src="icon-shib.png"
                alt="profile"
                className="w-full h-full object-cover"
              />
            </button>

            {dropdownOpen && (
              <div className="flex flex-col absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-3 z-10">
                <div className="flex flex-col items-center px-4 py-2">
                  <img
                    src="icon-shib.png"
                    alt="Profile"
                    className="w-16 h-16 object-cover border-2 border-red-800 shadow-lg rounded-full"
                  />
                  <p className="font-semibold text-gray-800">Admin</p>
                </div>
                <hr className="my-2" />
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

      <div className="flex flex-row items-center gap-4 mx-auto mt-4 text-left shadow-md bg-white">
        <a href="/admin-dashboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Dashboard</a>
      </div>

      <div className="bg-[#FFFBED] p-8 shadow-xl w-full min-h-screen text-left mt-2">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-black text-left text-[18px] lg:text-[22px] font-bold pl--20">{lessonName} - Quiz</h2>
          <button className="bg-green-500 text-white p-3 rounded-lg hover:bg-green-600 transition duration-200" onClick={() => setIsModalOpen(true)}>
            + Add Quiz Question
          </button>
        </div>

        <div className="space-y-6">
        {quizzes.map((quiz) => {
          const isEditing = editingId === quiz.questionId;
          return (
            <div key={quiz.questionId} className="bg-[#FFE79B] p-8 rounded-xl shadow-lg relative space-y-4 hover:shadow-2xl transition duration-200">
              <div className="text-lg font-semibold text-gray-800">{quiz.question}</div>

              {["choice1", "choice2", "choice3", "choice4"].map((field) => (
                <div key={field} className="text-md text-gray-700">
                  {isEditing ? (
                    <input
                      type="text"
                      value={quiz[field]}
                      onChange={(e) => handleChange(e, quiz.questionId, field)}
                      className="w-full bg-white p-3 border border-gray-300 rounded-md text-lg mb-2"
                      placeholder={`Option ${field.charAt(6)}`}
                    />
                  ) : (
                    <p className="p-3 bg-white rounded-md text-lg font-medium">{quiz[field]}</p>
                  )}
                </div>
              ))}

              {/* Add this section to display the correct answer */}
              <div className="text-md text-gray-700 font-bold">
                <p className="p-3 bg-white rounded-md text-lg">{`Answer: ${quiz.answer}`}</p>
              </div>

              <div className="flex justify-between items-center">
                {isEditing ? (
                  <button onClick={() => toggleEdit(quiz.questionId)} className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition duration-200">
                    Save
                  </button>
                ) : (
                  <button onClick={() => toggleEdit(quiz.questionId)} className="bg-yellow-500 text-white px-4 py-2 rounded-lg hover:bg-yellow-600 transition duration-200">
                    Edit
                  </button>
                )}

                <button
                  onClick={() => handleDelete(quiz.questionId)}
                  className="text-red-600 hover:text-red-800 transition duration-200"
                >
                  <i className="fas fa-trash-alt"></i>
                </button>
              </div>
            </div>
          );
        })}
        </div>

        {isModalOpen && (
          <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50" style={{ background: "rgba(0, 0, 0, 0.3)" }}>
            <div className="bg-white p-6 rounded-lg w-1/3">
              <h3 className="text-xl font-semibold mb-4">Add New Quiz Question</h3>

              {["question", "choice1", "choice2", "choice3", "choice4", "answer"].map((field) => (
                <input
                  key={field}
                  type="text"
                  name={field}
                  placeholder={field}
                  value={newQuiz[field]}
                  onChange={handleModalChange}
                  className="w-full p-3 border mb-4 rounded-md"
                />
              ))}

              <div className="flex justify-between">
                <button onClick={handleAddQuiz} className="bg-green-500 text-white p-3 rounded-md hover:bg-green-600 transition duration-200">
                  Save
                </button>
                <button onClick={handleModalClose} className="bg-gray-500 text-white p-3 rounded-md hover:bg-gray-600 transition duration-200">
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
}
