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
            email: localStorage.getItem('email')
        };
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