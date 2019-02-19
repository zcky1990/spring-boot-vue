<template>
    <div class="container comment">
        <article class="media">
        <figure class="media-left">
            <p class="image is-48x48">
            <img v-bind:src="userComment.User.imageUrl">
            </p>
        </figure>
        <div class="media-content">
            <div class="content">
            <p>
                <strong>{{userComment.User.userName}}</strong>
                <br>
                <!--{{userComment}} -->
                    {{userComment.comment}}
                <br>
                <small><a>Like</a> · <a>Reply</a> · 2 hrs</small>
            </p>
            </div>
        </div>
        </article>
      <reply-comment 
      v-for="reply in replyList" 
      v-bind:key="reply.replyId"
      v-bind:reply="reply"
      ></reply-comment>
    </div>
</template>

<script>
    import {AXIOS} from './../http-common'
    import Reply from './Reply'
    export default {
    name: 'comment',
    props: ['userComment','replyList'], 
    methods: {
        
    },
    components: {
                    'reply-comment': Reply
    },
    created() {
         console.log(this.userComment)
         this.replyList = this.userComment.replyList;
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
