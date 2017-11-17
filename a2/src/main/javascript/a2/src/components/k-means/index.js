import { h, Component } from 'preact'

export default class KMeans extends Component {
	render(props) {
	    const clusters = props.clusters || []

		return (
			<ul>
				{
					props.clusters.map((wordCollections, i) => (
						<li key={i}>
							<p>Cluster:</p>
							<ul>
								{
									wordCollections
                                        .sort((a, b) => {
                                            if(a.name < b.name) return -1
                                            if(a.name > b.name) return 1
                                            return 0
                                        })
                                        .map(wordCollection => (
										<li key={`${i}-${wordCollection.name}`}>{wordCollection.name}</li>
									))
								}
							</ul>
						</li>
					))
				}
			</ul>
		)
	}
}
