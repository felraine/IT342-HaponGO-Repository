import { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

export default function Dashboard() {
  const navigate = useNavigate();

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditModal, setIsEditModal] = useState(false);  // To distinguish between Add and Edit Modals
  const [lessonName, setLessonName] = useState('');
  const [maxScore, setMaxScore] = useState('');
  const [lessons, setLessons] = useState([]);
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null);
  const [currentLessonId, setCurrentLessonId] = useState(null); // Track the current lesson being edited
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const fetchLessons = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/lessons');
      const data = await response.json();
      setLessons(data);
    } catch (err) {
      console.error('Failed to fetch lessons:', err);
      setError('Failed to fetch lessons');
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/users/admin/get_all_users');
      if (!response.ok) {
        throw new Error(`Server error: ${response.status}`);
      }
      const data = await response.json();
      if (!Array.isArray(data)) {
        throw new Error('Expected an array but got something else.');
      }
      setUsers(data);
    } catch (err) {
      console.error('Failed to fetch users:', err);
      setError('Failed to fetch user statistics');
    }
  };

  useEffect(() => {
    fetchLessons();
    fetchUsers();
  }, []);

  const handleLessonSubmit = async (e) => {
    e.preventDefault();
    if (!lessonName || !maxScore) {
      setError('Both fields are required.');
      return;
    }

    const newLesson = { lessonName, maxScore: parseInt(maxScore, 10) };

    try {
      const response = await fetch('http://localhost:8080/api/lessons/save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newLesson),
      });

      if (!response.ok) {
        const msg = await response.text();
        setError(msg || 'Failed to save lesson.');
        return;
      }

      setError(null);
      setIsModalOpen(false);
      setLessonName('');
      setMaxScore('');
      await fetchLessons();
    } catch (err) {
      console.error('Error:', err);
      setError('An unexpected error occurred.');
    }
  };

  const handleEditLesson = (lesson) => {
    setCurrentLessonId(lesson.lessonId); // Set the ID of the lesson being edited
    setLessonName(lesson.lessonName); // Pre-fill the name
    setMaxScore(lesson.maxScore); // Pre-fill the max score
    setIsModalOpen(true); // Open the modal for editing
    setIsEditModal(true); // Set modal to edit mode
  };

  const handleUpdateLesson = async (e) => {
    e.preventDefault();
    if (!lessonName || !maxScore) {
      setError('Both fields are required.');
      return;
    }

    const updatedLesson = { lessonName, maxScore: parseInt(maxScore, 10) };

    try {
      const response = await fetch(`http://localhost:8080/api/lessons/${currentLessonId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedLesson),
      });

      if (!response.ok) {
        const msg = await response.text();
        setError(msg || 'Failed to update lesson.');
        return;
      }

      setError(null);
      setIsModalOpen(false);
      setLessonName('');
      setMaxScore('');
      setIsEditModal(false);
      await fetchLessons();
    } catch (err) {
      console.error('Error:', err);
      setError('An unexpected error occurred.');
    }
  };

  const handleDeleteLesson = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/lessons/${id}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        setError('Failed to delete the lesson.');
        return;
      }
      await fetchLessons();
    } catch (err) {
      console.error('Failed to delete lesson:', err);
      setError('An unexpected error occurred.');
    }
  };

  const totalUsers = users.length;
  const activeSubscriptions = users.filter(user => user.subscription_status).length;
  const totalAdmins = users.filter(user => user.admin).length;
  const latestUserDate = users
    .map(user => new Date(user.account_creation_date))
    .sort((a, b) => b - a)[0]?.toLocaleDateString() || 'N/A';

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
      {/* Main Content */}
      <div className="flex flex-row items-center gap-4 mx-auto pt-12 text-left px-20 bg-[#FFFBED]">
        <h2 className="text-black text-[20px] lg:text-[22px] font-bold">Dashboard</h2>
        <h2 className="text-black text-[20px] lg:text-[22px]">Dictionary</h2>
        <button
          onClick={() => {
            setIsModalOpen(true);
            setIsEditModal(false); // Reset to Add mode when adding new lesson
          }}
          className="ml-auto bg-[#BC002D] text-white text-sm lg:text-base py-2 px-4 rounded-md hover:bg-[#9a0024] transition-colors"
        >
          Add Lesson
        </button>
      </div>

      <div className="flex gap-8 p-8 min-h-screen items-stretch bg-[#FFFBED]">
        {/* Published Lessons Section */}
        <div className="flex-1 bg-[#FFE79B] p-6 rounded shadow-xl max-h-[450px]">
          <h2 className="text-2xl font-bold mb-4">Published Lessons</h2>
          <ul className="space-y-2 max-h-[350px] overflow-y-auto flex-1">
            {lessons.map((lesson) => (
              <li key={lesson.lessonId} className="flex justify-between items-center bg-white p-4 rounded shadow">
                <Link
                  to={`/admin-lesson/${lesson.lessonId}`} // Make the lesson clickable
                  className="text-lg font-semibold text-black hover:text-[#BC002D] block"
                >
                  📘 {lesson.lessonName} — Max Score: {lesson.maxScore}
                </Link>
                <div className="flex gap-2">
                  <button
                    onClick={() => handleEditLesson(lesson)}
                    className="text-blue-500 hover:text-blue-400"
                  >
                    <i className="fas fa-pen"></i> {/* Pen icon for Edit */}
                  </button>
                  <button
                    onClick={() => handleDeleteLesson(lesson.lessonId)}
                    className="text-red-500 hover:text-red-400"
                  >
                    <i className="fas fa-trash"></i> {/* Trash icon for Delete */}
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </div>

        {/* User Statistics Section */}
        <div className="w-[300px] flex flex-col justify-between space-y-4 max-h-[450px]">
          <div className="bg-white p-6 rounded shadow-md">
            <h3 className="text-xl font-bold mb-2">Total Users</h3>
            <p className="text-4xl font-bold text-[#BC002D]">{totalUsers}</p>
          </div>

          {/* Active Subscriptions */}
          <div className="bg-white p-6 rounded shadow-md">
            <h3 className="text-xl font-bold mb-2">Active Subscriptions</h3>
            <p className="text-4xl font-bold text-[#9A0024]">{activeSubscriptions}</p>
          </div>

          {/* Total Admins */}
          <div className="bg-white p-6 rounded shadow-md">
            <h3 className="text-xl font-bold mb-2">Total Admins</h3>
            <p className="text-4xl font-bold text-[#9A0024]">{totalAdmins}</p>
          </div>
        </div>
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50" style={{ background: "rgba(0, 0, 0, 0.3)" }}>
          <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
            <h2 className="text-2xl font-bold mb-4">{isEditModal ? 'Edit Lesson Details' : 'Add New Lesson'}</h2>
            <form onSubmit={isEditModal ? handleUpdateLesson : handleLessonSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium">Lesson Title</label>
                <input
                  type="text"
                  value={lessonName}
                  onChange={(e) => setLessonName(e.target.value)}
                  required
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>
              <div>
                <label className="block text-sm font-medium">Max Quiz Score</label>
                <input
                  type="number"
                  value={maxScore}
                  onChange={(e) => setMaxScore(e.target.value)}
                  required
                  min="1"
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>
              {error && <p className="text-red-600 text-sm">{error}</p>}
              <div className="flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setIsModalOpen(false)}
                  className="px-4 py-2 rounded border text-gray-700"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 rounded bg-[#BC002D] text-white hover:bg-[#9a0024]"
                >
                  {isEditModal ? 'Save Changes' : 'Save Lesson'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
}
