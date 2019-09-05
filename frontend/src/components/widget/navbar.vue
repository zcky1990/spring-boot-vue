<template>
  <div class="nav-menu">
    <v-navigation-drawer fixed clipped v-model="drawer" app>
      <v-divider></v-divider>
      <v-subheader class="mt-3 grey--text text--darken-1">Menu</v-subheader>
      <v-list-tile class="menu-btn-link" v-for="item in items" :key="item.text">
        <router-link class="nav-draw-links" v-bind:to="item.url">
          <v-list-tile-action>
            <v-icon class="menu-links-icon">{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </router-link>
      </v-list-tile>

       <v-divider></v-divider>

      <v-subheader class="mt-3 grey--text text--darken-1">HEADER 2</v-subheader>
      
      <v-list-tile v-if="!isLogged" >
        <router-link class="nav-draw-links" to="/login">
        <v-list-tile-avatar class="button-login">
          <v-icon>account_circle</v-icon>
        </v-list-tile-avatar>
        <v-list-tile>Login</v-list-tile>
        </router-link>
      </v-list-tile>
      <v-list-tile v-if="isLogged" @click="logout">
        <v-list-tile-avatar>
          <v-icon>account_circle</v-icon>
        </v-list-tile-avatar>
        <v-list-tile>Logout</v-list-tile>
      </v-list-tile>

      <v-list-tile v-for="item in items2" :key="item.text" avatar>
        <v-list-tile-avatar>
          <img :src="`https://randomuser.me/api/portraits/men/${item.picture}.jpg`" alt />
        </v-list-tile-avatar>
        <v-list-tile-title v-text="item.text"></v-list-tile-title>
      </v-list-tile>
    </v-navigation-drawer>

    <v-toolbar dense clipped-left dark fixed flat class="nav-toolbar">
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-icon>book</v-icon>
      <v-toolbar-title class="mr-5 align-center">
        <span class="title">{{title}}</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      
      <div v-if="!isMobile" class="nav-draw-links">
        <!--container user has login -->
        <div v-if="isLogged" class="login-container">
          <v-menu v-model="users" :close-on-content-click="false" :nudge-width="300" offset-x>
            <template v-slot:activator="{ on }">
              <div class="user-avatar-container">
                <v-chip class="button-login" v-on="on">
                  <v-avatar>
                    <img :src="`https://randomuser.me/api/portraits/men/1.jpg`" alt />
                  </v-avatar>si nau
                </v-chip>
              </div>
            </template>
            <v-card>
              <v-list-tile>
                <v-list-tile-action>
                  <v-icon>account_circle</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>User Setting</v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
              <v-btn color="warning" @click="logout">Logout</v-btn>
            </v-card>
          </v-menu>
        </div>

        <!--container user not login -->
        <div v-if="!isLogged" class="login-container">
          <div class="user-avatar-container">
            <router-link class="link-login" to="/login">
                <v-chip class="button-login">
                  <v-avatar>
                    <v-icon>account_circle</v-icon>
                  </v-avatar>Login
                </v-chip>
            </router-link>
          </div>
        </div>
      </div>
    </v-toolbar>
  </div>
</template>

<script>
import { EventBus } from './../../EventBus.js';

export default {
  name: "navbar",
  data() {
    return {
      drawer: false,
      isMobile: false,
      items: [
        { title: "Home", icon: "home", url: "./" },
        { title: "About", icon: "question_answer", url: "/test" }
      ],
      items2: [{ picture: 28, text: "Joseph" }, { picture: 38, text: "Apple" }],
      title: "this is example",
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
      }
    },
    closePopUpMenu() {
      this.menu = false;
      this.users = false;
    },
    setMessage(message, type){
      let data={}
                data.message = message
                data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
    },
    logout() {
      this.$session.destroy();
      this.closePopUpMenu();
      this.isLogged = false;
      this.$router.push("/");
    }
  }
};
</script>
    <!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.nav-draw-links {
  display: flex;
  text-decoration: none;
  color: #323232;
  font-weight: 400;
  font-size:1em;
}
.nav-toolbar {
  background-color: #00d1b2 !important;
}
.menu-links-icon {
  color:#00d1b2;
}
.button-login {
  background: white;
  color: #00d1b2;
}
.title {
  color: white;
}
.link-login {
  text-decoration: none;
}
.menu-btn-link:hover{
  color: #00d1b2;
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
