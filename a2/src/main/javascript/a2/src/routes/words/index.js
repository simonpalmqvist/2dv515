import { h, Component } from 'preact'
import style from './style'
import ClusterTypeDropdown, { TYPES } from '../../components/cluster-type-dropdown'
import KMeans from '../../components/k-means'
import Hierarchical from '../../components/hierarchical'

export default class Words extends Component {
	state = {
	    clusterType: TYPES.K_MEANS,
		hasSelectedWords: false,
		topWords: [],
		clusters: []
	}

	componentDidMount() {
		this.fetchWordsInfo(this.props.type)
	}

	componentWillUpdate(nextProps) {
		if (nextProps.type !== this.props.type) {
			this.fetchWordsInfo(nextProps.type)
		}
	}

	fetchWordsInfo = (type) => {
		fetch(`/api/${type}/words`)
			.then(response => response.json())
			.then(this.updateCommonWords)
			.then(this.maybeFetchClusters)
			.catch(console.log)
	}

	maybeFetchClusters = () => {
		if(!this.state.hasSelectedWords) return

		fetch(`/api/${this.props.type}/${this.state.clusterType}`)
			.then(response => response.json())
			.then(this.updateClusters)
			.catch(console.error)
	}

	updateClusters = (clusters) => {
		this.setState({ clusters })
	}

	requestClusterBasedOnWords = e => {
		e.preventDefault()

		const pickedWords = this.state.topWords
			.filter(item => item.checked)
			.map(item => item.word)

		fetch(`/api/${this.props.type}/words`, { method: 'POST', body: JSON.stringify(pickedWords) , headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		}})
			.then(() => this.setState({ hasSelectedWords: true }))
			.then(this.maybeFetchClusters)
			.catch(console.error)
	}

	updateCommonWords = ({topWords, selectedWords}) => {
		this.setState({
			topWords: Object.entries(topWords)
				.map(([word, occurrences]) => ({ word, occurrences, checked: false }))
				.sort((a, b) => b.occurrences - a.occurrences),
			hasSelectedWords: !!selectedWords
		})
	}

	checkWord = (i, item) => () => {
		const newItem = { ...item, checked: !item.checked}

		const newArray = this.state.topWords.slice()

		newArray[i] = newItem

		this.setState({
			topWords: newArray
		})
	}

	updateDropdown = (e) => {
	    this.setState({
	        clusterType: e.target.value,
            clusters: e.target.value === TYPES.K_MEANS ? [] : {}
	    }, this.maybeFetchClusters)
    }

	render(props, state) {

		if(state.hasSelectedWords) {
			return (
				<div class={style.words}>
                    <ClusterTypeDropdown value={state.clusterType} onChange={this.updateDropdown} />
                    {
                        state.clusterType === TYPES.K_MEANS ?
                            (<KMeans clusters={state.clusters} />) :
                            (<Hierarchical cluster={state.clusters}/>)
                    }
				</div>
			)
		}

		return (
			<div class={style.words}>
				<form onSubmit={this.requestClusterBasedOnWords}>
					<input type='submit' value='Get clusters based on words'/>
					{
						state.topWords.map((item, i) => (
							<div key={item.word}>
								<label>{item.word} ({item.occurrences})</label>
								<input type="checkbox" onChange={this.checkWord(i, item)} value={item.checked} />
							</div>
						))
					}
				</form>
			</div>
		)
	}
}
