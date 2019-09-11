    <template>
  <div class="admin-page">
    <category-layout></category-layout>
  </div>
</template>

    <script>
import CategoryLayout from "@/layout/admin/category-layout"

export default {
  name: "admin-category",
  components: {
    "category-layout": CategoryLayout
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
