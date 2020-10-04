import React from "react";
import axios from 'axios';

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

  import WorkerHeader from "../components/Headers/WorkerHeader.js";

class Worker extends React.Component {
    constructor(props) {    
        super(props);
        this.state = {
            firstName: localStorage.getItem('firstName'),
            lastName: localStorage.getItem('lastName'),
            email: localStorage.getItem('email'),
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
        axios.get("http://localhost:8080/api/timeslot/getbyworkerid/" + this.getCustomerId())
        .then(response => response.data)
        .then((data) => {
            this.setState({upcomingBookings: data});
        })
        .catch(error => { 
            this.setState({upcomingBookings: error.response.data});
        });
      }

    render() {
        return(
            <>
            <WorkerHeader/>
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
                                <Input readOnly value={this.state.firstName}
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
                                <Input readOnly value={this.state.lastName}
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
                                <Input readOnly value={this.state.email}
                                  className="form-control-alternative"
                                  // defaultValue=""
                                  id="input-email"
                                  placeholder="Email Address"
                                  type="email"
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

export default Worker;