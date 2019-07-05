let MetadataTable = Vue.component('metadata-table', {
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

export default MetadataTable;