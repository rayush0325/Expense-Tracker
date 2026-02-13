import React, { useContext } from 'react'
import Button from 'react-bootstrap/Button';
import { AuthContext } from '../context/AuthContext';
import toast from 'react-hot-toast';
const Expense = ({ deleteExpense, id,  name, amount }) => {
  const { jwt } = useContext(AuthContext)
  // async function deleteExpense(e) {
  //   try {
  //     const url = `http://localhost:8080/api/expense/${id}`;
  //     await fetch(url, {
  //       method: 'DELETE',
  //       headers: {
  //         'Content-Type': 'application/json',
  //         'Authorization': `Bearer ${jwt}`
  //       }
  //     })
  //     toast.success("expense deleted")
      
  //   }catch(error){
  //     console.log(error)
  //   }

  // }
  
  return (
    <div className='border border-dark-subtle m-2 p-2 w-75 d-flex justify-content-between'>
      <div>expense : {name} | amount : {amount}</div>
      <div className='d-flex gap-2'>
        <Button variant="primary" >Edit</Button>
        <Button variant="primary" onClick={() => deleteExpense(id)}>Delete</Button>
      </div>
    </div>
  )
}

export default Expense
