let FilterItem = Vue.component('filteritem', {
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

export default FilterItem;
