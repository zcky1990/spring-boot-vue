    <template>
  <div class="admin-page">
    <access-list-layout></access-list-layout>
  </div>
</template>

    <script>
//import Navbar from "@/components/widget/Navbar";
import AccessListLayout from "@/layout/admin/access-list-layout";

export default {
  name: "admin-access-list",
  components: {
    "access-list-layout": AccessListLayout
  },
  created() {
    this.checkAdminSession(this.$session, this.$router);
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
