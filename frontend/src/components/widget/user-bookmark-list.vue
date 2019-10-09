<template>
  <v-container>
    <div class="bookmark-card-container">
      <div class="bookmark" v-for="bookmark in dataList" :key="bookmark.id">
        <div @click="goTo(bookmark.article.slug)">
          <div class="bookmark-date">{{getDate(bookmark.created_date)}}</div>
          <div class="bookmark-post-title">{{bookmark.article.title}}</div>
          <div class="marker"></div>
          <div class="category">
            <p v-for="category in bookmark.article.category" :key="category">{{category}}</p>
          </div>
          <div class="post-desc" v-html="bookmark.article.content"></div>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "user-bookmark-list-component",
  props: {
    bookmarkList: {
      type: Array,
      default: []
    },
    nextPage: {
      type: Number,
      default: "0"
    }
  },
  data() {
    return {
      search: "",
      page: 1,
      listBookmarkUrl: "/bookmarks/get_bookmarks_article_list",
      deleteBookmarkUrl: "bookmarks/delete/",
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
    goTo: function(slug) {
      let url = "/article/"+slug
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
            self.addData(response.data.response);
            self.page++;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    deleteBookmark: function(id, index) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.delete(
        this.deleteBookmark + id,
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
.bookmark-date {
  font-size: 1.7rem;
  font-style: normal;
  font-weight: 400;
  height: auto;
  letter-spacing: -0.74px;
  color: #00d1b2;
}
.bookmark-post-title {
  color: rgb(0, 0, 0);
  font-size: 1.4rem;
  font-style: normal;
  font-weight: 400;
}
.category {
  font-family: minion-pro;
  font-size: 1rem;
  font-style: italic;
  font-weight: 400;
  height: auto;
  letter-spacing: normal;
  line-height: 25.5px;
}
.post-desc {
  color: rgb(176, 176, 176);
  font-family: Lato;
  font-size: 12px;
  font-style: normal;
  font-weight: 400;
  letter-spacing: 1.44px;
  line-height: 21.6px;
  text-decoration-color: rgb(176, 176, 176);
  text-decoration-line: none;
  text-decoration-style: solid;
  -moz-text-size-adjust: 100%;
  text-size-adjust: 100%;
  /*text-transform: uppercase;*/
}
</style>
