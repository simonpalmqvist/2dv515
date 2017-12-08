import React, { Component } from 'react'
import styled from 'styled-components'


const Wrapper = styled.div`
  display: flex;
  width: 100%;
`

const Menu = styled.nav`
  width: 20%;
`

class App extends Component {

  constructor (props) {
    super(props)

    this.state = {
      users: [],
      activeUser: null,
      recommendations: [],
      scoreType: 'euclidean',
      type: 'user'
    }
  }

  componentWillMount () {
    fetch('/api/users')
        .then(result => result.json())
        .then(users => this.setState({ users }))
  }

  setActiveUser (user, event) {
    this.setState({
      activeUser: user,
      recommendations: []
    }, () => this.fetchRecommendations())
  }

  setScoreType (event) {
    this.setState({
      scoreType: event.target.value
    }, () => this.fetchRecommendations())
  }

  setType (event) {
    this.setState({
      type: event.target.value
    }, () => this.fetchRecommendations())
  }

  fetchRecommendations () {
    if(!this.state.activeUser) return

    fetch(`/api/recommendation/${this.state.scoreType}/${this.state.type}?user=${this.state.activeUser.name}`)
        .then(result => result.json())
        .then(recommendations => this.setState({ recommendations }))
  }

  roundScore (score) {
    return Math.round(score * 1000) / 1000
  }

  render () {

    return (
        <Wrapper>
          <Menu>
            <label>Similarity measure: </label>
            <select value={this.state.scoreType} onChange={this.setScoreType.bind(this)}>
              <option value='euclidean'>Euclidean</option>
              <option value='pearson'>Pearson</option>
            </select>
            <br/>
            <label>Type: </label>
            <select value={this.state.type} onChange={this.setType.bind(this)}>
              <option value='user'>User-based</option>
              <option value='item'>Item-based</option>
            </select>
            <ul>
              {
                this.state.users.map(user => (
                    <li key={user.name}>
                      <a href='#' onClick={this.setActiveUser.bind(this, user)} >
                        {user.name}
                      </a>
                    </li>
                ))
              }
            </ul>
          </Menu>
          {
            this.state.activeUser && (
                <div>
                  <h1>{this.state.activeUser.name}</h1>
                  <h2>Recommendations</h2>
                  <ul>
                    {
                      this.state.recommendations.map(recommendation => (
                          <li key={recommendation.movie.name}>
                            {recommendation.movie.name} ({this.roundScore(recommendation.weightedScore)})
                          </li>
                      ))
                    }
                  </ul>
                </div>
            )
          }
        </Wrapper>
    )
  }
}

export default App;
