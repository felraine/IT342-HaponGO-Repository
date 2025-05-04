import React, { useState, useEffect } from 'react';
import axios from 'axios';

const DictionarySearch = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSearch = async () => {
    if (!searchTerm.trim()) return;

    setLoading(true);
    setError('');
    try {
      const response = await axios.get(`https://hapongo-backend-819908927275.asia-southeast1.run.app/api/dictionary/search?searchTerm=${searchTerm}`);
      setResults(response.data);
    } catch (err) {
      setError('An error occurred while fetching the data.');
    } finally {
      setLoading(false);
    }
  };

  // Fetch top 10 words on component mount
  useEffect(() => {
    const fetchTopWords = async () => {
      setLoading(true);
      setError('');
      try {
        // Call the updated endpoint /getTenWords
        const response = await axios.get('https://hapongo-backend-819908927275.asia-southeast1.run.app/api/dictionary/getTenWords');
        setResults(response.data);
      } catch (err) {
        setError('An error occurred while fetching the data.');
      } finally {
        setLoading(false);
      }
    };

    fetchTopWords();
  }, []);

  return (
    <>
      {/* Header */}
      <header className="w-full font-sans relative bg-[#BC002D]">
        <h1 className="m-0 text-[40px] text-white font-bold py-3 pl-20 text-left">HaponGO</h1>
      </header>

      {/* Navigation */}
      <div className="flex flex-row items-center gap-4 mx-auto mt-12 text-left">
        <a href="/dashboard" className="text-black text-[20px] lg:text-[22px] pl-20">Lessons</a>
        <h2 className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Dictionary</h2>
        <a href="/leaderboard" className="text-black text-[20px] lg:text-[22px] pl-20">Leaderboards</a>
      </div>

      {/* Search Container */}
      <div className="bg-[#FFE79B] min-h-screen py-10 px-6 mt-4">
        <div className="max-w-4xl mx-auto">
          <h2 className="text-3xl font-bold text-center mb-8">Dictionary Search</h2>

          {/* Search Bar */}
          <div className="mb-6 text-center">
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="px-4 py-2 border border-grey-300 focus:outline-none focus:ring-2 bg-white bg-[url('data:image/svg+xml;utf8,<svg fill=\'%2364A91B\' height=\'20\' viewBox=\'0 0 24 24\' width=\'20\' xmlns=\'http://www.w3.org/2000/svg\'><path d=\'M7 10l5 5 5-5H7z\'/></svg>')] bg-no-repeat bg-[right_1rem_center] bg-[length:1.25rem_1.25rem]"
              placeholder="Search for a word..."
            />
            <button
              onClick={handleSearch}
              disabled={loading}
              className="ml-4 px-6 py-2 bg-[#BC002D] text-white rounded-lg hover:bg-[#9a0025] focus:outline-none"
            >
              {loading ? 'Searching...' : 'Search'}
            </button>
          </div>

          {error && <p style={{ color: 'red' }} className="text-center">{error}</p>}

          {/* Results Table */}
          <div className="bg-white shadow-xl rounded-2xl p-6 overflow-x-auto">
            <table className="w-full text-left">
              <thead>
                <tr className="border-b border-gray-300">
                  <th className="pb-2">English Word</th>
                  <th className="pb-2">Kanji</th>
                  <th className="pb-2">Kana</th>
                </tr>
              </thead>
              <tbody>
                {results.length === 0 ? (
                  <tr>
                    <td colSpan="3" className="text-center py-4">No results found</td>
                  </tr>
                ) : (
                  results.map((item) => (
                    <tr key={item.id} className="border-b border-gray-200 hover:bg-yellow-100">
                      <td className="py-2">{item.englishWord}</td>
                      <td className="py-2">{item.japaneseKanji || 'N/A'}</td>
                      <td className="py-2">{item.japaneseReading}</td>
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

export default DictionarySearch;
