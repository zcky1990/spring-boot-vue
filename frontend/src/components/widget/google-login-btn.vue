<template>
  <g-signin-button
    :params="googleSignInParams"
    @success="onSignInSuccess"
    @error="onSignInError">
    Sign in with Google
  </g-signin-button>
</template>

<script>
import { EventBus } from './../../EventBus.js';

export default {
  data () {
    return {
      title:"Sign in with Facebook",
      googleSignInParams: {
        client_id: "90367855693-4r8nmo9qgktatecgkvn17c7eoh5997bh.apps.googleusercontent.com"
      },
      data: {}
    }
  },
  methods: {
    onSignInSuccess (googleUser) {
      console.log(googleUser)
       const profile = googleUser.getBasicProfile()
       this.data.id = profile.Eea;
       this.data.email = profile.U3;
       this.data.firstname = profile.ofa;
       this.data.lastname = profile.wea;
       this.data.display_name = profile.ig;
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
  display: inline-block;
  padding: 4px 8px;
  border-radius: 3px;
  background-color: #3c82f7;
  color: #fff;
  box-shadow: 0 3px 0 #0f69ff;
}
</style>