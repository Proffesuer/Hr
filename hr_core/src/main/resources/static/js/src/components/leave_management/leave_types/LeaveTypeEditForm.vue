<template>
	<div>
		<form>
			<div class="has-text-centered m-3">
				<h4 class="title is-6"><b>Edit Leave Type</b></h4>
			</div>
			<div class="field">
				<label class="label">Name <span><sup>*</sup></span></label>
				<div class="control">
					<input
							v-model="leaveType.name"
							@input="clearFieldError('name')"
							class="input"
							type="text">
					<span class="mb-2 has-text-danger" v-if="errors['name']">
						{{errors['name'][0]}}
					</span>
				</div>
			</div>
			<div class="field">
				<label class="label">Number of Days<span><sup>*</sup></span></label>
				<div class="control">
					<input
							@input="clearFieldError('numberOfDays')"
							class="min-w-full input"
							type="number"
							v-model="leaveType.numberOfDays">
					
					<span class="mb-2 has-text-danger" v-if="errors['numberOfDays']">
						{{errors['numberOfDays'][0]}}
					</span>
				</div>
			</div>
			<div class="flex justify-center m-3">
				<button
						
						:class="{'is-loading':loading}"
						:disabled="disableSubmitButton"
						@click.prevent.stop="updateLeaveType"
						class="button  is-rounded"
						type="submit">Submit
				</button>
			</div>
		</form>
	</div>
</template>
<script>
    import CommonMixin from "../../../mixins/common_mixin"

    export default {
        mixins: [CommonMixin],
        props: {
            leaveTypeId: {
                required: true,
            }
        },
        data() {
            return {
                leaveType: {
                    name: "",
                    numberOfDays: ""
                },
                loading: false
            }

        },
        created() {
            this.getLeaveTypeById();
        },
        methods: {
            getLeaveTypeById() {
                let vm = this;
                axios.get(`/api/leave-types/${vm.leaveTypeId}`).then(resp => {
                    vm.leaveType = resp.data;
                }, error => {

                })
            },
            updateLeaveType() {
                let vm = this;
                this.loading = true;
                axios.put(`/api/leave-types/${vm.leaveTypeId}`, this.leaveType).then(resp => {
                    vm.loading = false;
                    this.$swal({
                        text: "Leave type successfully updated",
                        type: "success"
                    })
                    this.$emit("leaveUpdateSuccessful");
                }, error => {
                    vm.loading = false;
                })
            }
        },
        computed: {
            disableSubmitButton() {
                return this.leaveType.name.length == 0 ||
                    this.leaveType.numberOfDays.length == 0 || this.loading;
            }
        }
    }
</script>