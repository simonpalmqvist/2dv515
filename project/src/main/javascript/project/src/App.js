import React, { Component } from 'react'
import styled from 'styled-components'


const Wrapper = styled.div`
  display: flex;
  width: 100%;
  margin: 0;
  padding: 0;
`

const Menu = styled.nav`
  padding: 1rem;
  margin: 0;
  width: 20%;
  border-right: 1px solid rgb(240, 240,240);
`

const Content = styled.div`
  padding: 1rem 2rem;
`

const Title = styled.h1`
  font-family: Helvetica, Arial, sans-serif;
  margin: 1rem 0;
  font-size: 3rem;
  font-weight: bold;
`

const Headline = styled.h2`
  font-family: Helvetica, Arial, sans-serif;
  margin: 1rem 0;
  font-size: 1.4rem;
  font-weight: bold;
`

const A = styled.a`
  font-family: Helvetica, Arial, sans-serif;
  font-size: 1rem;
  color: rgb(50,50,240);
  text-decoration: none;
`

const Label = styled.label`
  font-family: Helvetica, Arial, sans-serif;
  width: 100%;
  display:block;
  margin: 0.5rem 0;
  font-size: 1rem;
  font-weight: bold;
`

const Select = styled.select`
  width: 100%;
  font-family: Helvetica, Arial, sans-serif;
  font-size: 1rem;
  margin-bottom: 1rem;
`

const List = styled.ul`
  margin: 0;
  padding: 0;
  border-top: 1px solid rgb(240,240,240);  
`

const ListItem = styled.li`
	list-style-type: none;
	padding: 0.8rem 0;
	margin: 0;
	width: 100%;
	border-bottom: 1px solid rgb(240,240,240);
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

    fetch(`/api/recommendation/${this.state.scoreType}/${this.state.type}?user=${this.state.activeUser.id}`)
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
            <Label>Similarity measure</Label>
            <Select value={this.state.scoreType} onChange={this.setScoreType.bind(this)}>
              <option value='euclidean'>Euclidean</option>
              <option value='pearson'>Pearson</option>
            </Select>
            <br/>
            <Label>Type</Label>
            <Select value={this.state.type} onChange={this.setType.bind(this)}>
              <option value='user'>User-based</option>
              <option value='item'>Item-based</option>
            </Select>
            <Headline>Users</Headline>
            <List>
              {
                this.state.users.map(user => (
                    <ListItem key={user.id}>
                      <A href='#' onClick={this.setActiveUser.bind(this, user)} >
                        {user.name}
                      </A>
                    </ListItem>
                ))
              }
            </List>
          </Menu>
          {
            this.state.activeUser && (
                <Content>
                  <Title>{this.state.activeUser.name}</Title>
                  <Headline>Recommendations</Headline>
                  <List>
                    {
                      this.state.recommendations.map(recommendation => (
                          <ListItem key={recommendation.movie.id}>
                            {recommendation.movie.name} ({this.roundScore(recommendation.weightedScore)})
                          </ListItem>
                      ))
                    }
                  </List>
                </Content>
            )
          }
        </Wrapper>
    )
  }
}

export default App;
