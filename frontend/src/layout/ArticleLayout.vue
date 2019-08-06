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
              <article-content :slug="slug"></article-content>
              <article-comment></article-comment>
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

export default {
  name: "article-page-layout",
  props: ["slug"],
  data() {
    return {};
  },
  components: {
    "user-nav-menu": Navbar,
    "article-content": Article,
    "article-comment": Comment
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
  min-width: 25%;
}
@media only screen and (max-width: 959px){
  .content-layout{
    flex-direction: column;
  }
}
</style>
