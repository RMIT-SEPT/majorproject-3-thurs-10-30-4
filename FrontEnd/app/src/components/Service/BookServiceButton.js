import React, { Component } from 'react'

import { Button } from 'reactstrap'

class BookServiceButton extends Component {
    render() {
        return (
            <div>
                <Button
                    color="primary"
                    href="#pablo"
                    onClick={e => e.preventDefault()}
                >
                    BOOK SERVICE
                </Button>
            </div>
        )
    }
}

export default BookServiceButton;