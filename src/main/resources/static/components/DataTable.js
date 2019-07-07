let DataTable = Vue.component('data-table', {
	template: `
        <div class="card-body" v-if="allData">
            <table class="table" v-if="allData && allData.length">
                <tr>
                    <th scope="col">freq</th>
                    <th scope="col">geo</th>
                    <th scope="col">unit</th>
                    <th scope="col">objective</th>
                    <th scope="col">2000</th>
                    <th scope="col">2001</th>
                    <th scope="col">2002</th>
                    <th scope="col">2003</th>
                    <th scope="col">2004</th>
                    <th scope="col">2005</th>
                    <th scope="col">2006</th>
                    <th scope="col">2007</th>
                    <th scope="col">2008</th>
                    <th scope="col">2009</th>
                    <th scope="col">2010</th>
                    <th scope="col">2011</th>
                    <th scope="col">2012</th>
                    <th scope="col">2013</th>
                    <th scope="col">2014</th>
                    <th scope="col">2015</th>
                    <th scope="col">2016</th>
                    <th scope="col">2017</th>
                </tr>
                <tr v-for="item in allData">
                    <td>{{ item['freq'] }}</td>
                    <td>{{ item['geo'] }}</td>
                    <td>{{ item['unit'] }}</td>
                    <td>{{ item['objectiv'] }}</td>
                    <td v-for="el in item['aids']">{{ el }}</td>
                </tr>
			</table>
			<p v-else><strong>Non sono stati trovati elementi che soddisfano la richiesta!</strong></p>
        </div>
    `,
	data() {
		return {
			allData: null
		};
	},
	methods: {
		getAllData() {
			if (!this.allData) {
				fetch('/data', { method: 'post' }).then((res) => res.json()).then((data) => {
					this.allData = data;
				});
			}
		},
		refreshFilterResults(data) {
			this.allData = data;
		}
	}
});

export default DataTable;
