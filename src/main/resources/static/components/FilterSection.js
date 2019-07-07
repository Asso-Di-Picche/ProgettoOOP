import FilterForm from './FilterForm.js';

let FilterSection = Vue.component('filter-section', {
	template: `
    <div>
        <filter-form></filter-form>
        <button @click="submitFilter" class="btn btn-success btn-block" type="button">Submit</button>
    </div>
    `,
	methods: {
		submitFilter() {
			let submitData = this.$children[0].getFilterData();

			fetch('/data', {
				method: 'post',
				body: JSON.stringify(submitData)
			})
				.then((res) => res.json())
				.then((data) => {
					this.$emit('filter-submit', data);
				});
		}
	}
});

export default FilterSection;
