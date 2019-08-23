<template>
  <v-container d-flex fluid grid-list-xs @change="validBtn">
    <v-layout align-baseline wrap>
      <v-flex xs12 d-felx>
       <v-text-field
          v-model="data._id"   
          style="display:none;"     
      ></v-text-field>
        <v-text-field
          v-model="data.article_title"
          label="Title"
          placeholder="The Great me"
          v-on:input="setSlug()"
          outlined
      ></v-text-field>
        <v-text-field
          v-model="data.categoryArticle"
          label="category"
          placeholder="Fantasy"
          outlined
      ></v-text-field>
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
import Base64Uploader from "@ckeditor/ckeditor5-upload/src/base64uploadadapter";
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

import { AXIOS } from "./../http-common";
import Alert from "@/components/widget/Alert";
import { Util } from "@/components/util";

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
                AXIOS.post("upload_image_string", postBody, { headers })
                .then(response => {
                  if (response.data.status == 'success') {
                        resolve({
                            default: response.data.url
                        });
                    } else {
                        reject(response.data.message);
                    }
                })
                .catch(e => {
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
  props: ["addUrl", "updateUrl"],
  data() {
    return {
      data:{
        _id:"",
        articleId:"asdasdasd",
        categoryArticle:"",
        article_content:"",
        article_title: "",
        slug:""
      },
      configEditor:{
        editor: ClassicEditor,
        editorConfig: {
        extraPlugins: [ MyCustomUploadAdapterPlugin ],
        plugins: [
          //Base64Uploader,
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
      if( ('_id' in this.data) && this.data._id != ""){
        this.callUpdateRestService(this.data, this.updateUrl);
      }else {
        delete this.data['_id']
        this.callAddRestService(this.data, this.addUrl);
      }
    },
    getRequestHeader: function() {
      this.requestHeader = Util.getHeaders(this.$session);
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
      AXIOS.post(url, model, { headers })
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.data._id = responseData._id;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success add document");
            }
          }
        })
        .catch(e => {
          self.showErrorAlert(e);
        });
    },
    callUpdateRestService: function(model, url) {
      let self = this;
      let headers = this.getRequestHeader();
      AXIOS.put(url, model, { headers })
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data.response;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success edit document");
            }
          }
        })
        .catch(e => {
          self.showErrorAlert(e);
        });
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
<style scoped>
</style>
