import { h, Component } from 'preact';
import TreeView from 'react-treeview';
import style from './style';

export default class BlogHierarchical extends Component {
	state = {
		cluster: null
	}

	componentDidMount(props) {
	    fetch(`/api/blogs/h-clusters`)
            .then(response => response.json())
            .then(this.updateCluster)
            .catch(console.log);
	}

	updateCluster = (cluster) => {
		this.setState({ cluster });
	};

	createClusterRepresentations = (cluster, counter) => {
		if(cluster.item) return (<div class='info'>{cluster.item.name}</div>)

		return (
			<TreeView nodeLabel={<span class='node'>Cluster</span>} key={cluster.distance} defaultCollapsed={false}>
				{this.createClusterRepresentations(cluster.left)}
				{this.createClusterRepresentations(cluster.right)}
			</TreeView>
		)
	}

	render(props, state) {
		console.log(state)

		return (
			<div class={style.bloghierarchical}>
				{this.state.cluster ? this.createClusterRepresentations(this.state.cluster) : undefined }
			</div>
		);
	}
}
