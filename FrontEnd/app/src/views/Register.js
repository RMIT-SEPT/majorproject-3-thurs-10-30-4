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
import { createNewUser } from "../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from 'react-redux';
import classnames from "classnames";
import UserProfile from "../session/UserProfile";

// reactstrap components
import {
  Card,
  CardBody,
  Form,
  FormGroup,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Col
} from "reactstrap";

class Register extends React.Component {
  constructor(props){
    super(props);

    this.state = this.initialState;
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);

  }

  initialState = {
    firstName: "", lastName: "", password: "", email: "", retypedPassword: "", errors: {}
  }

  componentWillReceiveProps(nextProps){
    if(nextProps.errors) {
      this.setState({
        errors: nextProps.errors
      });
    }
  }

  onChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  }

  checkPasswords() {
    if (this.state.password === this.state.retypedPassword) {
      return true;
    } else {
      return false;
    }
  }

	onSubmit = e =>
	{  
		e.preventDefault();

		const newPerson =
		{
			firstName: this.state.firstName,
			lastName: this.state.lastName,
			password: this.state.password,
			email:this.state.email
    }

		if (this.checkPasswords())
		{
      this.props.createNewUser(newPerson, this.props.history);
		}
		else
		{
			alert("Passwords are not the same");
		}
	}

  render() {
    const { firstName, lastName, email, password, retypedPassword, errors } = this.state;
    return (
      <>
        <Col lg="6" md="8">
          <Card className="bg-secondary shadow-2 border-0">
            <CardBody className="px-lg-5 py-lg-5">
              <div className="text-center text-muted mb-4">
                <small>Sign up with credentials</small>
              </div>
              <Form onSubmit={this.onSubmit} id="registerFormId">

                {/* FIRST NAME INPUT */}
                <FormGroup>
                  <InputGroup className="input-group-alternative mb-3">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-hat-3" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required autoComplete="off"
                      type="text" 
                      placeholder="First Name" 
                      name="firstName"
                      className = {classnames("", {
                          "is-invalid": errors.firstName
                      })}
                      value= { firstName }
                      onChange = { this.onChange }
                    />
                    {errors.firstName && (
                      <div className="invalid-feedback p-2">{errors.firstName}</div>
                    )}
                  </InputGroup>
                </FormGroup>

                {/* LAST NAME INPUT */}
                <FormGroup>
                  <InputGroup className="input-group-alternative mb-3">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-hat-3" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required autoComplete="off"
                      type="text" 
                      placeholder="Last Name" 
                      name="lastName"
                      className = {classnames("", {
                        "is-invalid": errors.lastName
                      })}
                      value= {lastName}
                      onChange = {this.onChange}
                    />
                    {errors.lastName && (
                      <div className="invalid-feedback p-2">{errors.lastName}</div>
                    )}
                  </InputGroup>
                </FormGroup>

                {/* EMAIL INPUT */}
                <FormGroup>
                  <InputGroup className="input-group-alternative mb-3">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-email-83" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required autoComplete="off"
                      type="email"
                      placeholder="Email" 
                      name="email"
                      className = {classnames("", {
                        "is-invalid": errors.email
                      })}
                      value= {email}
                      onChange = {this.onChange}
                    />
                    {errors.email && (
                      <div className="invalid-feedback p-2">{errors.email}</div>
                    )}
                  </InputGroup>
                </FormGroup>

                {/* PASSWORD INPUT */}
                <FormGroup>
                  <InputGroup className="input-group-alternative">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-lock-circle-open" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required autoComplete="off"
                      type="password"
                      placeholder="Password" 
                      name="password"
                      className = {classnames("", {
                        "is-invalid": errors.password
                      })}
                      value= {password}
                      onChange = {this.onChange}
                    />
                    {errors.password && (
                      <div className="invalid-feedback p-2">{errors.password}</div>
                    )}
                  </InputGroup>
                </FormGroup>
      
                {/* RE-INPUT PASSWORD INPUT */}
                <FormGroup>
                  <InputGroup className="input-group-alternative">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-lock-circle-open" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input required
                      placeholder="Re-Type Password" 
                      type="password" 
                      name="retypedPassword"
                      className = {classnames("", {
                        "is-invalid": errors.password
                      })}
                      value= {retypedPassword}
                      onChange = {this.onChange}
                    />
                    {errors.password && (
                      <div className="invalid-feedback p-2">{errors.password}</div>
                    )}
                    
                  </InputGroup>
                </FormGroup>

                {/* Redirection button to dashboard after registration */}
                <div className="text-center">
                  {/*<Link to="/admin/services_dashboard">*/}
                    <input type="submit" className="btn btn-primary btn-block mt-4" value="Create Account"/>
                  {/*</Link>*/}
                </div>
              </Form>
            </CardBody>
          </Card>
        </Col>
      </>
    );
  }
}
// Register.propTypes = {
//   createProject: PropTypes.func.isRequired
// };

const mapStateToProps = state => {
  return { errors: state.errors };
};

export default connect(
  mapStateToProps,
  { createNewUser }
)(Register);
