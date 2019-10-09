<template>
  <div class="admin-page">
    <admin-layout-page></admin-layout-page>
  </div>
</template>

<script>
import AdminLayout from "@/layout/admin/admin-layout";

export default {
  name: "admin",
  components: {
    "admin-layout-page": AdminLayout
  }
};
</script>

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
