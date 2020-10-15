import React from "react";
import axios from 'axios';

import { Line, Bar } from "react-chartjs-2";
import Chart from "chart.js";

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
  CardTitle,
  FormGroup,
  Form,
  Input,
  Container,
  Row,
  Col
} from "reactstrap";
// core components
import AdminHeader from "../components/Headers/AdminHeader.js";
import {
  chartOptions,
  parseOptions
} from "../variables/charts.js";

class Admin extends React.Component {
  constructor(props){
    super(props);

    this.state = this.initialState;
    
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmitWorker.bind(this);
    this.onSubmit = this.onSubmitTimeslot.bind(this);

    this.handleChange = this.handleChange.bind(this);

    if (window.Chart) {
      parseOptions(Chart, chartOptions());
    }
  }

  // Initial States
  initialState = {
    firstName: "", lastName: "", password: "", email: "", retypedPassword: "", errorResponse: [], 
    startTime: "", endTime: "", date: "", timeslots: [], workers: [], priceList: this.priceList(),
    analytics: {}, chart1: {}, chart2: {}
  }

componentDidMount() {
    this.getWorkers();
    this.getAdminId();
    this.getTimeslots();
    this.getAnalytics();
  }

  getAdminId() {
    return localStorage.getItem('id');
  }

  getWorkerId() {
    return this.worker.value;
  }

  getServiceId() {
    axios.get("http://localhost:8080/api/service/findbyadmin/" + this.getAdminId())
        .then(response => response.data)
        .then((data) => {
          localStorage.setItem('serviceId', data.serviceId);
        })
        .catch(error => { 
            console.log(error.response.data);
        });
  }

  getServiceName() {
    return localStorage.getItem('serviceName');
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

  getTimeslots() {
    console.log()
    axios.get("http://localhost:8080/api/timeslot/getbyadmin/" + this.getAdminId())
    .then(response => response.data)
    .then((data) => {
        this.setState({timeslots: data});
    })
    .catch(error => { 
        console.log(error.response.data)

        {/* BIT OF A HACK*/}
        this.setState({timeslots: error.response.data});
    });
  }

  getStatus(timeslot) {
    if(timeslot.booking == null) {
      return (
      <Badge color="" className="badge-dot mr-4">
        <i className="bg-success"/>
        Available
      </Badge>
      );
    } else {
      return (
        <Badge color="" className="badge-dot mr-4">
        <i className="bg-danger"/>
        Booked
      </Badge>
      );
    }
  }

  deleteTimeslot(timeslot) {
    axios.delete("http://localhost:8080/api/timeslot/delete/" + timeslot.timeslotId)
    .then(response => {
      alert("Deleted Timeslot"); 
      window.location.reload(false);
    })
    .catch(err => {
      // returns error message corresponding to issue
      if (typeof err.response.data.defaultMessage != 'undefined') {
        alert(err.response.data.defaultMessage);
      } else {
        alert(err.response.data[0].defaultMessage);
      }
    })
  }

  // Helper Method: Check passwords are the same for new workers
  checkPasswords() {
    if (this.state.password === this.state.retypedPassword) {
      return true;
    } else {
      return false;
    }
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
          this.getWorkers();
          this.getTimeslots();
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
    this.getServiceId();

    axios.get("http://localhost:8080/api/service/getavailableworkers/" + localStorage.getItem('serviceId'))
        .then(response => response.data)
        .then((data) => {
            // console.log(data[0].account);
            this.setState({workers: data});
            // this.setState({workers: data});
        })
        .catch(error => { 
            console.log(error.response.data)
            
            {/* BIT OF A HACK*/}
            this.setState({workers: error.response.data});
        });
  }

  checkValidTime() {
    
    let regex = new RegExp(/^([01]\d|2[0-3]):([0-5]\d)$/); 

    if (regex.test(this.state.startTime) && regex.test(this.state.endTime)) {
      return true;
    } else {
      alert("Invalid Start or End Time");
      return false;
    }
  }

  checkValidDate() {
    
    let regEx = new RegExp(/^\d{4}-\d{2}-\d{2}$/);

    if(!this.state.date.match(regEx)) {
      alert("Invalid Date")
      return false;  // Invalid format
    }

    var d = new Date(this.state.date);
    var dNum = d.getTime();

    if(!dNum && dNum !== 0) {
      alert("Invalid Date")
      return false; // NaN value, Invalid date
    }

    return d.toISOString().slice(0,10) === this.state.date;
  }

  // Helper Method: Generate optional prices
  priceList() {
    var list = new Array(50);

    for(var i = 4; i <= 200; i++){
      list.push((i/4).toFixed(2));
    }
    
    return list
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

		if (this.checkValidTime() && this.checkValidDate())
		{
      this.getServiceId();
      
      axios.post("http://localhost:8080/api/timeslot/save/" + localStorage.getItem('serviceId') + "/" + this.getWorkerId(), newTimeslot)
			.then(response =>
			{
				if (response.data != null)
				{ 
          this.setState(this.initialState);
          this.getWorkers();
          this.getTimeslots();
					alert("New Timeslot Added");
				}
			})
			.catch(err =>
			{
        console.log(err.response.data);
        alert(err.response.data)
			});
		}
		else
		{
      // alert("Errors");
		}
  }

  getAnalytics() {
      axios.get("http://localhost:8080/api/Account/adminanalytics/" + this.getAdminId())
      .then(response => response.data)
      .then((data) => {
          console.log(data);
          this.setState({analytics: data});

          this.getChart1();
          this.getChart2();
      })
      .catch(error => { 
          console.log(error.response.data)
          
          {/* BIT OF A HACK*/}
          this.setState({workers: error.response.data});
      });
  }

  getChart1() {
    this.setState({
      chart1: {
        options: {
          scales: {
            yAxes: [
              {
                gridLines: {
                  color: "#525f7f",
                  zeroLineColor: "#525f7f"
                },
                ticks: {
                  callback: function(value) {
                    if (!(value % 1)) {
                      return '$' + value
                    }
                  }
                }
              }
            ]
          },
          tooltips: {
            callbacks: {
              label: function(item, data) {
                var label = data.datasets[item.datasetIndex].label || "";
                var yLabel = item.yLabel;
                var content = "";
      
                if (data.datasets.length > 1) {
                  content += label;
                }
      
                content += "$" + yLabel;
                return content;
              }
            }
          }
        },
        data: {
          labels: this.state.analytics.next_week_dates,
          datasets: [
            {
              label: "Income",
              data: this.state.analytics.next_week_expected_income
            }
          ]
        }
      }
    })
  }

  getChart2() {
    this.setState({
      chart2: {
        options: {
          scales: {
            yAxes: [
              {
                ticks: {
                  callback: function(value) {
                    if (!(value % 1)) {
                      //return '$' + value + 'k'
                      return value;
                    }
                  }
                }
              }
            ]
          },
          tooltips: {
            callbacks: {
              label: function(item, data) {
                var label = data.datasets[item.datasetIndex].label || "";
                var yLabel = item.yLabel;
                var content = "";
                if (data.datasets.length > 1) {
                  content += label;
                }
                content += yLabel;
                return content;
              }
            }
          }
        },
        data: {
          labels: ["Booked", "Not Booked"],
          datasets: [
            {
              label: "Bookings",
              data: [this.state.analytics.booked_today, this.state.analytics.unbooked_today],
              maxBarThickness: 10
            }
          ]
        }
      }
    })
  }
    
    // Render
    render() {

      const {firstName, lastName, email, password, retypedPassword, startTime, endTime, date, timeslots, workers, priceList, chart1, chart2} = this.state;
      const { active_bookings, completed_bookings, worker_count } = this.state.analytics;
      console.log(this.state.chart1);
      return (
        <>
          <AdminHeader />
          {/* Page content */}
          <Container className="mt--7" fluid>
            <Row>
              <Col>
                <Card className="bg-secondary shadow">

                  {/* VIEW TIMESLOTS AREA*/}
                  <CardHeader className="border-0">
                    <h3 className="mb-0"> {this.getServiceName()} Time slots</h3>
                  </CardHeader>
                  <CardBody>
                    <Table className="align-items-center table-flush" responsive>
                      <thead className="thead-light">
                        <tr>
                          <th scope="col"> Date</th>
                          <th scope="col">Start Time</th>
                          <th scope="col">End Time</th>
                          <th scope="col">Price</th>
                          <th scope="col">Worker Assigned</th>
                          <th scope="col">Availability Status</th>
                          <th scope="col" /> 
                        </tr>
                      </thead>
                      <tbody>
                        {
                          this.state.timeslots.length === 0 ? 
                          <tr align="center">
                              <td colSpan="7"> No Time slots. </td>
                          </tr> : 
                          this.state.timeslots.map((timeslot) => (
                            <tr key={timeslot.timeslotId}> {/**/}
                                <td>{timeslot.date}</td> {/* DATE */}
                                <td>{timeslot.startTime}</td> {/* START TIME */}
                                <td>{timeslot.endTime}</td> {/* FINISH TIME */}
                                <td>${timeslot.price} </td> {/*  PRICE */}
                                <td>{timeslot.worker.account.firstName} {timeslot.worker.account.lastName} </td> {/* WORKER NAME */}
                                <td> {/* PENDING STATUS */}
                                      {this.getStatus(timeslot)}
                                </td>
                                <td className="text-right">
                                  <Button className="pr-5 pl-5" color="primary" type="button" onClick={e => this.deleteTimeslot(timeslot)}>DELETE</Button>
                                </td>
                            </tr>
                          ))
                        }
                      </tbody>
                    </Table>
                  </CardBody>

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
                          <Col lg="4">

                            {/* START TIME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-first-name"
                              >
                                Start Time
                              </label>
                              <div>
                                <Input required
                                  className="form-control-alternative"
                                  name="startTime"
                                  placeholder="00:01"
                                  type="text"
                                  value= {startTime}
                                  onChange = {this.onChange}
                                />
                              </div>
                            </FormGroup>
                          </Col>
                          <Col lg="4">

                            {/* END TIME INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-last-name"
                              >
                                End Time
                              </label>
                              <div> 
                                <Input required
                                  className="form-control-alternative"
                                  name="endTime"
                                  placeholder="23:59"
                                  type="text"
                                  value= {endTime}
                                  onChange = {this.onChange}
                                />
                              </div>
                            </FormGroup>
                          </Col>
                          <Col lg="4">

                            {/* DATE INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-email"
                              >
                                Date
                              </label>
                              <div>
                                <Input required
                                  className="form-control-alternative"
                                  name="date"
                                  placeholder="YYYY-MM-DD"
                                  type="text"
                                  value= {date}
                                  onChange = {this.onChange}
                                />
                              </div>
                            </FormGroup>
                          </Col>
                          </Row>
                          <Row>
                          <Col lg="4">

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
                          <Col lg="3">
                            
                            {/* GET REQUEST ALL WORKERS OF A SERVICE */}
                            {/* WORKER INPUT */}
                            <FormGroup>
                              <label
                                className="form-control-label"
                                htmlFor="input-password"
                              >
                                Worker
                              </label>
                              <div>
                                <select id = "workers" onChange={this.handleChange} ref = {(input)=> this.worker = input}>
                                  {this.state.workers.map(worker => {
                                    return (
                                      <option value={worker.account.id}> {worker.account.firstName} {worker.account.lastName} </option>
                                      
                                    )
                                  })}
                                </select>
                              </div>
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

                  {/* ANALYTICS SECTION */}
                  <CardHeader className="bg-white border-0">
                    <Row className="align-items-center">
                      <Col xs="8">
                        <h3 className="mb-0">Analytics</h3>
                      </Col>
                    </Row>
                  </CardHeader>
                  <CardBody>
                  <Row>

                    {/* ACTIVE BOOKINGS CARD */}
                    <Col lg="6" xl="4">
                      <Card className="card-stats mb-4 mb-xl-0">
                        <CardBody>
                          <Row>
                            <div className="col">
                              <CardTitle
                                tag="h5"
                                className="text-uppercase text-muted mb-0"
                              >
                                Active Bookings
                              </CardTitle>
                              <span className="display-2 h1 font-weight-bold mb-0">
                                {active_bookings}
                              </span>
                            </div>
                          </Row>
                        </CardBody>
                      </Card>
                    </Col>

                    {/* COMPLETED BOOKINGS CARD */}
                    <Col lg="6" xl="4">
                      <Card className="card-stats mb-4 mb-xl-0">
                        <CardBody>
                          <Row>
                            <div className="col">
                              <CardTitle
                                tag="h5"
                                className="text-uppercase text-muted mb-0"
                              >
                                Completed Bookings
                              </CardTitle>
                              <span className="display-2 h1 font-weight-bold mb-0">
                                {completed_bookings}
                              </span>
                            </div>
                          </Row>
                        </CardBody>
                      </Card>
                    </Col>

                    {/* WORKERS CARD */}
                    <Col lg="6" xl="4">
                      <Card className="card-stats mb-4 mb-xl-0">
                        <CardBody>
                          <Row>
                            <div className="col">
                              <CardTitle
                                tag="h5"
                                className="text-uppercase text-muted mb-0"
                              >
                                Workers
                              </CardTitle>
                              <span className="display-2 h1 font-weight-bold mb-0">
                                {worker_count}
                              </span>
                            </div>
                          </Row>
                        </CardBody>
                      </Card>
                    </Col>
                  </Row>

                  {/* CHARTS */}
                  <Row className="mt-4">

                    {/* PRICE CHART */}
                    <Col className="mb-5 mb-xl-0" xl="8">
                      <Card className="bg-gradient-default shadow">
                        <CardHeader className="bg-transparent">
                          <Row className="align-items-center">
                            <div className="col">
                              <h6 className="text-uppercase text-light ls-1 mb-1">
                                Income
                              </h6>
                              <h2 className="text-white mb-0">Income for the next week</h2>
                            </div>
                          </Row>
                        </CardHeader>
                        <CardBody>
                          {/* Chart */}
                          <div className="chart">
                            <Line
                              data={chart1.data}
                              options={chart1.options}
                            />
                          </div>
                        </CardBody>
                      </Card>
                    </Col>

                    {/* BOOKINGS CHART */}
                    <Col xl="4">
                      <Card className="shadow">
                        <CardHeader className="bg-transparent">
                          <Row className="align-items-center">
                            <div className="col">
                              <h6 className="text-uppercase text-muted ls-1 mb-1">
                                Daily Overview
                              </h6>
                              <h2 className="mb-0">Bookings today</h2>
                            </div>
                          </Row>
                        </CardHeader>
                        <CardBody>
                        {/* Chart */}
                          <div className="chart">
                            <Bar
                              data={chart2.data}
                              options={chart2.options}
                            />
                          </div>
                        </CardBody>
                      </Card>
                    </Col>
                  </Row>
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