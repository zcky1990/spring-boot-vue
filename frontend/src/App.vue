<template>
  <div id="app">
    <!--need to add v-app so vuetify working correctly.
    This component is used for dynamically managing your content area 
    and is the mounting point for many components.-->
    <v-app class="app-container" id="content-container">
      <div class="snack-bar-container">
        <snack-bar ref="snackbar" @showSnackBar="setMessage()"></snack-bar>
      </div>
      <router-view></router-view>
    </v-app>
  </div>
</template>

<script>
import SnackBar from "@/components/widget/snack-bar";
import { EventBus } from "./EventBus.js";

export default {
  name: "app",
  data() {
    return {
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      }
    };
  },
  created() {
    EventBus.$on("SNACKBAR_TRIGGERED", val => {
      this.setMessage(val.message, val.type);
    });
  },
  components: {
    "snack-bar": SnackBar
  },
  methods: {
    setMessage: function(message, type) {
      if (type == 0) {
        this.snackBarConfig.color = "success";
      } else {
        this.snackBarConfig.color = "error";
      }
      this.$refs.snackbar.setConfig(this.snackBarConfig);
      this.$refs.snackbar.showSnackbar(message);
    }
  }
};
</script>
<style lang="scss">
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.app-container {
  background-color: #fff !important;
}
.marker {
  width: 100px;
  border: 1px solid #00d1b2;
  margin-bottom: 5px;
  margin-top: 5px;
}
</style>
