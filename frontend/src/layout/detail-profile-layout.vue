<template>
  <div class="detail-page-container">
    <section class="nav-section">
      <user-nav-menu></user-nav-menu>
    </section>
    <section class="content-section">
      <div class="detail-pofile">
        <detail-profile v-bind:user="userId"></detail-profile>
      </div>
      <div class="author-list-article">
        <v-container>
          <div class="title-user-list-article">Users Article List</div>
          <article-user url="/article/get_by_user_id" mode="getByUserId" v-bind:user="userId"></article-user>
        </v-container>
      </div>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import DetailProfile from "@/components/widget/detail-profile";
import ListArticle from "@/components/widget/list-article";

export default {
  name: "detail-profile-page-layout",
  data() {
    return {
      userId: {}
    };
  },
  created() {
    this.userId = { id: this.getId() };
  },
  components: {
    "user-nav-menu": Navbar,
    "detail-profile": DetailProfile,
    "article-user": ListArticle
  },
  methods: {
    getId: function() {
      return this.$route.params.id;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.detail-page-container {
  background-color: white;
}
.title-user-list-article {
  font-family: "Lato";
  text-align: left;
  font-size: 1.5em !important;
  line-height: 1.2 !important;
  margin-bottom: 5px;
}
</style>
