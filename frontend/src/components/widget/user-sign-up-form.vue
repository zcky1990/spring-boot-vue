<template>
  <v-container>
    <div class="title-container">
                <div class="title bulma-color">Sign Up</div>
                <div class="sub-title">Make your Account</div>
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
                v-model="firstname"
                :rules="nameRules"
                label="First name"
                required
                color="#00d1b2"
              ></v-text-field>

              <v-text-field
                v-model="lastname"
                :rules="nameRules"
                label="Last name"
                required
                color="#00d1b2"
              ></v-text-field>

              <v-text-field 
              v-model="email" 
              :rules="emailRules" 
              label="E-mail" 
              required 
              color="#00d1b2"
              ></v-text-field>

              <v-text-field
                v-model="username"
                :rules="useranameRules"
                label="Username"
                required
                color="#00d1b2"
              ></v-text-field>

              <v-text-field
                v-model="password"
                :rules="passwordRules"
                label="Password"
                hint="At least 8 characters"
                :type="show1 ? 'text' : 'password'"
                @click:append="show1 = !show1"
                :append-icon="show1 ? 'visibility' : 'visibility_off'"
                required
                color="#00d1b2"
              ></v-text-field>
          <v-flex align-center justify-center>
            <div class="submit-btn-container">
              <div class="link-not-sign-up">
                <div class="has-account">
                    <router-link class="sign-up-link" to="/login">
                      <v-btn text flat class="link-btn">Sudah punya akun?</v-btn>
                    </router-link>
                  </div>
              </div>
              <div class="sign-in-btn-container">
               <div class="sign-in-btn" @click="submit">Sign Up</div>
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
  name: "user-sign-up-form",
  components: {
    "fb-btn": FBLoginButton,
    "google-btn" :GoogleLoginButton
  },
  data() {
    return {
      valid: false,
      show1: false,
      firstname: "",
      lastname: "",
      password: "",
      username: "",
      email: "",
      nameRules: [
        v => !!v || "Name is required"
        //v => v.length <= 10 || 'Name must be less than 10 characters'
      ],
      emailRules: [
        v => !!v || "E-mail is required",
        v => /.+@.+/.test(v) || "E-mail must be valid"
      ],
      useranameRules: [
        v => !!v || "Username is required",
        v =>
          (v && v.length >= 8) || "Username must be or more than 8 characters"
      ],
      passwordRules: [
        v => !!v || "Password is required",
        v =>
          (v && v.length >= 8) || "Password must be or more than 8 characters"
      ],
      isLoading: false
    };
  },
  methods: {
    callRestService(model) {
      let self = this;
      this.isLoading = true;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.post("users/sign_up", model,  headers,
      function(response) {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setMessage(responseData.error_message, 1);
              self.isLoading = false
            } else {
              self.setMessage("Please check your email to verify your accounts", 0);
              self.isLoading = false
              router.push("/");
            }
          }
        },
        function(e){
          self.isLoading = false
          self.setMessage(e, 1);
        });
    },
    setMessage(message, type) {
      let data={}
      data.message = message
      data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
    },
    submit() {
      if (this.$refs.form.validate()) {
        let model = {};
        model.username = this.username;
        model.password = this.password;
        model.firstname = this.firstname;
        model.lastname = this.lastname;
        model.email = this.email;
        model.type = "Free Member"
        this.callRestService(model);
      }
    },
    goToPage() {
      this.$router.push("/login");
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
  font-size: 1.5rem !important;
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
  color: #00d1b2;
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
.sign-in-btn {
  padding: 8px;
  width: 110px;
  border: 1px solid;
  text-align: center;
  border-radius: 15px;
  border: 1px solid rgb(0, 209, 178);
  color: rgb(0, 209, 178);
  cursor:pointer;
}
.sign-in-btn:hover{
  background: #00d1b2;
  border: 1px solid rgb(0, 209, 178);
  color: white;
}
</style>
