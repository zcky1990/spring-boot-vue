<template>
  <div class="user-page-container">
    <section class="nav-section">
      <user-nav-menu></user-nav-menu>
    </section>
    <section class="article content-section">
      <v-container fill-height align-center justify-center>
        <authors-list-article></authors-list-article>
      </v-container>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import ListArticle from "@/components/widget/authors-list-article";

export default {
  name: "authors-article-page-layout",
  created() {
    let isLogged = this.isLoggin(this.$session);
    if (!isLogged) {
      this.$router.push("/");
    }
    let accessLevel = this.getUserAccessLevel();
    if (accessLevel < 20) {
      this.$router.push("/");
    }
  },
  components: {
    "user-nav-menu": Navbar,
    "authors-list-article": ListArticle
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.sign-up-page-container {
  background-color: white;
}
</style>
