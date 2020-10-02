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
import axios from 'axios';
import { Link } from "react-router-dom";

// reactstrap components
import { 
    Button,
    Container, 
    Row, 
    Col, 
    CardBody,
    Card,
    Table
} from "reactstrap";

class ServicesHeader extends React.Component {
  
    constructor(props) {
        super(props);

        this.state = {
            services : []
        };
    }


    componentDidMount() {
        this.getServices();
    }

    getServices() {
        axios.get("http://3.237.203.90:8080/api/service/getall")
        .then(response => response.data)
        .then((data) => {
            this.setState({services: data});
        })
        .catch(error => { 
            //console.log(error.response.data)
            
            //{/* BIT OF A HACK*/}
				//this.setState({services: error.response.data});
        });
    }

    myFunction(text) {
        localStorage.setItem('serviceId', text);
        localStorage.setItem('serviceName', this.state.services[parseInt(text) - 1].serviceName);
    }
  
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
            {/* PLACEHOLDERS FOR NOW */}
            <Container>
                <div>
                    <Card>
                        <CardBody>
                            <Table>
                                <thead>
                                    <tr>
                                        <th> Service Name </th>
                                        <th> Service Description </th>
                                        <th> Actions </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.services.length === 0 ? 
                                        <tr align="center">
                                            <td colSpan="3"> No Services Available. </td>
                                        </tr> : 
                                        this.state.services.map((service) => (
                                            <tr key={service.serviceId}>
                                                <td>{service.serviceName}</td>
                                                <td>{service.serviceDescription}</td>
                                                <td>
                                                    <Link to="/admin/timeslots">
                                                        <Button color="primary" type="button" value={service.serviceId} onClick={e => this.myFunction(e.target.value)}>
                                                            BOOK SERVICE
                                                        </Button>
                                                    </Link>
                                                </td>
                                            </tr>
                                        ))

                                    } 
                                </tbody>
                            </Table>
                        </CardBody>
                    </Card>
                </div>
            </Container>
        </div>
      </>
    );
  }
}

export default ServicesHeader;