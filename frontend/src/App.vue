<template>
  <div id="app">
    <!--need to add v-app so vuetify working correctly.
    This component is used for dynamically managing your content area 
    and is the mounting point for many components.-->
    <v-app id="content-container">
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
  font-family: Open Sans, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
