<template>
  <g-signin-button
    :params="googleSignInParams"
    @success="onSignInSuccess"
    @error="onSignInError">
    <div class="btn-google">
      <img class="icon-btn" :src="googleIcon">
      <div class="title-google-btn">
        {{title}}
      </div>
    </div>
  </g-signin-button>
</template>

<script>
import { EventBus } from './../../EventBus.js';

import Google from './../../assets/image/google-logo.svg'

export default {
  data () {
    return {
      googleIcon : Google,
      title:"Sign in with Google",
      googleSignInParams: {
        client_id: "90367855693-4r8nmo9qgktatecgkvn17c7eoh5997bh.apps.googleusercontent.com"
      },
      data: {}
    }
  },
  methods: {
    onSignInSuccess (googleUser) {
       const profile = googleUser.getBasicProfile()
       this.data.id = profile.getId();
       this.data.email = profile.getEmail();
       this.data.firstname = profile.getGivenName();
       this.data.lastname = profile.getFamilyName();
       this.data.display_name = profile.getName();
       this.data.image_url = profile.getImageUrl();
       this.data.type = "Free Member"
       this.callRestService(this.data);
    },
    onSignInError (error) {
      this.setMessage(error,1);
    },
    callRestService(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.post("/users/sign_in_with_google", model, headers,
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
              self.$session.set("signIn-mode", "google");
              if(responseData.response.username != undefined){
                self.$session.set("username", responseData.response.username);
              }
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
}
</script>

<style>
.g-signin-button {
  /* This is where you control how the button looks. Be creative! */
  cursor: pointer;
  display: inline-block;
  background-color: #4285F4;
  color:#FFFFFF;
  text-align: center;
  font-size: 1.2rem;
  font-weight: 600;
  width: 100%;
  border:1px solid #4285F4;
}
.icon-btn{
  width:46px;
  height:46px;
  background:#ffff;
}
.btn-google {
    display: flex;
    margin: 0 auto;
    justify-content: left;
    align-items: center;
}
.title-google-btn {
    text-align: center;
    margin-left: 25px;
}
</style>