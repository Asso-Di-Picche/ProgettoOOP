import DataTable from './components/DataTable.js'
import MetadataTable from './components/MetadataTable.js'
import FilterForm from './components/FilterForm.js'

new Vue({
	el: '#app',
	data: {
		allDataShown: false,
		metadataShown: false,
		filterFormShown: false,
		statsFormShown: false,
		filterResultLoaded: false,
		filterResultShown: false,
		jsonData: {}
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
		handleFilterSubmit() {
			this.filterResultLoaded = true;
			this.$refs.filterResultChild.refreshFilterResults();
		}
	}
});
