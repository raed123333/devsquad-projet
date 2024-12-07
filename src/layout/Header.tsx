import { useAuth } from "../context/AuthContext";
import { FaUser } from "react-icons/fa"; // Example: Replace with your actual User icon
import { FiLogOut } from "react-icons/fi"; // Example: Replace with your actual Logout icon
import { getUsername } from "../lib/jwt";
import { useNavigate } from "react-router-dom";

export function Header() {
  const { user, logout } = useAuth(); // Extract `user` and `logout` from context

  // Set dashboard name based on role
  const dashboardName = user?.role === "admin" ? "HR Dashboard" : "User Dashboard";
  const username  = getUsername(); 
  console.log(username);

  const navigate = useNavigate();
  const handleProfileClick = () => {
    navigate('/admin/profile'); // Navigate to the profile page
  };
  return (
    <header className="bg-white shadow">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
        <div className="flex items-center justify-between">
          {/* Left Section */}
          <div className="flex items-center space-x-4">
          <button
          onClick={handleProfileClick}
          className="flex items-center text-white hover:text-gray-300"
        >
         <FaUser className="h-8 w-8 text-indigo-600" />
          <span className="ml-2">Profile</span>
        </button>
            
            <h1 className="text-2xl font-bold text-gray-900">{dashboardName}</h1>
          </div>

          {/* Right Section */}
          <div className="flex items-center space-x-4">
            <div className="text-sm text-gray-600">
              <span className="font-medium">{username}</span>
              <span className="mx-1">·</span>
              <span className="capitalize">{user?.role}</span>
            </div>
            <button
              onClick={logout}
              className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              <FiLogOut className="h-4 w-4 mr-2" />
              Logout
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}
