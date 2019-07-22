    <template>
        <div class="sign-in-page-container">
            <section class="nav-section">
                <nav-menu></nav-menu>
            </section>
            <section class="content-section">
                <sign-in-form></sign-in-form>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/Navbar'
    import SignInForm from '@/components/widget/SignInForm'

    export default   {
        name: 'sign-in-page-layout',
        data(){
            return {

            }
        },
        components: {
                    'nav-menu' : Navbar,
                    'sign-in-form' : SignInForm
        }
    }
    </script>

    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>

    </style>
