<template>
    <article class="media">
        <figure class="media-left">
            <p class="image is-64x64">
                <img src="https://bulma.io/images/placeholders/128x128.png">
            </p>
        </figure>
    <div class="media-content">
        <div class="field">
            <p class="control">
                <textarea ref="replycomment" class="textarea" placeholder="Add a Reply..."></textarea>
            </p>
        </div>
        <div class="field">
            <p class="control">
                <button class="button">Post comment</button>
            </p>
        </div>
     </div>
    </article>
</template>

<script>
    import {AXIOS} from './../http-common'
    export default {
    name: 'login-form',
    props: {
        props: ['reply'],
        response: [],
        errors: []
    },
    methods: {
        submitLogin: function (event) {
            var username = this.$refs.username.value;
            var password = this.$refs.password.value;

            if(username == undefined || username == "" || password == undefined || password == ""){
                this.setErrorMesasge("Username / Password cannot be empty");
            }else{
                this.setErrorMesasge("");

                var model={};
                model.username = username;
                model.password = password;

                this.callRestService(model);
            }
        },
        setErrorMesasge(errorMessage){
                this.$refs.errormessage.value = errorMessage;
                this.$refs.errormessage.innerHTML = errorMessage;
        },
        callRestService (model) {
        var self= this;
        AXIOS.post('/sign_up', model)
          .then(response => {
                self.response = response.data
                if(self.response.errorMessage != undefined || self.response.errorMessage != ''){
                    self.setErrorMesasge(self.response.errorMessage);
                }else{
                    
                }
            })
          .catch(e => {
            self.errors.push(e)
          })
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
