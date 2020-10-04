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
import TimePicker from 'react-time-picker';

import "react-datepicker/dist/react-datepicker.css";

class Admin extends React.Component {
  constructor(props){
    super(props);

    this.state = this.initialState;
    
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmitWorker.bind(this);
    this.onSubmit = this.onSubmitTimeslot.bind(this);

    this.handleChange = this.handleChange.bind(this);
  }

  // Initial States
  initialState = {
    firstName: "", lastName: "", password: "", email: "", retypedPassword: "", errorResponse: [], 
    startTime: '', endTime: '', date: new Date(), worker: "", price: "", priceList: this.priceList()
  }

  // Helper Method: Generate optional prices
  priceList() {
    var list = new Array(50);

    for(var i = 4; i <= 200; i++){
      list.push((i/4).toFixed(2));
    }
    
    return list
  }

  // HandleChange Method
  handleChange = date => {
    this.setState({
      startDate: date
    });
  };

  // Primary OnChange Method
  onChange = e => {
    this.setState({[e.target.name]: e.target.value});
  }

  // Secondary OnChange Method Related to time
  onChangeStartTime = startTime => this.setState({ startTime })
  onChangeEndTime = endTime => this.setState({ endTime })

  // Helper Method: Check passwords are the same for new workers
  checkPasswords() {
    if (this.state.password === this.state.retypedPassword) {
      return true;
    } else {
      return false;
    }
  }

  // Helper Method: Gets Admin ID for URL Building
  getAdminId() {
    return localStorage.getItem('id');
  }

  // POST Request: Create A Valid Worker
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

  // GET Request: Retreieves All Workers for a given service
  getWorkers() {
    axios.get("http://localhost:8080/api/service/getall")
        .then(response => response.data)
        .then((data) => {
            this.setState({workers: data});
        })
        .catch(error => { 
            console.log(error.response.data)
            
            {/* BIT OF A HACK*/}
            this.setState({workers: error.response.data});
        });
  }
  
  // Helper Method: Gets the Service ID associated with the administrator logged in for URL Building
  getServiceId() {

  }

  // Helper Method: Gets the Worker ID the timeslots is being assigned to for URL Building
  getWorkerId() {

  }

  // POST Request: Get a valid Timeslot
  onSubmitTimeslot = e =>
	{
    e.preventDefault();

    let res = this.menu.value;

		const newTimeslot =
		{
      date: this.state.date,
      price: res,
      startTime: this.state.startTime,
      endTime: this.state.endTime
		}

		if (/*this.checkPasswords()*/ false)
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
		else
		{
      // alert("Passwords are not the same");
      // console.log(this.state.startTime);
      // console.log(this.state.endTime);

      // HOW TO GET PRICE
      let res = this.menu.value;
      alert(res);
		}
  }
    
    // Render
    render() {

      const {firstName, lastName, email, password, retypedPassword, startTime, endTime, date, worker, price, priceList} = this.state;
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
                            <input type="submit" className="btn btn-primary btn-block mt-4" value="Add Worker"/>
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
                          <Col lg="3">

                            {/* START TIME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-first-name"
                              >
                                Start Time
                              </label>
                              <div>
                                <TimePicker // required
                                  // className="form-control-alternative"
                                  // name="startTime"
                                  onChangeStartTime={this.onChangeStartTime}
                                  value={startTime}
                                  locale="sv-sv"
                                />
                              </div>
                            </FormGroup>
                          </Col>
                          <Col lg="3">

                            {/* END TIME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-last-name"
                              >
                                End Time
                              </label>
                              <div> 
                                <TimePicker // required
                                  // className="form-control-alternative"
                                  // name="endTime"
                                  onChangeEndTime={this.onChangeEndTime}
                                  value={endTime}
                                  locale="sv-sv"
                                />
                              </div>
                            </FormGroup>
                          </Col>
                          <Col lg="3">

                            {/* DATE INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-email"
                              >
                                Date
                              </label>
                              <div>
                                <DatePicker // required

                                  // className="form-control-alternative"
                                  name="date"
                                  value={date}
                                  type="date"

                                  selected={this.state.date}
                                  onSelect={this.handleSelect} //when day is clicked
                                  onChange={this.handleChange} //only when value has changed
                                />
                              </div>
                            </FormGroup>
                          </Col>
                          <Col lg="3">

                            {/* PRICE INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Price
                              </label>
                              <div>
                                <select id = "priceSelected" onChange={this.handleChange} ref = {(input)=> this.menu = input}>
                                  {priceList.map(aPrice => {
                                    return (
                                      <option value={aPrice}> $ {aPrice} </option>
                                      
                                    )
                                  })}
                                </select>
                              </div>
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg="12">
                            
                            {/* GET REQUEST ALL WORKERS OF A SERVICE */}
                            {/* WORKER INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Worker
                              </label>
                              <Input // required autoComplete="off"
                                className="form-control-alternative"
                                name="worker"
                                placeholder="Worker"
                                type="password"
                                value= {worker}
                                onChange = {this.onChange}
                              />
                            </FormGroup>
                          </Col>
                        </Row>

                        {/* ADD TIMESLOT SUBMISSION BUTTON */}
                        <div className="text-center">
                            <input type="submit" className="btn btn-primary btn-block mt-4" value="Add Timeslot"/>
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