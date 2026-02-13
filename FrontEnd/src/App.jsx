import './App.css'
import MyNavbar from './components/Navbar'
import { Route, Routes } from 'react-router-dom'
import LoginPage from './pages/loginPage'
import Home from './pages/Home'
import RegisterPage from './pages/RegisterPage'
import Dashboard from './pages/Dashboard'

function App() {
  

  return (
    <div className=' vh-100 d-flex flex-column '>
      <MyNavbar/>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/login' element={ <LoginPage/> }/>
        <Route path='/register' element={ <RegisterPage/> }/>
        <Route path='/dashboard' element={ <Dashboard/> }/>
      </Routes>
      <MyNavbar/>
    </div>
  )
}

export default App
