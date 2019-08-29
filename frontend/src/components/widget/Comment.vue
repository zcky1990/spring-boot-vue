<template>
<div class="message-article">
    <v-container
      class="pa-2"
      fluid
      grid-list-md
    >
      <v-layout column>
        <v-flex>
          <div class="comment-title">
            Komentar <v-avatar><v-icon color="rgb(0, 209, 178)">chat</v-icon></v-avatar>
          </div>
          <div class="message" v-for="item in messageList" :key="item.id" >
              <div class="image-comment">
                <v-img
                  :src="item.users.image_profile_url"
                  :lazy-src="item.users.image_placeHolder"
                  aspect-ratio="1"
                  cover
                  class="grey lighten-2 circle"
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
              <div class="message-content">
                <div class="author">
                  <div class="authors-name">{{item.users.firstname}} {{item.users.lastname}} <div class="alias">@{{item.users.username}}</div></div>
                  <div class="authors-comment">{{item.comment}} </div>
                  <div class="article-create-date">{{item.created_date}}</div>
                </div>
              </div>
          </div>
        </v-flex>
      </v-layout>
    </v-container>
    <div class="submit-comment-container">
      <v-container class="message-add-comment" >
        <div class="comment-area">
              <textarea 
              v-model="postBody.comment"
              class="textarea" placeholder="Add a comment..."
               v-on:keyup="validate"
              ></textarea>

        </div>
        <div class="submit-container">
            <v-btn color="#00d1b2" :disabled="isDisable" @click="submit" class="submit-btn" >Submit</v-btn>
       </div>
       </v-container>
    </div>
</div>
</template>

<script>
export default {
    name: 'comment',
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
          messageList: []
      }
    },
    methods: {
        submit(){
          this.postBody.articleId = this.articleId;
          this.addCommentService(this.postBody)
        },
        getCommentListService: function() {
          let self = this;
          let headers = this.getHeaders(this.$session);
          this.get(this.getCommentUrl +"/"+this.articleId+"?page="+this.page ,  headers, 
            function(response){
              if (response.status == 200) {
                let responseData = response.data.response;
                self.messageList = responseData;
              }
            },
            function(e) {
              self.setMessage(e, 1)
            });
        },
        addCommentService: function(data) {
          let self = this;
          let headers = this.getHeaders(this.$session);
          this.post(this.addCommentUrl, data, headers ,
          function(response) {
              if (response.status == 200) {
                let responseData = response.data.response;
                self.messageList.unshift(responseData);
                self.postBody.comment= "";
                self.validate();
              }
            },
         function(e) {
              self.setMessage(e, 1)
            });
        },
        validate: function(){
          if (this.postBody.comment.length > 0){
            this.isDisable = false;
          }else{
            this.isDisable = true;
          }
        }
    },
    watch: { 
      	articleId: function(newVal, oldVal) {
          //console.log('Prop changed: ', newVal, ' | was: ', oldVal)
          this.getCommentListService();
        }
      }
    }
</script>
<style scoped>
.comment-area {
    background-color: #fff;
    border-color: #dbdbdb;
    color: #363636;
    border-radius: 10px;
    box-shadow: inset 0 1px 2px rgba(10,10,10,.1);
    border:1px solid rgba(219,219,219,.5);
}
.textarea {
    width: 100%;
    min-height: 80px;
    display: block;
    max-width: 100%;
    min-width: 100%;
    padding: .625em;
    resize: vertical;
    font-size:14px;
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
  border-top: 1px solid rgba(219,219,219,.5);
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  padding: 10px;
}
.message-add-comment{
   border-top: 1px solid rgba(219,219,219,.5);
  display: column;
  flex-direction: row;
  flex-wrap: nowrap;
}
.submit-btn{
  color:white;
}
.image-comment{
    width: 64px;
    height: 64px;
    margin-top: 0.5em;
    flex-shrink: 0;
}
.authors-comment{
  font-size: 14px;
}
.authors-name {
    font-size: 16px;
    font-weight: 600;
    display: flex;
}
.alias {
  font-size:12px;
  font-weight:300;
  padding: 5px;
}
.comment-title {
    font-size: 1.5em;
    font-weight: 600;
}
.circle {
  border-radius: 40px;
}
</style>
