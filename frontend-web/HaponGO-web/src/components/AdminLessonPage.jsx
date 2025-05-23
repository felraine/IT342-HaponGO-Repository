import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function LessonPage() {
  const { id } = useParams();
  const [lessonName, setLessonName] = useState("");
  const [lessonContent, setLessonContent] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [editingId, setEditingId] = useState(null); // ID of the card being edited
  const [isModalOpen, setIsModalOpen] = useState(false); // Modal visibility state
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [newTerm, setNewTerm] = useState({
    englishWord: "",
    japaneseWord: "",
    pronunciation: "",
  });
  const navigate = useNavigate();

  // Function to handle dropdown toggle
  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const fetchLessonData = async () => {
    try {
      // Fetching the lesson data by its ID
      //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
      //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
      const lessonResponse = await fetch(`https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lessons/${id}`);
      const lessonData = await lessonResponse.json();
  
      // Fetching the lesson content data
      //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
      //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
      const contentResponse = await fetch(`https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${id}`);
      const contentData = await contentResponse.json();
  
      // Set the lesson name and content data
      setLessonName(lessonData?.lessonName || "");
      setLessonContent(contentData);
  
    } catch (err) {
      console.error("Error fetching lesson data:", err);
    } finally {
      setIsLoading(false);
    }
  };  

  useEffect(() => {
    fetchLessonData();
  }, [id]);

  const handleChange = (e, termId, field) => {
    setLessonContent((prev) =>
      prev.map((term) =>
        term.lessonContentId === termId ? { ...term, [field]: e.target.value } : term
      )
    );
  };

  const handleSave = async (termId) => {
    const term = lessonContent.find((t) => t.lessonContentId === termId);
    if (!term) return;

    try {
      //production https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/lesson/${lessonId}
      //development http://localhost:8080/api/lesson-contents/lesson/${lessonId}
      const response = await fetch(`https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/${termId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          lesson: term.lesson,
          japaneseWord: term.japaneseWord,
          pronunciation: term.pronunciation,
          englishWord: term.englishWord,
        }),
      });

      if (response.ok) {
        console.log("Updated successfully");
        setEditingId(null);
      } else {
        console.error("Failed to update. Status:", response.status);
      }
    } catch (error) {
      console.error("Error saving:", error);
    }
  };

  const handleDelete = async (termId) => {
    try {
      await fetch(`https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents/${termId}`, { method: "DELETE" });
      setLessonContent((prev) => prev.filter((item) => item.lessonContentId !== termId));
    } catch (error) {
      console.error("Error deleting:", error);
    }
  };

  const toggleEdit = (termId) => {
    if (editingId === termId) {
      handleSave(termId);
    } else {
      setEditingId(termId);
    }
  };

  const handleAddWord = async () => {
    const newLessonContent = {
      lesson: { lessonId: id },
      englishWord: newTerm.englishWord,
      japaneseWord: newTerm.japaneseWord,
      pronunciation: newTerm.pronunciation,
    };

    try {
      const response = await fetch("https://hapongo-backend-819908927275.asia-southeast1.run.app/api/lesson-contents", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newLessonContent),
      });

      if (response.ok) {
        const data = await response.json();
        setLessonContent((prev) => [...prev, data]); // Add new content to the list
        setIsModalOpen(false); // Close the modal
        setNewTerm({ englishWord: "", japaneseWord: "", pronunciation: "" }); // Reset input fields
      } else {
        console.error("Failed to add new word.");
      }
    } catch (error) {
      console.error("Error adding new word:", error);
    }
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setNewTerm({ englishWord: "", japaneseWord: "", pronunciation: "" }); // Reset on close
  };

  const handleModalChange = (e) => {
    const { name, value } = e.target;
    setNewTerm((prev) => ({ ...prev, [name]: value }));
  };

  if (isLoading) return <div>Loading...</div>;

  return (
    <>
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
      
      {/* Dashboard Link */}
      <div className="flex flex-row items-center gap-4 mx-auto mt-4 text-left shadow-md bg-white px-4 md:px-10">
      <a href="/admin-dashboard" className="text-black text-[18px] md:text-[20px] lg:text-[22px] font-bold">
        Dashboard
      </a>
    </div>
            
    <div className="bg-[#FFFBED] p-4 md:p-16 shadow-xl w-full min-h-screen text-left mt-2">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6 gap-4">
        <h2 className="text-black text-left text-[18px] lg:text-[22px] font-bold">
          {lessonName}
        </h2>
        {/*Buttons*/}
        <div className="flex justify-center flex-wrap gap-4 mt-8">
          <button
            className="bg-[#E53838] font-bold text-white px-4 py-2 rounded-lg w-full sm:w-auto hover:bg-red-700 shadow-md transition-all duration-300"
            onClick={() => navigate(`/admin-quiz/${id}`)}
          >
            Edit Lesson Quiz
          </button>
        
        <button
          className="bg-[#00BC8F] font-bold text-white px-4 py-2 rounded-lg w-full sm:w-auto hover:bg-[#009D77]"
          onClick={() => setIsModalOpen(true)}
        >
          + Add Word
        </button>
        </div>
      </div>

      <div className="hidden md:flex text-sm md:text-base font-bold text-black mb-4">
        <div className="flex-1 pl-2 md:pl-5">English Word</div>
        <div className="flex-1">Japanese Word</div>
        <div className="flex-1">Pronunciation</div>
      </div>

      <div className="space-y-4">
        {lessonContent.map((term) => {
          const isEditing = editingId === term.lessonContentId;

          return (
            <div
              key={term.lessonContentId}
              className="flex flex-col md:flex-row items-start md:items-center gap-2 md:gap-4 bg-[#FFE79B] px-4 pt-10 pb-4 rounded-md shadow-sm relative"
            >
              {/* English Word */}
              <div className="flex-1 w-full">
                {isEditing ? (
                  <input
                    type="text"
                    value={term.englishWord}
                    onChange={(e) => handleChange(e, term.lessonContentId, "englishWord")}
                    className="w-full bg-white text-base md:text-lg font-semibold text-gray-800 p-2 border border-gray-300 rounded"
                  />
                ) : (
                  <p className="bg-white rounded p-2 text-sm md:text-base font-semibold text-gray-800 w-full h-10 overflow-x-auto">
                    {term.englishWord}
                  </p>
                )}
              </div>

              {/* Japanese Word */}
              <div className="flex-1 w-full ">
                {isEditing ? (
                  <input
                    type="text"
                    value={term.japaneseWord}
                    onChange={(e) => handleChange(e, term.lessonContentId, "japaneseWord")}
                    className="w-full bg-white text-base md:text-xl font-bold text-gray-900 p-2 border border-gray-300 rounded"
                  />
                ) : (
                  <p className="bg-white rounded p-2 text-sm md:text-base font-semibold text-gray-800 w-full h-10 overflow-x-auto">
                    {term.japaneseWord}
                  </p>
                )}
              </div>

              {/* Pronunciation */}
              <div className="flex-1 w-full">
                {isEditing ? (
                  <input
                    type="text"
                    value={term.pronunciation}
                    onChange={(e) => handleChange(e, term.lessonContentId, "pronunciation")}
                    className="w-full bg-white text-base md:text-lg text-gray-600 p-2 border border-gray-300 rounded"
                  />
                ) : (
                  <p className="bg-white rounded p-2 text-sm md:text-base font-semibold text-gray-800 w-full h-10 overflow-x-auto">
                    {term.pronunciation}
                  </p>
                )}
              </div>

              {/* Buttons */}
              <div className="flex items-center absolute top-2 right-6 space-x-2">
                <button
                  onClick={() => toggleEdit(term.lessonContentId)}
                  className="text-blue-600 hover:text-blue-800"
                >
                  {isEditing ? <i className="fas fa-check"></i> : <i className="fas fa-pencil-alt"></i>}
                </button>

                <button
                  onClick={() => handleDelete(term.lessonContentId)}
                  className="text-red-600 hover:text-red-800"
                >
                  <i className="fas fa-trash"></i>
                </button>
              </div>
            </div>
          );
        })}
      </div>

        {/* Modal for adding new word */}
        {isModalOpen && (
          <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50" style={{ background: "rgba(0, 0, 0, 0.3)" }}>
            <div className="bg-[#FFE79B] p-6 rounded-lg w-1/3">
              <h3 className="text-xl font-semibold mb-4 text-center">Add New Word</h3>
              <input
                type="text"
                name="englishWord"
                placeholder="English Word"
                value={newTerm.englishWord}
                onChange={handleModalChange}
                className="w-full p-3 mb-4 bg-white shadow-sm"
              />
              <input
                type="text"
                name="japaneseWord"
                placeholder="Japanese Word"
                value={newTerm.japaneseWord}
                onChange={handleModalChange}
                className="w-full p-3 mb-4 bg-white shadow-sm"
              />
              <input
                type="text"
                name="pronunciation"
                placeholder="Pronunciation"
                value={newTerm.pronunciation}
                onChange={handleModalChange}
                className="w-full p-3 mb-4 bg-white shadow-sm"
              />
              <div className="flex justify-end gap-4 mt-4">
                <button
                  onClick={handleAddWord}
                  className="bg-[#00BC8F] text-white p-3 rounded hover:bg-[#009D77] w-full"
                >
                  Save
                </button>
                <button
                  onClick={handleModalClose}
                  className="bg-red-600 text-white p-3 rounded hover:bg-red-700 w-full"
                >
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
