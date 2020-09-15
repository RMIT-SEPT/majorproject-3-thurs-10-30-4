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
import axios from "axios";

// reactstrap components
import {
  Badge,
  Card,
  Container,
  CardHeader,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  Table,
  Row,
  UncontrolledDropdown,
} from "reactstrap";
// core components
import TimeslotHeader from "../components/Headers/TimeslotHeader.js";

class Timeslots extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
        timeslots : []
    };
  }

  componentDidMount() {
    this.getTimeslots();
  }

  getServiceChosenID() {
      return localStorage.getItem('serviceId');
  }   

  getTimeslots() {
    console.log()
    axios.get("http://localhost:8080/api/timeslot/getbyservice/" + this.getServiceChosenID())
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

  
  
  render() {
    return (
      <>
        <TimeslotHeader />
        {/* Page content */}
        <Container className="mt--7" fluid>
          {/* Table */}
          <Row>
            <div className="col">
              <Card className="shadow">
                <CardHeader className="border-0">
                  <h3 className="mb-0">Time slots</h3>
                </CardHeader>
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
                          <td colSpan="7"> No Time slots Available. </td>
                      </tr> : 
                      this.state.timeslots.map((timeslot) => (
                        <tr key={timeslot.timeslotId}> {/**/}
                            <td>{timeslot.date}</td> {/* DATE */}
                            <td>{timeslot.startTime}</td> {/* START TIME */}
                            <td>{timeslot.endTime}</td> {/* FINISH TIME */}
                            <td>{/*timeslot.price*/} PRICE </td> {/*  PRICE */}
                            <td>{/*timeslot.serviceName*/} WORKER NAME</td> {/* WORKER NAME */}
                            <td> {/* PENDING STATUS */}
                              <Badge color="" className="badge-dot mr-4">
                                  <i className="bg-warning" />
                                  pending
                              </Badge>
                              </td>
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
                                      Book
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
        </Container>
      </>
    );
  }
}

export default Timeslots;
