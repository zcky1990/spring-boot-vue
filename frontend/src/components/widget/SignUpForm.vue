<template>
    <v-container grid-list-xl>
        <v-layout align-center justify-center row fill-height>
            <v-flex xs12 md6 d-flex >
                <v-card class="form-sign-in-container">
                    <v-card-title>
                        Sign Up
                    </v-card-title>
                    <v-card-text>
                        <v-form ref="form" v-model="valid">
                                <v-text-field
                                v-model="firstname"
                                :rules="nameRules"
                                :counter="10"
                                label="First name"
                                required
                                ></v-text-field>
                            
                                <v-text-field
                                v-model="lastname"
                                :rules="nameRules"
                                :counter="10"
                                label="Last name"
                                required
                                ></v-text-field>
                            
                                <v-text-field
                                v-model="email"
                                :rules="emailRules"
                                label="E-mail"
                                required
                                ></v-text-field>
                            
                                <v-text-field
                                v-model="username"
                                :rules="useranameRules"
                                label="Username"
                                required
                                ></v-text-field>
                            
                                <v-text-field
                                v-model="password"
                                :rules="passwordRules"
                                label="Password"
                                hint="At least 8 characters"
                                required
                                ></v-text-field>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn class="white--text" color="#00d1b2" @click="submit">
                            Sign In
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import {AXIOS} from './../http-common'

    export default {
    name: 'user-sign-up-form',
    components: {

    }, 
    data(){
        return {
            valid: false,
            firstname: '',
            lastname: '',
            password:'',
            username:'',
            email: '',
            nameRules: [
                v => !!v || 'Name is required',
                v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            emailRules: [
                v => !!v || 'E-mail is required',
                v => /.+@.+/.test(v) || 'E-mail must be valid'
            ],
            useranameRules: [
                v => !!v || 'Username is required',
                v => (v && v.length >= 10) || 'Username must be more than 8 characters'
            ],
            passwordRules: [
                v => !!v || 'Password is required',
                v => (v && v.length >= 8) || 'Password must be more than 8 characters'
            ],
        }
    },
    methods: {
        callRestService (model) {
        let self= this;
        let router =  this.$router;
        AXIOS.post('users/sign_up', model)
          .then(response => {
              if(response.status == 200){
                let responseData = response.data
                if(responseData['error_message'] != undefined ){
                    self.setErrorMessage(responseData.error_message);
                }else {
                    self.$session.start()
                    self.$session.set('jwt', responseData.token)
                    self.$session.set('uid', responseData.response.id)
                    self.$session.set('username', responseData.response.username)
                    self.$session.set('exp_date', responseData.exp_date)
                    router.push('/user')
                }
              }
            })
          .catch(e => {
            self.errors.push(e)
          })
      },
      submit () {
            if (this.$refs.form.validate()) {
            let model={};
            model.username = this.username;
            model.password = this.password;
            this.callRestService(model)
            }
        },
    },
    computed: {
        
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
