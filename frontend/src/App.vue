<template>
  <div id="app">
    <!--need to add v-app so vuetify working correctly.
    This component is used for dynamically managing your content area 
    and is the mounting point for many components.-->
    <v-app class="app-container" id="content-container">
      <router-view></router-view>
    </v-app>
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {};
  }
};
</script>
<style lang="scss">
#app {
  font-family: 'Lato';
  font-weight: 400;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.app-container {
  background-color: #fff;
}
</style>
