<template>
    <ModalTemplate @modalClosed="$emit('modalClosed')">
        <div slot="modal-content">
            <div>
                <form>
                    <div class="has-text-centered m-3">
                        <h1 class="has-text-black"><b>Update Addition</b></h1>
                    </div>
                    <div class="field">
                        <label class="label">Name<span><sup>*</sup></span></label>
                        <div class="control">
                            <input
                                    v-model="addition.name"
                                    @input="clearFieldError('name')"
                                    class="input"
                                    type="text">
                            <span class="mb-2 has-text-danger" v-if="errors['name']">
						{{errors['name'][0]}}
					</span>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label">Calculation <span><sup>*</sup></span></label>
                        <div class="control">
                            <div class="block">
                                <b-radio v-model="addition.calculation"
                                         name="name"
                                         native-value="PERCENTAGE">
                                    Percentage of Basic Salary
                                </b-radio>
                                <b-radio v-model="addition.calculation"
                                         name="name"
                                         native-value="FLAT_RATE">
                                    Fixed Amount
                                </b-radio>
                            </div>

                        </div>
                    </div>
                    <div class="field">
                        <label class="label"> Amount <span><sup>*</sup></span></label>
                        <div class="control">
                            <input
                                    v-model="addition.amount"

                                    @input="clearFieldError('amount')"
                                    class="input"
                                    type="number">
                            <span class="mb-2 has-text-danger" v-if="errors['amount']">
						{{errors['amount'][0]}}
					</span>
                        </div>
                    </div>
                    <div class="flex justify-center m-3">
                        <button
                                :class="{'is-loading':loading}"
                                :disabled="false"
                                @click.prevent.stop="updateAddition()"
                                class="button  is-rounded"
                                type="submit">Submit
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </ModalTemplate>
</template>
<script>
    import ModalTemplate from "../../../common/ModalTemplate";
    import common_mixin from "../../../../mixins/common_mixin";

    export default {
        components: {
            ModalTemplate
        },
        mixins: [
            common_mixin
        ],
        props:
            {
                id: {
                    required: true
                }
            },
        data() {
            return {
                addition: {},
                loading: false,
            }
        },
        created() {
            this.getAdditionById();
        },
        methods: {
            getAdditionById() {
                axios.get(`/api/payroll-additions/${this.id}`).then(resp => {
                    this.addition = resp.data;
                })
            },
            updateAddition() {
                this.loading = true
                axios.put(`/api/payroll-additions/${this.id}`,
                    this.addition).then(resp => {
                    this.loading = false;
                    this.$swal({
                        type: "success",
                        title: "Success",
                        text: "Payroll addition successfully updated"
                    })
                    this.$emit("payrollAdditionUpdated");
                }, error => {
                    this.loading = false
                    if (error.response.status == 400) {
                        this.errors = error.response.data;
                    }
                })
            }
        }
    }
</script>
<style scoped lang="scss">

</style>