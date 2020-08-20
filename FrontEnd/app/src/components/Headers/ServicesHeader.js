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
    Button,
    Card, 
    CardBody,
    CardTitle,
    CardText, 
    Container, 
    Row, 
    Col 
} from "reactstrap";

class ServicesHeader extends React.Component {
  render() {
    return (
      <>
        <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
            
            {/* CONTAINS SERVICE PAGE HEADER */}
            <Container>
                <div className="header-body text-center mb-7">
                    <Row className="justify-content-center">
                        <Col lg="5" md="6">
                            <h1 className="text-white">Available Services</h1>
                            <p className="text-lead text-light">
                                Pick from the list of services to make a booking.
                            </p>
                        </Col>
                    </Row>
                </div>
            </Container>

            {/* CONTAINS CARDS FOR SERVICES*/}
            <Container>
                <div>
                    <Row>
                        <Col lg="6" xl="3">
                            {/* SERVICE #1 */}
                            <Card className="card-stats mb-4 mb-xl-0">
                                {/* 
                                CARD IMG HERE 

                                <CardImg
                                alt="..."
                                src={require("assets/img/theme/img-1-1000x900.jpg")}
                                top
                                />
                                */}
                                <CardBody>
                                <CardTitle>SERVICE NAME</CardTitle>
                                <CardText>
                                    INFORMATION ABOUT SERVICE
                                </CardText>
                                <Button
                                    color="primary"
                                    href="#pablo"
                                    onClick={e => e.preventDefault()}
                                >
                                    LINK TO SERVICE PAGE
                                </Button>
                                </CardBody>
                            </Card>
                        </Col>
                        
                        <Col lg="6" xl="3">
                            {/* SERVICE #2 */}
                            <Card className="card-stats mb-4 mb-xl-0">
                                {/* 
                                CARD IMG HERE 

                                <CardImg
                                alt="..."
                                src={require("assets/img/theme/img-1-1000x900.jpg")}
                                top
                                />
                                */}
                                <CardBody>
                                <CardTitle>SERVICE NAME</CardTitle>
                                <CardText>
                                    INFORMATION ABOUT SERVICE
                                </CardText>
                                <Button
                                    color="primary"
                                    href="#pablo"
                                    onClick={e => e.preventDefault()}
                                >
                                    LINK TO SERVICE PAGE
                                </Button>
                                </CardBody>
                            </Card>
                        </Col>

                        <Col lg="6" xl="3">
                            {/* SERVICE #3 */}
                            <Card className="card-stats mb-4 mb-xl-0">
                                {/* 
                                CARD IMG HERE 

                                <CardImg
                                alt="..."
                                src={require("assets/img/theme/img-1-1000x900.jpg")}
                                top
                                />
                                */}
                                <CardBody>
                                <CardTitle>SERVICE NAME</CardTitle>
                                <CardText>
                                    INFORMATION ABOUT SERVICE
                                </CardText>
                                <Button
                                    color="primary"
                                    href="#pablo"
                                    onClick={e => e.preventDefault()}
                                >
                                    LINK TO SERVICE PAGE
                                </Button>
                                </CardBody>
                            </Card>
                        </Col>
                        
                        <Col lg="6" xl="3">
                            {/* SERVICE #4 */}
                            <Card className="card-stats mb-4 mb-xl-0">
                                {/* 
                                CARD IMG HERE 

                                <CardImg
                                alt="..."
                                src={require("assets/img/theme/img-1-1000x900.jpg")}
                                top
                                />
                                */}
                                <CardBody>
                                <CardTitle>SERVICE NAME</CardTitle>
                                <CardText>
                                    INFORMATION ABOUT SERVICE
                                </CardText>
                                <Button
                                    color="primary"
                                    href="#pablo"
                                    onClick={e => e.preventDefault()}
                                >
                                    LINK TO SERVICE PAGE
                                </Button>
                                </CardBody>
                            </Card>
                        </Col>
                    </Row>
                </div>
            </Container>
        </div>
      </>
    );
  }
}

export default ServicesHeader;