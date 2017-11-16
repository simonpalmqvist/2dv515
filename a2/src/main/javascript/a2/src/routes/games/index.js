import { h, Component } from 'preact';
import style from './style';

export default class Games extends Component {
	state = {
		hasSelectedWords: false,
		topWords: [],
		clusters: []
	}

	componentDidMount() {
	    fetch(`/api/games/words`)
            .then(response => response.json())
			.then(this.updateCommonWords)
			.then(this.maybeFetchClusters)
            .catch(console.log)
	}

	maybeFetchClusters = () => {
		if(!this.state.hasSelectedWords) return;

		fetch(`/api/games/clusters`)
			.then(response => response.json())
			.then(this.updateClusters)
			.catch(console.log);
	}

	updateClusters = (clusters) => {
		this.setState({ clusters });
	};

	requestClusterBasedOnWords = e => {
		e.preventDefault()

		const pickedWords = this.state.topWords
			.filter(item => item.checked)
			.map(item => item.word)

		fetch(`/api/games/words`, { method: 'POST', body: JSON.stringify(pickedWords) , headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		}})
			.then(() => this.setState({ hasSelectedWords: true }))
			.then(this.maybeFetchClusters)
			.catch(console.log)
	}

	updateCommonWords = ({topWords, selectedWords}) => {
		this.setState({
			topWords: Object.entries(topWords)
				.map(([word, occurrences]) => ({ word, occurrences, checked: false }))
				.sort((a, b) => b.occurrences - a.occurrences),
			hasSelectedWords: !!selectedWords
		});
	};

	checkWord = (i, item) => () => {
		const newItem = { ...item, checked: !item.checked}

		const newArray = this.state.topWords.slice();

		newArray[i] = newItem

		this.setState({
			topWords: newArray
		})
	}

	render(props, state) {

		if(state.hasSelectedWords) {
			return (
				<div class={style.games}>
					<ul>
						{
							state.clusters.map(wordCollections => (
								<li>
									<p>Cluster:</p>
									<ul>
										{wordCollections.map(wordCollection => <li>{wordCollection.name}</li>)}
									</ul>
								</li>
							))
						}
					</ul>
				</div>
			)
		}

		return (
			<div class={style.games}>
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
		);
	}
}
