<template>
   <div class="article-component">
     <div v-if="isHasData" class="article-image-container">
       <v-container grid-list-xs>
         <v-layout column>
            <v-flex class="article-list-container" v-for="item in data" :key="item.slug">
              <div class="article-list">
                  <div class="image-article-list">
                    <v-img
                      :src="item.imageHeader"
                      :lazy-src="item.imageHeaderLazy"
                      aspect-ratio="1"
                      class="grey lighten-2 rounded"
                      width="auto"
                      min-width="150"
                      height="150"
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
                  <div class="desc-article-list">
                       <div class="article-list-headline">{{item.article_title}}</div>
                        <div class="article-list-desc">
                          <div v-html="item.article_content"></div>
                         </div>
                        <router-link class="read-more-links" :to="getUrl(item.slug)">
                        Selengkapnya
                        </router-link>
              
                  </div>
              </div>
            </v-flex>
            <v-flex>
              <div class="center">
                  <v-btn 
                  :loading="isLoadMoreOnProgress"
                  :disabled ="isDisable"
                  elevation = "0"
                  depressed
                  class="load-more-btn" @click="loadMore">Selanjutnya</v-btn>
              </div>
            </v-flex>
         </v-layout>
       </v-container>
     </div>
     <div class="not-found" v-if="!isHasData">
       <div v-if="isSearch">
         Pencarian tidak ditemukan
       </div>
      <div v-if="!isSearch">
        Tidak ada Artikel
      </div>
     </div>
   </div>
</template>
<script>

import { EventBus } from './../../EventBus.js';

export default {
  name: "list-article",
  props: {
    url: {
      type: String,
      default: ""
    },
    mode: {
      type: String,
      default: "article"
    },
    query:{
      type: String,
      default: ""
    }
  },
  data() {
    return {
      page : 0,
      index : 0,
      listCount: 0,
      articleUrl: "/article/get_article_list",
      isLoadMoreOnProgress : false,
      isDisable : false, 
      data : []
    };
  },
  created(){
    if(this.url != ""){
      this.articleUrl = this.url;
    }
    this.getArticle();
  },
  methods: {
    getUrl: function (slug) {
      let article = "/article/"
      return article+slug;
    },
    disableEnableLoadMoreBtn : function (value) {
      this.isLoadMoreOnProgress = value;
      this.isDisable = value;
    },
    getArticle : function(){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
        this.get(this.getUrlRequest() + "page="+this.page,  headers ,
        function(response) {
          if(response.status == 200){
            self.data = response.data.response.content
          }         
        },
        function(e) {
          this.setMessage(e,1);
        });
    },
    getLoadMoreService: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.disableEnableLoadMoreBtn(true);
      this.get(this.getUrlRequest() + "page="+this.page,  headers ,
      function(response) {
           let newData = response.data.response.content
          self.addData(newData)
        },
      function (e){
          this.disableEnableLoadMoreBtn(false);
          this.setMessage(e,1);
      });
    },
    loadMore: function (){
      this.page++;
      this.getLoadMoreService()
    },
    addData: function (response){
      this.data = this.data.concat(response);
      this.disableEnableLoadMoreBtn(false);
      this.setListCount();
    },
    setListCount : function (){
      this.listCount = this.data.length - 1;
    },
    setMessage(message, type){
      let data={}
                data.message = message
                data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
    },
    getUrlRequest: function(){
      if(this.mode == "search"){
        return  this.articleUrl+"?query="+this.query+"&"
      }else{
        return this.articleUrl+"?"
      }
    }
  },
  computed: {
    isHasData: function(){
      if(this.data.length > 0){
        return true
      }else{
        return false
      }
    },
    isSearch: function(){
      if(this.mode == "search"){
        return true;
      }else{
        return false;
      }
    }
  }
};
</script>
<style scoped>
.no-container {
  padding-left:0px !important;
  padding-right:0px !important;
  padding-top: 16px !important;
  padding-bottom: 16px !important;
}
.article {
  margin-bottom: 0.7em;
}
.img-article_container {
  position: relative;
}
.article-content-card {
  flex-basis: 33.33333333333333%;
    -webkit-box-flex: 0;
    -ms-flex-positive: 0;
    flex-grow: 0;
    max-width: 100%;
    padding:5px !important;
}
.article-list-container {
  margin-top: 0.7em;
}
.article-list {
  display: flex;
  flex-direction: row;
  margin-bottom: 0.7em;
}
.no-elevation {
    -webkit-box-shadow: 0 0 0 0 rgba(0,0,0,.2),0 0 0 0 rgba(0,0,0,.14),0 0 0 0 rgba(0,0,0,.12)!important;
    box-shadow: 0 0 0 0 rgba(0,0,0,.2),0 0 0 0 rgba(0,0,0,.14),0 0 0 0 rgba(0,0,0,.12)!important;
}
.desc-article-list {
    padding-left: 16px;
}
.read-more-links {
    text-decoration: none;
    font-size: 1em;
    color: #1eafed !important;
}
.article-headline {
  font-size:  1.2em;
  font-weight: bold;
  font-style: normal;
  margin-bottom: 0.5em;
  margin-top: 0.5em;
}
.article-list-headline {
    font-size:  2.2em;
    line-height: 1.5;
    font-weight: bold;
    font-weight: 400;
    margin-bottom: 0.5em;
}
.article-list-desc{
  font-size:  1em;
  margin-bottom: 0.5em;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;  
  line-height: 1.8;
  font-weight: 400;
  color: #666666;
  overflow: hidden;
}

.center {
  margin: 0 auto;
  position: relative;
  text-align: center;
}
.load-more-btn {
    background-color: white !important;
    border: 1px solid #00d1b2;
    border-radius: 25px;
    font-weight: 700;
    color: #00d1b2;
    cursor: pointer;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
}
.load-more-btn:hover {
    background-color: #00d1b2 !important;
    border: 1px solid #00d1b2;
    border-radius: 25px;
    font-weight: 700;
    color: white;
    cursor: pointer;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
}
.not-found {
    width: 100%;
    text-align: center;
    font-size: 1.8rem;
    font-weight: 400;
    line-height: 1.5;
}

@media only screen and (max-width: 600px) {
  .article-list {
    display: flex;
    flex-direction: column;
    margin-bottom: 0.7em;
  }
  .desc-article-list {
    padding-left: 0px;
  }
}
.btn-no-shadow {
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    background: none !important;
}
.link-btn {
  color: #3670d2 !important;
  
}
.no-padding{
  padding: 0px;
}
.article-image-container {
    width: 100%;
}
.no-padding-btm {
  padding-bottom: 0 !important;
}
.no-padding-top {
  padding-top: 0 !important;
}
.article-component {
  display: flex;
  flex-direction: row;
}
.image-article {
  width : 240px;
  height : 128px;
}
</style>
