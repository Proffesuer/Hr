<template>
  <div>
    <b-field>
      <template slot="label">
        {{ label }} <span v-if="required"><sup>*</sup></span>
      </template>
      <b-autocomplete
          :data="projects"
          placeholder="Select Project"
          field="name"
          :loading="isFetching"
          :check-infinite-scroll="true"
          @typing="getAsyncData"
          @select="option => selectedProject = option"
          @infinite-scroll="getMoreAsyncData">
        <template slot="header">
          <a @click="showAddProject">
            <span class="has-text-link"> Add new... </span>
          </a>
        </template>
        <template slot-scope="props">
          <div class="media">
            <div class="media-left">
              <!--                                <img width="32" :src="`https://image.tmdb.org/t/p/w500/${props.option.poster_path}`">-->
            </div>
            <div class="media-content">
              {{ props.option.name }}
              <br>
              <small>
                <!--                                    Released at {{ props.option.release_date }},-->
                <!--                                    rated <b>{{ props.option.vote_average }}</b>-->
              </small>
            </div>
          </div>
        </template>
        <template slot="footer">
          <span v-show="page > totalPages" class="has-text-grey"> Thats it! No more movies found. </span>
        </template>
      </b-autocomplete>
      <div slot="message">
        <slot name="errors">
        </slot>
      </div>
    </b-field>
  </div>
</template>
<script>
import _debounce from "lodash.debounce";

export default {
  props: {
    label: {
      default: "Project"
    },
    required: {
      default: true
    },
    clientId: {}
  },
  data() {
    return {
      projects: [],
      loading: false,
      isFetching: false,
      projectName: '',
      page: 0,
      totalPages: 0,
      selectedProject: {}
    }
  },
  methods: {
    showAddProject() {

    },
    fetchProjects(name) {
      let request = {
        page: this.page,
        pageSize: 10
      }
      if (this.clientId) {
        request.clientId = this.clientId;
      }
      axios.get("/api/projects", {
        params: request
      })
          .then(({data}) => {
            data.content.forEach((item) => this.projects.push(item))
            this.page++
            this.totalPages = data.totalPages
            this.isFetching = false
          }, error => {
            this.isFetching = false
          })
    },
    getAsyncData: _debounce(function (name) {
      // String update
      if (this.projectName !== name) {
        this.projectName = name
        this.projects = []
        this.page = 0
        this.totalPages = 0
      }
      // String cleared
      if (!name.length) {
        this.projects = []
        this.page = 0
        this.totalPages = 0
        return
      }
      // Reached last page
      if (this.page > this.totalPages) {
        return
      }
      this.isFetching = true
      //call fetchemployees
      this.fetchProjects(name)
    }, 500),
    getMoreAsyncData: _debounce(function () {
      this.getAsyncData(this.projectName)
    }, 250)
  },
  watch: {
    selectedProject: function (val) {
      this.$emit('input', val.id);
    }
  }
}
</script>
<style lang="scss" scoped>

</style>