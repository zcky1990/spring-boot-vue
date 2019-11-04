<template>
  <v-container>
    <div class="follow-card-container">
      <div class="follow" v-for="follow in dataList" :key="follow.id">
        asdasd
      </div>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "user-follow-list",
  props: {
    followList: {
      type: Array,
      default: () => ([])
    },
    nextPage: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      search: "",
      page: 1,
      listFollowUrl: "/users/get_follewed_author",
      unfollowUrl: "/unfollow/authors/",
      selected: [],
      tableHeaderList: [],
      dataList: [],
      singleSelect: true
    };
  },
  methods: {
    addData: function(response) {
      this.dataList = this.dataList.concat(response);
    },
    removeData: function(index) {
      this.dataList.arr.splice(index, 1);
    },
    getMoreFollowList: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.get(
        this.listFollowUrl + "?page=" + this.page,
        headers,
        function(response) {
          if (response.status == 200) {
            self.addData(response.data.response);
            self.page++;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    unFollow: function(id, index) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.delete(
        this.unfollowUrl + id,
        headers,
        function(response) {
          if (response.status == 200) {
            self.removeData(index);
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    }
  },
  watch: {
    nextPage: function() {
      this.page = this.nextPage;
    },
    followList: function() {
      if(this.followList.length < 5){
        this.isHasMoreData = false;
      }
      this.addData(this.followList);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.follow-card-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.follow-table-title {
  color: rgb(0, 0, 0);
  font-size: 2rem;
  font-style: normal;
  font-weight: 500;
  letter-spacing: 1.8px;
  padding-bottom: 10px;
  padding-right: 10px;
  padding-top: 10px;
  pointer-events: auto;
  text-decoration-color: rgb(0, 0, 0);
  text-rendering: optimizelegibility;
  text-size-adjust: 100%;
  text-transform: none;
  -webkit-font-smoothing: subpixel-antialiased;
}
</style>
