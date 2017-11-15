import { h, Component } from 'preact';
import style from './style';

export default class BlogKMeans extends Component {
	state = {
		clusters: []
	}

	componentDidMount(props) {
	    fetch(`/api/blogs/k-clusters`)
            .then(response => response.json())
            .then(this.updateClusters)
            .catch(console.log);
	}

	updateClusters = (clusters) => {
		this.setState({ clusters });
	};

	render(props, state) {
		return (
			<div class={style.blogkmeans}>
                <ul>
                    {
                        state.clusters.map(blogs => (
                            <li>
                                <p>Cluster:</p>
                                <ul>
                                    {blogs.map(blog => <li>{blog.name}</li>)}
                                </ul>
                            </li>
                        ))
                    }
                </ul>
			</div>
		);
	}
}
