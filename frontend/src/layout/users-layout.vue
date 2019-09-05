    <template>
        <div class="user-page-container">
            <section class="nav-section">
                <user-nav-menu></user-nav-menu>
            </section>
            <section class="content-section">
                <v-container fill-height align-center justify-center>
                    <user-profile></user-profile>
                </v-container>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/navbar'
    import ProfileComponents from '@/components/widget/profile'

    export default {
        name: 'user-page-layout',
        beforeCreate(){
            let isLogged = this.isLoggin(this.$session);
            if(!isLogged){
                this.$router.push("/");
            }
        },
        data(){
            return {

            }
        },
        components: {
                    'user-nav-menu' : Navbar,
                    "user-profile" : ProfileComponents
        }
    }
    </script>

    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>
        .sign-up-page-container {
            background-color: white;
        }
        .content-section {
            padding-top: 45px;
        }
    </style>
