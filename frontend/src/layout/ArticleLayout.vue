<template>
  <div class="article-page-container">
    <section class="nav-section"></section>
    <section class="content-section container">
      <div class="snack-bar-container">
        <snack-bar ref="snackbar"></snack-bar>
      </div>
      <v-content>
        <v-container fill-height>
          <v-layout class="content-layout">
            <v-flex class="left-content" >
            </v-flex>
            <v-flex class="main-content-container">
              <article-content 
                v-bind:content="article">
              </article-content>
              <article-comment 
                v-bind:article-id="article._id">
              </article-comment>
            </v-flex>
            <v-flex class="right-content" >
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/UserNavbar";
import Article from "@/components/widget/Article";
import Comment from "@/components/widget/Comment"
import SnackBar from "@/components/widget/SnackBar";

export default {
  name: "article-page-layout",
  props: ["slug"],
  data() {
    return {
      getArticleUrl: "/article/get_article/",
      article: {
        _id: "",
        article_title: "",
        article_content: "",
        author: {},
        created_date: "",
        slug: ""
      },
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
    };
  },
  components: {
    "user-nav-menu": Navbar,
    "article-content": Article,
    "article-comment": Comment,
    "snack-bar": SnackBar
  },
  methods: {
     getArticleService: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get(this.getArticleUrl + this.slug,  headers,
      function(response){
        let responseData = response.data.response;
        self.article = responseData;
      },function (e){
        self.setMessage(e, 1)
      })
    },
    setMessage(message, type) {
      if(type == 0){
        this.snackBarConfig.color = "success"
      }else{
        this.snackBarConfig.color = "error"
      }
      this.$refs.snackbar.setConfig(this.snackBarConfig);
      this.$refs.snackbar.showSnackbar(message);
    },
  },
  created() {
    this.getArticleService();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.sign-up-page-container {
  background-color: white;
}
.content-section {
  padding-top: 45px;
}
.content-layout{
  flex-direction: row;
}
.left-content,.right-content {
  min-width: 15%;
}
@media only screen and (max-width: 959px){
  .content-layout{
    flex-direction: column;
  }
}
</style>
