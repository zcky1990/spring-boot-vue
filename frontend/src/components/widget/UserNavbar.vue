<template>
  <div class="nav-menu">
    <v-navigation-drawer fixed clipped v-model="drawer" app>

      <v-subheader class="mt-3 grey--text text--darken-1">HEADER</v-subheader>
      <v-list-tile v-for="item in items" :key="item.text">
        <router-link class="nav-draw-links" v-bind:to="item.url">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </router-link>
      </v-list-tile>
      <v-subheader class="mt-3 grey--text text--darken-1">HEADER 2</v-subheader>
      
    </v-navigation-drawer>

    <v-toolbar dense clipped-left app dark color="#00d1b2" class="nav-toolbar">
      <v-icon class="mx-3">fa-youtube</v-icon>
      <v-toolbar-title class="mr-5 align-center">
        <span class="title">{{title}}</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-side-icon v-if="isMobile" @click.stop="drawer = !drawer"></v-toolbar-side-icon>
       
      </v-toolbar>
    <div class="error-message-container">
      <snack-bar ref="navSnackbar"></snack-bar>
    </div>
  </div>
</template>

<script>
import SnackBar from "./SnackBar";

export default {
  name: "user-navbar",
  components: {
    "snack-bar": SnackBar
  },
  data() {
    return {
      drawer: true,
      isMobile: false,
      items: [
        { title: "Home", icon: "dashboard", url: "/" },
        { title: "About", icon: "question_answer", url: "/test" }
      ],
      items2: [{ picture: 28, text: "Joseph" }, { picture: 38, text: "Apple" }],
      title: "MariBelajar",
      icon: {
        type: String,
        default: "$vuetify.icons.cancel"
      },
      window: {
        width: 0,
        height: 0
      },
      users: false,
      menu: false,
      message: false,
      valid: true,
      username: "",
      password: "",
      showPassword: false,
      rules: {
        required: value => !!value || "Required.",
        min: v => v.length >= 8 || "Min 8 characters"
      },
      useranameRules: [
        v => !!v || "Username is required",
        v => (v && v.length >= 8) || "Username must be more than 8 characters"
      ],
      passwordRules: [
        v => !!v || "Password is required",
        v => (v && v.length >= 8) || "Password must be more than 8 characters"
      ],
      errorSnackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
      successSnackBarConfig: {
        color: "success",
        timeout: 6000,
        top: true
      },
      isLogged: false
    };
  },
  created() {
    this.isLogged = this.isLoggin(this.$session);
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      this.checkRes();
    },
    checkRes() {
      if (this.window.width <= 640) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
        this.drawer = true;
      }
    },
    closePopUpMenu() {
      this.menu = false;
      this.users = false;
    },
    setSuccessMessage(message) {
      this.$refs.navSnackbar.setConfig(this.successSnackBarConfig);
      this.$refs.navSnackbar.showSnackbar(message);
      this.closePopUpMenu();
      this.$refs.form.reset();
    },
    setErrorMessage(errorMessage) {
      this.$refs.navSnackbar.setConfig(this.errorSnackBarConfig);
      this.$refs.navSnackbar.showSnackbar(errorMessage);
      this.closePopUpMenu();
      this.$refs.form.reset();
    },
    logout() {
      this.$session.destroy();
      this.closePopUpMenu();
      this.isLogged = false;
    }
  }
};
</script>
    <!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.nav-draw-links {
  display: flex;
  text-decoration: none;
}

a.v-list__tile.v-list__tile--link.theme--light {
    border: 1px solid red;
    margin: 5px;
    border-radius: 40px;
}

.nav-link,
.user-avatar-container {
  display: flex;
  padding: 5px;
}

.login-form-container {
  padding: 20px;
}
</style>
