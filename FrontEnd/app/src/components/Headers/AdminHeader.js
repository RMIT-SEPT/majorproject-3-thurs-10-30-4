import React from "react";

// reactstrap components
import { Button, Container, Row, Col } from "reactstrap";

class AdminHeader extends React.Component {
  
    getAdminName() {
        return localStorage.getItem('firstName'); 
    }

    render() {
    return (
      <>
        <div
          className="header pb-8 pt-5 pt-lg-8 d-flex align-items-center"
          style={{
            minHeight: "300px",
            backgroundSize: "cover",
            backgroundPosition: "center top"
          }}
        >
          <span className="mask bg-gradient-default opacity-8" />
          <Container className="d-flex align-items-center" fluid>
              <Col lg="7" md="10">
                <h1 className="display-2 text-white">Hello {this.getAdminName()}</h1>
                <p className="text-white mt-0 mb-5">
                  This is your Administration page. You can see the all the core business information here.
                </p>
              </Col>
          </Container>
        </div>
      </>
    );
  }
}

export default AdminHeader;