    <template>
  <div class="admin-page">
    <add-user-layout></add-user-layout>
  </div>
</template>

    <script>
//import Navbar from "@/components/widget/Navbar";
import AddUsersLayout from "@/layout/admin/add-users-layout"

export default {
  name: "admin-roles",
  components: {
    "add-user-layout": AddUsersLayout
  },
  created(){
    this.checkAdminSession(this.$session, this.$router)
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
