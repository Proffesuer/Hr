<template>
    <div>
        <div class="pb-2 flex justify-end">
            <router-link
                    to="/role-create"
                    tag="button"
                    class="button mr-1 is-rounded">
				<span class="icon">
					<i class="fa fa-plus-circle mr-1"></i>
				</span>
                <span>
					 Add Role
				</span>
            </router-link>
            <a
                    href="/api/roles/excel-report"
                    class="button is-rounded">
				<span class="icon">
					<i class="fa fa-download mr-1"></i>
				</span>
                <span>
					 Export
				</span>
            </a>
        </div>
        <div class="columns">
            <div class="column is-12">
                <div class="card" ref="leaveRequests">
                    <div class="card-content">
                        <div class="content b-table is-relative" :class="{'is-loading':loading}">
                            <h4>Roles</h4>
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
                                <tr v-for="role in roles">
                                    <td data-label="Name">{{role.name}}</td>
                                    <td data-label="Date">{{role.description}}</td>
                                    <td data-label="Action">
                                        <div class="action-controls d-flex justify-end">
                                            <button
                                                    class="button is-white is-small">
										           <span class="icon">
						                            <i class="fa fa-user-plus has-text-primary"></i>
					                               </span>
                                            </button>
                                            <router-link
                                                    :to="`/role-edit/${role.id}`" tag="button"
                                                    class="button is-white is-small">
												<span class="icon">
					                        	<i class="fa fa-pencil-square-o has-text-primary"></i>
					                       </span>
                                            </router-link>

                                            <button
                                                    @click="confirmRemoveRole(role)"
                                                    ref="deleteButton"
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
                                    <td colspan="8">
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <router-view
                @roleCreateSuccessful="onRoleCreateSuccessful"
                @roleUpdated="onRoleUpdateSuccessful">
        </router-view>
    </div>
</template>
<script>
    import DataTableMixin from "../../../mixins/data_table_mixin"
    import Paginator from "../../common/paginator/Paginator";

    export default {
        mixins: [DataTableMixin],
        components: {
            Paginator
        },
        data() {
            return {
                roles: [],
                loading: false,

            }
        },
        created() {
            this.fetchRecords();
        },
        methods: {
            fetchRecords() {
                return this.getRoles()
            },
            confirmRemoveRole(role) {
                this.$buefy.dialog.confirm({
                    title: 'Remove Role',
                    message: `Are you sure want to remove <b> ${role.name}</b> from roles`,
                }).then((result) => {
                    if(result==true) {
                        this.removeRole(role)
                    }
                },error=>{
                    console.error("some error occurred")
                })
            },
            removeRole(role) {
                axios.delete(`/api/roles/${role.id}`).then(resp => {
                    this.$swal({
                        type: "success",
                        title: "Success",
                    })
                    this.getRoles();
                })
            },
            onRoleCreateSuccessful() {
                this.getRoles();
            },
            onRoleUpdateSuccessful() {
                this.getRoles();
            },
            getRoles() {
                let vm = this;
                vm.loading = true;
                axios.get("/api/roles-paged", {
                    params: {
                        pageSize: vm.pageSize,
                        page: vm.page

                    }
                }).then(resp => {
                    this.roles = resp.data.content;
                    this.pageable = resp.data;
                    vm.loading = false;
                }, error => {
                    vm.loading = false;
                })
            }
        }
    }
</script>
<style lang="scss" scoped></style>