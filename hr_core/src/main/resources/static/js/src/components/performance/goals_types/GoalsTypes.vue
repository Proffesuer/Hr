<template>
  <div>
    <div class="pb-2 flex justify-end">
      <button
          @click="showCreateForm=true"
          to="/goal-type-create"
          tag="button"
          class="button mr-1 is-rounded">
				<span class="icon">
					<i class="fa fa-plus-circle mr-1"></i>
				</span>
        <span>
					 Add New
				</span>
      </button>
    </div>
    <div class="columns">
      <div class="column is-12">
        <div class="card" ref="leaveRequests">
          <div class="card-content">
            <div class="content b-table is-relative">
              <h4>Goal Types</h4>
              <table class="table has-mobile-cards w-full is-hoverable">
                <thead class="font-thin">
                <tr>
                  <th>
                    Name
                  </th>
                  <th>
                    Description
                  </th>
                  <th>
                    Action
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="goalType in goalTypes">
                  <td data-label="Name">{{ goalType.type }}</td>
                  <td data-label="Description">{{ goalType.description }}</td>
                  <td data-label="Action">
                    <div class="action-controls d-flex justify-end">
                      <button
                          :to="`/goal-type-edit/${goalType.id}`" tag="button"
                          @click="setGoalTypeEdit(goalType)" class="button is-white is-small">
												<span class="icon">
					                        	<i class="fa fa-pencil-square-o has-text-primary"></i>
					                       </span>
                      </button>
                      <button
                          @click="confirmRemoveGoalType(goalType)"
                          class="button is-white is-small">
										           <span class="icon">
						                            <i class="fa fa-trash-o has-text-danger"></i>
					                               </span>
                      </button>
                    </div>
                  </td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                  <td colspan="4">
                    <Paginator
                        @previousPage="goToPrevious"
                        @nextPage="goToNext"
                        @paginationChanged="onPaginationChanged"
                        :paginationData="pageable"
                    ></Paginator>
                  </td>
                </tr>
                </tfoot>
              </table>
              <b-loading :is-full-page="false" :active.sync="loading" :can-cancel="true"></b-loading>
            </div>
          </div>
        </div>
      </div>
    </div>
    <GoalTypeCreateForm
        @modalClosed="showCreateForm=false"
        @createSuccess="handleCreateSuccess"
        v-if="showCreateForm"
    ></GoalTypeCreateForm>
    <GoalTypeEditForm
        v-if="showEditForm"
        :id="goalTypeToEdit.id"
        @updateSuccessful="handleUpdateSuccessful"
        @modalClosed="showEditForm=false">
    </GoalTypeEditForm>
  </div>
</template>
<script>
import data_table_mixin from "../../../mixins/data_table_mixin";
import Paginator from "../../common/paginator/Paginator";
import GoalTypeCreateForm from "./GoalTypeCreateForm";
import GoalTypeEditForm from "./GoalTypeEditForm";
import {Message} from "element-ui"

export default {
  components: {
    Paginator,
    GoalTypeEditForm,
    GoalTypeCreateForm
  },
  mixins: [data_table_mixin],
  data() {
    return {
      loading: false,
      goalTypeToEdit: null,
      showCreateForm: false,
      showEditForm: false,
      goalTypes: []
    }
  },
  created() {
    this.getGoalTypes();
  },
  methods: {
    getGoalTypes() {
      axios.get("/api/goal-types/paged", {
        params: {
          page: this.page,
          pageSize: this.pageSize
        }
      }).then(resp => {
        this.goalTypes = resp.data.content;
        this.pageable = resp.data;
      })
    },
    confirmRemoveGoalType(goalType) {
      this.$buefy.dialog.confirm({
        title: 'Delete Holiday',
        message: `Are you sure want to delete <b> ${goalType.type}</b> goal type`,
        onConfirm: () => this.removeGoalType(goalType)
      })
    },
    removeGoalType(goalType) {
      axios.delete(`/api/goal-types/${goalType.id}`).then(resp => {
        Message.success("Goal Type Removed");
        this.getGoalTypes()
      })
    },

    handleUpdateSuccessful() {
      this.showEditForm = false;
      this.getGoalTypes();
    },
    handleCreateSuccess() {
      this.showCreateForm = false;
      this.getGoalTypes();
    },
    setGoalTypeEdit(goalType) {
      this.goalTypeToEdit = goalType;
      let vm = this;
      this.$nextTick(() => {
        vm.showEditForm = true;
      })
    }
  }
}
</script>