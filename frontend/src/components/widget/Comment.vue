<template>
    <v-container
      class="pa-2"
      fluid
      grid-list-md
    >
      <v-layout column>
        <v-flex>
          <v-container class="message" v-for="item in messageList" >
            <v-layout>
              <div class="image image-comment">
                <v-img
                  :src="item.user.image_url"
                  :lazy-src="item.user.image_placeHolder"
                  aspect-ratio="1"
                  class="grey lighten-2"
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
              <v-flex>
                <div class="author">
                  <div class="authors-name">{{item.user.name}}</div>
                  <div class="authors-comment">{{item.message}} </div>
                  <div class="article-create-date">{{item.created_at}}</div>
                </div>
              </v-flex>
            </v-layout>
          </v-container>
          <v-container class="message" >
            <v-layout>
              <div class="image image-comment">
                <v-img
                  :src="user.image_url"
                  :lazy-src="user.image_placeHolder"
                  aspect-ratio="1"
                  class="grey lighten-2"
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
              <v-flex>
                <div class="author">
                    <v-textarea
                      v-model="postBody.comment"
                      label="comment"
                      auto-grow
                      outlined
                      rows="3"
                      row-height="25"
                    ></v-textarea>
                  <v-flex xs12 right class="submit-container">
                    <v-btn color="#00d1b2" dark @click="submit">Submit</v-btn>
                  </v-flex>
                </div>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>
      </v-layout>
    </v-container>
</template>

<script>
    //import {AXIOS} from './../http-common'
    import Reply from './Reply'
    export default {
    name: 'comment',
    //props: ['userComment'], 
    data() {
      return {
          postBody: {
            comment: ""
          },
          user: {
                id:"saddasda",
                name :"Barbara Middleton",
                image_url : "https://picsum.photos/500/300?image=12",
                image_placeHolder : "https://picsum.photos/10/6?image=12"
          },
          messageList: [
            { id:"sadsadaswqe21321321",
              user : {
                id:"saddasda",
                name :"Barbara Middleton",
                image_url : "https://picsum.photos/500/300?image=12",
                image_placeHolder : "https://picsum.photos/10/6?image=12"
              },
              message : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eros lacus, nec ultricies elit blandit non. Suspendisse pellentesque mauris sit amet dolor blandit rutrum. Nunc in tempus turpis. ",
              created_at: "11/15/2019"
            },
            { id:"sadaq21312321321321",
              user : {
                id:"saddasadsda",
                name :"Sarah Middleton",
                image_url : "https://picsum.photos/500/300?image=12",
                image_placeHolder : "https://picsum.photos/10/6?image=12"
              },
              message : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eros lacus, nec ultricies elit blandit non. Suspendisse pellentesque mauris sit amet dolor blandit rutrum. Nunc in tempus turpis. ",
              created_at: "11/15/2019"
            }
          ]
      }
    },
    methods: {
        submit(){
          console.log(this.postBody)
        }
    },
    components: {
    //                'reply-comment': Reply
    },
    computed:{
        replyList: function(){
           // return this.userComment.replyList;

        }
    }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.error-message {
   text-align: center;
   padding: 10px;
   color: red;
}

.author {
    margin-left: 10px;
}
.message {
  border-top: 1px solid rgba(219,219,219,.5);
}

.image-comment{
    width: 64px;
    height: 64px;
    margin-top: 0.5em;
}
.authors-comment{
  font-size: 14px;
}

.authors-name {
    font-size: 16px;
    font-weight: 600;
}

::-webkit-input-placeholder {
    font-size: 20px !important;
}

:-moz-placeholder {  
    font-size: 20px !important;
}

/*--for IE10 support--*/
:-ms-input-placeholder {
    font-size: 20px !important;
}
</style>
