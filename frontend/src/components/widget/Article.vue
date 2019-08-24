<template>
  <section class="section article">
    <v-container id="grid" fluid grid-list-sm tag="section">
      <v-layout wrap>
        
        <v-flex tag="h1" class="headline">{{content.article_title}}</v-flex>

        <v-flex d-flex xs12>
          <v-container>
            <v-layout>
              <div class="image" v-if="content.author.image_profile_url">
                <v-img
                  :src="content.author.image_profile_url"
                  lazy-src="https://picsum.photos/10/6?image=11"
                  aspect-ratio="1"
                  class="grey lighten-2 rounded"
                >
                  <template v-slot:placeholder>
                    <v-layout
                      fill-height
                      align-center
                      justify-center
                      ma-0
                    >
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-layout>
                  </template>
                </v-img>

              </div>
              <v-flex>
                <div class="author">
                  <div class="authors-name">{{content.author.firstname}} {{content.author.lastname}}</div>
                  <div class="article-create-date">{{content.created_date}}</div>
                </div>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>

        <v-flex d-flex xs12 order-xs5 class="content">
          <v-layout column>
            <v-flex class="article-content">
                <div v-html="content.article_content"></div>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
    </v-container>
  </section>
</template>

<script>


export default {
  name: "article-component",
  props: {
    content : Object
  },
  data() {
    return {
      }
  },
  methods: {
    setCssSideImage: function(){
      let elm = document.querySelectorAll('.image-style-side')
      if(elm.length > 0){
        for(let i = 0 ; i < elm.length; i++){
          let el = elm[i];
          el.style.float = 'right';
          el.style.padding ='15px';
          el.style.textAlign = 'center';
        }
      }
    }
  },
  updated(){
    this.setCssSideImage();
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.author {
  padding-left: 20px;
  padding-top: 7px;
}
.rounded {
  border-radius: 66px;
    width: 64px;
    height: 64px;
}
.authors-name {
    font-size: 1.5em;
    font-weight: 600;
    line-height: 1.5;
}
.article-create-date {
  color: grey;
}
.headline {
  font-size: 2rem !important;
}
.content {
  margin-top: 1.95em;
  font-size: 1em;
}
.article-content {
    display: table;
}
</style>
