/* eslint-disable @typescript-eslint/no-unused-vars */
import { useState, useEffect } from 'react';
import { ClipboardCheck, UserCheck, Users } from 'lucide-react';
import toast from 'react-hot-toast';
import { Employee } from '../../types/employee';
import { StatsCard } from '../../components/hr/stats/StatsCard';
import { EmployeeTable } from '../../components/hr/tables/EmployeeTable';
import { RegistrationRequestsTable } from '../../components/hr/tables/RegistrationRequestsTable';
import api from '../../lib/axios';
import { Header } from '../../layout/Header';

export default function HRDashboard() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [registrationRequests, setRegistrationRequests] = useState<Employee[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  // Fetch employees
  const fetchEmployees = async () => {
    try {
      const { data } = await api.get<Employee[]>('/Employee/getAllEmployeeTrue');
      setEmployees(data);
    } catch (error) {
      toast.error('Failed to fetch employees');
    }
  };

  // Fetch registration requests
  const fetchRegistrationRequests = async () => {
    try {
      const { data } = await api.get<Employee[]>('/Employee/getAllEmployeeFalse');
      setRegistrationRequests(data);
    } catch (error) {
      toast.error('Failed to fetch registration requests');
    }
  };

  // Handle request (accept/reject)
  const handleRequest = async (id: number, action: 'validate' | 'delete') => {
    try {
      if (action == "validate" ) {
        await api.put(`/Employee/validate/${id}`);
      }else {
        await api.delete(`/Employee/delete/${id}`);
      }

      
      toast.success('Registration request handled successfully');
      // Refresh data
      fetchEmployees();
      fetchRegistrationRequests();
    } catch (error) {
      toast.error('Failed to handle registration request');
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      await Promise.all([fetchEmployees(), fetchRegistrationRequests()]);
      setLoading(false);
    };
    fetchData();
  }, []);

  return (
    
    <div className="min-h-screen bg-gray-50">
       <Header />
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
     

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatsCard
            title="Total Employees"
            value={employees.length}
            icon={Users}
            color="bg-blue-500"
          />
          <StatsCard
            title="Active Employees"
            value={employees.filter((e) => e.etat).length}
            icon={UserCheck}
            color="bg-green-500"
          />
          <StatsCard
            title="Pending Requests"
            value={registrationRequests.length}
            icon={ClipboardCheck}
            color="bg-amber-500"
          />
        </div>

        {loading ? (
          <div className="flex justify-center items-center py-8">
            <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <div className="space-y-6">
            <RegistrationRequestsTable
              requests={registrationRequests}
              onAccept={(id) => handleRequest(id, 'validate')}
              onReject={(id) => handleRequest(id, 'delete')}
            />
            <EmployeeTable employees={employees} />
          </div>
        )}
      </div>
    </div>
  );
}
