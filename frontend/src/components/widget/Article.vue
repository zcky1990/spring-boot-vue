<template>
  <section class="section article">
    <div class="container article-content">
      <span class="tag is-primary">Primary</span>
      <h1 class="title is-1" >{{article.article_title}}</h1>
      <div class="content">
        <div v-html="article.article_content">
        </div>
      </div>
      <div class="media">
        <div class="media-content"></div>
      </div>
      <div class="media">
        <div class="media-left">
          <figure class="image is-96x96">
            <img
              class="is-rounded"
              src="https://bulma.io/images/placeholders/96x96.png"
              alt="Placeholder image"
            />
          </figure>
        </div>

        <div class="media-content">
          <div class="address">
            <div class="name title is-5">Hank O'Connell</div>
            <div class="email subtitle is-6">Viva.Mraz2@yahoo.com</div>
            <div class="date subtitle is-6">12-12-2019</div>
          </div>
        </div>
      </div>

      <div class="media">
        <div class="media-content"></div>
      </div>
    </div>
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
        article : {
          _id:"",
          article_title:"",
          article_content:"",
          author:{},
          created_date:"",
          slug:""
        }
    }
  },
  created() {
    this.getArticleService();
  },
  methods: {
    getArticleService: function() {
      let self = this;
      let headers = Util.getHeaders(this.$session)
      console.log(this.getArticleUrl)
      AXIOS.get(this.getArticleUrl+this.slug, { headers })
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.article = responseData;
          }
        })
        .catch(e => {

      });
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
