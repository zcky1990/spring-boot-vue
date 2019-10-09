<template>
  <div class="user-tab-container">
    <div class="page-title">
      <v-container>
        <div class="overview">Overview</div>
        <div class="title">User Details Profile</div>
      </v-container>
    </div>
    <v-tabs
      v-model="tab"
      background-color="transparent"
      slider-color="#00d1b2"
      grow
      class="tabs-container"
    >
      <v-tab v-for="item in tabs" :key="item.index">
        <div class="tab-name">{{ item.name }}</div>
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item v-for="item in tabs" :key="item.index">
        <div class="tab-content-container" v-show="item.index == 1">
          <user-profile v-bind:user="users"></user-profile>
        </div>
        <div v-show="item.index == 2">
          <user-article v-bind:article-list="articleList" v-bind:next-page="articlePage"></user-article>
        </div>
        <div v-show="item.index == 3">
          <bookmark-list v-bind:bookmark-list="bookmarkList" v-bind:next-page="bookmarkPage"></bookmark-list>
        </div>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import UserProfile from "./user-profile";
import Article from "./user-list-article";
import BookmarkList from "./user-bookmark-list";

export default {
  name: "user-tabs-component",
  data() {
    return {
      tab: null,
      centered: true,
      grow: true,
      currentTab: 0,
      users: {},
      articleList: [],
      articlePage: 0,
      bookmarkList: [],
      bookmarkPage: 0,
      getArticleUrl: "/article/get_list_article",
      getUserProfileUrl: "/users/get_user_detail/",
      listBookmarkUrl: "/bookmarks/get_bookmarks_article_list",
      tabs: [
        { name: "Profil", index: "1" },
        { name: "Article", index: "2" },
        { name: "Reading", index: "3" }
      ]
    };
  },
  components: {
    "user-profile": UserProfile,
    "user-article": Article,
    "bookmark-list": BookmarkList
  },
  created() {
    this.getUsers();
    this.getArticle();
    this.getBookmarkList();
  },
  methods: {
    getBookmarkList: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.get(
        this.listBookmarkUrl + "?page=" + this.bookmarkPage,
        headers,
        function(response) {
          if (response.status == 200) {
            self.bookmarkList = self.bookmarkList.concat(
              response.data.response
            );
            self.bookmarkPage++;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    getArticle: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.get(
        this.getArticleUrl + "?page=" + this.articlePage,
        headers,
        function(response) {
          if (response.status == 200) {
            self.articleList = self.articleList.concat(response.data.response);
            self.articlePage++;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    getUsers: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      let id = this.$session.get("uid");
      this.get(
        this.getUserProfileUrl + id,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.users = responseData;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    }
  }
};
</script>

<style scoped>
@media only screen and (max-width: 640px) {
  .tab-content-container {
    padding: 0 !important;
  }
  .page-title {
    padding: 0 !important;
    color: #000;
    font-size: 1.2rem;
    font-weight: 400;
  }
}
.tabs-container {
  border-bottom: 1px solid #dee2e6 !important;
}
.table-container {
  padding-left: 16px;
  padding-right: 16px;
}
.user-tab-container {
  width: 100%;
}
.tab-content-container {
  padding: 24px;
}
.page-title {
  padding: 24px;
  color: #000;
  font-size: 1.2rem;
  font-weight: 400;
}
.overview {
  font-size: 1.2em;
  line-height: 1.5;
}
.title {
  font-size: 2em !important;
  line-height: 1.25 !important;
  font-weight: 600;
}
.tab-name {
  font-size: 1rem;
  font-weight: 600;
  color: #000;
}
</style>
