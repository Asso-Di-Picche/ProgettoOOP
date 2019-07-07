import CardEntry from './CardEntry.js';

let StatsTable = Vue.component('stats-table', {
	template: `
        <div class="card-body" v-if="stats" id="stats">
            <card-entry v-if="showMode == 0">
                <template v-slot:type>Area geopolitica:</template>
                <template v-slot:content>{{ stats['geo'] }}</template>
            </card-entry>
            <card-entry v-if="showMode == 0">
                <template v-slot:type>Unità di misura:</template>
                <template v-slot:content>{{ stats['unit'] }}</template>
            </card-entry>
            <card-entry v-if="showMode == 1">
                <template v-slot:type>Campo:</template>
                <template v-slot:content>{{ stats['field'] }}</template>
            </card-entry>
            <card-entry>
                <template v-slot:type>Media dei dati:</template>
                <template v-slot:content>{{ stats['avg'] }}</template>
            </card-entry>
            <card-entry v-if="showMode == 0">
                <template v-slot:type>Anni in cui si è verificato il massimo valore:</template>
                <template v-slot:content>{{ maxYearList }}</template>
            </card-entry>
            <card-entry v-if="showMode == 1">
                <template v-slot:type>Paesi in cui si è verificato il massimo valore:</template>
                <template v-slot:content>{{ maxGeoList }}</template>
            </card-entry>
            <card-entry>
                <template v-slot:type>Valore massimo riscontrato:</template>
                <template v-slot:content>{{ stats['max']['value'] }}</template>
            </card-entry>
            <card-entry v-if="showMode == 0">
                <template v-slot:type>Anni in cui si è verificato il minimo valore:</template>
                <template v-slot:content>{{ minYearList }}</template>
            </card-entry>
            <card-entry v-if="showMode == 1">
                <template v-slot:type>Paesi in cui si è verificato il minimo valore:</template>
                <template v-slot:content>{{ minGeoList }}</template>
            </card-entry>
            <card-entry>
                <template v-slot:type>Valore minimo riscontrato:</template>
                <template v-slot:content>{{ stats['min']['value'] }}</template>
            </card-entry>
            <card-entry>
                <template v-slot:type>Deviazione Standard:</template>
                <template v-slot:content>{{ stats['devstd'] }}</template>
            </card-entry>
            <card-entry>
                <template v-slot:type>Somma dei valori:</template>
                <template v-slot:content>{{ stats['sum'] }}</template>
            </card-entry>
        </div>
    `,
	data() {
		return {
			stats: null,
			showMode: 0
		};
	},
	computed: {
		minYearList() {
			if (this.stats['min']['year']) return this.stats['min']['year'].join(', ');
		},
		maxYearList() {
			if (this.stats['min']['year']) return this.stats['max']['year'].join(', ');
		},
		minGeoList() {
			if (this.stats['min']['geo']) return this.stats['min']['geo'].join(', ');
		},
		maxGeoList() {
			if (this.stats['min']['geo']) return this.stats['max']['geo'].join(', ');
		}
	},
	methods: {
		refreshFilterResults(data, mode) {
			this.stats = data;
			this.showMode = mode;
		}
	}
});

export default StatsTable;
