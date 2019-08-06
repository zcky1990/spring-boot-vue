<template>
  <section class="section article">
    <v-container id="grid" fluid grid-list-sm tag="section">
      <v-layout wrap>
        
        <v-flex tag="h1" class="headline">{{article.article_title}}</v-flex>

        <v-flex d-flex xs12>
          <v-container>
            <v-layout>
              <div class="image">
                <v-img
                  src="https://picsum.photos/500/300?image=11"
                  lazy-src="https://picsum.photos/10/6?image=11"
                  aspect-ratio="1"
                  class="grey lighten-2 rounded"
                >
                  <template v-slot:placeholder>
                    <v-layout
                      fill-height
                      align-center
                      justify-center
                      ma-0
                    >
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-layout>
                  </template>
                </v-img>

              </div>
              <v-flex>
                <div class="author">
                  <div class="authors-name">{{article.author.name}}</div>
                  <div class="article-create-date">{{article.created_date}}</div>
                </div>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>

        <v-flex d-flex xs12 order-xs5 class="content">
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
.author {
  padding-left: 20px;
  padding-top: 7px;
}
.rounded {
  border-radius: 66px;
    width: 64px;
    height: 64px;
}
.authors-name {
    font-size: 1.2em;
}
.article-create-date {
  color: grey;
}

.headline {
  font-size: 40px !important;
}
.content {
  margin-top: 1.95em;
  font-size: 20px;
}
</style>
