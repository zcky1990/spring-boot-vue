<template>
  <div class="nav-menu">
    <v-navigation-drawer fixed clipped v-model="drawer" app>
      <!--user detail drawer-->
      <div class="user-detail-controller">
        <div class="btn-drawer-login"  v-if="!isLogged">
          <div class="user-title">
            <div class="name">
              Welcome
            </div>
            <div class="email">
              please login before you continue
            </div>
          </div>
          <div class="btn-container">
            <div class="button-login rounded" >
              <router-link class="link-login nav-draw-links button-login" to="/login">
                    <v-avatar>
                        <v-icon color="#00d1b2">account_circle</v-icon>
                    </v-avatar>Login
              </router-link>
            </div>
          </div>
        </div>


        <div class="user-item" v-if="isLogged">
          <div class="image-user">
            <router-link class="link-login nav-draw-links button-login image-border" to="/user">
                   <v-avatar v-if="isUserImageExits" size="70">
                      <img :src="usersData.image_url" alt />
                  </v-avatar>
                  <v-avatar v-if="!isUserImageExits" size="70">
                         <v-icon >account_circle</v-icon>
                      </v-avatar>
            </router-link>
          </div>
          <div class="user-title">
            <div class="name">
              {{fullName}}
            </div>
            <div class="email">
              {{usersData.email}}
            </div>
          </div>
        </div>
      </div>

      <v-divider></v-divider>

      <v-list-tile class="menu-btn-link" v-for="item in items" :key="item.text">
        <router-link class="nav-draw-links drawer-links" v-bind:to="item.url">
          <v-list-tile-content>
            <div class="container">
              <v-list-tile-title>{{ item.title }}</v-list-tile-title>
            </div>
          </v-list-tile-content>
        </router-link>
      </v-list-tile>
      
     <div class="user-controller"  v-if="isLogged">
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


    <div class="toolbar">
      <div class="container toolbar-title">
        <div class="title-container">
          <div class="title-navbar">
          {{title}}
          </div>
        <div class="sub-title">
          Knowledge archive
        </div>
        </div>
        <div v-if="isMobile">
          <v-toolbar-side-icon @click.stop="drawer = !drawer" color="#00d1b2" class="burger-menu"></v-toolbar-side-icon>
        </div>
         <div v-if="!isMobile">
          <div class="icon-container">
            <div v-if="!isLogged" class="login-container">
              <div class="user-avatar-container">
                  <v-chip class="button-login">
                    <router-link class="link-login button-login" to="/login">
                      <v-avatar>
                        <v-icon>account_circle</v-icon>
                      </v-avatar>Login
                    </router-link>
                  </v-chip>
              </div>
            </div>
            <div v-if="isLogged" >
              <v-menu v-model="users" :close-on-content-click="false" :nudge-width="300" offset-x>
                <template v-slot:activator="{ on }">
                  <div class="user-avatar-container">
                    <v-chip class="button-login" v-on="on">
                      <v-avatar v-if="isUserImageExits">
                        <img  :src="usersData.image_url" alt />
                      </v-avatar>
                      <v-avatar v-if="!isUserImageExits" >
                         <v-icon >account_circle</v-icon>
                      </v-avatar>
                      {{fullName}}
                    </v-chip>
                  </div>
                </template>
                <v-card>
                  <v-container>
                    <div class="pop-up-menu-title">
                      User Menu
                    </div>
                    <v-list-tile>
                      <router-link class="pop-up-link" to="/user">
                        <v-list-tile-action>
                          <v-icon >account_circle</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                          <v-list-tile-title>User Setting</v-list-tile-title>
                        </v-list-tile-content>
                      </router-link>
                    </v-list-tile>
                    <div class="user-avatar-container" @click="logout">
                      <div class="logout-drawer">
                          <div class="logout-btn logout">
                            Logout
                          </div>
                      </div>
                    </div>
                  </v-container>
                </v-card>
              </v-menu>
            </div>
          </div>
         </div>
      </div>

      <div v-if="!isMobile" ref="navLink" class="link-container">
        <div class="container toolbar-link">
         <div class="links">
            <div class="menu-btn-link" v-for="item in items" :key="item.text">
              <router-link class="nav-draw-links" v-bind:to="item.url">
              <div class="link">{{ item.title }}</div>
              </router-link>
            </div>
         </div>
        </div>
      </div>

      <div v-if="!isMobile && isHeaderFixedShow" class="link-container fixed-menu">
        <div class="container toolbar-link">
         <div class="links">
            <div class="menu-btn-link" v-for="item in items" :key="item.text">
              <router-link class="nav-draw-links" v-bind:to="item.url">
              <div class="link">{{ item.title }}</div>
              </router-link>
            </div>
         </div>
        </div>
      </div>
    </div>
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
        { title: "Home", url: {"name": "Index"} },
        { title: "About", url: "/test" }
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
      isLogged: false,
      usersData:{},
      isHeaderFixedShow: false
    };
  },
  created() {
    this.isLogged = this.isLoggin(this.$session);
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.usersData = this.getUsers(this.$session)
    window.addEventListener("scroll", this.handleFixedNavBar);
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
    window.removeEventListener("scroll", this.handleFixedNavBar);
  },
  computed: {
    fullName(){
      if(this.usersData.display_name != undefined){
        return this.usersData.display_name
      }else{
        return this.usersData.firstname+" "+this.usersData.lastname;
      }
    }
  },
  methods: {
    isUserImageExits(){
      if(this.usersData.image_url != undefined){
        return true
      }else{
        return false
      }
    },
    handleFixedNavBar() {
      if(this.isMobile == false){
        if(this.$refs.navLink){
          const top = this.$refs.navLink.getBoundingClientRect().top
          if(top < 0 ){
            this.isHeaderFixedShow = true;
          } else {
            this.isHeaderFixedShow = false;
          }
        }
      }         
    },
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      this.checkRes();
    },
    checkRes() {
      if (this.window.width <= 640) {
        this.isMobile = true;
      } else {
        this.drawer = false;
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
@media only screen and (max-width: 640px) {
  .toolbar-title{
    border-bottom: 1px solid #efefef;
  }
}
.drawer-links {
    color: #000 !important;
    font-weight: 500 !important;
    font-size: 1rem ! important;
}
.toolbar-title{
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: space-between;
}
.title-navbar {
    line-height: 1;
    font-size: 2rem;
    font-weight: 500;
    margin-bottom: 4px;
}
.sub-title {
    font-size: 0.9rem;
    text-transform: uppercase;
    line-height: 1;
}
.link-container{
  border-bottom: 1px solid #00d1b2;
  background: #00d1b2;
}
.links {
    display: flex;
    flex-direction: row;
    min-width: 200px;
    max-width: 200px;
    justify-content: space-between;
}
.nav-draw-links {
  /*color: #00d1b2 !important; */
    text-transform: uppercase;
    font-size: 1rem;
    letter-spacing: 0.2rem;
    line-height: 1;
    font-weight: 800;
    display: inline-block;
    text-decoration: none!important;
    color: #fff;
}
.nav-toolbar {
  color: #00d1b2 !important;
}
.green-color{
  color: #00d1b2 !important;
}
.login-container {
    border: 1px solid #00d1b2;
    border-radius: 5px;
}
.button-login {
  background: white;
  color: #00d1b2;
}
.pop-up-menu-title {
    font-size: 1.2rem;
    font-weight: 600;
    line-height: 1;
}
.pop-up-link {
    text-decoration: none;
    display: flex;
    font-size: 1rem;
    color: #000;
}
.rounded {
  border-radius: 25px;
}
.title {
  color: white;
}
.link-login {
  text-decoration: none;
}
.nav-link,
.user-avatar-container {
  display: flex;
  padding: 5px;
}
.login-form-container {
  padding: 20px;
}
/** drawer css **/
.user-detail-controller {
  padding: 10px;
  background: #00d1b2;
  min-height: 200px;
}
.btn-drawer-login {
    border: 1px solid #00d1b2;
    width: fit-content;
    align-items: center;
    text-align: center;
    margin: 0 auto;
}
.image-user {
    margin: 0 auto;
    align-items: center;
    text-align: center;
}
.image-border{
    border: 2px solid white;
    border-radius: 50%;
}
.name {
    text-transform: capitalize;
    text-align: center;
    font-size: 1.5rem;
    font-weight: 400;
    letter-spacing: normal;
    line-height: 2rem;
    padding: 16px 16px 8px;
    word-break: break-all;
    color:#fff;
}
.email {
  text-align: center;
  color:#fff;
  letter-spacing: normal;
}
.btn-container {
    margin-top: 20px;
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
div.logout:hover {
    color:  white;
    background-color: #00d1b2;
}
.burger-menu {
    color: white;
}
.fixed-menu {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 15;
}
</style>
