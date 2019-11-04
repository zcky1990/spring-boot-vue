<template>
  <v-container>
    <div class="bookmark-card-container">
      <div class="bookmark" v-for="(bookmark, index) in dataList" :key="bookmark.id">
        <div class="bookmark-container">
          <div class="post-date">
            <div class="date">{{getDate(bookmark.created_date)}}</div>
            <div class="bookmark-btn" @click="deleteBookmark(bookmark.id, index)">
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-icon color="red" v-on="on">bookmark</v-icon>
                </template>
                <span>Unbookmark this Article</span>
              </v-tooltip>
            </div>
          </div>
          <div class="bookmark-detail-container" @click="goToArticle(bookmark.article.slug)">
            <div class="post-title">{{bookmark.article.title}}</div>
            <div class="marker"></div>
            <div class="post-category">
              <p v-for="category in bookmark.article.category" :key="category">{{category}}</p>
            </div>
            <div class="post-desc" v-html="bookmark.article.content"></div>
          </div>
        </div>
      </div>
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
  </v-container>
</template>

<script>
import { EventBus } from "./../../EventBus.js";

export default {
  name: "user-bookmark-list-component",
  props: {
    bookmarkList: {
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
      listBookmarkUrl: "/bookmarks/get_bookmarks_article_list",
      deleteBookmarkUrl: "/bookmarks/delete/",
      dataList: [],
      isLoadMoreOnProgress: false,
      dialog: false,
      isDisable: false,
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
    goToArticle: function(slug) {
      let url = "/article/" + slug;
      this.$router.push(url);
    },
    getMoreBookmarkList: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.get(
        this.listBookmarkUrl + "?page=" + this.page,
        headers,
        function(response) {
          if (response.status == 200) {
            if (response.data.length > 0) {
              self.addData(response.response);
              self.page++;
            } else {
              self.isHasMoreData = false;
            }
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    deleteBookmark: function(id, index) {
      let self = this;
      console.log(id, index);
      let headers = this.getHeaders(this.$session);
      this.delete(
        this.deleteBookmarkUrl + id,
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
    },
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    },
    getDate: function(date) {
      let months = [
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Augustus",
        "September",
        "Oktober",
        "November",
        "Desember"
      ];
      let d = new Date(date);
      return months[d.getMonth()] + " " + d.getDate() + ", " + d.getFullYear();
    }
  },
  computed: {},
  watch: {
    nextPage: function() {
      this.page = this.nextPage;
    },
    bookmarkList: function() {
      if (this.bookmarkList.length < 10) {
        this.isHasMoreData = false;
      } else {
        this.isHasMoreData = true;
      }
      this.addData(this.bookmarkList);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.bookmark-card-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.post-date {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.bookmark-table-title {
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
