<template>
	<div class="bg-white">
		<EmptyState v-if="showEmptyState" >
			<h4 class="ml-10">Leave request has no history</h4>
		</EmptyState>
		<div class="is-size-8 ">
		<div v-for="leaveHistory in leaveRequestHistory" class="bg-white p-5 h-full w-full">
			<div class="flex flex-row">
				<h4 class="px-1">{{leaveHistory.leaveStatuses}}</h4>
				<span>
					<span class="icon px-1"><i class="fa fa-calendar"></i></span><span><span class="has-text-primary">Performed On : </span>{{leaveHistory.createdAt|dateFormat}}</span>
					<span class="icon px-1"><i class="fa fa-user"></i></span><span><span class="has-text-primary">Performed By: </span>{{leaveHistory.user.firstName}}</span>
				</span>
			
			</div>
		</div>
		</div>
	</div>
</template>
<script>
	import EmptyState from "../../common/EmptyState.vue"
    export default {
	    components:{
            EmptyState
		},
        data() {
            return {
                leaveRequestHistory: [],
                loaded: false
            }
        },
        created() {
            this.getLeaveHistory();
        },
        methods: {
            getLeaveHistory() {
                let leaveId = this.$route.params.id;
                axios.get(`/api/leave-request-history/${leaveId}`).then(resp => {
                    this.leaveRequestHistory = resp.data;
                    this.loaded = true;
                })
            }

        },
		computed:{
            showEmptyState(){
                return this.loaded && this.leaveRequestHistory.length<=0;
            }
		},
        filters: {
            dateFormat(date) {
                return moment(date).format("DD-MM-YYYY")
            }
        },
       
    }
</script>

