import React, { Component } from 'react'

import { Link } from 'react-router-dom';

class BookServiceButton extends Component {
    render() {
        return (
            <div>
                <Link to="/admin/available_times" className="btn btn-primary btn-block mt-4"> BOOK SERVICE </Link>
            </div>
        )
    }
}

export default BookServiceButton;