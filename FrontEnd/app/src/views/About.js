/*!

=========================================================
* Argon Design System React - v1.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-design-system-react
* Copyright 2020 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-design-system-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";

// reactstrap components
import {
  Card,
  CardBody,
  Row,
  Col,
  Container
} from "reactstrap";

import {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  Marker
} from "react-google-maps";

import { Link } from "react-router-dom";
import AuthNavbar from "../components/Navbars/AuthNavbar"
import AuthFooter from "../components/Footers/AuthFooter.js";
const MapWrapper = withScriptjs(
  withGoogleMap(props => (
    <GoogleMap
      defaultZoom={12}
      defaultCenter={{ lat: -37.806313, lng: 144.965115 }}
      defaultOptions={{
        scrollwheel: false,
        styles: [
          {
            featureType: "administrative",
            elementType: "labels.text.fill",
            stylers: [{ color: "#444444" }]
          },
          {
            featureType: "landscape",
            elementType: "all",
            stylers: [{ color: "#f2f2f2" }]
          },
          {
            featureType: "poi",
            elementType: "all",
            stylers: [{ visibility: "off" }]
          },
          {
            featureType: "road",
            elementType: "all",
            stylers: [{ saturation: -100 }, { lightness: 45 }]
          },
          {
            featureType: "road.highway",
            elementType: "all",
            stylers: [{ visibility: "simplified" }]
          },
          {
            featureType: "road.arterial",
            elementType: "labels.icon",
            stylers: [{ visibility: "off" }]
          },
          {
            featureType: "transit",
            elementType: "all",
            stylers: [{ visibility: "off" }]
          },
          {
            featureType: "water",
            elementType: "all",
            stylers: [{ color: "#5e72e4" }, { visibility: "on" }]
          }
        ]
      }}
    >
      <Marker position={{ lat: -37.806313, lng: 144.965115 }} />
    </GoogleMap>
  ))
);

class Landing extends React.Component {
  state = {}
  componentDidMount() {
    document.body.classList.add("bg-default");
  }
  componentWillUnmount() {
    document.body.classList.remove("bg-default");
  }

  render() {
    return (
        <main ref="main">
        <section className="section section-lg bg-gradient-primary">
          <AuthNavbar/>
          <Row className="text-center justify-content-center pt-5">
            <Col lg="4">
              <h2 className="display-3 text-white">Booking made easy</h2>
              <p className="lead text-white pt-1">
              Book your favourite services or easily
              allow customers to book for your service.
              </p>
            </Col>
          </Row>
          <Container className="pt-6 pb-6">
            <Row className="justify-content-center">
              <Col lg="12">
                <Row className="row-grid">
                  <Col lg="4">
                    <Card className="card-lift--hover shadow border-0">
                      <CardBody className="py-5">
                        <h4 className="text-primary text-uppercase font-weight-light">
                        Easy booking
                        </h4>
                        <p className="description mt-3">
                            Use our intuitive design to easily
                            pick booking times for a service.
                        </p>
                      </CardBody>
                    </Card>
                  </Col>
                  <Col lg="4">
                  <Card className="card-lift--hover shadow border-0">
                    <CardBody className="py-5">
                      <h4 className="text-success text-uppercase font-weight-light">
                      Manage bookings
                      </h4>
                      <p className="description mt-3">
                          Easily manage all your bookings in one
                          place and be notified of any changes.
                      </p>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="4">
                  <Card className="card-lift--hover shadow border-0">
                    <CardBody className="py-5">
                      <h4 className="text-warning text-uppercase font-weight-light">
                      Choose workers
                      </h4>
                      <p className="description mt-3">
                          Discover what workers are assigned to
                          each booking or manage your workers within
                          your business.
                      </p>
                    </CardBody>
                  </Card>
                </Col>
                </Row>
              </Col>
            </Row>
          </Container>
          <Container className="pt-5 pb-100">
            <Row className="justify-content-center">
              <Col lg="12">
              <div className="btn-wrapper text-center">
                <Link
                to="/auth/login"
                role="button"
                className="btn btn-info btn-lg mb-3 mb-sm-0"
                color="info"
                >
                  <span className="btn-inner--text text-uppercase p-2">Login</span>
                </Link>
                <Link
                to="/auth/register"
                role="button"
                className="btn btn-white btn-lg mb-3 mb-sm-0"
                color="info"
                >
                  <span className="btn-inner--text text-uppercase p-2">Register</span>
                </Link>
              </div>
              </Col>
            </Row>
          </Container>
        </section>
        <section className="section section-lg bg-white pt-7">
          <Container className="py-lg-md d-flex pb-5">
            <div className="col px-0">
              <h1 className="display-3">Contact Us</h1>
              <p><b>Phone:</b> 0412 345 678 <br/>
              <b>Email:</b> support@AGME.com <br/>
              <b>Address:</b> 124 La Trobe St, Melbourne VIC 3000
              </p>
            </div>
          </Container>
          <Card className="shadow border-0">
                <MapWrapper
                  googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyA4pDq87pg47AZMJzOKQzlFr4u86acmvx4"
                  loadingElement={<div style={{ height: `100%` }} />}
                  containerElement={
                    <div
                      style={{ height: `600px` }}
                      className="map-canvas"
                      id="map-canvas"
                    />
                  }
                  mapElement={
                    <div style={{ height: `100%`, borderRadius: "inherit" }} />
                  }
                />
              </Card>
        </section>
        <AuthFooter />
        </main>
    );
  }
}

export default Landing;
