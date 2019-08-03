    <template>
        <div class="user-page-container">
            <section class="nav-section">
                <user-nav-menu></user-nav-menu>
            </section>
            <section class="content-section container">
                <v-content>
      <v-container fill-height>
        <v-layout
          justify-center
          align-center
        >
          asdasdasdsaad
        </v-layout>
      </v-container>
    </v-content>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/UserNavbar'

    export default   {
        name: 'user-page-layout',
        data(){
            return {

            }
        },
        components: {
                    'user-nav-menu' : Navbar,
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
