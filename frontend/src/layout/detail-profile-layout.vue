<template>
  <div class="detail-page-container">
    <section class="nav-section">
      <user-nav-menu></user-nav-menu>
    </section>
    <section class="content-section"></section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";

export default {
  name: "detail-profile-page-layout",
  created() {
    console.log(this.getUser());
  },
  components: {
    "user-nav-menu": Navbar
  },
  methods: {
    getUser: function() {
      return this.$route.params.user;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.detail-page-container {
  background-color: white;
}
</style>
