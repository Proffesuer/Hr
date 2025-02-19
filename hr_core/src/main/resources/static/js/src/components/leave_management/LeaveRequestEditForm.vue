<template>
	<form>
		<div class="has-text-centered m-3">
			<h1 class="has-text-black"><b>Edit Leave</b></h1>
		</div>
		<div class="field">
			<label class="label">Leave Types <span><sup class="has-text-danger">*</sup></span></label>
			<div class="control w-full">
				<div class="select w-full">
					<select
							@input="clearFieldError('leaveTypeId')"
							v-model="leaveRequest.leaveTypeId"
							class="w-full">
						<option value="" disabled selected hidden>Select One</option>
						<option
								v-for="leaveType in leaveTypes"
								:value="leaveType.id"
								:key="leaveType.id">{{leaveType.name}}
						</option>
					</select>
				</div>
				<span v-if="errors['leaveTypeId']" class="text-red-400">
							{{errors['leaveTypeId'][0]}}
						</span>
			</div>
		</div>
		<div class="columns">
			<div class="column">
				<div class="field">
					<label class="label">From <span><sup class="has-text-danger">*</sup></span></label>
					<div class="control">
						<DatePicker
								@input="clearFieldError('startDate')"
								format="dd-MM-yyyy"
								value-format="yyyy-MM-dd"
								v-model="leaveRequest.startDate"
								class="min-w-full">
						</DatePicker>
						<span v-if="errors['startDate']" class="text-red-400">
							{{errors['startDate'][0]}}
						</span>
					</div>
				</div>
			</div>
			<div class="column">
				<div class="field">
					<label class="label">To <span><sup class="has-text-danger">*</sup></span></label>
					<div class="control">
						<DatePicker
								@input="clearFieldError('endDate')"
								format="dd-MM-yyyy"
								value-format="yyyy-MM-dd"
								v-model="leaveRequest.endDate"
								class="min-w-full"></DatePicker>
						<span v-if="errors['endDate']" class="text-red-400">
							{{errors['endDate'][0]}}
						</span>
					</div>
				</div>
			</div>
		
		</div>
		
		<div class="field">
			<label class="label is-size-7">Employee In Place</label>
			<div class="control w-full">
				<div class="select w-full">
					<select
							@input="clearFieldError('inPlaceId')"
							v-model="leaveRequest.inPlaceId"
							class="w-full">
						<option value="" disabled selected hidden>Select One</option>
						<option v-for="inPlaceEmployee in inPlaceEmployees" :value="inPlaceEmployee.staffId">
							{{inPlaceEmployee.fullName}}
						</option>
					</select>
				</div>
				<span
						v-if="errors['inPlaceId']"
						class="text-red-400">
									{{errors['inPlaceId'][0]}}
							</span>
			</div>
		</div>
		<div class="field">
			<label class="label">Number of Days<span><sup>*</sup></span></label>
			<div class="control">
				<input
						:value="numberOfDays"
						disabled
						class="input"
						type="text">
				<span
						v-if="errors['numberOfDays']"
						class="text-red-400">
					{{errors['numberOfDays'][0]}}
				</span>
			</div>
		</div>
		<div class="field">
			<label class="label">Remaining Leaves Days<span><sup>*</sup></span></label>
			<div class="control">
				<input
						:value="remainingLeaveDays"
						disabled
						class="input"
						type="text">
			</div>
		</div>
		<div class="field">
			<label class="label">Reason<span><sup>*</sup></span></label>
			<div class="control">
				<textarea class="textarea"></textarea>
			</div>
		</div>
		<div class="flex justify-center m-3">
			<button
					@click.prevent.stop="updateLeaveRequest"
					class="button is-rounded"
					type="submit">Submit
			</button>
		</div>
	</form>
</template>

<script>
    import CommonMixin from "../../mixins/common_mixin"
    import {DatePicker} from "element-ui"

    export default {
        name: "LeaveRequestEditForm",
        props: {
            requestId: {}
        },
        mixins: [CommonMixin],
        components: {
            DatePicker
        },
        data() {
            return {
                leaveRequest: {},
                leaveTypes: [],
                remainingLeaveDays: "",
                inPlaceEmployees: [],
            }
        },
        created() {
            this.getLeaveRequestById();
            this.getInPlaceEmployees();
            this.getLeaveTypes();
        },
        methods: {
            getLeaveRequestById() {
                axios.get(`/api/user/leave-requests/${this.requestId}`).then(resp => {
                    this.leaveRequest = resp.data;
                })
            },
            getLeaveBalanceByLeaveTypeId(leaveTypeId) {
                axios.get(`/api/user/leave-balances/${leaveTypeId}`).then(resp => {
                    this.remainingLeaveDays = resp.data;
                })
            },
            getLeaveTypes() {
                axios.get("/api/leave-types").then(resp => {
                    this.leaveTypes = resp.data;
                })
            },
            getInPlaceEmployees() {
                axios.get("/api/leave-requests/in-place").then(resp => {
                    this.inPlaceEmployees = resp.data;
                })
            },
            updateLeaveRequest() {
                let vm = this;
                axios.put(`/api/leave-request/${vm.leaveRequest.id}`,
                    vm.leaveRequest).then(resp => {
                    this.$swal({
                        text: "Leave request successfully",
                        type: "success"
                    })
                    vm.$emit("leaveUpdateSuccessful");
                }, error => {
                    if (error.response.status == 400) {
                        vm.errors = error.response.data;
                    }
                });
            }
        },
        computed: {
            numberOfDays() {
                let vm = this;
                let startDate = this.leaveRequest.startDate;
                let endDate = this.leaveRequest.endDate;
                if (startDate && endDate) {
                    let diff = moment(endDate).diff(startDate, "days");
                    vm.leaveRequest.numberOfDays = diff;
                    return diff;
                }
                vm.leaveRequest.numberOfDays = 0;
                return 0;
            }
        },
        watch: {
            'numberOfDays': function (val) {
                this.clearFieldError("numberOfDays");
            },
            'leaveRequest.leaveTypeId': {
                handler: 'getLeaveBalanceByLeaveTypeId',
                immediate: false
            }
        }
    }
</script>

<style scoped>

</style>