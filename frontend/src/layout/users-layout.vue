    <template>
        <div class="user-page-container">
            <section class="nav-section">
                <user-nav-menu></user-nav-menu>
            </section>
            <section class="content-section">
                <tabs-component></tabs-component>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/navbar'
    import TabsComponents from '@/components/widget/user-tabs-components'

    export default {
        name: 'user-page-layout',
        created(){
            let isLogged = this.isLoggin(this.$session);
            if(!isLogged){
                this.$router.push("/");
            }
        },
        components: {
                    'user-nav-menu' : Navbar,
                    "tabs-component" : TabsComponents
        }
    }
    </script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.sign-up-page-container {
        background-color: white;
}
</style>
