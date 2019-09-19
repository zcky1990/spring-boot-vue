<template>
<v-container>
    <div class="title-container">
      <div class="title-sub-title-container">
          <div class="title">Login</div>
          <div class="sub-title">Masuk ke Akun Anda</div>
      </div>
    </div>
    <v-layout class="sign-up-container" align-center justify-center flex>
        <div class="facebook">
          <div class="facebook-btn-container">
            <div class="facebook-btn">
              <fb-btn></fb-btn>
            </div>
            <div class="google-btn">
              <google-btn></google-btn>
            </div>
          </div>
        </div>
      <div class="form-container">
        <v-form ref="form" v-model="valid" width="300">
          <v-text-field
            v-model="username"
            :rules="useranameRules"
            label="Username"
            hint="At least 6 characters"
            required
            color="rgb(0, 209, 178)"
          ></v-text-field>

          <v-text-field
            v-model="password"
            :rules="passwordRules"
            :type="show1 ? 'text' : 'password'"
            @click:append="show1 = !show1"
            :append-icon="show1 ? 'visibility' : 'visibility_off'"
            label="Password"
            hint="At least 8 characters"
            required
            color="rgb(0, 209, 178)"
          ></v-text-field>

          <v-flex align-center justify-center>
            <div class="submit-btn-container">
              <div class="link-not-sign-up">
               <router-link class="sign-up-link" to="/sign_up">
                  <v-btn text flat class="link-btn">Belum terdaftar ?</v-btn>
                </router-link>
              </div>
              <div class="sign-in-btn-container">
               <v-btn class="white--text desc" color="#00d1b2" @click="submitLogin">Login</v-btn>
              </div>
            </div>
          </v-flex>
        </v-form>
        </div>
    </v-layout>
    <div class="term-condition-container">
      * By signing up, you agree to our Terms of Use and to receive emails & updates and acknowledge that you read our Privacy Policy.
    </div>
  </v-container>
</template>

<script>
import { EventBus } from './../../EventBus.js';
import FBLoginButton from './fb-login-btn'
import GoogleLoginButton from './google-login-btn'

export default {
  name: "user-login-form",
  components: {
    "fb-btn": FBLoginButton,
    "google-btn" :GoogleLoginButton
  },
  data() {
    return {
      valid: false,
      username: "",
      password: "",
      show1: false,
      messageError: "",
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
      useranameRules: [
        v => !!v || "Username is required",
        v =>
          (v && v.length >= 6) || "Username must be or more than 8 characters"
      ],
      passwordRules: [
        v => !!v || "Password is required",
        v =>
          (v && v.length >= 8) || "Password must be or more than 8 characters"
      ]
    };
  },
  created(){
    let isLogged = this.isLoggin(this.$session);
    if(isLogged){
      this.$router.push("/");
    }
  },
  methods: {
    submitLogin: function() {
      if (this.$refs.form.validate()) {
        var username = this.username;
        var password = this.password;
        var model = {};
        model.username = username;
        model.password = password;
        this.callRestService(model);
      }
    },
    callRestService(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.post("/users/sign_in", model, headers,
      function(response){
        if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setMessage(responseData.error_message, 1);
            } else {
              self.$session.start();
              self.$session.set("users", responseData.response);
              self.$session.set("jwt", responseData.token);
              self.$session.set("uid", responseData.response.id);
              self.$session.set("username", responseData.response.username);
              self.$session.set("exp_date", responseData.exp_date);
              router.push("/user");
            }
          }
      },
      function(e){
          self.setMessage(e, 1);
      });
    },
    setMessage(message, type){
      let data={}
      data.message = message
      data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
    }
  }
};
</script>
<style scoped>
@media only screen and (max-width: 600px) {
  .sign-up-container {
    flex-direction: column !important;
  }
  .title {
  font-size: 1.4rem !important;
  font-weight: 600;
  color:rgb(0, 209, 178);
  text-align: center;
  }
}
.atau-spacer {
  text-align: center;
  font-size: 1rem;
  color: #6c757d;
  text-transform: uppercase;
}
.title-container {
  flex-grow: 1;
  text-align: center;
  margin-bottom: 16px;
}
.form-container {
  flex-grow: 0;
  width: 320px;
  margin-left: 2%;
    margin-right: 2%;
}
.sign-up-container {
  flex-direction: row;
}
.submit-btn-container {
    display: flex;
    flex-direction: row;
}
.link-not-sign-up {
    flex-grow: 1;
    z-index: 1;
}
.sign-up-link {
  text-decoration: none;
}
.title {
  text-align: center;
  line-height: 1.5 !important;
  font-size: 3.5rem !important;
  color: rgb(0, 209, 178);;
  font-weight: 900;
}
.sub-title {
  text-align: center;
  color: #6c757d;
  font-size: 1rem;
  font-weight: 300;
}
.bulma-color {
   color: rgb(0, 209, 178);
}
.submit-btn-container {
    display: flex;
    flex-direction: row;
}
.link-not-sign-up {
  flex-grow: 1;
}
.sign-up-link {
  text-decoration: none;
}
.link-btn {
  color: #00d1b2;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  cursor: pointer;
}
.facebook {
  margin-bottom: 20px;
  width:300px;
  margin-left: 2%;
  margin-right: 2%;
}
.title-sub-title-container {
  width: fit-content;
  margin: 0 auto;
}
.desc {
  font-size: 1.25rem;
  font-weight: 600;
}
.box-1 {
    width: 200px;
    height: 200px;
    border: 3px solid rgb(0, 209, 178);
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
    transform: rotate(20deg);
}
.facebook-btn, .google-btn {
    margin-bottom: 10px;
}
.term-condition-container {
  text-align: center;
  max-width: 600px;
  margin: 0 auto;
  margin-top: 5%;
  line-height: 24px;
  color: #858f96;
}
</style>
