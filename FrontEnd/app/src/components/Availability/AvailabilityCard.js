import React, { Component } from 'react'

// reactstrap components
import {
    Badge,
    DropdownMenu,
    DropdownItem,
    UncontrolledDropdown,
    DropdownToggle,
    UncontrolledTooltip
  } from "reactstrap";

class AvailabilityCard extends Component {
    render() {
        return (
            <tr>
                <th scope="row">
                    02/09/2020
                </th>
                <td>3:00 PM</td>
                <td>4:00 PM</td>
                <td>$100</td>
                <td>
                    <div className="avatar-group">
                        <a
                        className="avatar avatar-sm"
                        href="#pablo"
                        id="tooltip742438047"
                        onClick={e => e.preventDefault()}
                        >
                        <img
                            alt="..."
                            className="rounded-circle"
                            src={require("assets/img/theme/team-1-800x800.jpg")}
                        />
                        </a>
                        <UncontrolledTooltip
                        delay={0}
                        target="tooltip742438047"
                        >
                        Ryan Tompson
                        </UncontrolledTooltip>
                    </div>
                </td>
                <td>
                <Badge color="" className="badge-dot mr-4">
                    <i className="bg-warning" />
                    pending
                </Badge>
                </td>
                <td className="text-right">
                <UncontrolledDropdown>
                    <DropdownToggle
                    className="btn-icon-only text-light"
                    href="#pablo"
                    role="button"
                    size="sm"
                    color=""
                    onClick={e => e.preventDefault()}
                    >
                    <i className="fas fa-ellipsis-v" />
                    </DropdownToggle>
                    <DropdownMenu className="dropdown-menu-arrow" right>
                    <DropdownItem
                        href="#pablo"
                        onClick={e => e.preventDefault()}
                    >
                        Book
                    </DropdownItem>
                    </DropdownMenu>
                </UncontrolledDropdown>
                </td>
            </tr>
        )
    }
}

export default AvailabilityCard;