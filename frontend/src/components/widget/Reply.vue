<template>
    <article class="media">
      <figure class="media-left">
        <p class="image is-48x48">
          <img src="https://bulma.io/images/placeholders/96x96.png">
        </p>
      </figure>
      <div class="media-content">
        <div class="content">
          <p>
            <strong>{{reply.User.userName}}</strong>
            <br>
            {{reply.reply}}
            <br>
            <small><a>Like</a> · 2 hrs</small>
          </p>
        </div>
      </div>
    </article>
</template>

<script>
    //import {AXIOS} from './../http-common'
    export default {
    name: 'reply',
    props: ['reply'],
    created(){
    }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.media {
    margin-left: 20px;
}
</style>
