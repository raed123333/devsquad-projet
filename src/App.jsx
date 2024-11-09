import React from 'react'
import { Routes,Route } from 'react-router-dom'
import CreateEmployee from'./pages/CreateEmployee';
import DeleteEmployee from'./pages/DeleteEmployee';
import EditEmployee from './pages/EditEmployee';
import Home from './pages/Home';
import ShowEmployee from './pages/ShowEmployee';

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<Home/>} />
      <Route path="/employees/craete" element={<CreateEmployee/>} />
      <Route path="/employees/delete/:id" element={<DeleteEmployee/>} />
      <Route path="/employees/edit/:id" element={<EditEmployee/>} />
      <Route path="/employees/details/:id" element={<ShowEmployee/>} />
    </Routes>
  )
}

export default App