<template>
  <v-container d-flex fluid grid-list-xs>
    <v-layout align-baseline wrap>
      <v-flex xs12 d-flex>
        <alert-component ref="alert"></alert-component>
      </v-flex>
      <v-flex xs12 d-flex>
        <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" ></ckeditor>
      </v-flex>
      <v-flex xs12 d-flex>
        <v-btn class="white--text desc" color="#00d1b2" @click="submit">Submit</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import ClassicEditor from "@ckeditor/ckeditor5-editor-classic/src/classiceditor";
import Essentials from "@ckeditor/ckeditor5-essentials/src/essentials";
import UploadAdapter from "@ckeditor/ckeditor5-adapter-ckfinder/src/uploadadapter";
import Base64Uploader from "@ckeditor/ckeditor5-upload/src/base64uploadadapter";
//import CKFinder from '@ckeditor/ckeditor5-adapter-ckfinder/src/uploadadapter';
import Autoformat from "@ckeditor/ckeditor5-autoformat/src/autoformat";
import Bold from "@ckeditor/ckeditor5-basic-styles/src/bold";
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
//import EasyImage from "@ckeditor/ckeditor5-easy-image/src/easyimage";
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

class MyUploadAdapter {
    constructor( loader ) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file
            .then( file => new Promise( ( resolve, reject ) => {
                this._initRequest();
                this._initListeners( resolve, reject, file );
                this._sendRequest( file );
            } ) );
    }

    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        xhr.open( 'POST', 'http://localhost:8088/api/upload_image', true );
        xhr.responseType = 'json';
    }

    // Initializes XMLHttpRequest listeners.
    _initListeners( resolve, reject, file ) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = `Couldn't upload file: ${ file.name }.`;

        xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        xhr.addEventListener( 'abort', () => reject() );
        xhr.addEventListener( 'load', () => {
            const response = xhr.response;

            if ( !response || response.error ) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            resolve( {
                default: response.url
            } );
        } );

        if ( xhr.upload ) {
            xhr.upload.addEventListener( 'progress', evt => {
                if ( evt.lengthComputable ) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            } );
        }
    }

    _sendRequest( file ) {
        const data = new FormData();
        data.append( 'upload', file );
        this.xhr.send( data );
    }
}

class AUploadAdapter {
   constructor( loader ) {
        this.loader = loader;
    }
          upload = () => {
            const body = new FormData();
            body.append('file', this.loader.file);
            return fetch('http://localhost:8088/api/upload_image', {
              body: body,
              method: 'POST'
            })
              .then(response => response.json())
              .then(downloadUrl => {
                return {
                  default: downloadUrl
                }
              })
              .catch(error => {
                console.log(error);
              });
          }
          abort = () => {
            console.log('Abort upload.')
          }
        }

function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        return new AUploadAdapter( loader );
    };
}

export default {
  name: "editor-component",
  props: ["url"],
  data() {
    return {
      data:{},
      editor: ClassicEditor,
      editorData: "<p></p>",
      editorConfig: {
        extraPlugins: [ MyCustomUploadAdapterPlugin ],
        plugins: [
          Essentials,
          //Base64Uploader,
          //UploadAdapter,
         // CKFinder,
          Autoformat,
          Bold,
          Italic,
          BlockQuote,
          //EasyImage,
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
    submit: function() {
      this.callRestService({});
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
    callRestService: function(model) {
      let self = this;
      let headers = this.getRequestHeader();
      AXIOS.post(this.url, model, { headers })
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success add/edit document");
            }
          }
        })
        .catch(e => {
          self.showErrorAlert(e);
        });
    }
  }
};
</script>
<style scoped>
</style>
