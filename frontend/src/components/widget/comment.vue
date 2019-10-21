<template>
  <div class="message-article">
    <v-container class="pa-2" fluid grid-list-md>
      <v-layout column>
        <v-flex>
          <div class="comment-title">
            Komentar
            <v-avatar>
              <v-icon color="rgb(0, 209, 178)">chat</v-icon>
            </v-avatar>
          </div>
          <div class="message" v-for="item in messageList" :key="item.id">
            <div class="image-comment">
              <v-img
                :src="item.users.image_profile_url"
                :lazy-src="item.users.image_placeHolder"
                aspect-ratio="1"
                cover
                class="grey lighten-2 circle"
              >
                <template v-slot:placeholder>
                  <v-layout fill-height align-center justify-center ma-0>
                    <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                  </v-layout>
                </template>
              </v-img>
            </div>
            <div class="message-content">
              <div class="author">
                <div class="authors-name">
                  {{item.users.firstname}} {{item.users.lastname}}
                  <div class="alias">@{{item.users.username}}</div>
                </div>
                <div class="authors-comment" v-html="item.comment"></div>
                <div class="article-create-date">{{item.created_date}}</div>
              </div>
            </div>
          </div>
          <div v-if="hasMoreComment" class="center">
            <v-btn
              :loading="isLoadMoreCommentOnProgress"
              elevation="0"
              depressed
              class="load-more-btn"
              @click="loadMore"
            >Load More Comment</v-btn>
          </div>
        </v-flex>
      </v-layout>
    </v-container>
    <div class="submit-comment-container">
      <v-container class="message-add-comment">
        <div class="comment-area">
          <ckeditor
          :editor="configEditor.editor"
          v-model="postBody.comment"
          :config="configEditor.editorConfig"
          @input="validate"
        ></ckeditor>
        </div>
        <div class="submit-container">
          <v-btn color="#00d1b2" :disabled="isDisable" @click="submit" class="submit-btn">Submit</v-btn>
        </div>
      </v-container>
    </div>
  </div>
</template>

<script>
import ClassicEditor from "@ckeditor/ckeditor5-editor-classic/src/classiceditor";
import Essentials from "@ckeditor/ckeditor5-essentials/src/essentials";
import Autoformat from "@ckeditor/ckeditor5-autoformat/src/autoformat";
import Bold from "@ckeditor/ckeditor5-basic-styles/src/bold";
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
import Heading from "@ckeditor/ckeditor5-heading/src/heading";
import Link from "@ckeditor/ckeditor5-link/src/link";
import List from "@ckeditor/ckeditor5-list/src/list";
import Paragraph from "@ckeditor/ckeditor5-paragraph/src/paragraph";
import Alignment from "@ckeditor/ckeditor5-alignment/src/alignment";
import CodeBlock from "@/lib/ckeditor5-code/src/codeblock"
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github.css";

import { EventBus } from "./../../EventBus.js";

export default {
  name: "comment",
  props: {
    articleId: String
  },
  data() {
    return {
      getCommentUrl: "article/get_comment",
      addCommentUrl: "article/add_comment",
      page: 0,
      postBody: {
        articleId: "",
        comment: ""
      },
      isDisable: true,
      messageList: [],
      hasMoreComment: false,
      isLoadMoreCommentOnProgress: false,
      configEditor: {
        editor: ClassicEditor,
        editorConfig: {
          plugins: [
            Essentials,
            Autoformat,
            Bold,
            Italic,
            BlockQuote,
            Heading,
            Link,
            List,
            Paragraph,
            Alignment,
            CodeBlock
          ],
          toolbar: {
            items: [
              "heading",
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
              "blockQuote",
              "pre"
            ]
          }
        }
      }
    };
  },
  methods: {
    submit: function() {
      if (this.articleId != undefined) {
        this.postBody.articleId = this.articleId;
        this.addCommentService(this.postBody);
      }
    },
    setCssSideImage: function() {
      let elm = document.querySelectorAll(".image");
      if (elm.length > 0) {
        for (let i = 0; i < elm.length; i++) {
          let el = elm[i];
          if(el.className.includes("image-style-side")){
            el.style.float = "right";
            el.style.maxWidth = "50%";
          }
          el.style.padding = "15px";
          el.style.textAlign = "center";
          el.styleposition = "relative";
          el.styleoverflow = "hidden";
          let child = el.children[0];
          child.style.maxWidth = "100%";
          child.style.display = "block";
          child.style.margin = "0 auto";
        }
      }
    },
    setCssQuote: function() {
      let elm = document.querySelectorAll("blockquote");
      if (elm.length > 0) {
        for (let i = 0; i < elm.length; i++) {
          let el = elm[i];
          el.style.background = "aliceblue";
          el.style.padding = "5px";
          el.style.borderLeft = "5px solid #00d1b2";
        }
      }
    },
    getCommentListService: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      if (this.articleId != undefined) {
        this.get(
          this.getCommentUrl + "/" + this.articleId + "?page=" + this.page,
          headers,
          function(response) {
            if (response.status == 200) {
              let responseData = response.data.response;
              self.messageList = responseData;
            }
          },
          function(e) {
            self.setMessage(e, 1);
          }
        );
      }
    },
    getMoreCommentListService: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.isLoadMoreCommentOnProgress = true;
      if (this.articleId != undefined) {
        this.get(
          this.getCommentUrl + "/" + this.articleId + "?page=" + this.page,
          headers,
          function(response) {
            if (response.status == 200) {
              let responseData = response.data.response;
              self.addData(responseData);
            }
          },
          function(e) {
            self.setMessage(e, 1);
          }
        );
      }
    },
    loadMore: function() {
      this.page++;
      this.getMoreCommentListService();
    },
    addData: function(response) {
      this.messageList = this.messageList.concat(response);
      this.isLoadMoreCommentOnProgress = false;
      if (response.length == 0) {
        this.hasMoreComment = false;
      }
    },
    addCommentService: function(data) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.post(
        this.addCommentUrl,
        data,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.messageList.unshift(responseData);
            self.postBody.comment = "";
            self.validate();
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    validate: function() {
      if (this.postBody.comment.length > 0) {
        this.isDisable = false;
      } else {
        this.isDisable = true;
      }
    },
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  },
  updated() {
    this.setCssSideImage();
    this.setCssQuote();
    document.querySelectorAll("pre p").forEach(block => {
      hljs.highlightBlock(block);
    });

    document.querySelectorAll("pre ol").forEach(block => {
      hljs.highlightBlock(block);
    });
  },
  watch: {
    articleId: function() {
      this.getCommentListService();
    }
  }
};
</script>
<style scoped>
.comment-area {
  background-color: #fff;
  border-color: #dbdbdb;
  color: #363636;
  border-radius: 10px;
  box-shadow: inset 0 1px 2px rgba(10, 10, 10, 0.1);
  border: 1px solid rgba(219, 219, 219, 0.5);
}
.textarea {
  width: 100%;
  min-height: 80px;
  display: block;
  max-width: 100%;
  min-width: 100%;
  padding: 0.625em;
  resize: vertical;
  font-size: 14px;
}
::-webkit-input-placeholder {
  font-size: 14px;
}
::-moz-placeholder {
  font-size: 14px;
}
:-ms-input-placeholder {
  font-size: 14px;
}
::placeholder {
  font-size: 14px;
}
.author {
  margin-left: 10px;
}
.message {
  border-top: 1px solid rgba(219, 219, 219, 0.5);
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  padding: 10px;
}
.message-content {
    max-width: 90%;
}
.message-add-comment {
  border-top: 1px solid rgba(219, 219, 219, 0.5);
  display: column;
  flex-direction: row;
  flex-wrap: nowrap;
}
.submit-btn {
  color: white;
}
.image-comment {
  width: 64px;
  height: 64px;
  margin-top: 0.5em;
  flex-shrink: 0;
}
.authors-comment {
  font-size: 14px;
}
.authors-name {
  font-size: 16px;
  font-weight: 600;
  display: flex;
}
.alias {
  font-size: 12px;
  font-weight: 300;
  padding: 5px;
}
.comment-title {
  font-size: 1.5em;
  font-weight: 600;
}
.circle {
  border-radius: 40px;
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
</style>
