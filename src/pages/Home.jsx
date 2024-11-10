import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Spinner from '../components/Spinner';
import { Link } from 'react-router-dom';
import { AiOutlineEdit } from 'react-icons/ai';
import { BsInfoCircle } from 'react-icons/bs';
import { MdOutlineAddBox, MdOutlineDelete } from 'react-icons/md';

const Home = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    axios.get('http://localhost:8089/Employee/getAllEmployees')
      .then((response) => {
        setEmployees(response.data); // Access data directly
        setLoading(false);
      })
      .catch((error) => {
        console.log(error);
        setLoading(false);
      });
  }, []);

  return (
    <div className='p-4'>
      <div className='flex justify-between items-center'>
        <h1 className='text-3xl my-8'>Liste des employés</h1>
        <Link to='/employees/create'>
          <MdOutlineAddBox className='text-sky-800 text-4xl' />
        </Link>
      </div>
      {loading ? (
        <Spinner />
      ) : (
        <table className='w-full border-separate border-spacing-2'>
          <thead>
            <tr>
              <th className='border border-slate-600 rounded-md'>No</th>
              <th className='border border-slate-600 rounded-md'>Nom</th>
              <th className='border border-slate-600 rounded-md'>Prenom</th>
              <th className='border border-slate-600 rounded-md'>Email</th>
              <th className='border border-slate-600 rounded-md'>CIN</th>
              <th className='border border-slate-600 rounded-md'>Age</th>
              <th className='border border-slate-600 rounded-md'>Tel</th>
              <th className='border border-slate-600 rounded-md'>Poste</th>
              <th className='border border-slate-600 rounded-md'>Operations</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((employee, index) => (
              <tr key={employee.id} className='h-8'>
                <td className='border border-slate-700 rounded-md text-center'>
                  {index + 1}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.nom}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.prenom}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.email}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.cin}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.age}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.numPhone}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  {employee.poste}
                </td>
                <td className='border border-slate-700 rounded-md text-center'>
                  <div className='flex justify-center gap-x-4'>
                    <Link to={`/employees/details/${employee.id}`}>
                      <BsInfoCircle className='text-2xl text-green-800' />
                    </Link>
                    <Link to={`/employees/edit/${employee.id}`}>
                      <AiOutlineEdit className='text-2xl text-yellow-600' />
                    </Link>
                    <Link to={`/employees/delete/${employee.id}`}>
                      <MdOutlineDelete className='text-2xl text-red-600' />
                    </Link>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Home;
