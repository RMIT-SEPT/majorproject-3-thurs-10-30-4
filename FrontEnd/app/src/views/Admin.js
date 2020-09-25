import React from "react";
import axios from 'axios';

// FIX ISSUES WITH ERROR HANDLING MESSAGES

// reactstrap components
import {
  Button,
  Table,
  Badge,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Form,
  Input,
  Container,
  Row,
  Col
} from "reactstrap";
// core components
import AdminHeader from "../components/Headers/AdminHeader.js";

class Admin extends React.Component {
  constructor(props){
    super(props);

    this.state = this.initialState;
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);

  }

  initialState = {
    firstName: "", lastName: "", password: "", email: "", retypedPassword: "", errorResponse: []
  }

  onChange = e => {
    this.setState({[e.target.name]: e.target.value});
  }

  checkPasswords() {
    if (this.state.password === this.state.retypedPassword) {
      return true;
    } else {
      return false;
    }
  }

  getAdminId() {
    return localStorage.getItem('id');
  }

	onSubmit = e =>
	{  
		e.preventDefault();

		const newWorker =
		{
      adminId: this.getAdminId(),
      firstName: this.state.firstName,
			lastName: this.state.lastName,
			password: this.state.password,
			email:this.state.email
		}

		if (/*this.checkPasswords()*/ true)
		{
			axios.post("http://localhost:8080/api/Account/saveworker/" + this.getAdminId(), newWorker)
			.then(response =>
			{
				if (response.data != null)
				{ 
					this.setState(this.initialState);
					//localStorage.setItem('username', newWorker.firstName);
					alert("New Worker Added"); 
					// window.location.href = "http://localhost:3000/auth/login";
				}
			})
			.catch(err =>
			{
				console.log("Error");
				if (typeof err.response.data.defaultMessage != 'undefined')
				{
					alert(err.response.data.defaultMessage);
				}
				else
				{
					alert(err.response.data[0].defaultMessage);
				}
			});
		}
		else
		{
			alert("Passwords are not the same");
		}
	}
  
    
    // Render
    render() {

      const {firstName, lastName, email, password, retypedPassword} = this.state;
      return (
        <>
          <AdminHeader />
          {/* Page content */}
          <Container className="mt--7" fluid>
            <Row>
              <Col>
                <Card className="bg-secondary shadow">
                  <CardHeader className="bg-white border-0">
                    <Row className="align-items-center">
                      <Col xs="8">
                        <h3 className="mb-0">Add Worker</h3>
                      </Col>
                    </Row>
                  </CardHeader>
                  <CardBody>
                    <Form onSubmit={this.onSubmit} id="addWorkerFormId">

                      {/* SHOW AND DELETE WORKERS AREA*/}



                      {/* ADD WORKERS AREA*/}

                      <h6 className="heading-small text-muted mb-4"> Worker information </h6>
                      <div className="pl-lg-4">
                        <Row>
                          <Col lg="6">

                            {/* FIRST NAME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-first-name"
                              >
                                First Name
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="firstName"
                                placeholder="First Name"
                                type="text"
                                value= {firstName}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                          <Col lg="6">

                            {/* LAST NAME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-last-name"
                              >
                                Last Name
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="lastName"
                                placeholder="Last Name"
                                type="text"
                                value= {lastName}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg="6">

                            {/* EMAIL INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-email"
                              >
                                Email Address
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="email"
                                placeholder="Email Address"
                                type="email"
                                value= {email}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                          <Col lg="6">

                            {/* PASSWORD INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Password
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="password"
                                placeholder="Password"
                                type="password"
                                value= {password}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                        </Row>

                        {/* ADD WORKER SUBMISSION BUTTON */}
                        <div className="text-center">
                          {/*<Link to="/admin/services_dashboard">*/}
                            <input type="submit" className="btn btn-primary btn-block mt-4" value="Add Worker"/>
                          {/*</Link>*/}
                        </div>

                      </div>
                      <hr className="my-4" />


                      {/* ADD TIMESLOTS AREA*/}



                    </Form>
                  </CardBody>
                </Card>
              </Col>
            </Row>
          </Container>
        </>
      );
    }
  }

export default Admin;