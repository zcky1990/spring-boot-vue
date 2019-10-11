<template>
  <v-container>
    <div class="bookmark-card-container">
      <div class="bookmark" v-for="bookmark in dataList" :key="bookmark.id">
        <div @click="goTo(bookmark.article.slug)">
          <div class="post-date">{{getDate(bookmark.created_date)}}</div>
          <div class="post-title">{{bookmark.article.title}}</div>
          <div class="marker"></div>
          <div class="post-category">
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
</style>
