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
        <router-link class="nav-draw-links" to="/sign_in">
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
                <v-chip class="button-login" v-on="on">
                  <v-avatar>
                    <v-icon>account_circle</v-icon>
                  </v-avatar>Login
                </v-chip>
            </router-link>
          </div>
        </div>
      </div>
    </v-toolbar>
    <div class="error-message-container">
      <snack-bar ref="navSnackbar"></snack-bar>
    </div>
  </div>
</template>

<script>
import { AXIOS } from "./../http-common";
import SnackBar from "./SnackBar";
import { Util } from "./../util";

export default {
  name: "navbar",
  components: {
    "snack-bar": SnackBar
  },
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
    this.isLogged = Util.isLoggin(this.$session);
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
    submit() {
      if (this.$refs.form.validate()) {
        let model = {};
        model.username = this.username;
        model.password = this.password;
        this.callRestService(model);
      }
    },
    callRestService(model) {
      let self = this;
      AXIOS.post("/users/sign_in", model)
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setErrorMessage(responseData.error_message);
            } else {
              self.$session.start();
              self.$session.set("jwt", responseData.token);
              self.$session.set("uid", responseData.response.id);
              self.$session.set("username", responseData.response.username);
              self.$session.set("exp_date", responseData.exp_date);
              self.isLogged = true;
              self.closePopUpMenu();
              self.setSuccessMessage("Success Login");
            }
          }
        })
        .catch(e => {
          self.errors.push(e);
        });
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
