import React, { Component } from 'react'
import Person from './Persons/Person'

class Dashboard extends Component {
    render() {
        return (
            <div>
                <Person/>
                <Person/>
                <Person/>
            </div>
        )
    }
}

export default Dashboard;