<template>
  <div class="nav-menu">
    <v-navigation-drawer fixed clipped v-model="drawer" app>
      <v-subheader>Application Menu</v-subheader>
      <v-list-tile v-for="item in items" :key="item.text">
        <router-link class="nav-draw-links" v-bind:to="item.url">
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </router-link>
      </v-list-tile>

      <div class="user-controller" v-if="isLogged">
       <div class="user-container">
          <v-container>
              <div class="user-avatar-container" @click="logout">
                <div class="logout-drawer">
                    <div class="logout-btn logout">
                      Logout
                    </div>
                </div>
              </div>
          </v-container>
       </div>
     </div>
      
    </v-navigation-drawer>

    <v-toolbar dense clipped-left app dark color="#00d1b2" class="nav-toolbar">
      <v-toolbar-title class="mr-5 align-center">
        <span class="title">{{title}}</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-side-icon v-if="isMobile" @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      </v-toolbar>
  </div>
</template>

<script>

import { EventBus } from './../../../EventBus.js';

export default {
  name: "admin-navbar",
  data() {
    return {
      drawer: true,
      isMobile: false,
      items: [
        { title: "Home", url: "/" },
        { title: "Category", url: "/admin/category" },
        { title: "Access Level", url: "/admin/access_level" },
        { title: "Roles", url: "/admin/roles" },
        { title: "Users", url: "/admin/users" },
      ],
      title: "Admin",
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
    setMessage(message, type) {
      let data={}
        data.message = message
        data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
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
.user-controller{
    position: absolute;
    bottom: 0;
    width: 100%;
}
.logout-drawer {
  width: 100%;
}
.logout-btn {
    padding: 5px;
    color:  #00d1b2;
    border: 1px solid  #00d1b2;
    border-radius: 15px;
    text-align: center;
}
</style>
