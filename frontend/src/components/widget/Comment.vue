<template>
    <v-container
      class="pa-2"
      fluid
      grid-list-md
    >
      <v-layout column>
        <v-flex>
          <v-container>
            <v-layout>
              <div class="image image-comment">
                <v-img
                  src="https://picsum.photos/500/300?image=12"
                  lazy-src="https://picsum.photos/10/6?image=12"
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
                  <div class="authors-name">asdasdsad</div>
                  <div class="article-create-date">sadsadsad</div>
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
    methods: {
        
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

.image-comment{
    width: 64px;
    height: 64px;
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
