<template>
  <div>
    <form>
      <div class="has-text-centered m-3">
        <h1 class="has-text-black"><b>Add Provident Fund</b></h1>
      </div>
      <div class="columns">
        <div class="column">
          <EmployeeSelectInput
              v-model="providentFund.employeeId"
              label="Employee Name">
         <span
             slot="errors"
             class="mb-2 has-text-danger"
             v-if="errors['employeeId']">
                {{ errors['employeeId'][0] }}
         </span>
          </EmployeeSelectInput>
        </div>
        <div class="column">
          <div class="field">
            <label class="label">Provident Fund Type<span><sup>*</sup></span></label>
            <div class="select is-fullwidth">
              <select
                  @input="clearFieldError('providentFundType')"
                  v-model="providentFund.providentFundType">
                <option value="percentOfBasic">Percentage of Basic Salary</option>
                <option value="fixedAmount">Fixed Amount</option>
              </select>
              <span
                  class="mb-2 has-text-danger"
                  v-if="errors['providentFundType']">
                {{ errors['providentFundType'][0] }}
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column">
          <div class="field">
            <label class="label">Employee Share (Amount)</label>
            <div class="control">
              <input
                  class="input"
                  v-model="providentFund.employeeShare"
                  @input="clearFieldError('employeeShare')">
              </input>
              <span
                  class="mb-2 has-text-danger"
                  v-if="errors['employeeShare']">
						{{ errors['employeeShare'][0] }}
					</span>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="field">
            <label class="label">Organization Share (Amount)</label>
            <div class="control">
              <input
                  class="input"
                  v-model="providentFund.organisationShare"
                  @input="clearFieldError('organisationShare')">
              </input>
              <span class="mb-2 has-text-danger" v-if="errors['organisationShare']">
						{{ errors['organisationShare'][0] }}
					</span>
            </div>
          </div>
        </div>

      </div>
      <div class="columns">
        <div class="column">
          <div class="field">
            <label class="label">Employee Share (%)</label>
            <div class="control">
              <input
                  class="input"
                  v-model="providentFund.percentageEmployeeShare"
                  @input="clearFieldError('percentageEmployeeShare')">
              </input>
              <span class="mb-2 has-text-danger" v-if="errors['percentageEmployeeShare']">
						{{ errors['percentageEmployeeShare'][0] }}
					</span>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="field">
            <label class="label">Organization Share (%)</label>
            <div class="control">
              <input
                  class="input"
                  v-model="providentFund.percentageOrganisationShare"
                  @input="clearFieldError('percentageOrganisationShare')">
              </input>
              <span class="mb-2 has-text-danger" v-if="errors['percentageOrganisationShare']">
						{{ errors['percentageOrganisationShare'][0] }}
					</span>
            </div>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column">
          <div class="field">
            <label class="label">Description<span><sup>*</sup></span></label>
            <textarea
                class="textarea"
                @input="clearFieldError('description')"
                v-model="providentFund.description">
              </textarea>
            <span class="mb-2 has-text-danger" v-if="errors['description']">
						{{ errors['description'][0] }}
					</span>
          </div>
        </div>
      </div>
      <div class="flex justify-center m-3">
        <button
            ref="createButton"
            :class="{'is-loading':isLoading}"
            :disabled="disableSubmitButton"
            @click.prevent.stop="createProvidentFund()"
            class="button  is-rounded"
            type="submit">Submit
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import common_mixin from "../../../mixins/common_mixin";
import EmployeeSelectInput from "../../common/EmployeeSelectInput";
import {DatePicker} from "element-ui"
import {Message} from "element-ui"

export default {
  components: {
    DatePicker,
    EmployeeSelectInput

  },
  mixins: [common_mixin],
  data() {
    return {
      providentFund: {},
      isLoading: false
    }
  },

  methods: {
    createProvidentFund() {
      this.isLoading = true;
      axios.post("/api/provident-funds/",
          this.providentFund).then(resp => {
        this.isLoading = false
        Message.success("Provident fund successfully created")
        this.$emit("createSuccessful");
      }, error => {
        this.isLoading = false;
        if (error.response.status == 400) {
          this.errors = error.response.data;
        }
      })
    }
  },
  computed: {
    disableSubmitButton() {
      return this.isLoading;
    }
  }

}
</script>
<style scoped lang="scss">

</style>