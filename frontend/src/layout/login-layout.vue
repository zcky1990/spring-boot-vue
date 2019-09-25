    <template>
        <div class="login-page-container">
             <v-container align-center justify-center row fill-height>
                <login-form></login-form>
            </v-container>
        </div>
    </template>

    <script>
    import LoginForm from '@/components/widget/user-login-form'

    export default   {
        name: 'login-page-layout',
        components: {
            'login-form' : LoginForm,
        }
    }
    </script>

    <style scoped>
    .login-page-container {
            width: 100%;
            height: 100%;
    }
        .sign-up-page-container {
            background-color: white;
        }
        .content-section {
            padding-top: 45px;
        }
    </style>
