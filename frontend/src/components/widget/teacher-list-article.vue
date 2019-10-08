<template>
   <div class="article-component">
     <div class="container  title-container">
       <div class="title">
        My Article List
       </div>
       <div class="title-btn-container">
         <div class="add-btn" @click="refreshData"> 
               <v-icon class="icon-add-btn">refresh</v-icon>
            </div>
            <div class="add-btn" @click="addNewArticle"> 
               <v-icon class="icon-add-btn">add</v-icon>
            </div>
       </div>
     </div>
     <div v-if="isHasData" class="article-image-container">
       <v-container grid-list-xs>
         <v-layout column>
            <v-flex class="article-list-container" v-for="item in data" :key="item.slug">
              
              <div class="article-list">
                <div class="article-container">
                    <div class="image-article-list">
                      <v-img
                        :src="item.imageHeader"
                        :lazy-src="item.imageHeaderLazy"
                        aspect-ratio="1"
                        class="grey lighten-2 rounded"
                        width="50"
                        min-width="50"
                        height="50"
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
                    </div>
                  </div>

                  <div class="action">
                    <div class="btn-container">
                      <div class="edit-btn btn" @click="getArticleById(item.id)">Edit</div>
                      <div class="delete-btn btn" @click="deleteData(item.id)">Delete</div>
                    </div>
                  </div>
              </div>
            </v-flex>
            <v-flex>
              <div v-if="isHasMoreData" class="center">
                  <v-btn 
                  :loading="isLoadMoreOnProgress"
                  :disabled ="isDisable"
                  elevation = "0"
                  depressed
                  class="load-more-btn" @click="loadMore">Load More Article</v-btn>
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

     <div class="dialog">
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <v-card>
        <v-toolbar dark color="#00d1b2">
          <v-btn icon dark @click="closeDialog">
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>{{title}}</v-toolbar-title>
          <div class="flex-grow-1"></div>
        </v-toolbar>
        <v-container>
          <editor ref="editor" :article="article" add-url="/article/add_article" update-url="/article/update_article"></editor>
        </v-container>
      </v-card>
    </v-dialog>
      </div>
   </div>
</template>
<script>

import { EventBus } from './../../EventBus.js';
import Editor from './editor'

export default {
  name: "teacher-list-article",
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
  components: {
    'editor' : Editor,
  },
  data() {
    return {
      page : 0,
      index : 0,
      listCount: 0,
      listArticleUrl: "/article/get_list_article",
      articleUrl:"/article/get_article_by_id/",
      delelteArticleUrl: "/article/delete_article/",
      isLoadMoreOnProgress : false,
      dialog: false,
      isDisable : false, 
      title: "",
      data : [],
      article:{},
      isHasMoreData: true
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
      let headers = this.getHeaders(this.$session);
        this.get(this.getUrlRequest() + "page="+this.page,  headers ,
        function(response) {
          if(response.status == 200){
            self.data = response.data.response
          }         
        },
        function(e) {
          self.setMessage(e,1);
        });
    },
    getLoadMoreService: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.disableEnableLoadMoreBtn(true);
      this.get(this.getUrlRequest() + "page="+this.page,  headers ,
      function(response) {
           let newData = response.data.response
           if(newData.length > 0){
              self.addData(newData)
           }else{
             self.isHasMoreData= false;
           }
        },
      function (e){
          self.disableEnableLoadMoreBtn(false);
          self.setMessage(e,1);
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
        return  this.listArticleUrl+"?query="+this.query+"&"
      }else{
        return this.listArticleUrl+"?"
      }
    },
    addNewArticle: function(){
      this.title  = "Add New Article"
      this.article = {}
      this.dialog = true;
    },
    getArticleById : function(id){
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.get(this.articleUrl + id,  headers ,
        function(response) {
          if(response.status == 200){
           self.article = response.data.response;
           self.title  = "Edit Article"
           self.dialog = true;
          }         
        },
        function(e) {
          self.setMessage(e,1);
        });
    },
    closeDialog : function(){
      this.article = {};
      this.dialog =! this.dialog;
      this.$refs.editor.resetData();
    },
    refreshData: function(){
      this.data=[];
      this.getArticle();
    },
    deleteData: function(id){
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.delete(this.delelteArticleUrl+id, headers,
      function(response){
        if(response.status == 200){
          self.refreshData();
        }else{
          self.setMessage("failed to delete",1)
        }
      },
      function(e){
        self.setMessage(e,1)
      })
    },
  },
  computed: {
    isHasData: function(){
      if(this.data != undefined){
        if(this.data.length > 0){
                return true
              }else{
                return false
              }
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
.article-component {
    border: 1px solid #efefef;
    width: 100%;
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
  justify-content: space-between;
  border-bottom: 1px solid #efefef;
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
    font-size:  1.2em;
    font-weight: bold;
    font-weight: 400;
}
.article-list-desc{
  font-size:  1em;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;  
  font-weight: 400;
  color: #666666;
  overflow: hidden;
}
.article-container {
    display: flex;
    flex-direction: row;
}
.center {
  margin: 0 auto;
  position: relative;
  text-align: center;
}
.title-btn-container {
    display: flex;
}
.btn {
    background-color: white !important;
    border: 1px solid #00d1b2;
    border-radius: 25px;
    color: #00d1b2;
    cursor: pointer;
    text-align: center;
    padding-left: 20px;
    padding-right: 20px;
    height: 25px;
}
.btn:hover {
  background-color: #00d1b2 !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  color: white;
  cursor: pointer;
  text-align: center;
    padding-left: 20px;
    padding-right: 20px;
}
.icon-add-btn {
color: #00d1b2;
}
.icon-add-btn:hover {
  color: white;
}
.add-btn {
    width: 30px;
    height: 30px;
    padding: 2px;
    border: 1px solid #00d1b2;
    border-radius: 50%;
    margin: 2px;
}
.add-btn:hover {
    width: 30px;
    height: 30px;
    padding: 2px;
    background: #00d1b2;
    border: 1px solid #00d1b2;
    border-radius: 50%;
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

.title-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    flex-wrap: nowrap;
    border-bottom: 1px solid #efefef;
}
.btn-container {
    position: relative;
    display: flex;
    flex-direction: column;
}
.add-article-btn {
  position: absolute;
  right: 0;
  top: -25px;
}

@media only screen and (max-width: 600px) {
  .article-list {
    display: flex;
    flex-direction: column;
    margin-bottom: 0.7em;
  }
  .edit-btn {
    float: left;
    margin-right: 5px;
}
.delete-btn {
  margin-left: 5px;
    float: right;
}
.btn-container {
    margin-top: 5px;
    flex-direction: row;
    margin-bottom: 5px;
}
.action {
    display: flex;
    text-align: center;
    justify-content: center;
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
  flex-direction: column;
}
.image-article {
  width : 240px;
  height : 128px;
}
.edit-btn {
    margin-bottom: 5px;
     width: 100px;
}
.delete-btn {
    width: 100px;
}
</style>
