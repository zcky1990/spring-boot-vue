<template>
 <div class="user-page-container">
  <section class="nav-section">
   <user-nav-menu></user-nav-menu>
  </section>
  <section class="content-section">
   <authors-profile></authors-profile>
  </section>
 </div>
</template>

    <script>
import Navbar from "@/components/widget/navbar";
import TeacherProfileComponents from "@/components/widget/authors-tabs-components";

export default {
 name: "authors-profile-page-layout",
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
  "authors-profile": TeacherProfileComponents
 }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.sign-up-page-container {
 background-color: white;
}
</style>
