/*!

=========================================================
* Argon Dashboard React - v1.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";
import axios from 'axios';

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
import UserHeader from "../components/Headers/UserHeader.js";

class Profile extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
        upcomingBookings : []
    };
  }

  componentDidMount() {
    this.getUpcomingBookings();
  }

  getCustomerId() {
      return localStorage.getItem('id');
  }   

  getUpcomingBookings() {
    console.log()
    axios.get("http://localhost:8080/api/booking/getbycustomer/" + this.getCustomerId())
    .then(response => response.data)
    .then((data) => {
        this.setState({upcomingBookings: data});
    })
    .catch(error => { 
        console.log(error.response.data)

        {/* BIT OF A HACK*/}
        this.setState({upcomingBookings: error.response.data});
    });
  }
  // Connect Axios


  // GET Request

  
  // Render
  render() {
    return (
      <>
        <UserHeader />
        {/* Page content */}
        <Container className="mt--7" fluid>
          <Row>
            <Col>
              <Card className="bg-secondary shadow">
                <CardHeader className="bg-white border-0">
                  <Row className="align-items-center">
                    <Col xs="8">
                      <h3 className="mb-0">My account</h3>
                    </Col>
                  </Row>
                </CardHeader>
                <CardBody>
                  <Form>
                    {/* User Information */}
                    {/* Replace placeholders with token information later */}
                    <h6 className="heading-small text-muted mb-4"> User information </h6>
                    <div className="pl-lg-4">
                      <Row>
                        <Col lg="6">
                          <FormGroup>
                            <label
                              className="form-control-label"
                              htmlFor="input-first-name"
                            >
                              First Name
                            </label>
                            <Input readOnly value={"First Name"}
                              className="form-control-alternative"
                              // defaultValue=""
                              id="input-first-name"
                              placeholder="First Name"
                              type="text"
                            />
                          </FormGroup>
                        </Col>
                        <Col lg="6">
                          <FormGroup>
                            <label
                              className="form-control-label"
                              htmlFor="input-last-name"
                            >
                              Last Name
                            </label>
                            <Input readOnly value={"Last Name"}
                              className="form-control-alternative"
                               // defaultValue=""
                              id="input-last-name"
                              placeholder="Last Name"
                              type="text"
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col lg="6">
                          <FormGroup>
                            <label
                              className="form-control-label"
                              htmlFor="input-email"
                            >
                              Email Address
                            </label>
                            <Input readOnly value={"Email Address"}
                              className="form-control-alternative"
                              // defaultValue=""
                              id="input-email"
                              placeholder="Email Address"
                              type="email"
                            />
                          </FormGroup>
                        </Col>
                        <Col lg="6">
                          <FormGroup>
                            <label
                              className="form-control-label"
                              htmlFor="input-password"
                            >
                              Password
                            </label>
                            <Input readOnly value={"Password"}
                              className="form-control-alternative"
                              // defaultValue=""
                              id="input-password"
                              placeholder="Password"
                              type="password"
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                    </div>
                    <hr className="my-4" />

                    {/* Upcoming Bookings */}
                    <h6 className="heading-small text-muted mb-4">Upcoming Bookings</h6>
                    <div className="pl-lg-4">
                      {/* Table */}
                      <Row>
                        <div className="col">
                          <Card className="shadow">
                            <CardHeader className="border-0">
                              <h3 className="mb-0">Upcoming Bookings</h3>
                            </CardHeader>
                            <Table className="align-items-center table-flush" responsive>
                              <thead className="thead-light">
                                <tr>
                                  <th scope="col"> Date</th>
                                  <th scope="col">Start Time</th>
                                  <th scope="col">End Time</th>
                                  <th scope="col">Price</th>
                                  <th scope="col">Worker Assigned</th>
                                  <th scope="col" />
                                </tr>
                              </thead>
                              <tbody>
                                {
                                  this.state.upcomingBookings.length === 0 ? 
                                  <tr align="center">
                                      <td colSpan="7"> No Time slots Available. </td>
                                  </tr> : 
                                  this.state.upcomingBookings.map((upcomingBooking) => (
                                    <tr key={upcomingBooking.timeslotId}> {/**/}
                                        <td>{upcomingBooking.date}</td> {/* DATE */}
                                        <td>{upcomingBooking.startTime}</td> {/* START TIME */}
                                        <td>{upcomingBooking.endTime}</td> {/* FINISH TIME */}
                                        <td>{/*timeslot.price*/} PRICE </td> {/*  PRICE */}
                                        <td>{/*timeslot.serviceName*/} WORKER NAME</td> {/* WORKER NAME */}
                                        <td className="text-right">
                                          <UncontrolledDropdown> {/* OPTION TO BOOK */}
                                              <DropdownToggle
                                              className="btn-icon-only text-light"
                                              href="#pablo"
                                              role="button"
                                              size="sm"
                                              color=""
                                              onClick={e => e.preventDefault()}
                                              >
                                              <i className="fas fa-ellipsis-v" />
                                              </DropdownToggle>
                                              <DropdownMenu className="dropdown-menu-arrow" right>
                                                <DropdownItem
                                                    href="#pablo"
                                                    onClick={e => e.preventDefault()}
                                                >
                                                    Edit Booking
                                                </DropdownItem>
                                                <DropdownItem
                                                    href="#pablo"
                                                    onClick={e => e.preventDefault()}
                                                >
                                                    Cancel Booking
                                                </DropdownItem>
                                              </DropdownMenu>
                                          </UncontrolledDropdown>
                                        </td>
                                    </tr>
                                  ))
                                }
                              </tbody>
                            </Table>
                          </Card>
                        </div>
                      </Row>
                    </div>
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

export default Profile;
