<template>
  <fb-signin-button
    class="facebook-btn fb-signin-button "
    :params="fbSignInParams"
    @success="onSignInSuccess"
    @error="onSignInError">
    Sign in with Facebook
  </fb-signin-button>
</template>

<script>
import { EventBus } from './../../EventBus.js';

export default {
  data () {
    return {
      title:"Sign in with Facebook",
      fbSignInParams: {
        scope: 'email,user_likes,user_birthday,public_profile',
        return_scopes: true
      },
      data: {
        id:'',
        name:''
      }
    }
  },
  methods: {
    onSignInSuccess (response) {
      let self = this;
      FB.api('/me', dude => {
        self.data = dude
        self.data.type = "Free Member"
        self.callRestService( self.data)
      })
    },
    onSignInError (error) {
      this.setMessage(error,1);
    },
    callRestService(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.post("/users/sign_in_with_facebook", model, headers,
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
              self.$session.set("signIn-mode", "facebook");
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
.fb-signin-button {
  /* This is where you control how the button looks. Be creative! */
  display: inline-block;
    padding: 10px 10px;
    border-radius: 3px;
    background-color: #4267b2;
    color: #fff;
    text-align: center;
    font-size: 1.2rem;
    font-weight: 500;
    width: 100%;
    box-shadow: 0 3px 1px -2px rgba(0,0,0,.2), 0 2px 2px 0 rgba(0,0,0,.14), 0 1px 5px 0 rgba(0,0,0,.12);
}
</style>