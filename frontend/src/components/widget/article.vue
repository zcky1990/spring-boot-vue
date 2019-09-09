<template>
  <section class="section article">
    <div v-if="isCategoryExists" class="categories-container">
      <div class="categories">
          {{content.categoryArticle.name}}
      </div>
    </div>
    <div id="grid" class="content-container" tag="section">
        <div class="article-title">
          <div class="headline">{{content.article_title}}</div>
        </div>
        <div class="article-authors">
              <div class="image-authors" v-if="content.author.image_profile_url">
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
              <div class="author-desc">
                <div class="author">
                  <div class="authors-name">{{content.author.firstname}} {{content.author.lastname}}</div>
                  <div class="article-create-date">{{content.created_date}}</div>
                </div>
              </div>
        </div>

        <div class="content">
              <div class="article-content-container">
                <div class="bookmark-container">
                  <div class="bookmark-btn">
                     <v-tooltip bottom>
                      <template v-slot:activator="{ on }">
                        <v-icon color="rgb(0, 209, 178)" v-on="on" >bookmark</v-icon>
                      </template>
                      <span>Bookmark this Article</span>
                    </v-tooltip>
                  </div>
                </div>
                <div class="article-content">
                    <div v-html="content.article_content"></div>
                </div>
              </div>
        </div>
    </div>
  </section>
</template>
<script>

export default {
  name: "article-components",
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
  },
  computed: {
    isCategoryExists: function(){
      if(this.content.categoryArticle != undefined){
        if(this.content.categoryArticle.name != null){
          return true
        }else{
          return false
        }
      }else{
        return false
      }
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.article-authors {
    display: flex;
    flex-wrap: nowrap;
    flex-direction: row;
}
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
    font-size: 2.3rem !important;
    font-weight: 600;
    line-height: 2 !important;
    text-transform: capitalize;
}
.content {
  font-size: 1em;
}
.article-authors {
    padding-bottom: 16px;
}
.article-content {
    display: table;
}
.categories {
    padding: 5px 15px 5px 15px;
    border: 1px solid rgb(0, 209, 178);
    border-radius: 15px;
    width: max-content;
    font-weight: 400;
}
.categories:hover {
  background: rgb(0, 209, 178);
  color: white;
}
.content-container {
  padding-top: 10px;
  padding-bottom: 16px;
}
.article-content-container {
    display: flex;
    flex-wrap: nowrap;
    flex-direction: row;
}
.bookmark-container {
  width: 100px;
}
</style>
