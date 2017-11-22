import { h, Component } from 'preact'
import style from './style'
import ClusterTypeDropdown, { TYPES } from '../../components/cluster-type-dropdown'
import KMeans from '../../components/k-means'
import Hierarchical from '../../components/hierarchical'

export default class Blogs extends Component {
	state = {
		clusterType: TYPES.K_MEANS,
		clusters: []
	}

	componentDidMount() {
		this.fetchClusters()
	}

	fetchClusters = () => {
		fetch(`/api/blogs/${this.state.clusterType}`)
			.then(response => response.json())
			.then(this.updateClusters)
			.catch(console.log)
	}

	updateClusters = (clusters) => {
		this.setState({ clusters })
	}

	updateDropdown = (e) => {
		this.setState({
			clusterType: e.target.value,
			clusters: e.target.value === TYPES.K_MEANS ? [] : {}
		}, this.fetchClusters)
	}

	render(props, state) {
		return (
			<div class={style.blogs}>
				<ClusterTypeDropdown value={state.clusterType} onChange={this.updateDropdown} />
				{
					state.clusterType === TYPES.K_MEANS ?
						(<KMeans clusters={state.clusters} />) :
						(<Hierarchical cluster={state.clusters}/>)
				}
			</div>
		)
	}
}
