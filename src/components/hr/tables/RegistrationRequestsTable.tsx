import { useState } from 'react';
import { Check, X } from 'lucide-react';
import { Employee } from '../../../types/employee';

interface RegistrationRequestsTableProps {
  requests: Employee[];
  onAccept: (id: number) => void;
  onReject: (id: number) => void;
}

export function RegistrationRequestsTable({
  requests,
  onAccept,
  onReject,
}: RegistrationRequestsTableProps) {
  const [showModal, setShowModal] = useState(false);
  const [currentRequest, setCurrentRequest] = useState<{
    id: number;
    action: 'accept' | 'reject';
  } | null>(null);

  const handleAction = (id: number, action: 'accept' | 'reject') => {
    setCurrentRequest({ id, action });
    setShowModal(true);
  };

  const confirmAction = () => {
    if (!currentRequest) return;
    if (currentRequest.action === 'accept') {
      onAccept(currentRequest.id);
    } else {
      onReject(currentRequest.id);
    }
    setShowModal(false);
    setCurrentRequest(null);
  };

  const cancelAction = () => {
    setShowModal(false);
    setCurrentRequest(null);
  };

  if (requests.length === 0) {
    return null;
  }

  return (
    <>
      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <div className="px-6 py-4 border-b border-gray-100">
          <h2 className="text-lg font-semibold text-gray-900">Registration Requests</h2>
        </div>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Employee
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  CIN
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Department
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {requests.map((request) => (
                <tr key={request.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="h-10 w-10 flex-shrink-0">
                        <div className="h-10 w-10 rounded-full bg-indigo-100 flex items-center justify-center">
                          <span className="text-indigo-600 font-medium text-sm">
                            {request.nom}
                          </span>
                        </div>
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-gray-900">{request.nom}</div>
                        <div className="text-sm text-gray-500">{request.email}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {request.cin}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {request.poste}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                    <div className="flex justify-end space-x-2">
                      <button
                        onClick={() => handleAction(request.id, 'accept')}
                        className="p-1 rounded-full text-green-600 hover:bg-green-100"
                        title="Accept"
                      >
                        <Check className="h-5 w-5" />
                      </button>
                      <button
                        onClick={() => handleAction(request.id, 'reject')}
                        className="p-1 rounded-full text-red-600 hover:bg-red-100"
                        title="Reject"
                      >
                        <X className="h-5 w-5" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-gray-500 bg-opacity-75 z-50">
          <div className="bg-white rounded-lg shadow-xl p-6 w-80">
            <h3 className="text-lg font-medium text-gray-900">
              {currentRequest?.action === 'accept'
                ? 'Confirm Accept'
                : 'Confirm Reject'}
            </h3>
            <p className="mt-2 text-sm text-gray-600">
              Are you sure you want to{' '}
              {currentRequest?.action === 'accept' ? 'accept' : 'reject'} this
              request?
            </p>
            <div className="mt-4 flex justify-end space-x-3">
              <button
                onClick={cancelAction}
                className="py-2 px-4 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300"
              >
                Cancel
              </button>
              <button
                onClick={confirmAction}
                className={`py-2 px-4 rounded-lg text-white ${
                  currentRequest?.action === 'accept'
                    ? 'bg-green-600 hover:bg-green-700'
                    : 'bg-red-600 hover:bg-red-700'
                }`}
              >
                {currentRequest?.action === 'accept' ? 'Accept' : 'Reject'}
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
