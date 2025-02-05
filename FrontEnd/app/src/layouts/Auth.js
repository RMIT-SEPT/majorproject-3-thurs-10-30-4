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
import { Route, Switch, Redirect } from "react-router-dom";
// reactstrap components
import { Container, Row, Col } from "reactstrap";
 
// core components
import AuthNavbar from "../components/Navbars/AuthNavbar.js";
import AuthFooter from "../components/Footers/AuthFooter.js";

import routes from "../routes.js";

import UserProfile from '../session/UserProfile';

//UserProfile.setName("MR TEST");

var id = localStorage.getItem('id');

class Auth extends React.Component {
  componentDidMount() {
    document.body.classList.add("bg-white");
  }
  componentWillUnmount() {
    document.body.classList.remove("bg-white");
  }
  getRoutes = routes => {
    return routes.map((prop, key) => {
      if (prop.layout === "/auth") {
        return (
          <Route
            path={prop.layout + prop.path}
            component={prop.component}
            key={key}
          />
        );
      } else {
        return null;
      }
    });
  };

  checkLogin() {
    if(id != null) {
		 // This needs to redirect based on account types.
      //window.location.href = "http://localhost:3000/Admin/services_dashboard";
    }
  };

  render() {
    
    this.checkLogin();
	  
    return (
      <>
        <div className="main-content">
          <AuthNavbar />
          <div className="header bg-default py-7 py-lg-8">
            <Container>
              <div className="header-body text-center mb-7">
                <Row className="justify-content-center">
                  <Col lg="5" md="6">
                    <h1 className="text-white">Welcome to the AGME Online Appointment Booking System</h1>
                    <p className="text-lead text-light">
                      Login or register to access or manage your bookings.
                    </p>
                  </Col>
                </Row>
              </div>
            </Container>
            <div className="separator separator-bottom separator-skew zindex-100">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                preserveAspectRatio="none"
                version="1.1"
                viewBox="0 0 2560 100"
                x="0"
                y="0"
              >
                <polygon
                  className="fill-white"
                  points="2560 0 2560 100 0 100"
                />
              </svg>
            </div>
          </div>
          {/* Page content */}
          <Container className="mt--8 pb-5">
            <Row className="justify-content-center">
              <Switch>
                {this.getRoutes(routes)}
                <Redirect from="*" to="/auth/login" />
              </Switch>
            </Row>
          </Container>
        </div>
        <AuthFooter />
      </>
    );
  }
}
 
export default Auth;
