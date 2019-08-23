<template>
  <v-container>
    <v-layout class="sign-up-container" align-center justify-center flex fill-height>
      <div class="snack-bar-container">
        <snack-bar ref="snackbar"></snack-bar>
      </div>
      <div class="title-container">
                <div class="title bulma-color">Sign Up</div>
                <div class="sub-title bulma-color">Make your Account</div>
      </div>
      <div class="form-container">
        <v-form ref="form" v-model="valid" width="300">
          <v-text-field
                v-model="firstname"
                :rules="nameRules"
                label="First name"
                required
                outline 
                flat
                color="#00d1b2"
              ></v-text-field>

              <v-text-field
                v-model="lastname"
                :rules="nameRules"
                label="Last name"
                required
                outline 
                flat
                color="#00d1b2"
              ></v-text-field>

              <v-text-field 
              v-model="email" 
              :rules="emailRules" 
              label="E-mail" 
              required 
              outline 
              flat
              color="#00d1b2"
              ></v-text-field>

              <v-text-field
                v-model="username"
                :rules="useranameRules"
                label="Username"
                required
                outline 
                flat
                rounded
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
                outline 
                flat
                color="#00d1b2"
              ></v-text-field>
          <v-flex align-center justify-center>
            <div class="submit-btn-container">
              <div class="link-not-sign-up">
                <div class="has-account" @click="goToPage">
                    <span>Sudah punya Akun?</span>
                  </div>
              </div>
              <div class="sign-in-btn-container">
                <v-btn class="white--text desc" color="#00d1b2" @click="submit">Daftar</v-btn>
              </div>
            </div>
          </v-flex>
        </v-form>
        </div>
    </v-layout>
  </v-container>
</template>

<script>
import { AXIOS } from "./../http-common";
import SnackBar from "./SnackBar";
import { Util } from "./../util";

export default {
  name: "user-sign-up-form",
  components: {
    "snack-bar": SnackBar
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
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
    };
  },
  methods: {
    callRestService(model) {
      let self = this;
      let router = this.$router;
      let headers = Util.getDefaultHeaders(Util.getMeta("token"))
      AXIOS.post("users/sign_up", model, { headers })
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setMessage(responseData.error_message, 1);
            } else {
              self.$session.start();
              self.$session.set("jwt", responseData.token);
              self.$session.set("uid", responseData.response.id);
              self.$session.set("username", responseData.response.username);
              self.$session.set("exp_date", responseData.exp_date);
              router.push("/user");
            }
          }
        })
        .catch(e => {
          self.setMessage(e, 1);
        });
    },
    setMessage(message, type) {
      if(type == 0){
        this.snackBarConfig.color = "success"
      }else{
        this.snackBarConfig.color = "error"
      }
      this.$refs.snackbar.setConfig(this.snackBarConfig);
      this.$refs.snackbar.showSnackbar(message);
    },
    submit() {
      if (this.$refs.form.validate()) {
        let model = {};
        model.username = this.username;
        model.password = this.password;
        model.firstname = this.firstname;
        model.lastname = this.lastname;
        model.email = this.email;
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
  color: #444f60;
  text-align: center;
  padding-bottom: 16px;
  }
}

.title-container {
    flex-grow: 1;
    text-align: center;
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
.center {
  text-align: center;
  width: 100%;
}
.title {
  font-size: 2.8rem !important;
  font-weight: 600;
  color: #444f60;
  text-align: center;
  padding-bottom: 16px;
}
.sub-title {
  font-size: 1.4rem !important;
  color: #444f60;
  text-align: center;
}
input,
.desc {
  color: #4a4a4a;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
}
.has-account {
  padding-top: 15px;
  color: rgb(0, 209, 178);
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  cursor: pointer;
}
.bulma-color {
   color: rgb(0, 209, 178);
}

</style>
