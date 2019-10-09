<template>
  <div class="article-page-container">
    <section class="nav-section">
      <user-nav-menu></user-nav-menu>
    </section>
    <section class="content-section container">
      <v-content>
        <v-container fill-height>
          <v-layout class="content-layout">
            <v-flex class="main-content-container">
              <article-content v-bind:content="article"></article-content>
              <article-comment v-bind:article-id="article.id"></article-comment>
            </v-flex>
            <v-flex class="right-content"></v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import ArticleComponents from "@/components/widget/article";
import Comment from "@/components/widget/comment";

import { EventBus } from "../EventBus.js";

export default {
  name: "article-page-layout",
  data() {
    return {
      getArticleUrl: "/article/get_article/",
      article: {},
      slug: "",
      isUserLoggin: false
    };
  },
  components: {
    "user-nav-menu": Navbar,
    "article-content": ArticleComponents,
    "article-comment": Comment
  },
  methods: {
    getArticleService: function() {
      let self = this;
      this.isUserLoggin = this.isLoggin(this.$session);
      let headers = {};
      if (this.isUserLoggin) {
        headers = this.getHeaders(this.$session);
      } else {
        headers = this.getDefaultHeaders(this.getMeta("token"));
      }
      this.get(
        this.getArticleUrl + this.slug,
        headers,
        function(response) {
          let responseData = response.data.response;
          self.article = responseData;
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    setMessage(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  },
  created() {
    this.slug = this.$route.params.slug;
    this.getArticleService();
  }
};
</script>
<style scoped>
.sign-up-page-container {
  background-color: white;
}
.content-layout {
  flex-direction: row;
}
.left-content,
.right-content {
  min-width: 15%;
}
@media only screen and (max-width: 959px) {
  .content-layout {
    flex-direction: column;
  }
}
</style>
