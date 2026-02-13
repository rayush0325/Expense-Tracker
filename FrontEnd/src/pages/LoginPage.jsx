import { useContext } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import toast, { Toaster } from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

function LoginPage() {
    const {jwt, setJwt} = useContext(AuthContext);
    const navigate = useNavigate();
    let username = "";
    let passowrd = "";
    async function handleSubmit(e) {
        e.preventDefault();
        const url = "http://localhost:8080/api/auth/login";
        try {
            const reponse = await fetch(url, {
                method : 'POST',
                headers : {
                    'Content-Type' : 'application/json',
                },
                body : JSON.stringify({
                    'username' : username,
                    'password' : passowrd
                })
            });
            const data = await reponse.json();
            setJwt(data.jwtToken);
            toast.success("login successful");
            navigate("/dashboard");
        } catch (error) {
            toast.error(error);
        }
    }
    return (
        <>
            <div><Toaster /></div>
            <div className='flex-grow-1 overflow-auto d-flex justify-content-center'>
                <div className='d-flex flex-column justify-content-center w-50'>
                    <h2 className='text-center mb-4'>Sign In</h2>
                    <Form className='container w-50 border border-black rounded-3 p-3' onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="username" placeholder="Enter Username" onChange={(e) => { username = e.target.value }} />

                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" onChange={(e) => { passowrd = e.target.value }} />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                </div>
            </div>
        </>
    );
}

export default LoginPage;


