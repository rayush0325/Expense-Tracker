import { useState } from 'react'
import { AuthContext } from '../context/AuthContext'
const AuthProvider = ({children}) => {
    const [jwt, setJwt] = useState(null);
  return (
    <AuthContext.Provider value = {{jwt, setJwt}}>
        {children}
    </AuthContext.Provider>
  )
}

export default AuthProvider
