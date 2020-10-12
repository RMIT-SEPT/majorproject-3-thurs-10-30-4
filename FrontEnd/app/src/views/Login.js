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
import { login } from "../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from 'react-redux';
import classnames from "classnames";

// reactstrap components
import {
  Button,
  Card,
  CardBody,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Row,
  Col
} from "reactstrap";

import {
  Link
} from "react-router-dom";

class Login extends React.Component {
  constructor(props){
    super(props);

    this.state= this.initialState;
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);

  }

  initialState = {
    email: "",
    password: "",
    errors: {}
  }

  componentWillReceiveProps(nextProps){
    if(nextProps.errors) {
      this.setState({
        errors: nextProps.errors
      });
    }
  }

  onChange = e => {
    this.setState({[e.target.name]: e.target.value});
  }

  onSubmit = e =>
	{  
		e.preventDefault();

		const LoginRequest =
		{
			password: this.state.password,
      email: this.state.email,
      firstName: "abcdef",
			lastName: "abcdef"
		}

		axios.post("http://localhost:8080/api/Account/Login", newPerson)
			.then(response =>
			{
				if (response.data != null)
				{ 
          this.setState(this.initialState);
          localStorage.setItem('token', response.data.token);
          console.log("Token: "+response.data.token);
        }
        

        // JWT was successful. Now we authorise and obtain Account info.
        // POST token to Authenticate whenever we want Account info
        // This means we can authorise the account without needing to store password.
        axios.post("http://localhost:8080/api/Account/Authenticate", localStorage.getItem('token'))
        .then(response =>
        {
          if (response.data != null)
          {
            localStorage.setItem('id', response.data.id);
            localStorage.setItem('firstName', response.data.firstName);
            localStorage.setItem('lastName', response.data.lastName);
            localStorage.setItem('type', response.data.accountType);

            console.log("authentication successful");
            //redirect here
            if(response.data.accountType == "CUSTOMER") {
              window.location.href = "http://localhost:3000/admin/services_dashboard";
            } else if (response.data.accountType == "ADMIN") {
              window.location.href = "http://localhost:3000/admin/admin";
            } else if (response.data.accountType == "WORKER") {
              window.location.href = "http://localhost:3000/admin/worker";
            }
          }
        })
        .catch(err =>
          {
            console.log("Error authorising JWT with Account");
            if (typeof err.response.data.defaultMessage != 'undefined')
            {
              alert(err.response.data.defaultMessage);
            }
            else
            {
              alert(err.response.data[0].defaultMessage);
            }
          });
			})
			.catch(err =>
			{
				console.log("Error getting JWT");
				if (typeof err.response.data.defaultMessage != 'undefined')
				{
					alert(err.response.data.defaultMessage);
				}
				else
				{
					alert(err.response.data[0].defaultMessage);
				}
			});
		}
    }
    
    this.props.login(LoginRequest, this.props.history);

	}

  render() {
    const {email, password, errors} = this.state;
    return (
      <>
        <Col lg="5" md="7">
          <Card className="bg-secondary shadow-2 border-0">
            <CardBody className="px-lg-5 py-lg-5">
              <div className="text-center text-muted mb-4">
                <small>Sign in with credentials</small>
              </div>
              <Form onSubmit={this.onSubmit} role="form">
                <FormGroup className="mb-3">
                  <InputGroup className="input-group-alternative">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-email-83" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required
                      placeholder="Email"
                      type="email"
                      autoComplete="off"
                      name="email"
                      className = {classnames("", {
                        "is-invalid": errors.email
                      })}
                      value={email}
                      onChange = {this.onChange}
                    />
                    {errors.email && (
                      <div className="invalid-feedback p-2">{errors.email}</div>
                    )}
                  </InputGroup>
                </FormGroup>
                <FormGroup>
                  <InputGroup className="input-group-alternative">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-lock-circle-open" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required
                      placeholder="Password"
                      type="password"
                      autoComplete="off"
                      name="password"
                      className = {classnames("", {
                        "is-invalid": errors.password
                      })}
                      value={password}
                      onChange = {this.onChange}
                    />
                    {errors.password && (
                      <div className="invalid-feedback p-2">{errors.password}</div>
                    )}
                  </InputGroup>
                </FormGroup>
                <div className="text-center">
                  <Input type="submit" className="btn btn-primary my-4" value="Login"/>
                </div>
              </Form>
            </CardBody>
          </Card>
          <Row className="mt-3">
            <Col xs="6">
              <Link className="text-light">
                <small>Forgot password?</small>
              </Link>
            </Col>
            <Col className="text-right" xs="6">
              <Link to="/auth/register" className="text-light">
                <small>Create new account</small>
              </Link>
            </Col>
          </Row>
        </Col>
      </>
    );
  }
}

const mapStateToProps = state => {
  return { errors: state.errors };
};

export default connect(
  mapStateToProps,
  { login }
)(Login);
