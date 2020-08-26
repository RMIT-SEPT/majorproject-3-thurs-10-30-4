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
import { Container, Row, Col } from "reactstrap";

class AvailableTimesHeader extends React.Component {
  render() {
    return (
      <>
        <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
            
            {/* CONTAINS SERVICE PAGE HEADER */}
            <Container>
                <div className="header-body text-center mb-7">
                    <Row className="justify-content-center">
                        <Col lg="5" md="6">
                            <h1 className="text-white">Available Times</h1>
                            <p className="text-lead text-light">
                                Pick from the list of available times to make a booking.
                            </p>
                        </Col>
                    </Row>
                </div>
            </Container>
        </div>
      </>
    );
  }
}

export default AvailableTimesHeader;
