<template>
  <tr>
    <td>
      {{ index + 1 }}
    </td>
    <td>
      <div class="field">
        <div class="control">
          <input
              @input="clearFieldError(`items[${index}].name`)"
              class="input"
              v-model="invoiceItem.name">
          </input>
          <span class="mb-2 has-text-danger"
                v-if="errors[`items[${index}].name`]">
             {{ errors[`items[${index}].name`][0] }}
          </span>
        </div>
      </div>
    </td>
    <td>
      <div class="field">
        <div class="control">
          <input
              @input="clearFieldError(`items[${index}].description`)"
              class="input"
              v-model="invoiceItem.description"
              placeholder="Textarea">

          </input>
          <span class="mb-2 has-text-danger"
                v-if="errors[`items[${index}].description`]">
             {{ errors[`items[${index}].description`][0] }}
          </span>
        </div>
      </div>
    </td>
    <td>
      <div class="field">
        <div class="control">
          <InputNumber
              @input="clearFieldError(  `items[${index}].unitCost`)"
              :controls="false"
              v-model="invoiceItem.unitCost">

          </InputNumber>
          <span class="mb-2 has-text-danger"
                v-if="errors[`items[${index}].unitCost`]">
             {{ errors[`items[${index}].unitCost`][0] }}
          </span>
        </div>
      </div>
    </td>
    <td>
      <div class="field">
        <div class="control">
          <InputNumber
              @input="clearFieldError(`items[${index}].quantity`)"
              :min="1"
              v-model="invoiceItem.qty">
          </InputNumber>
          <span class="mb-2 has-text-danger"
                v-if="errors[`items[${index}].quantity`]">
             {{ errors[`items[${index}].quantity`][0] }}
          </span>
        </div>
      </div>
    </td>
    <td>
      <div class="field">
        <div class="control is-relative">
          <input
              class="input"
              placeholder="Textarea">
          </input>
          <div
              @click="removeInvoiceItem(index)"
              class="estimate-remove-btn has-text-danger">
            <i class="fa fa-trash"></i>
          </div>
        </div>
      </div>
    </td>
  </tr>
</template>
<script>
import common_mixin from "../../../mixins/common_mixin";
import ClientSelectInput from "../../common/ClientSelectInput";
import {InputNumber} from "element-ui"
export default {
  components: {
    ClientSelectInput,
    InputNumber,

  },
  mixins: [common_mixin],
  props: {
    invoiceItem: {
      // required: true
    },
    index: {},
    errorMessages: {}
  },
  methods: {
    removeInvoiceItem(index) {
      this.$emit("removedItem",index)
    },
  },
  watch: {
    errorMessages(val) {
      this.errors = val;
    }
  }
}
</script>
<style scoped lang="scss">
.estimate-remove-btn {
  position: absolute;
  right: -1rem;
  top: 0.5rem;
}
</style>