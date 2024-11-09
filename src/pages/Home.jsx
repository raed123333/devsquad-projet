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
    <div>Home</div>
  )
}

export default Home