import React, { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../context/AuthContext'
import toast, { Toaster } from 'react-hot-toast';
import Expense from '../components/Expense';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import ExpenseList from '../components/ExpenseList';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const { jwt } = useContext(AuthContext);
  const naviagte = useNavigate();
  // if(jwt == null){
  //   console.log("hello")
    
  // }
  let title = "";
  let amount = 0;
  const [expenseList, setExpenseList] = useState([]);
  

  async function loadData(jwt) {
    
    // const url = "http://localhost:8080/api/user/";
    const url = "http://localhost:8080/api/expense/";
    try {
      const response = await fetch(url, {
        method: "GET",
        headers: {
          'Content-Type': "application/json",
          'Authorization': `Bearer ${jwt}`
        }
      })
      const result = await response.json();
      console.log(result)
      // setData(result);
      setExpenseList(result);

    } catch (exception) {
      console.log(exception)

    }
  }
  useEffect(() => {
    if (jwt == null) {
      toast.error("not logged in");
      naviagte("/login");
    }
    else {
      loadData(jwt);
    }

  }, [])

  async function addExpense(e) {
    e.preventDefault();
    console.log(jwt);
    const url = "http://localhost:8080/api/expense/add";
    
    try{
      await fetch(url, {
        method : 'POST',
        headers : {
          'Content-Type' : 'application/json',
          'Authorization' : `Bearer ${jwt}`
        },
        body : JSON.stringify({
          "name" : `${title}`,
          "amount" : amount
        })
      })

      toast.success("expense added succcessfully")
      loadData(jwt)
    }
    catch(error){
      console.log(error)
    }
  }

  return (
    <div className='flex-grow-1 overflow-auto'>
      <div><Toaster /></div>
      <div className='text-center'>DashBoard</div>
      ,<Form className='w-50 m-5 border border-dark rounded p-3' onSubmit={addExpense}>
        <Form.Group className="mb-3">
          <Form.Control type="text" placeholder="Enter expense" onChange={(e)=>{title = e.target.value}}/>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Control type="text" placeholder="Enter amount" onChange={(e)=>{amount = e.target.value}}/>
        </Form.Group>
        <Button variant="primary" type="submit" >
          Add
        </Button>
      </Form>
      {/* <Button variant="primary" id="button-addon2" onClick={addExpense}>
          Add
        </Button> */}

      {
        (expenseList.length == 0) ? <div>No expenses yet</div> : <ExpenseList expenseList={expenseList} setExpenseList = {setExpenseList} loadData = {loadData} jwt = {jwt}/>
      }



    </div>
  )
}

export default Dashboard
