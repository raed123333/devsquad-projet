/* eslint-disable @typescript-eslint/no-unused-vars */
import { useState, useEffect } from 'react';
import { Building2, UserCheck, Users, Plus } from 'lucide-react'; // Added Plus icon
import toast, { Toaster } from 'react-hot-toast';
import { CreateHRModal } from '../../components/admin/modals/CreateHRModal';
import { StatsCard } from '../../components/admin/stats/StatsCard';
import { UserTable } from '../../components/admin/tables/UserTable';
import api from '../../lib/axios';
import { Employee } from '../../types/employee';
import { Header } from '../../layout/Header';

export default function AdminDashboard() {
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [hrUsers, setHrUsers] = useState<Employee[]>([]);
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  // Fetch HR Users
  const fetchHRUsers = async () => {
    try {
      const { data } = await api.get<Employee[]>('/Employee/getAllRh');
      setHrUsers(data);
    } catch (error) {
      toast.error('Failed to fetch HR users');
    }
  };

  // Fetch Employees
  const fetchEmployees = async () => {
    try {
      const { data } = await api.get<Employee[]>('/Employee/getAllEMPLOYEE');
      setEmployees(data);
    } catch (error) {
      toast.error('Failed to fetch employees');
    }
  };

  // Delete User
  const deleteUser = async (id: number) => {
    if (!window.confirm('Are you sure you want to delete this user?')) return;

    try {
      await api.delete(`/Employee/delete/${id}`);
      toast.success('User deleted successfully');
      fetchHRUsers();
      fetchEmployees();
    } catch (error) {
      toast.error('Failed to delete user');
    }
  };

  // Load data on component mount
  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      await Promise.all([fetchHRUsers(), fetchEmployees()]);
      setIsLoading(false);
    };
    fetchData();
  }, []);

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <Header />

      {/* Toaster for notifications */}
      <Toaster position="top-right" />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Header Section with Create Button */}
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold">Admin Dashboard</h1>
          <button
            onClick={() => setIsCreateModalOpen(true)} // Open modal
            className="flex items-center px-4 py-2 bg-indigo-600 text-white rounded-md text-sm font-medium hover:bg-indigo-700"
          >
            <Plus className="mr-2 h-5 w-5" />
            Create HR
          </button>
        </div>

        {/* Statistics Section */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatsCard
            title="Total HR Staff"
            value={hrUsers.length}
            icon={Building2}
            color="bg-blue-500"
          />
          <StatsCard
            title="Total Employees"
            value={employees.length}
            icon={Users}
            color="bg-green-500"
          />
          <StatsCard
            title="Active Users"
            value={[...hrUsers, ...employees].filter((u) => u.etat).length}
            icon={UserCheck}
            color="bg-indigo-500"
          />
        </div>

        {/* User Management Section */}
        {isLoading ? (
          <div className="flex justify-center items-center py-8">
            <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <div className="space-y-6">
            <UserTable
              users={hrUsers}
              title="HR Staff"
              onDelete={deleteUser}
            />
            <UserTable
              users={employees}
              title="Employees"
              onDelete={deleteUser}
            />
          </div>
        )}
      </div>

      {/* Modal for Creating HR Accounts */}
      <CreateHRModal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)} // Close modal
      />
    </div>
  );
}
