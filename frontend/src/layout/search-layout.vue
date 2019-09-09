    <template>
    <div class="index-page-container">
    <section class="nav-section">
        <nav-bar></nav-bar>
    </section>
    <section class="content-section">
      <v-content>
        <v-container fill-height>
          <v-layout class="index-content">
            <v-flex class="main-content" >
                <list-article url="/article/search" mode="search" :query="query" ></list-article>
            </v-flex>
            <v-flex class="right-content">
                <v-container>
                    <v-layout>

                    </v-layout>
                </v-container>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </section>
  </div>
    </template>
    <script>
    import NavbarComponents from "@/components/widget/navbar";
    import listArticleComponents from "@/components/widget/list-article";

    export default   {
        name: 'index-page-layout',
        props:{
            query: {
                type: String,
                default: ""
            }
        },
        components: {
            'nav-bar' : NavbarComponents,
            'list-article': listArticleComponents
        }
    }
    </script>

    <style scoped>
        .main-content {
            flex-basis: 66.66666666666666%;
            -webkit-box-flex: 0;
            -ms-flex-positive: 0;
            flex-grow: 0;
            max-width: 66.66666666666666%;
        }
        .right-content {
                -ms-flex-preferred-size: 33.33333333333333%;
                flex-basis: 33.33333333333333%;
                -webkit-box-flex: 0;
                -ms-flex-positive: 0;
                flex-grow: 0;
                max-width: 33.33333333333333%;
                background: #f8f9fa !important;
        }
        .index-content{
            flex-direction: row;
        }
        @media only screen and (max-width: 600px){
            .index-content{
                flex-direction: column;
            }
            .main-content {
                flex-basis: 100%;
                -webkit-box-flex: 0;
                -ms-flex-positive: 0;
                flex-grow: 0;
                max-width: 100%;
            }
            .right-content {
                flex-basis: 100%;
                -webkit-box-flex: 0;
                -ms-flex-positive: 0;
                flex-grow: 0;
                max-width: 100%;
            }
        }
    </style>
