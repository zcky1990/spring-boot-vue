<template>
  <div class="article-page-container">
    <section class="nav-section"></section>
    <section class="content-section container">
      <v-content>
        <v-container fill-height>
          <v-flex class="left-content" d-flex xs2></v-flex>
          <v-flex d-flex xs8 class="main-content-container">
            <article-content :slug="slug"></article-content>
          </v-flex>
          <v-flex class="right-content" d-flex xs2></v-flex>
        </v-container>
      </v-content>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/UserNavbar";
import Article from "@/components/widget/Article";

export default {
  name: "article-page-layout",
  props: ["slug"],
  data() {
    return {};
  },
  components: {
    "user-nav-menu": Navbar,
    "article-content": Article
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
</style>
