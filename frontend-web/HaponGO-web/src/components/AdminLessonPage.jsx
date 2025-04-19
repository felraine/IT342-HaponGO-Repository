import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function LessonPage() {
  const { id } = useParams();
  const [lessonName, setLessonName] = useState("");
  const [lessonContent, setLessonContent] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [editingId, setEditingId] = useState(null); // ID of the card being edited
  const [isModalOpen, setIsModalOpen] = useState(false); // Modal visibility state
  const [newTerm, setNewTerm] = useState({
    englishWord: "",
    japaneseWord: "",
    pronunciation: "",
  });

  const fetchLessonData = async () => {
    try {
      // Fetching the lesson data by its ID
      const lessonResponse = await fetch(`http://localhost:8080/api/lessons/${id}`);
      const lessonData = await lessonResponse.json();
  
      // Fetching the lesson content data
      const contentResponse = await fetch(`http://localhost:8080/api/lesson-contents/lesson/${id}`);
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
      const response = await fetch(`http://localhost:8080/api/lesson-contents/${termId}`, {
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
      await fetch(`http://localhost:8080/api/lesson-contents/${termId}`, { method: "DELETE" });
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
      const response = await fetch("http://localhost:8080/api/lesson-contents", {
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
      <header className="w-full font-sans">
        <h1 className="m-0 text-[40px] text-white bg-[#BC002D] font-bold py-3 pl-20 text-left">
          HaponGO
        </h1>
      </header>

      <div className="flex flex-row items-center gap-4 mx-auto mt-4 text-left shadow-md bg-white">
        <a href="/admin-dashboard" className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</a>
        <a href="/" className="text-xl text-black hover:text-[#9a0024] pl-8">Logout</a>
      </div>

      <div className="bg-[#FFFBED] p-8 shadow-xl w-full min-h-screen text-left mt-2">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-black text-left text-[18px] lg:text-[22px] font-bold pl--20">
            Lesson {lessonName}
          </h2>
          <button
            className="bg-green-500 text-white p-2 rounded-lg"
            onClick={() => setIsModalOpen(true)} // Open the modal
          >
            + Add Word
          </button>
        </div>

        <div className="flex text-lg font-bold text-black mb-4">
          <div className="flex-1 pl-5">English Word</div>
          <div className="flex-1">Japanese Word</div>
          <div className="flex-1">Pronunciation</div>
        </div>

        <div className="space-y-4">
          {lessonContent.map((term) => {
            const isEditing = editingId === term.lessonContentId;

            return (
              <div key={term.lessonContentId} className="flex items-center bg-[#FFE79B] p-10 rounded-lg shadow-lg relative">
                {/* English Word */}
                <div className="flex-1">
                  {isEditing ? (
                    <input
                      type="text"
                      value={term.englishWord}
                      onChange={(e) => handleChange(e, term.lessonContentId, "englishWord")}
                      className="w-75 bg-white text-lg font-semibold text-gray-800 p-3 border border-gray-300 rounded"
                    />
                  ) : (
                    <p className="bg-white rounded p-3 text-lg font-semibold text-gray-800 w-75">{term.englishWord}</p>
                  )}
                </div>

                {/* Japanese Word */}
                <div className="flex-1">
                  {isEditing ? (
                    <input
                      type="text"
                      value={term.japaneseWord}
                      onChange={(e) => handleChange(e, term.lessonContentId, "japaneseWord")}
                      className="w-75 bg-white text-xl font-bold text-gray-900 p-3 border border-gray-300 rounded"
                    />
                  ) : (
                    <p className="bg-white rounded p-3 text-xl font-bold text-gray-900 w-75">{term.japaneseWord}</p>
                  )}
                </div>

                {/* Pronunciation */}
                <div className="flex-1">
                  {isEditing ? (
                    <input
                      type="text"
                      value={term.pronunciation}
                      onChange={(e) => handleChange(e, term.lessonContentId, "pronunciation")}
                      className="w-75 bg-white text-lg text-gray-600 p-3 border border-gray-300 rounded"
                    />
                  ) : (
                    <p className="bg-white rounded p-3 text-lg text-gray-600 w-75">{term.pronunciation}</p>
                  )}
                </div>

                {/* Edit / Save Button */}
                <button
                  onClick={() => toggleEdit(term.lessonContentId)}
                  className="absolute top-2 right-12 text-blue-600 hover:text-blue-800"
                >
                  {isEditing ? <i className="fas fa-check"></i> : <i className="fas fa-pencil-alt"></i>}
                </button>

                {/* Delete Button */}
                <button
                  onClick={() => handleDelete(term.lessonContentId)}
                  className="absolute top-2 right-4 text-red-600 hover:text-red-800"
                >
                  <i className="fas fa-trash"></i>
                </button>
              </div>
            );
          })}
        </div>

        {/* Modal for adding new word */}
        {isModalOpen && (
          <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50" style={{ background: "rgba(0, 0, 0, 0.3)" }}>
            <div className="bg-white p-6 rounded-lg w-1/3">
              <h3 className="text-xl font-semibold mb-4">Add New Word</h3>
              <input
                type="text"
                name="englishWord"
                placeholder="English Word"
                value={newTerm.englishWord}
                onChange={handleModalChange}
                className="w-full p-3 border mb-4"
              />
              <input
                type="text"
                name="japaneseWord"
                placeholder="Japanese Word"
                value={newTerm.japaneseWord}
                onChange={handleModalChange}
                className="w-full p-3 border mb-4"
              />
              <input
                type="text"
                name="pronunciation"
                placeholder="Pronunciation"
                value={newTerm.pronunciation}
                onChange={handleModalChange}
                className="w-full p-3 border mb-4"
              />
              <div className="flex justify-between">
                <button
                  onClick={handleAddWord}
                  className="bg-green-500 text-white p-3 rounded"
                >
                  Save
                </button>
                <button
                  onClick={handleModalClose}
                  className="bg-gray-500 text-white p-3 rounded"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}

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
