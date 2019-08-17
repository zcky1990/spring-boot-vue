<template>
  <v-container grid-list-sm>
    <v-layout align-center justify-center row fill-height>
      <v-flex xs12 md6 d-flex>
        <v-card class="form-sign-in-container">
          <v-card-title align-center justify-center>
            <div class="center">
              <v-flex>
                <div class="title bulma-color">Sign Up</div>
              </v-flex>
              <v-flex>
                <div class="desc bulma-color">Make your Account</div>
              </v-flex>
            </div>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text>
            <v-form ref="form" v-model="valid">
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
            </v-form>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-container>
              <v-layout align-center justify-center row fill-height>
                <v-flex xs12 d-flex>
                  <div class="has-account" @click="goToPage">
                    <span>Sudah punya Akun?</span>
                  </div>
                </v-flex>
                <v-flex xs12 md4 d-flex>
                  <v-btn class="white--text desc" color="#00d1b2" @click="submit">Daftar</v-btn>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { AXIOS } from "./../http-common";

export default {
  name: "user-sign-up-form",
  components: {},
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
      ]
    };
  },
  methods: {
    callRestService(model) {
      let self = this;
      let router = this.$router;
      AXIOS.post("users/sign_up", model)
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
              router.push("/user");
            }
          }
        })
        .catch(e => {
          self.errors.push(e);
        });
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
  },
  computed: {}
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.center {
  text-align: center;
  width: 100%;
}
.title {
  font-size: 1.2rem;
  font-weight: 600;
  color: #444f60;
}
input,
.desc {
  color: #4a4a4a;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
}

.has-account {
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
