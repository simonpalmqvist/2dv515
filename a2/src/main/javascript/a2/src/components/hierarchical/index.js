import { h, Component } from 'preact';
import TreeView from 'react-treeview';

export default class Hierarchical extends Component {

	createClusterRepresentations = (cluster) => {
		if(cluster.item) return (<div class='info'>{cluster.item.name}</div>)

		return (
			<TreeView nodeLabel={<span class='node'>Cluster</span>} key={cluster.distance} defaultCollapsed={false}>
				{this.createClusterRepresentations(cluster.left)}
				{this.createClusterRepresentations(cluster.right)}
			</TreeView>
		)
	}


	render(props) {
		return (
			<div>
				{props.cluster.distance ? this.createClusterRepresentations(props.cluster) : undefined}
			</div>
		)
	}
}
