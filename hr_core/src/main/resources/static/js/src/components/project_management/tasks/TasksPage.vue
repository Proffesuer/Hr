<template>
  <div class="task-page h-full columns is-gapless">
    <div class="project-list column is-one-fifth">
      <ProjectList :key="currentProject.id" @projectSelected="setCurrentProject"></ProjectList>
    </div>
    <div class="project-task-list column">
      <div class="task-list-header pl-5 pr-5 pb-3 pt-3 bg-white flex items-center">
        <div class="flex-1">
          <button @click="showAddTaskInput=!showAddTaskInput" class="button">Add task</button>
        </div>
        <div>
       <span class="icon">
       <i class="fa fa-cog fa-2x"></i>
        </span>
        </div>
      </div>
      <div class="p-5">
        <div class="task-list-item-container">
          <div v-for="(task,index) in tasks"
               :key="index"
               class="task-list-item bg-white mt-0 p-2 flex">
            <div class="flex flex-1 items-center">
              <div class="gap-1">
                <i class="fa fa-check-circle fa-2x"></i>
              </div>
              <div class="ml-1">
                {{ task.title }}
              </div>
            </div>
            <div class="flex action-icons items-center justify-end gap-1">
              <i  class="fa fa-user-plus fa-2x"></i>
              <i @click="deleteTask(task)" class="fa fa-trash fa-2x"></i>
            </div>

          </div>
        </div>
        <div v-if="showAddTaskInput">
          <input class="input" v-model="taskCreateRequest.title" placeholder="Enter new task here ..." type="text">
          <div class="flex justify-end mt-3 gap-1">
            <button @click="showAddTaskInput=!showAddTaskInput" class="button is-light">Close</button>
            <button @click="createTask" class="button">
              Add Task
            </button>
          </div>
        </div>
      </div>

    </div>
    <div class="project-details column bg-white">

      <div
          class="project-details-header border-solid	 border-b border-slate-200 pr-5 pl-5 pb-4 pt-4 flex items-center">
        <div class="flex-1">
          <button class="button">
          <span class="icon">
      <i class="fa fa-check"></i>
    </span>
            <span>Mark Complete</span>
          </button>
        </div>
        <span class="icon">
           <i class="fa fa-ellipsis-v"></i>
        </span>
      </div>

      <div class="project-details-body pl-5 pr-5 pt-3">
        <h2>Hospital Administration Phase 1</h2>
      </div>

      <div class="project-details-footer border-t border-slate-200 pr-5 pl-5 pb-4 pt-4">


        <div>
          <input placeholder="Type message..." class="input" type="text"></input>
        </div>
        <div class="flex justify-start items-center mt-3 gap-2">
          <span>Followers</span>
          <figure class="image is-32x32">
            <img src="/images/undraw_profile_pic_ic-5-t.svg">
          </figure>
          <figure class="image is-32x32">
            <img src="/images/undraw_profile_pic_ic-5-t.svg">
          </figure>
          <figure class="image is-32x32">
            <img src="/images/undraw_profile_pic_ic-5-t.svg">
          </figure>
        </div>
      </div>
    </div>

  </div>
</template>
<script>
import _throttle from "lodash.throttle"
import ProjectList from "./ProjectList";

export default {
  components: {
    ProjectList
  },
  data() {
    return {
      page: 0,
      pageSize: 10,
      projects: [],
      loading: false,
      loaded: false,
      showAddTaskInput: false,
      taskCreateRequest: {},
      currentProject: {},
      tasks: []
    }
  },
  watch: {
    currentProject() {
      this.getTasks();
    }
  },
  methods: {
    getTasks() {
      let vm = this;
      let request = {
        active: true,
        projectId: vm.currentProject.id
      }
      vm.loading = true;
      axios.get("/api/tasks",
          {params: request}).then(resp => {
        vm.loaded = true;
        vm.loading = false;
        vm.tasks=resp.data
      }, error => {
        vm.loading = false;
      })
    },
    async createTask() {
      this.taskCreateRequest.projectId = this.currentProject.id;
      let result = await axios.post("/api/tasks", this.taskCreateRequest)
      this.tasks = [...this.tasks,result.data];
      this.taskCreateRequest = {}
    },

    async deleteTask(task) {
      await axios.delete(`/api/tasks/${task.id}`)
      this.getTasks();
    },
    setCurrentProject(project) {
      this.currentProject = project;
    },
  }
}
</script>
<style scoped lang="scss">
.task-page {
  margin-top: 3.25rem;
  min-height: 100%;

  .project-list {
    background-color: #34444c;
    min-height: 100%;
  }

  .project-details {
    position: relative;

    .project-details-footer {
      position: absolute;
      bottom: 0px;
      left: 0px;
      right: 0px;
    }
  }

  .task-list-item-container:first-child {
    border-bottom: none;
  }

  .task-list-item-container {
    .task-list-item {
      border: 1px solid #eaeaea;

      .action-icons {
        display: none;
      }

      &:hover {
        .action-icons {
          display: flex;
        }
      }
    }
  }
}
</style>