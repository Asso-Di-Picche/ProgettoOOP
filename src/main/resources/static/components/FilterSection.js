import FilterForm from './FilterForm.js';

let FilterSection = Vue.component('filter-section', {
	template: `
	<div>
		<div v-if="error" class="alert alert-danger" role="alert"><strong>Errore:</strong> {{ error }}</div>
        <filter-form></filter-form>
        <button @click="submitFilter" class="btn btn-success btn-block" type="button">Submit</button>
    </div>
	`,
	data(){
		return {
			error: null
		}
	},
	methods: {
		submitFilter() {
			this.error = null;
			let submitData = this.$children[0].getFilterData();

			fetch('/data', {
				method: 'post',
				body: JSON.stringify(submitData)
			})
				.then((res) => res.json())
				.then((data) => {
					if(data['error']) this.error = data['error'];
					else this.$emit('filter-submit', data);
				});
		}
	}
});

export default FilterSection;
