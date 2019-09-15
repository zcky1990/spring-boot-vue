<template>
<v-container>
    <v-layout class="sign-up-container" align-center justify-center flex fill-height>
      <div class="title-container">
        <div class="buble-container">
            <div class="buble-outer">
              <div class="buble">
                  <div class="title">Login</div>
                  <div class="sub-title">Masuk ke Akun Anda</div>
              </div>
            </div>
        </div>
      </div>
      <div class="form-container">
        <v-form ref="form" v-model="valid" width="300">
          <v-text-field
            v-model="username"
            :rules="useranameRules"
            label="Username"
            required
            outline 
            flat
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
            outline 
            flat
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
  </v-container>
</template>

<script>
import { EventBus } from './../../EventBus.js';

export default {
  name: "teacher-login-form",
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
          (v && v.length >= 8) || "Username must be or more than 8 characters"
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
      this.$router.push("/teacher/profile");
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
              router.push("/teacher/profile");
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
  color: #444f60;
  text-align: center;
  padding-bottom: 16px;
  }
}
.title-container {
    flex-grow: 1;
    text-align: center;
    margin-bottom: 16px;
}
.form-container {
    flex-grow: 0;
    width: 320px;
}
.sign-up-container {
  flex-direction: row;
  justify-content: space-between;
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
  font-size: 3.2rem !important;
    font-weight: 600;
    color:  white;
    text-align: center;
    line-height: 1.5 !important;
}
.sub-title {
  font-size: 1.4rem !important;
  color: white;
  text-align: center;
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
  color: rgb(0, 209, 178);
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  cursor: pointer;
}
.buble-outer {
    border: 2px solid rgb(0, 209, 178);
    border-radius: 50%;
}
.buble {
    background: rgb(0, 209, 178);
    width: 250px;
    height: 250px;
    padding: 65px;
    border-radius: 50%;
    color: white;
    border: 5px solid white;
}
.buble-container {
    text-align: center;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    display: flex;
    position: relative;
    animation: pulse 2s 0.75s cubic-bezier(.5,.5,0,1) infinite;
}
</style>
