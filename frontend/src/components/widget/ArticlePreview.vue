<template>
    <section class="section article">
    <div class="container article-content">
      <h1 class="title is-1">{{title}}</h1>
      <h2 class="subtitle is-3">
        {{subtitle}}
      </h2>
      <div class="content">
        <div v-html="content"></div>
    </div>
    </div>
  </section>
</template>

<script>
 
export default {
    name: 'article',
     props: ['title', 'subtitle', 'content'], 

    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.article-content{
        text-align: left;
}
</style>
