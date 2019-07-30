<template>
  <v-container d-flex fluid grid-list-xs>
    <v-layout align-baseline wrap>
      <v-flex xs12 d-flex>
        <alert-component ref="alert"></alert-component>
      </v-flex>
      <v-flex xs12 d-flex>
        <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
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
import Autoformat from "@ckeditor/ckeditor5-autoformat/src/autoformat";
import Bold from "@ckeditor/ckeditor5-basic-styles/src/bold";
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
import EasyImage from "@ckeditor/ckeditor5-easy-image/src/easyimage";
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

export default {
  name: "editor-component",
  props: ["url"],
  data() {
    return {
      editor: ClassicEditor,
      editorData: "<p></p>",
      editorConfig: {
        plugins: [
          Essentials,
          Base64Uploader,
          Autoformat,
          Bold,
          Italic,
          BlockQuote,
          EasyImage,
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
      },
    };
  },
  components: {
    "alert-component": Alert
  },
  methods: {
    submit: function() {
      this.showSuccessAlert("success input");
    },
    hideAlert:function(){
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
    callRestService: function (model) {
      let self = this;
      let router = this.$router;
      AXIOS.post(url, model)
        .then(response => {
          if (response.status == 200) {
            let responseData = response.data;

            if (responseData["error_message"] != undefined) {
            } else {
            }
          }
        })
        .catch(e => {
          self.errors.push(e);
        });
    }
  }
};
</script>
<style scoped>
</style>
