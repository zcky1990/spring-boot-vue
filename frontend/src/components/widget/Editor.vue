<template>
  <v-container d-flex fluid grid-list-xs @change="validBtn">
    <v-layout align-baseline wrap>
      <v-flex xs12 d-felx>
       <v-text-field
          v-model="data.articleId"   
          style="display:none;"     
      ></v-text-field>
        <v-text-field
          v-model="data.titleArticle"
          label="Title"
          placeholder="The Great me"
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
        :editor="editor" 
        v-model="data.editorData" 
        :config="editorConfig"
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
            return new Promise((resolve, reject) => {
                const data = new FormData();
                data.append('file', this.loader.file);

                AXIOS.post("upload_image", data ,  {
                        'content-type': 'multipart/form-data'
                    },)
                .then(response => {
                  if (response.data.result == 'success') {
                        resolve({
                            default: response.data.url
                        });
                    } else {
                        reject(response.data.message);
                    }
                })
                .catch(e => {
                  reject ( 'Upload failed');
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
      btnDisabled: true,
      data:{
        articleId:"asdasdasd",
        categoryArticle:"",
        editorData: "",
        titleArticle: ""
      },
      editor: ClassicEditor,
      editorConfig: {
        extraPlugins: [ MyCustomUploadAdapterPlugin ],
        plugins: [
          Essentials,
          //Base64Uploader,
          Autoformat,
          Bold,
          Italic,
          BlockQuote,
          Heading,
          Image,
          ImageCaption,
          ImageStyle,
          ImageToolbar,
          ImageUpload,
          Link,
          List,
          Paragraph,
          Alignment,
          Image,
          ImageToolbar,
          ImageCaption,
          ImageStyle
        ],
        toolbar: {
          items: [
            "heading",
            "|",
            "alignment",
            "|",
            "bold",
            "italic",
            "|",
            "link",
            "|",
            "undo",
            "redo",
            "|",
            "bulletedList",
            "numberedList",
            "|",
            "imageUpload",
            "imageTextAlternative",
            "|",
            "imageStyle:full",
            "imageStyle:side"
          ]
        }
      }
    };
  },
  components: {
    "alert-component": Alert
  },
  methods: {
    validBtn: function(){
      if( this.data.editorData.length > 0 && this.data.titleArticle.length > 0 && this.data.categoryArticle.length > 0){
        this.btnDisabled = false
      }else {
        this.btnDisabled = true
      }
    },
    submit: function() {
      this.data.article_content = this.editorData;
      if( ('_id' in this.data) && this.data._id != ""){
          this.callUpdateRestService(this.data, this.updateUrl);
      }else {
        if(this.editorData != ""){
              this.callAddRestService(this.data, this.addUrl);
        }
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
