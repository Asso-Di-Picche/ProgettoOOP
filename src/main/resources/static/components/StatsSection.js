import FilterForm from './FilterForm.js';

let StatsSection = Vue.component('stats-section', {
	template: `
    <div>
        <div v-if="error" class="alert alert-danger" role="alert"><strong>Errore:</strong> {{ error }}</div>
        <div class="mb-3">
            <p>Seleziona su quale dato effettuare la statistica:</p>
            <div class="custom-control custom-radio custom-control-inline">
                <input class="custom-control-input" v-model="statChoice" value="0" type="radio" id="statpaese">
                <label class="custom-control-label" for="statpaese">Sul singolo paese</label>
            </div>
            <div class="custom-control custom-radio custom-control-inline">
                <input class="custom-control-input" v-model="statChoice" value="1" type="radio" id="statyear0">
                <label class="custom-control-label" for="statyear0">Su un anno (senza filtri)</label>
            </div>
            <div class="custom-control custom-radio custom-control-inline">
                <input class="custom-control-input" v-model="statChoice" value="2" type="radio" id="statyear1">
                <label class="custom-control-label" for="statyear1">Su un anno (con filtri)</label>
            </div>
        </div>
        <div v-if="statChoice == '0'" class="row mb-3">
            <div class="col">
                <label for="geo">Ente geopolitico:</label>
                <input id="geo" v-model="geo" type="text" class="form-control" placeholder="Ente Geopolitico">
            </div>
            <div class="col">
                <label for="unit">Unità di misura (MEUR_KP_PRE o PC_GDP):</label>
                <input id="unit" v-model="unit" type="text" class="form-control" placeholder="Unità di misura">
            </div>
        </div>
        <div v-else class="form-group">
            <label for="valore">Seleziona l'anno su cui effettuare le statistiche:</label>
            <input v-model="year" type="text" class="form-control" id="valore" placeholder="Valore da filtrare">
        </div>
        <filter-form v-if="statChoice == '2'"></filter-form>
        <button @click="submitStats" class="btn btn-success btn-block" type="button">Submit</button>
    </div>
    `,
	data() {
		return {
			statChoice: '0',
			geo: null,
			unit: null,
			year: null,
			error: null
		};
	},
	methods: {
		submitStats() {
			this.error = null;
			switch (this.statChoice) {
				case '0':
					fetch('/stats/geo/' + this.geo + '/unit/' + this.unit).then((res) => res.json()).then((data) => {
						if (data['error']) this.error = data['error'];
						else this.$emit('stats-submit', data, 0);
					});
					break;
				case '1':
					fetch('/stats/year/' + this.year, {
						method: 'post'
					})
						.then((res) => res.json())
						.then((data) => {
							if (data['error']) this.error = data['error'];
							else this.$emit('stats-submit', data, 1);
						});
					break;
				case '2':
					let submitData = this.$children[0].getFilterData();
					fetch('/stats/year/' + this.year, {
						method: 'post',
						body: JSON.stringify(submitData)
					})
						.then((res) => res.json())
						.then((data) => {
							if (data['error']) this.error = data['error'];
							else this.$emit('stats-submit', data, 1);
						});
					break;
			}
		}
	}
});

export default StatsSection;
