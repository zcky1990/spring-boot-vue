<template>
  <div class="article-page-container">
    <section class="nav-section">
      <admin-menu-navbar></admin-menu-navbar>
    </section>
    <section class="access-level-layout content-section">
      <v-content>
          <access-level-admin-content></access-level-admin-content>
      </v-content>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import AccessLevelForm from "@/components/widget/admin/access-level-form";

export default {
  name: "category-page-layout",
  components: {
    "admin-menu-navbar": Navbar,
    "access-level-admin-content": AccessLevelForm,
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
