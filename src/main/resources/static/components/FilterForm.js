import filterItem from './FilterItem.js'

let FilterForm = Vue.component('filter-form', {
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
			objToSubmit: {}
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
			let obj = {};
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

export default FilterForm;