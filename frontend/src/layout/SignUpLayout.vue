    <template>
        <div class="sign-up-page-container">
            <section class="nav-section">
                <nav-menu></nav-menu>
            </section>
            <section class="content-section">
                <sign-up-form></sign-up-form>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/Navbar'
    import SignUpForm from '@/components/widget/SignUpForm'

    export default   {
        name: 'sign-up-page-layout',
        data(){
            return {

            }
        },
        components: {
                    'nav-menu' : Navbar,
                    'sign-up-form' : SignUpForm
        }
    }
    </script>

    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>

    </style>
