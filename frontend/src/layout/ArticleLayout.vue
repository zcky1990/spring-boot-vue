    <template>
        <div class="article-page-container">
            <section class="nav-section">
              
            </section>
            <section class="content-section container">
                <v-content>
      <v-container fill-height>
        <v-layout wrap>
             <v-flex class="left-content-container"
        shrink
        pa-1
      >
        <v-card
          dark
          color="green darken-3"
        >
          <v-card-text>I'm a Flex that will NOT grow</v-card-text>
        </v-card>
      </v-flex>
      <v-flex class="main-content-container"
        pa-1
      >
        <v-card
          dark
          color="green darken-4"
        >
          <v-card-text>I'm a Flex that will grow and shrink (default behavior)</v-card-text>
        </v-card>
      </v-flex>
      <v-flex class="right-content-container"
        shrink
        pa-1
      >
        <v-card
          dark
          color="green darken-3"
        >
          <v-card-text>I'm a Flex that will NOT grow</v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
      </v-container>
    </v-content>
            </section>
        </div>
    </template>

    <script>

    import Navbar from '@/components/widget/UserNavbar'

    export default   {
        name: 'article-page-layout',
        props:["article"],
        data(){
            return {

            }
        },
        components: {
                    'user-nav-menu' : Navbar,
        },
        created(){
            console.log(this.article);
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
