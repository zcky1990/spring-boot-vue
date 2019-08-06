<template>
  <section class="section article">
    <v-container id="grid" fluid grid-list-sm tag="section">
      <v-layout wrap>
        <v-flex d-flex xs12>
          
        </v-flex>
        <v-flex tag="h1" class="headline">{{article.article_title}}</v-flex>
        <v-flex d-flex xs12 order-xs5>
          <v-layout column>
            <v-flex>
              <v-card flat>
                <div v-html="article.article_content"></div>
              </v-card>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
    </v-container>
  </section>
</template>

<script>
import { AXIOS } from "./../http-common";
import { Util } from "@/components/util";

export default {
  name: "article-component",
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
      }
    };
  },
  created() {
    this.getArticleService();
  },
  methods: {
    getArticleService: function() {
      let self = this;
      let headers = Util.getHeaders(this.$session);
      console.log(this.getArticleUrl);
      AXIOS.get(this.getArticleUrl + this.slug, { headers })
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.article = responseData;
          }
        })
        .catch(e => {});
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.article-content {
  text-align: left;
}
</style>
