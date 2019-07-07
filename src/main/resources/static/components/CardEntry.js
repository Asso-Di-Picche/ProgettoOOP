let CardEntry = Vue.component('card-entry', {
	template: `
    <div class="card">
        <div class="row pt-2 pb-2 pl-4 pr-4">
            <div class="col">
                <strong><slot name="type"></slot></strong>
            </div>
            <div class="col">
                <slot name="content"></slot>
            </div>
        </div>
    </div>
    `
});

export default CardEntry;
