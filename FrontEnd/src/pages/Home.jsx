import { useContext, useState } from "react"
import { AuthContext } from "../context/AuthContext"
import { Button } from "bootstrap";
const Home = () => {
  // const [count, setCount] = useState(0);
  //  const {jwt} = useContext(AuthContext);
   
  return (
   
    <div className=' flex-grow-1 overflow-auto d-flex flex-column justify-content-center'>
      <div className='d-flex justify-content-center w-100 h-50'>
        <div className='w-50 h-100  align-middle d-flex justify-content-center'>
          <div className='d-flex flex-column justify-content-center text-center'>
            {/* <button onClick={()=>{setCount(count => count+1)}}>count = {count}</button> */}
            <h1>Welcome</h1>
            <div>To</div>
            <div className='fs-1'>Expense Manager</div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Home
