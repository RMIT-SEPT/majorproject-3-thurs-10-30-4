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

// reactstrap components
import {
  Card,
  CardHeader,
  Table,
  Container,
  Row,
} from "reactstrap";
// core components
import AvailableTimesHeader from "components/Headers/AvailableTimesHeader.js";
import AvailabilityCard from "../components/Availability/AvailabilityCard";

class AvailableTimes extends React.Component {
  render() {
    return (
      <>
        <AvailableTimesHeader />
        {/* Page content */}
        <Container className="mt--7" fluid>
          {/* Table */}
          <Row>
            <div className="col">
              <Card className="shadow">
                <CardHeader className="border-0">
                  <h3 className="mb-0">Available Times</h3>
                </CardHeader>
                <Table className="align-items-center table-flush" responsive>
                  <thead className="thead-light">
                    <tr>
                      <th scope="col">Date</th>
                      <th scope="col">Start Time</th>
                      <th scope="col">End Time</th>
                      <th scope="col">Price</th>
                      <th scope="col">Worker Assigned</th>
                      <th scope="col">Availability Status</th>
                      <th scope="col" />
                    </tr>
                  </thead>
                  <tbody>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
                    <AvailabilityCard/>
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

export default AvailableTimes;
