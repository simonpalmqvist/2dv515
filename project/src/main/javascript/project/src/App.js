import React, { Component } from 'react'
import styled from 'styled-components'

class App extends Component {

  constructor(props) {
    super(props)
  }

  componentWillMount() {
    fetch('/api/users')
        .then(result => result.json())
        .then(console.log)
  }

  render() {

    return (
        <div>hejsan</div>
    )
  }
}

export default App;
