// Navbar.js

import { useState } from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleNavbar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <nav className="bg-gray-800 p-4 h-[7vh]">
      <div className="container mx-auto flex justify-between items-center max-sm:flex-col">
        <div className="text-white text-xl font-bold">Zero Hunger</div>
        <div className="lg:hidden  max-sm:hidden">
          <button
            onClick={toggleNavbar}
            className="text-white focus:outline-none"
          >
            <svg
              className="h-6 w-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M4 6h16M4 12h16m-7 6h7"
              ></path>
            </svg>
          </button>
        </div>
        <div
          className={`${
            isOpen ? 'block' : 'hidden'
          } lg:flex lg:items-center lg:w-auto mt-4 lg:mt-0 flex max-sm:flex-col max-sm:hidden`}
        >
          <Link to="" className="text-white mr-6">
            Home
          </Link>
          <Link to="/plant-disease-detection" className="text-white mr-6">
            Plant Detection System
          </Link>
          <Link to="#" className="text-white">
           
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
