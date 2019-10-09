    <template>
  <div class="admin-page">
    <roles-layout></roles-layout>
  </div>
</template>

    <script>
//import Navbar from "@/components/widget/Navbar";
import RolesLayout from "@/layout/admin/roles-layout";

export default {
  name: "admin-roles",
  components: {
    "roles-layout": RolesLayout
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
