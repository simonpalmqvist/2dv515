import React, { Component } from 'react'
import styled from 'styled-components'

import logo from './logo.svg'

const Centered = styled.div`
  box-sizing: border-box;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  margin: 0;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;

  @media(max-height: 400px) {
    display: block;
    width: 100%;
    position: relative;
  }
`

const SearchForm = styled.form`
  width: 340px;
  text-align: center;
  padding: 1rem 0;
  @media(max-height: 400px) { margin: 0 auto; }
`

const Result = styled.div`
  width: 600px;
  margin: 0 auto;
`

const Img = styled.img`
  display:block;
  margin: 1rem auto;
`

const Input = styled.input`
  width: 100%;
  padding: 0.5rem;
  font-family: Helvetica, Arial, sans-serif;
  font-size: 1rem;
`

const Li = styled.li`
  list-style-type: none;
  width: 100%;
  margin: 0.5rem
  padding: 0.5rem;
  font-family: Helvetica, Arial, sans-serif;
  font-size: 1rem;
`

const Ul = styled.ul`
  margin: 0;
  padding: 0;
`

const Link = styled.a`
  display: block;
  width: 100%;
  text-decoration: none;
  padding: 0.5rem 0;
  font-family: Helvetica, Arial, sans-serif;
  font-size: 1.2rem;
`

const ScoreInfo = styled.span`
  display:block;
  color: rgb(100,100,100);
  font-family: Helvetica, Arial, sans-serif;
  font-size: 1rem;
`

class App extends Component {

  constructor(props) {
    super(props)

    this.state = {
      results: null,
      query: ''
    }
  }

  componentWillMount() {
    window.onpopstate = (event) => {
      event.preventDefault()

      if (event.state) {
        this.setState({ query: event.state.query}, this.fetchResults.bind(this))
      } else {
        this.setState({ query: '', results: null})
      }
    }


    if (window.location.search.includes('query')) {
      this.setState({
        query: window.location.search.replace('?', '').split('&').map(string => string.split('=')).find(([key, value]) => key === 'query')[1]
      }, this.fetchResults.bind(this))
    }
  }

  onSubmit (event) {
    event.preventDefault()

    window.history.pushState({ query: this.state.query},'', `/?query=${encodeURIComponent(this.state.query)}`);

    this.fetchResults()
  }

  fetchResults () {
    fetch(`/api?query=${encodeURIComponent(this.state.query)}`)
        .then(result => result.json())
        .then(results => this.setState({ results }))
  }

  onChange (event) {
    this.setState({ query: event.target.value })
  }

  render() {

    if(!this.state.results) {
      return (
          <Centered>
            <SearchForm onSubmit={this.onSubmit.bind(this)}>
              <Img src={logo} alt='elgoog' />
              <Input placeholder='search' value={this.state.query} onChange={this.onChange.bind(this)} />
            </SearchForm>
          </Centered>
      )
    }

    return (
        <Result>
          <SearchForm onSubmit={this.onSubmit.bind(this)}>
            <Img src={logo} alt='elgoog' />
            <Input placeholder='search' value={this.state.query} onChange={this.onChange.bind(this)} />
          </SearchForm>
          <Ul>
            {
              this.state.results.map((result, i) => (
                  <Li key={i}>
                    <Link href={'https://en.wikipedia.org' + result.page.name}>{result.page.name.replace('/wiki/', '').replace(/_/g, ' ')}</Link>
                    <ScoreInfo>Score: {Math.round(result.score * 100)/100}</ScoreInfo>
                  </Li>
              ))
            }
          </Ul>
        </Result>
    )
  }
}

export default App;
