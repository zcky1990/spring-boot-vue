<template>
  <v-container d-flex fluid grid-list-xs @change="validBtn">
    <v-layout align-baseline wrap>
      <v-flex xs12 d-felx>
       <v-text-field
          v-model="data.id"   
          style="display:none;"     
      ></v-text-field>
        <v-text-field
          v-model="data.article_title"
          label="Title"
          placeholder="The Great me"
          v-on:input="setSlug()"
          outlined
      ></v-text-field>
        <v-select
              :items="categoryDropdown"
              v-model="data.categoryArticle"
              label="Category"
              outlined
              multiple
              item-text="name"
              return-object
              color="rgb(0, 209, 178)"
            ></v-select>
          
      </v-flex>
      <v-flex xs12 d-flex>
        <alert-component ref="alert"></alert-component>
      </v-flex>
      <v-flex xs12 d-flex>
        <ckeditor
        :editor="configEditor.editor" 
        v-model="data.article_content" 
        :config="configEditor.editorConfig"
        @input="validBtn"
        ></ckeditor>
      </v-flex>
      <v-flex xs12 d-flex>
        <div class="refs">
          <div class="reference-title">Reference</div>
          <div class="reference-container">
            <div v-for="(item, index) in data.reference_list" ref="refereceContainer" class="ref-container" :key="item">
              <v-text-field
              v-model="data.reference_list[index]"
              label="Reference"
              :value="item"
              >
                <v-icon slot="append" small color="#00d1b2" @click="addNewRefData">fa-plus</v-icon>
              </v-text-field>
            </div>
          </div>
        </div>
      </v-flex>
      <v-flex xs12 d-flex>
        <v-btn 
        v-bind="btnOptions"
        class="white--text desc submit-btn" 
        @click="submit">Save</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>

import ClassicEditor from "@ckeditor/ckeditor5-editor-classic/src/classiceditor";
import Essentials from "@ckeditor/ckeditor5-essentials/src/essentials";
import Autoformat from "@ckeditor/ckeditor5-autoformat/src/autoformat";
import Bold from "@ckeditor/ckeditor5-basic-styles/src/bold";
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
import Heading from "@ckeditor/ckeditor5-heading/src/heading";
import Image from "@ckeditor/ckeditor5-image/src/image";
import ImageCaption from "@ckeditor/ckeditor5-image/src/imagecaption";
import ImageStyle from "@ckeditor/ckeditor5-image/src/imagestyle";
import ImageToolbar from "@ckeditor/ckeditor5-image/src/imagetoolbar";
import ImageUpload from "@ckeditor/ckeditor5-image/src/imageupload";
import Link from "@ckeditor/ckeditor5-link/src/link";
import List from "@ckeditor/ckeditor5-list/src/list";
import Paragraph from "@ckeditor/ckeditor5-paragraph/src/paragraph";
import Alignment from "@ckeditor/ckeditor5-alignment/src/alignment";

import Alert from "@/components/widget/alert";
import {Util} from "@/components/util"
import {AXIOS} from "@/components/http-common";

class UploadAdapter {
        constructor(loader) {
            this.loader = loader;
        }
        upload() {
          let self = this;
            return new Promise((resolve, reject) => {
              let headers = Util.getDefaultHeaders(Util.getMeta("token"))
              let stringImage = self.loader._reader._reader.result
              let postBody = {
                  'image':stringImage,
                  'content' : 'article'
              }
                AXIOS.post("upload_image_string", postBody,  {headers})
                .then(response => {
                  if (response.data.status == 'success') {
                        resolve({
                            default: response.data.url
                        });
                    } else {
                        reject(response.data.message);
                    }
                })
                .catch(e=> {
                  reject (e);
                });
            });
        }
        abort() {
        }
    }

function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        return new UploadAdapter( loader );
    };
}

export default {
  name: "editor-component",
  props: {
    addUrl: {
      type: String,
      default: ""
    },
    updateUrl: {
      type: String,
      default: ""
    },
    article:{
      type: Object
    }
  },
  data() {
    return {
      data:{
        id:"",
        articleId:"asdasdasd",
        categoryArticle:[],
        article_content:"",
        article_title: "",
        slug:"",
        reference_list:[""]
      },
      urlData : {
        getListUrl: "/category/get_category_list"
      },
      categoryDropdown: [],
      configEditor:{
        editor: ClassicEditor,
        editorConfig: {
        extraPlugins: [ MyCustomUploadAdapterPlugin ],
        plugins: [
          Essentials,
          Autoformat,Bold,Italic,BlockQuote,Heading,
          Image,ImageCaption,ImageStyle,ImageToolbar,
          ImageUpload,Link,List,Paragraph,
          Alignment,Image,ImageToolbar,ImageCaption,
          ImageStyle
        ],
        toolbar: {
          items: [
            "heading","alignment","|",
            "bold","italic","|",
            "link","|",
            "undo","redo","|",
            "bulletedList","numberedList","|",
            "blockQuote","|",
            "imageUpload","imageTextAlternative","|",
            "imageStyle:full","imageStyle:side"
          ]
        }
      }
      }
    };
  },
  components: {
    "alert-component": Alert
  },
  methods: {
    setSlug (){
      this.data.slug = this.slugify(this.data.article_title);
    },
    slugify (text, ampersand = 'and') {
      const a = 'àáäâèéëêìíïîòóöôùúüûñçßÿỳýœæŕśńṕẃǵǹḿǘẍźḧ'
      const b = 'aaaaeeeeiiiioooouuuuncsyyyoarsnpwgnmuxzh'
      const p = new RegExp(a.split('').join('|'), 'g')

      return text.toString().toLowerCase()
        .replace(/[\s_]+/g, '-')
        .replace(p, c =>
          b.charAt(a.indexOf(c)))
        .replace(/&/g, `-${ampersand}-`)
        .replace(/[^\w-]+/g, '')
        .replace(/--+/g, '-')
        .replace(/^-+|-+$/g, '')
    },   
    validBtn: function(){
      if( this.data.article_content.length > 0 && this.data.article_title.length > 0 && this.data.categoryArticle.length > 0){
        this.btnDisabled = false
      }else {
        this.btnDisabled = true
      }
    },
    submit: function() {
      if( ('id' in this.data) && this.data.id != ""){
        this.callUpdateRestService(this.data, this.updateUrl);
      }else {
        delete this.data['id']
        this.callAddRestService(this.data, this.addUrl);
      }
    },
    getRequestHeader: function() {
      this.requestHeader = this.getHeaders(this.$session);
      return this.requestHeader;
    },
    hideAlert: function() {
      this.$refs.alert.hide();
    },
    showSuccessAlert: function(message) {
      this.$refs.alert.setConfig({ type: "success" });
      this.$refs.alert.setMessage(message);
      this.$refs.alert.show();
    },
    showErrorAlert: function(message) {
      this.$refs.alert.setConfig({ type: "error" });
      this.$refs.alert.setMessage(message);
      this.$refs.alert.show();
    },
    callAddRestService: function(model, url) {
      let self = this;
      let headers = this.getRequestHeader();
      this.post(url, model,  headers ,
       function(response) {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.data.id = responseData.id;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success add document");
            }
          }
        },
        function(e){
          self.showErrorAlert(e);
        });
    },
    callUpdateRestService: function(model, url) {
      let self = this;
      let headers = this.getRequestHeader();
      this.put(url, model,  headers,
       function(response){
          if (response.status == 200) {
            let responseData = response.data.response;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success edit document");
            }
          }
        },
        function(e) {
          self.showErrorAlert(e);
        });
    },
    getDataList: function(){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get(this.urlData.getListUrl+"?status=true", headers,
      function(response){
        if(response.status == 200){
          self.categoryDropdown = response.data.response
        }
      },
      function(e){
        self.showErrorAlert(e)
      })
    },
    addNewRefData: function(){
      this.data.reference_list.push("");
    },
    resetData: function(){
      this.data = {};
    }
  },
  created(){
    this.getDataList();
  },
  watch: {
      article: function() {
        if(this.article.id != undefined){
          this.data = this.article;
        }
      }
    },
  computed: {
     btnOptions () {
        const options = {
          disabled: this.btnDisabled,
          color : "#00d1b2"
        }
        return options
     }
  }
};
</script>
<style>
.reference-title {
    padding-top: 1em;
    font-size: 1.5em;
    font-weight: 600;
}
</style>
