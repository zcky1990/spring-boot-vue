<template>
    <section class="section article">
    <div class="container article-content">
      <span class="tag is-primary">Primary</span>
      <h1 class="title is-1">Section</h1>
      <h2 class="subtitle is-3">
        A simple container to divide your page into <strong>sections</strong>, like the one you're currently reading
      </h2>
      <div class="content ">
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
            labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation 
            ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in 
            reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur 
            sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <p>
            Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, 
            eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. 
            Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur 
            magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia 
            dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et 
            dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis 
            suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea 
            voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?
        </p>
    </div>
      <div class="media">
        <div class="media-content">
        </div>
      </div>
      <div class="media">
      <div class="media-left">
        <figure class="image is-96x96">
          <img class="is-rounded" src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">
        </figure>
      </div>

      <div class="media-content">
        <div class="address">
          <div class="name title is-5">Hank O'Connell</div> 
          <div class="email subtitle is-6">Viva.Mraz2@yahoo.com</div>
          <div class="date subtitle is-6">12-12-2019</div>
       </div>
      </div>
    </div>
    
    <div class="media">
        <div class="media-content">
        </div>
      </div>
      
    </div>
  </section>
</template>

<script>
    //import {AXIOS} from './../http-common'
export default {
    name: 'article'
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.article-content{
        text-align: left;
}
</style>
