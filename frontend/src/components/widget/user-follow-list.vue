<template>
  <v-container>
    <div class="follow-card-container">
      <div v-if="!isFollowListNotNull" class="no-data">No data</div>
      <div v-else class="follow" v-for="(follow, index) in dataList" :key="follow.id">
        <v-card class="mx-auto" max-width="400" width="400">
          <v-img
            :src="follow.author.image_profile_url"
            lazy-src="https://picsum.photos/10/6?image=11"
            aspect-ratio="1"
            width="400"
            height="150"
            class="grey lighten-2 white--text align-end"
            @click="seeAuthorsDetails(follow.author.id)"
          >
            <template v-slot:placeholder>
              <v-layout fill-height align-center justify-center ma-0>
                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
              </v-layout>
            </template>
            <v-card-title>{{follow.author.firstname}} {{follow.author.lastname}}</v-card-title>
          </v-img>
          <div class="desc">
            <div class="email">{{follow.author.email}}</div>
            <div class="follow-btn">
              <div class="btn-follow" @click="unFollow(follow.id, index)">Unfollow</div>
            </div>
          </div>
        </v-card>
      </div>
      <div class="load-more-container" v-if="isHasMoreData">
        <v-btn
          :loading="isLoadMoreOnProgress"
          :disabled="isDisable"
          elevation="0"
          depressed
          class="load-more-btn"
          @click="getMoreBookmarkList"
        >Load More Article</v-btn>
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
      default: () => []
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
      unfollowUrl: "/users/unfollow/authors/",
      dataList: [],
      isHasMoreData: false
    };
  },
  methods: {
    addData: function(response) {
      this.dataList = this.dataList.concat(response);
    },
    removeData: function(index) {
      this.dataList.splice(index, 1);
    },
    seeAuthorsDetails: function(id) {
      let url = "/detail/" + id;
      this.$router.push(url);
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
  computed: {
    isFollowListNotNull: function() {
      return this.followList.length > 0 ? true : false;
    }
  },
  watch: {
    nextPage: function() {
      this.page = this.nextPage;
    },
    followList: function() {
      if (this.followList.length < 10) {
        this.isHasMoreData = false;
      } else {
        this.isHasMoreData = true;
      }
      this.addData(this.followList);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.follow {
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 20px;
}
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
.author-detail-container,
.image-author-container {
  padding: 10px;
}
.desc {
  padding: 16px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.follow-author-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.follow-btn {
  color: #00d1b2;
  text-align: center;
  font-size: 14px;
  font-weight: 100;
}
.btn-follow {
  border: 1px solid #00d1b2;
  border-radius: 5px;
  padding-left: 15px;
  padding-right: 15px;
  cursor: pointer;
}
.load-more-btn {
  background-color: white !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  font-weight: 700;
  color: #00d1b2;
  cursor: pointer;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
}
.load-more-btn:hover {
  background-color: #00d1b2 !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  font-weight: 700;
  color: white;
  cursor: pointer;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
}
.load-more-container {
  display: flex;
  text-align: center;
  justify-content: center;
  margin-top: 15px;
}
</style>
