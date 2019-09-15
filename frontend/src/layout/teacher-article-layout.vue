    <template>
        <div class="user-page-container">
            <section class="nav-section">
                <user-nav-menu></user-nav-menu>
            </section>
            <section class="article content-section">
                <v-container fill-height align-center justify-center>
                  <teacher-list-article></teacher-list-article>
                </v-container>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/navbar'
    import ListArticle from '@/components/widget/teacher-list-article'
    
    export default {
        name: 'teacher-article-page-layout',
        created(){
            let isLogged = this.isLoggin(this.$session);
            if(!isLogged){
                this.$router.push("/");
            }
        },
        components: {
                    'user-nav-menu' : Navbar,
                    "teacher-list-article" : ListArticle
                   
        }
    }
    </script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.sign-up-page-container {
        background-color: white;
}
</style>
