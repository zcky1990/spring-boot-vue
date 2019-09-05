<template>
  <div class="article-page-container">
    <section class="nav-section"></section>
    <section class="content-section container">
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
import Navbar from "@/components/widget/user-navbar";
import Article from "@/components/widget/article";
import Comment from "@/components/widget/comment"

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

      })
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
