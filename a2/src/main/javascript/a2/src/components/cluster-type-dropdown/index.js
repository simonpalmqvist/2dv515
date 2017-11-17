import { h, Component } from 'preact';

export const TYPES = {
    K_MEANS: 'k-clusters',
    HIERARCHICAL: 'h-clusters'
}

export default class ClusterTypeDropdown extends Component {
    render(props) {
        return (
            <select name='clusters' value={props.value} onChange={props.onChange}>
                <option value={TYPES.K_MEANS}>K-Means Clustering</option>
                <option value={TYPES.HIERARCHICAL}>Hierarchical Clustering</option>
            </select>
        );
    }
}
