import {useMutation, useQuery} from '@tanstack/react-query';
import {useForm} from 'react-hook-form';
import {Briefcase, Calendar, CreditCard, Mail, Phone, User} from 'lucide-react';
import {getEmailFromToken} from '../lib/jwt';
import api from '../lib/axios';
import toast from 'react-hot-toast';
import {Employee} from '../types/employee';


const Profile = () => {
  const email = getEmailFromToken();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<Employee>();

  const { data: employee, isLoading } = useQuery({
    queryKey: ['profile', email],
    queryFn: async () => {
      if (!email) throw new Error('No email found');
      const { data } = await api.get<Employee>(`/Employee/test/${email}`);
      reset({
        nom: data.nom,
        prenom: data.prenom,
        email: data.email,
        phoneNumber: data.phoneNumber,
        poste: data.poste,
      });
      return data;
    },
  });

  const updateProfile = useMutation({
    mutationFn: (data: Employee) =>
      api.put(`/Employee/profile/${employee?.id}`, data),
    onSuccess: () => {
      toast.success('Profile updated successfully');
    },
    onError: () => {
      toast.error('Failed to update profile');
    },
  });

  if (isLoading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  if (!employee) {
    return (
      <div className="text-center py-12">
        <h2 className="text-2xl font-semibold text-gray-900">Profile not found</h2>
        <p className="mt-2 text-gray-600">Unable to load profile information.</p>
      </div>
    );
  }

  const onSubmit = (data: Employee) => {
    updateProfile.mutate(data);
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <div className="bg-white rounded-lg shadow-lg overflow-hidden">
        {/* Profile Header */}
        <div className="bg-gradient-to-r from-indigo-600 to-purple-600 px-8 py-12">
          <div className="flex items-center">
            <div className="h-24 w-24 rounded-full bg-white flex items-center justify-center">
              <User className="h-12 w-12 text-indigo-600" />
            </div>
            <div className="ml-6">
              <h1 className="text-3xl font-bold text-white">
                {employee.nom} {employee.prenom}
              </h1>
              <p className="text-indigo-100 mt-1">{employee.poste}</p>
            </div>
          </div>
        </div>

        {/* Profile Information */}
        <div className="p-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="space-y-6">
              <h2 className="text-xl font-semibold text-gray-900">Personal Information</h2>
              
              <div className="flex items-center space-x-3">
                <Mail className="h-5 w-5 text-gray-400" />
                <span className="text-gray-600">{employee.email}</span>
              </div>
              
              <div className="flex items-center space-x-3">
                <Phone className="h-5 w-5 text-gray-400" />
                <span className="text-gray-600">{employee.phoneNumber}</span>
              </div>
              
              <div className="flex items-center space-x-3">
                <CreditCard className="h-5 w-5 text-gray-400" />
                <span className="text-gray-600">CIN: {employee.cin}</span>
              </div>
              
              <div className="flex items-center space-x-3">
                <Calendar className="h-5 w-5 text-gray-400" />
                <span className="text-gray-600">Age: {employee.age}</span>
              </div>
              
              <div className="flex items-center space-x-3">
                <Briefcase className="h-5 w-5 text-gray-400" />
                <span className="text-gray-600">Role: {employee.role}</span>
              </div>
            </div>

            <div className="space-y-6">
              <h2 className="text-xl font-semibold text-gray-900">Update Profile</h2>
              <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    First Name
                  </label>
                  <input
                    {...register('nom', { required: 'First name is required' })}
                    type="text"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                  {errors.nom && (
                    <p className="mt-1 text-sm text-red-600">{errors.nom.message}</p>
                  )}
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Last Name
                  </label>
                  <input
                    {...register('prenom', { required: 'Last name is required' })}
                    type="text"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                  {errors.prenom && (
                    <p className="mt-1 text-sm text-red-600">{errors.prenom.message}</p>
                  )}
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Phone Number
                  </label>
                  <input
                    {...register('phoneNumber', { required: 'Phone number is required' })}
                    type="tel"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                  {errors.phoneNumber && (
                    <p className="mt-1 text-sm text-red-600">{errors.phoneNumber.message}</p>
                  )}
                </div>

                <div className="pt-4">
                  <button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    {isSubmitting ? 'Saving...' : 'Save Changes'}
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Profile;