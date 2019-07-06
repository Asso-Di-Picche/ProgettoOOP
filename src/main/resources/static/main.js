import DataTable from './components/DataTable.js';
import MetadataTable from './components/MetadataTable.js';
import FilterSection from './components/FilterSection.js';
import StatsSection from './components/StatsSection.js';
import StatsTable from './components/StatsTable.js';

new Vue({
	el: '#app',
	data: {
		allDataShown: false,
		metadataShown: false,
		filterFormShown: false,
		statsFormShown: false,
		filterResultLoaded: false,
		filterResultShown: false,
		statsResultLoaded: false,
		statsResultShown: false
	},
	methods: {
		getAllData() {
			this.allDataShown = !this.allDataShown;
			this.$refs.dataChild.getAllData();
		},
		getMetadata() {
			this.metadataShown = !this.metadataShown;
			this.$refs.metadataChild.getMetadata();
		},
		openResults() {
			this.filterResultShown = !this.filterResultShown;
		},
		openStats() {
			this.statsResultShown = !this.statsResultShown;
		},
		handleFilterSubmit(data) {
			this.filterResultLoaded = true;
			this.$refs.filterResultChild.refreshFilterResults(data);
		},
		handleStatsSubmit(data, mode) {
			this.statsResultLoaded = true;
			this.$refs.statsResultChild.refreshFilterResults(data, mode);
		}
	}
});
