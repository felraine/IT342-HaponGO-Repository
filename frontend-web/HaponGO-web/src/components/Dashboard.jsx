
export default function Dashboard(){

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
            <h2 className="text-black text-[20px] lg:text-[22px] font-bold pl-20">Lessons</h2>
            <h2 className="text-black text-[20px] lg:text-[22px] pl-10">Dictionary</h2>
            {/*temporary logout link */}
            <a href="/" className="text-xl text-black hover:text-[#9a0024] pl-8">Logout</a>
            
        </div>

        {/* Lessons Container*/}
        <div className="bg-[#FFE79B] p-8  shadow-xl w-full min-h-screen text-left mt-4">

        {/*List of lesson cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 pl-12 pr-12">
            <div className="bg-white p-4 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300 ">
                <h3 className="text-lg font-bold">Lesson 1</h3>
                <p className="text-sm">Basic Greetings</p>
            </div>
            <div className="bg-white p-4 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300">
                <h3 className="text-lg font-bold">Lesson 2</h3>
                <p className="text-sm">Basic Hiragana</p>
            </div>
            <div className="bg-white p-4 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300">
                <h3 className="text-lg font-bold">Lesson 3</h3>
                <p className="text-sm">Numbers and Counting</p>
            </div>
            <div className="bg-white p-4 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300">
                <h3 className="text-lg font-bold">Lesson 4</h3>
                <p className="text-sm">Basic Vocabulary</p>

                 </div>
            </div>

        </div>
    </>
    );
}