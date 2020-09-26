import React from "react";
import axios from 'axios';

// CREATE SEPARATE ADMIN PROFILE PAGE??

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
import DatePicker from "react-datepicker";


import "react-datepicker/dist/react-datepicker.css";

class Admin extends React.Component {
  constructor(props){
    super(props);

    this.state = this.initialState;
    
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmitWorker.bind(this);
    this.onSubmit = this.onSubmitTimeslot.bind(this);

    this.handleChange = this.handleChange.bind(this)
  }

  initialState = {
    firstName: "", lastName: "", password: "", email: "", retypedPassword: "", errorResponse: [], 
    startTime: "", endTime: "", date: new Date(), worker: "", price: "", times: [1, 2, 3, 4, 5]
  }

  priceList() {
    
    var list = new Array(50);

    for(var i = 1; i <= 50; i++){
      list[i] = i;
    }
    
    return list
  }

  sizes = this.priceList;

  handleChange = date => {
    this.setState({
      startDate: date
    });
  };

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

	onSubmitWorker = e =>
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

		if (this.checkPasswords())
		{
			axios.post("http://localhost:8080/api/Account/saveworker/" + this.getAdminId(), newWorker)
			.then(response =>
			{
				if (response.data != null)
				{ 
					this.setState(this.initialState);
					//localStorage.setItem('username', newWorker.firstName);
					alert("New Worker Added");
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
					alert(err.response.data.errors[0].defaultMessage);
				}
			});
		}
		else
		{
			alert("Passwords are not the same");
		}
  }
  
  getServiceId() {

  }

  getWorkerId() {

  }

  onSubmitTimeslot = e =>
	{
    e.preventDefault();

		const newTimeslot =
		{
      date: this.state.date,
      price: this.state.price,
      startTime: this.state.startTime,
      endTime: this.state.endTime
		}

		if (/*this.checkPasswords()*/ true)
		{
			axios.post("http://localhost:8080/api/timeslot/save/" + this.getServiceId() + "/" + this.getWorkerId(), newTimeslot)
			.then(response =>
			{
				if (response.data != null)
				{ 
					this.setState(this.initialState);
					//localStorage.setItem('username', newWorker.firstName);
					alert("New Timeslot Added");
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
					alert(err.response.data.errors[0].defaultMessage);
				}
			});
		}
		// else
		// {
		// 	alert("Passwords are not the same");
		// }
  }
    
    // Render
    render() {

      const {firstName, lastName, email, password, retypedPassword, startTime, endTime, date, worker, price, times} = this.state;
      return (
        <>
          <AdminHeader />
          {/* Page content */}
          <Container className="mt--7" fluid>
            <Row>
              <Col>
                <Card className="bg-secondary shadow">

                  {/* ADD WORKERS AREA*/}
                  <CardHeader className="bg-white border-0">
                    <Row className="align-items-center">
                      <Col xs="8">
                        <h3 className="mb-0">Add Worker</h3>
                      </Col>
                    </Row>
                  </CardHeader>
                  <CardBody>
                    <Form onSubmit={this.onSubmitWorker} id="addWorkerFormId">

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
                          <Col lg="12">

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
                        </Row>
                        <Row>
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

                          <Col lg="6">

                            {/* RETYPED PASSWORD INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Re-Type Password
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="retypedPassword"
                                placeholder="Re-Type Password" 
                                type="password"
                                value= {retypedPassword}
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
                    </Form>
                  </CardBody>

                  {/* ADD TIME SLOT */}
                  <CardHeader className="bg-white border-0">
                    <Row className="align-items-center">
                      <Col xs="8">
                        <h3 className="mb-0">Add Time Slot</h3>
                      </Col>
                    </Row>
                  </CardHeader>
                  <CardBody>
                    <Form onSubmit={this.onSubmitTimeslot} id="addWorkerFormId">

                      <h6 className="heading-small text-muted mb-4"> Time Slot information </h6>
                      <div className="pl-lg-4">
                        <Row>
                          <Col lg="6">

                            {/* START TIME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-first-name"
                              >
                                Start Time
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="startTime"
                                placeholder="Start Time"
                                type="text"
                                value= {startTime}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                          <Col lg="6">

                            {/* END TIME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-last-name"
                              >
                                End Time
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="endTime"
                                placeholder="End Time"
                                type="text"
                                value= {endTime}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg="12">

                            {/* DATE INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-email"
                              >
                                Date
                              </label>
                              <div>
                                <DatePicker required
                                  className="form-control-alternative"
                                  selected={this.state.date}
                                  onSelect={this.handleSelect} //when day is clicked
                                  onChange={this.handleChange} //only when value has changed
                                />
                              </div>
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg="6">

                            {/* WORKER INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Worker
                              </label>
                              <Input required autoComplete="off"
                                className="form-control-alternative"
                                name="worker"
                                placeholder="Worker"
                                type="password"
                                value= {worker}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>

                          <Col lg="6">

                            {/* PRICE INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Price
                              </label>
                              <div>
                                <select onChange={this.handleChange}>
                                  {times.map(time => {
                                    return (
                                      <option value={time}> {time} </option>
                                    )
                                  })}
                                </select>
                              </div>
                            </FormGroup>
                          </Col>
                        </Row>

                        {/* ADD WORKER SUBMISSION BUTTON */}
                        <div className="text-center">
                          {/*<Link to="/admin/services_dashboard">*/}
                            <input type="submit" className="btn btn-primary btn-block mt-4" value="Add Timeslot"/>
                          {/*</Link>*/}
                        </div>

                      </div>
                      <hr className="my-4" />
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