    <template>
        <div class="user-page-container">
            <section class="nav-section">
                <user-nav-menu></user-nav-menu>
            </section>
            <section class="content-section">
                <v-container fill-height align-center justify-center>
                    <teacher-profile></teacher-profile>
                </v-container>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/navbar'
    import TeacherProfileComponents from '@/components/widget/teacher-profile'

    export default {
        name: 'teacher-profile-page-layout',
        created(){
            let isLogged = this.isLoggin(this.$session);
            if(!isLogged){
                this.$router.push("/");
            }
        },
        components: {
                    'user-nav-menu' : Navbar,
                    "teacher-profile" : TeacherProfileComponents
        }
    }
    </script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.sign-up-page-container {
        background-color: white;
}
</style>
