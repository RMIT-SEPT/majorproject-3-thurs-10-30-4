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
  Button,
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

  getServiceName() {
    return localStorage.getItem('serviceName');
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

  book(timeslot) {
    
    const newBooking = {
      timeslotId: timeslot.timeslotId,
      customerId: 1
    }

    axios.post("http://localhost:8080/api/booking/save", newBooking)
      .then(response => {
        alert("Booked"); 
      })
      .catch(err => {

        // returns error message corresponding to issue
        if (typeof err.response.data.defaultMessage != 'undefined') {
          alert(err.response.data.defaultMessage);
        } else {
          alert(err.response.data[0].defaultMessage);
        }

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
                  <h3 className="mb-0"> {this.getServiceName()} Time slots</h3>
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
                            <td>${timeslot.price} </td> {/*  PRICE */}
                            <td>{/*timeslot.serviceName*/} WORKER NAME</td> {/* WORKER NAME */}
                            <td> {/* PENDING STATUS */}
                              <Badge color="" className="badge-dot mr-4">
                                  <i className="bg-warning" />
                                  pending
                              </Badge>
                              </td>
                              <td className="text-right">
                              <Button className="pr-5 pl-5" color="primary" type="button" onClick={e => this.book(timeslot)}>BOOK</Button>
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
