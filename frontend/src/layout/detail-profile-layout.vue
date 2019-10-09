<template>
  <div class="detail-page-container">
    <section class="nav-section">
      <user-nav-menu></user-nav-menu>
    </section>
    <section class="content-section">
      <detail-profile v-bind:user="userDetails"></detail-profile>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import DetailProfile from "@/components/widget/detail-profile";

export default {
  name: "detail-profile-page-layout",
  data() {
    return {
      userDetails: {}
    };
  },
  created() {
    this.userDetails = { test: this.getUser() };
  },
  components: {
    "user-nav-menu": Navbar,
    "detail-profile": DetailProfile
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
