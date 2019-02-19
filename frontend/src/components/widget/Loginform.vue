<template>
    <div class="container has-text-centered login-form">
                <div class="column is-6 is-offset-3">
                    <h3 class="title has-text-grey">Login</h3>
                    <p class="subtitle has-text-grey">Please login to proceed.</p>
                    <div class="box">
                        <form>
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
                                    <p ref="errormessage" class="error-message"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
</template>

<script>
    export default {
    name: 'login-form',
    props: {
        msg: String
    },
    methods: {
        submitLogin: function (event) {
            var username = this.$refs.username.value;
            var password = this.$refs.password.value;

            if(username == undefined || username == "" || password == undefined || password == ""){
                this.$refs.errormessage.value = "Username / Password cannot be empty";
                this.$refs.errormessage.innerHTML = "Username / Password cannot be empty";
            }else{
                var model={};
                model.username = username;
                model.password = password;
                console.log(model);
            }
        }
    }
    }
</script>
    <!-- Add "scoped" attribute to limit
     CSS to this component only -->
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
