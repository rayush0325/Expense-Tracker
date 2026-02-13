import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import toast, { Toaster } from 'react-hot-toast';


function RegisterPage() {
    let username = "";
    let password = "";
    let role = "";
    async function handleSubmit(e) {
        try {
            e.preventDefault();
            const url = "http://localhost:8080/api/auth/register";
            const reponse = await fetch(url, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    'username': username,
                    'password': password,
                    'role': role
                })
            });
            toast.success("Registration Successful");
            e.target.reset();
        } catch (exception) {
           toast.error(exception)
        }


    }
    return (
        <>
            <div><Toaster/></div>
            <div className='flex-grow-1 overflow-auto d-flex justify-content-center'>
                <div className='d-flex flex-column justify-content-center w-50'>
                    <h2 className='text-center mb-4'>Sign Up</h2>
                    <Form className='container w-50 border border-black rounded-3 p-3' onSubmit={handleSubmit}

                    >
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="username" placeholder="Enter Username" onChange={
                                (e) => {
                                    username = e.target.value;
                                }
                            } />

                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password"
                                onChange={
                                    (e) => {
                                        password = e.target.value;
                                    }
                                }
                            />
                        </Form.Group>
                        <Form.Label>Role</Form.Label>
                        <Form.Select aria-label="Default select example" className=''
                            onChange={(e) => { role = e.target.value }}
                        >
                            <option>Select Role</option>
                            <option value="USER">USER</option>
                            <option value="ADMIN">ADMIN</option>
                        </Form.Select>
                        <Button variant="primary" type="submit" className='my-4 '>
                            Register
                        </Button>
                    </Form>
                </div>
            </div>
        </>
    );
}

export default RegisterPage
