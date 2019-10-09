<template>
  <fb-signin-button
    class="facebook-btn fb-signin-button"
    :params="fbSignInParams"
    @click="setProgres"
    @success="onSignInSuccess"
    @error="onSignInError"
  >
    <div class="btn-fb">
      <img class="icon-btn" :src="facebookIcon" />
      <div class="title-fb-btn">
        <div v-if="!isOnProgres" class="desc">{{title}}</div>
        <div v-else class="progress">
          <v-progress-circular indeterminate color="white"></v-progress-circular>
        </div>
      </div>
    </div>
  </fb-signin-button>
</template>

<script>
import { EventBus } from "./../../EventBus.js";
import Facebook from "./../../assets/image/facebook-logo.svg";

export default {
  data() {
    return {
      facebookIcon: Facebook,
      isOnProgres: false,
      title: "Sign in with Facebook",
      fbSignInParams: {
        scope: "email,user_likes,user_birthday,public_profile",
        return_scopes: true
      },
      data: {
        id: "",
        name: ""
      }
    };
  },
  methods: {
    onSignInSuccess(response) {
      let self = this;
      FB.api("/me", dude => {
        self.data = dude;
        self.data.type = "Free Member";
        self.callRestService(self.data);
      });
    },
    onSignInError(error) {
      this.isOnProgres = false;
      this.setMessage(error, 1);
    },
    setProgress() {
      this.isOnProgres = true;
    },
    callRestService(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.post(
        "/users/sign_in_with_facebook",
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setMessage(responseData.error_message, 1);
              self.isOnProgres = false;
            } else {
              self.$session.start();
              self.$session.set("users", responseData.response);
              self.$session.set("jwt", responseData.token);
              self.$session.set("uid", responseData.response.id);
              self.$session.set("signIn-mode", "facebook");
              if (responseData.response.username != undefined) {
                self.$session.set("username", responseData.response.username);
              }
              self.$session.set("exp_date", responseData.exp_date);
              self.isOnProgres = false;
              router.push("/user");
            }
          }
        },
        function(e) {
          self.isOnProgres = false;
          self.setMessage(e, 1);
        }
      );
    },
    setMessage(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  }
};
</script>

<style>
.fb-signin-button {
  /* This is where you control how the button looks. Be creative! */
  display: inline-block;
  background-color: #4267b2;
  color: #fff;
  text-align: center;
  font-size: 1.2rem;
  font-weight: 600;
  width: 100%;
  cursor: pointer;
  border: 1px solid #4267b2;
}
.icon-btn {
  width: 46px;
  height: 46px;
  background: #ffff;
}
.btn-fb {
  display: flex;
  margin: 0 auto;
  justify-content: left;
  align-items: center;
}
.title-fb-btn {
  text-align: center;
  margin-left: 25px;
}
</style>