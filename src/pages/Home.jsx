import React , {useState , useEffect }from 'react'
import axios from 'axios';
import Spinner from '../components/Spinner';
import { Link } from 'react-router-dom';
import { AiOutlineEdit } from 'react-icons/ai';
import { BsInfoCircle } from 'react-icons/bs';
import { MdOutlineAddBox , MdOutlineDelete} from'react-icons/md';


const Home = () => {
  const [employees, setEmployees] = useState([]);
  const[loading,setLoading] = useState(false);
  useEffect(() => {
    axios.get('http:// localhost:8089/Employee')
    .then((response) =>{
      setEmployees(response.data.data);
      setLoading(false);
    })
    .catch((error) => {
      console.log(error);
      setLoading(false);
    })
  },[])
  return (
    <div className='p-4'>
      <div className='flex justify-between items-center'>
        <h1 className='text-3xl my-8'> liste des employés</h1>
        <Link to='/employees/craete' >
        <MdOutlineAddBox className='text-sky-800 text-4xl' />
        </Link>
      </div>
      {loading ?(
        <Spinner/>

      ):(
        <table className='w-full border-separate border-spacing-2'>
          <thead>
            <tr>
              <th className='border border-slate-600 rounded-md'>No</th>
              <th className='border border-slate-600 rounded-md'>Nom</th>
              <th className='border border-slate-600 rounded-md'>Prenom</th>
              <th className='border border-slate-600 rounded-md'>email</th>
              <th className='border border-slate-600 rounded-md'>cin</th>
              <th className='border border-slate-600 rounded-md'>age</th>
              <th className='border border-slate-600 rounded-md'>tel</th>
              <th className='border border-slate-600 rounded-md'>poste</th>
              
            </tr>

          </thead>
          <tbody>
            {employees.map( (employee,index)=>
            <tr key={employee._id} className='h-8'>
              <td className='border border-slate-700 rounded-md text-center'>
                {index+1}
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
                {employee.numPhone}
              </td>
              <td className='border border-slate-700 rounded-md text-center'>
                {employee.poste}
              </td>

            </tr>
            
            
            )}

          </tbody>

        </table>

      )}

    </div>
  )
}

export default Home