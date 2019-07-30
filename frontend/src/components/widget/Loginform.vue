<template>
  <v-container grid-list-sm>
    <v-layout align-center justify-center row fill-height>
      <v-flex xs12 md6 d-flex>
        <v-form ref="form" v-model="valid">
          <v-text-field
            v-model="username"
            :rules="useranameRules"
            label="Username"
            required
            outline
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
          ></v-text-field>

          <v-flex xs12 d-flex>
            <v-btn class="white--text desc" color="#00d1b2" @click="submitLogin">Login</v-btn>
          </v-flex>
        </v-form>
        <v-flex xs12 md6 d-flex>
          <snack-bar ref="snackbar"></snack-bar>
        </v-flex>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { AXIOS } from "./../http-common";
import SnackBar from "./SnackBar";

export default {
  name: "login-form",
  components: {
    "snack-bar": SnackBar
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
          (v && v.length >= 8) || "Username must be or more than 8 characters"
      ],
      passwordRules: [
        v => !!v || "Password is required",
        v =>
          (v && v.length >= 8) || "Password must be or more than 8 characters"
      ]
    };
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
    setErrorMessage(errorMessage) {
      this.showErrorMessage = errorMessage;
      this.$refs.snackbar.setConfig(this.snackBarConfig);
      this.$refs.snackbar.showSnackbar(errorMessage);
      this.$refs.alert.setMessage(errorMessage);
    },
    callRestService(model) {
      let self = this;
      let router = this.$router;
      AXIOS.post("/users/sign_in", model)
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data;
            console.log(responseData);
            if (responseData["error_message"] != undefined) {
              self.setErrorMessage(responseData.error_message);
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
          self.errors.push(e);
        });
    }
  },
  computed: {
    showErrorMessage: {
      get: function() {
        return this.messageError;
      },
      set: function(message) {
        this.messageError = message;
      }
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
