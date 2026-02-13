import React, { useContext } from 'react'
import Expense from './Expense'
import { AuthContext } from '../context/AuthContext';
import toast from 'react-hot-toast';

const ExpenseList = ({ expenseList, setExpenseList , loadData, jwt}) => {
  // const {jwt} = useContext(AuthContext);

  async function deleteExpense(id) {
    
  
    try {
      const url = `http://localhost:8080/api/expense/${id}`;
      const respnse = await fetch(url, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${jwt}`
        }
      })
      const expense = await respnse.json();
      toast.success("expense deleted")
      loadData(jwt);
      // setExpenseList(updateList(expenseList, expense))
      
    } catch (error) {
      console.log(error)
    }

  }
  function updateList(expenseList, deletedExpense){
    
    expenseList = expenseList.filter(
      (expense) =>
        expense.id !== deletedExpense.id
      
    );
    
    return expenseList;
  }

  return (
    <div>
      {
        expenseList.map(
          (expense) => {
            return <Expense deleteExpense={deleteExpense} id={expense.id} name={expense.name} amount={expense.amount} />
          }
        )
      }
    </div>
  )
}

export default ExpenseList
