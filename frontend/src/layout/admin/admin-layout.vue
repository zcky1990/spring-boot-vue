<template>
  <div class="article-page-container">
    <section class="nav-section">
      <admin-menu-navbar></admin-menu-navbar>
    </section>
    <section class="admin-layout content-section">
      <v-content>
        
      </v-content>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/admin/admin-navbar";


export default {
  name: "admin-page-layout",
  components: {
    "admin-menu-navbar": Navbar
  },
  created(){
    let isLogged = this.isLoggin(this.$session);
    if(!isLogged){
      this.$router.push("/");
    }
    let accessLevel = this.getUserAccessLevel();
      if(accessLevel >30){
        this.$router.push("/");
      }
  }
};
</script>
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
  min-width: 15%;
}
@media only screen and (max-width: 959px){
  .content-layout{
    flex-direction: column;
  }
}
</style>
