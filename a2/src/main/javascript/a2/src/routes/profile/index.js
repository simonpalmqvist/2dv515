import { h, Component } from 'preact';
import style from './style';

export default class Profile extends Component {
	state = {
		greeting: ''
	};

	componentDidMount(props) {
	    fetch(`/api/greeting?name=${this.props.user}`)
            .then(response => response.json())
            .then(this.updateGreeting)
            .catch(console.log);
	}

	updateGreeting = ({ content }) => {
		this.setState({ greeting: content });
	};

	render({ user }, { greeting }) {
		return (
			<div class={style.profile}>
				<h1>{ greeting }</h1>
				<p>This is the user profile for a user named { user }.</p>
			</div>
		);
	}
}
