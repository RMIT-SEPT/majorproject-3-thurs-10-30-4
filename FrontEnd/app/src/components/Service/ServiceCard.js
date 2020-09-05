import React, { Component } from 'react'

// reactstrap components
import {
    Card, 
    CardBody
} from "reactstrap";

import BookServiceButton from './BookServiceButton';
import ServiceDescription from './ServiceDescription';
import ServiceName from './ServiceName';

class ServiceCard extends Component {
    render() {
        return (
            <div>
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
                    <ServiceName/>
                    <ServiceDescription/>
                    <center> <BookServiceButton/> </center>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default ServiceCard;