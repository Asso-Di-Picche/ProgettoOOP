Vue.component('data-table', {
	template: `
        <div class="card-body" v-if="allData">
            <table class="table">
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
				fetch('/data').then((res) => res.json()).then((data) => {
					this.allData = data;
				});
			}
		},
		refreshFilterResults() {
			this.allData = this.$root.jsonData;
		}
	}
});

Vue.component('metadata-table', {
	template: `
    <div class="card-body" v-if="metadata">
            <table class="table">
                <tr>
                    <th scope="col">Campo</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Descrizione</th>
                </tr>
                <tr v-for="(item, index) in metadata['properties']">
                    <td>{{ index }}</td>
                    <td>{{ item['type'] }}</td>
                    <td>{{ item['description'] }}</td>
                </tr>
            </table>
        </div>
    `,
	data() {
		return {
			metadata: null
		};
	},
	methods: {
		getMetadata() {
			if (!this.metadata) {
				fetch('/metadata').then((res) => res.json()).then((data) => {
					this.metadata = data;
					this.fieldnames = Object.keys(data['properties']);
				});
			}
		}
	}
});

let filterItem = Vue.component('filteritem', {
	template: `
    <div class="mb-3 mt-3 card">
        <div class="card-body">
            <div class="form-group">
                <label for="field">Indica il campo su cui eseguire il filtro:</label>
                <input v-model="field" type="text" class="form-control" id="field" placeholder="Campo su cui eseguire il filtro">
            </div>
            <div class="form-group">
                <label for="exampleFormControlSelect1">Seleziona il filtro da usare:</label>
                <select v-model="filtertype" class="form-control mb-3" id="exampleFormControlSelect1">
                    <option value="$eq">Uguale</option>
                    <option value="$not">Not</option>
                    <option value="$gt">Maggiore</option>
                    <option value="$lt">Minore</option>
                    <option value="$bt">Between</option>
                </select>
                <div class="form-group" v-if="filtertype != '$bt'">
                    <label for="valore">Seleziona i valori da filtrare:</label>
                    <input v-model="val" type="text" class="form-control" id="valore" placeholder="Valore da filtrare">
                </div>
                <div v-else class="row">
                    <div class="col">
                        <label for="minval">Valore Minimo:</label>
                        <input id="minval" v-model="minVal" type="text" class="form-control" placeholder="Valore Minimo">
                    </div>
                    <div class="col">
                        <label for="maxval">Valore Massimo:</label>
                        <input id="maxval" v-model="maxVal" type="text" class="form-control" placeholder="Valore Massimo">
                    </div>
                </div>
            </div>
        </div>
    </div>
    `,
	data() {
		return {
			filtertype: '$eq',
			minVal: null,
			maxVal: null,
			val: null,
			field: null
		};
	}
});

Vue.component('filter-form', {
	template: `
    <form>
        <p>Seleziona la logica su cui vuoi operare:</p>
        <div class="custom-control custom-radio custom-control-inline">
            <input class="custom-control-input" v-model="choice" value="0" type="radio" name="inlineRadioOptions" id="inlineRadio1">
            <label class="custom-control-label" for="inlineRadio1">AND</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input class="custom-control-input" v-model="choice" value="1" type="radio" name="inlineRadioOptions" id="inlineRadio2">
            <label class="custom-control-label" for="inlineRadio2">OR</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input class="custom-control-input" v-model="choice" value="2" type="radio" name="inlineRadioOptions" id="inlineRadio3">
            <label class="custom-control-label" for="inlineRadio3">Nessuna delle due</label>
        </div>
        <template v-for="(child, index) in children">
            <component @delete-filter="deleteFilter(index)" :is="child" :key="index"></component>
        </template>
        <div class="row">
            <div class="col-4">
                <button @click="addFilter" :disabled="choice == 2" class="btn btn-primary btn-block" type="button">Aggiungi un nuovo filtro</button>
            </div>
            <div class="col-4">
                <button :disabled="lastIndex == 0" @click="deleteFilter(lastIndex)" class="btn btn-danger btn-block" type="button">Elimina l'ultimo filtro</button>
            </div>
            <div class="col-4">
                <button @click="submitFilter" class="btn btn-success btn-block" type="button">Submit</button>
            </div>  
        </div>
    </form>
    `,
	data() {
		return {
			children: [ filterItem ],
			choice: '2',
			objToSubmit: {},
			finaldata: 'ciao'
		};
	},
	methods: {
		addFilter() {
			this.children.push(filterItem);
		},
		deleteFilter(index) {
			this.children.splice(index, 1);
		},
		addFilterObj(child) {
			obj = {};
			obj[child.field] = new Object();
			if (child.filtertype != '$bt') {
				obj[child.field][child.filtertype] = !isNaN(child.val) ? parseFloat(child.val) : child.val;
			} else {
				obj[child.field][child.filtertype] = [ parseFloat(child.minVal), parseFloat(child.maxVal) ];
			}
			return obj;
		},
		submitFilter() {
			switch (this.choice) {
				case '0':
					this.objToSubmit = new Object();
					this.objToSubmit['$and'] = [];
					for (let child of this.$children) {
						this.objToSubmit['$and'].push(this.addFilterObj(child));
					}
					break;
				case '1':
					this.objToSubmit = new Object();
					this.objToSubmit['$or'] = [];
					for (let child of this.$children) {
						this.objToSubmit['$or'].push(this.addFilterObj(child));
					}
					break;
				case '2':
					console.log(this.addFilterObj(this.$children[0]));
					this.objToSubmit = this.addFilterObj(this.$children[0]);
					break;
			}
			fetch('/data', {
				method: 'post',
				body: JSON.stringify(this.objToSubmit)
			})
				.then((res) => res.json())
				.then((data) => {
					this.$root.jsonData = data;
					this.$emit('filter-submit');
				});
		}
	},
	computed: {
		lastIndex() {
			return this.children.length - 1;
		}
	}
});

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
