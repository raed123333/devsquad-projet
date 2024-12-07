/* eslint-disable @typescript-eslint/no-unused-vars */
import { useState } from 'react';
import { X } from 'lucide-react';
import toast from 'react-hot-toast';
import api from '../../../lib/axios';

interface CreateHRModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export function CreateHRModal({ isOpen, onClose }: CreateHRModalProps) {
  const [formData, setFormData] = useState({
    nom: '',
    prenom: '',
    email: '',
    cin: '',
    age: '',
    genre: '',
    numPhone: '',
    poste: '',
    password: '',
  });

  const [isPending, setIsPending] = useState(false);

  const handleCreateHR = async (data: typeof formData) => {
    try {
      setIsPending(true);
      await api.post('/auth/registerRH', data);
      toast.success('HR account created successfully');
      onClose();
    } catch (error) {
      toast.error('Failed to create HR account');
    } finally {
      setIsPending(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg w-full max-w-md p-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold">Create HR Account</h2>
          <button onClick={onClose} className="text-gray-500 hover:text-gray-700">
            <X className="h-5 w-5" />
          </button>
        </div>
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleCreateHR(formData);
          }}
        >
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700">Nom</label>
              <input
                type="text"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.nom}
                onChange={(e) => setFormData((prev) => ({ ...prev, nom: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Prenom</label>
              <input
                type="text"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.prenom}
                onChange={(e) => setFormData((prev) => ({ ...prev, prenom: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Email</label>
              <input
                type="email"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.email}
                onChange={(e) => setFormData((prev) => ({ ...prev, email: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">CIN</label>
              <input
                type="text"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.cin}
                onChange={(e) => setFormData((prev) => ({ ...prev, cin: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Age</label>
              <input
                type="number"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.age}
                onChange={(e) => setFormData((prev) => ({ ...prev, age: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Genre</label>
              <select
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.genre}
                onChange={(e) => setFormData((prev) => ({ ...prev, genre: e.target.value }))}
              >
                <option value="">Select Gender</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Phone Number</label>
              <input
                type="text"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.numPhone}
                onChange={(e) => setFormData((prev) => ({ ...prev, numPhone: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Poste</label>
              <input
                type="text"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.poste}
                onChange={(e) => setFormData((prev) => ({ ...prev, poste: e.target.value }))}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Password</label>
              <input
                type="password"
                required
                className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2"
                value={formData.password}
                onChange={(e) => setFormData((prev) => ({ ...prev, password: e.target.value }))}
              />
            </div>
          </div>
          <div className="mt-6 flex justify-end space-x-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-indigo-600 text-white rounded-md text-sm font-medium hover:bg-indigo-700"
              disabled={isPending}
            >
              {isPending ? 'Creating...' : 'Create HR Account'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
