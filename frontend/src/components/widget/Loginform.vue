<template>
    <div class="container has-text-centered login-form">
                <div class="column is-6 is-offset-3">
                    <h3 class="title has-text-grey">Login</h3>
                    <p class="subtitle has-text-grey">Please login to proceed.</p>
                    <div class="box">
                            <div class="field">
                                <div class="control">
                                    <input ref="username" class="input is-large" type="text" placeholder="Your Username" autofocus="">
                                </div>
                            </div>

                            <div class="field">
                                <div class="control">
                                    <input ref="password" class="input is-large" type="password" placeholder="Your Password">
                                </div>
                            </div>
                            <button v-on:click="submitLogin" class="button is-block is-info is-large is-fullwidth">Login</button>
                            <div class="field">
                                <div class="control">
                                    <p ref="errormessage" class="error-message">{{showErrorMessage}}</p>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
</template>

<script>
    import {AXIOS} from './../http-common'
    import Vue from 'vue'

    export default {
    name: 'login-form',
    props: {
        errors: []
    },
    data(){
        return {
            messageError:''
        }
    },
    methods: {
        submitLogin: function () {
            var username = this.$refs.username.value;
            var password = this.$refs.password.value;

            if(username == undefined || username == "" || password == undefined || password == ""){
                this.setErrorMessage("Username / Password cannot be empty");
            }else{
                this.setErrorMessage("");

                var model={};
                model.username = username;
                model.password = password;

                this.callRestService(model);
            }
        },
        setErrorMessage(errorMessage){
                this.showErrorMessage = errorMessage;
        },
        callRestService (model) {
        let self= this;
        let router =  this.$router;
        AXIOS.post('/users/sign_in', model)
          .then(response => {
              if(response.status == 200){
                let responseData = response.data
                if(responseData['error_message'] != undefined ){
                    self.setErrorMessage(responseData.errorMessage);
                }else {
                    self.$session.start()
                    self.$session.set('jwt', responseData.token)
                    self.$session.set('uid', responseData.response.id)
                    self.$session.set('username', responseData.response.username)
                    console.log(router);
                    router.push('/user')
                }
              }
            })
          .catch(e => {
            self.errors.push(e)
          })
      }
    },
    computed: {
        showErrorMessage: {
               get : function(){
                   return this.messageError;
               },
               set: function(message){
                   this.messageError = message
               }
        }
    }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.error-message {
   text-align: center;
   padding: 10px;
   color: red;
}

::-webkit-input-placeholder {
font-size: 20px !important;
}

:-moz-placeholder {  
font-size: 20px !important;
}

/*--for IE10 support--*/
:-ms-input-placeholder {
font-size: 20px !important;
}
</style>
