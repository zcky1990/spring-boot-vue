// JS fiddle : https://jsfiddle.net/eL4gvp1c/

new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue.js!',
    title:'How to create application boilerplate with Vert.x, VueJS, and OAuth2 in five steps',
    topics:'Javascript',
    short_desc : '<p>In this tutorial you will learn how to setup an application boilerplate based on Vert.x (Java) as a backend and VueJs as a frontend with a focus on User Authentication against Keycloak through OAuth2.</p>',
    image : 'https://res.cloudinary.com/dk0z4ums3/image/upload/w_667,h_336,c_fill/v1545292047/attached_image/alergi-si-kecil-sering-kambuh-bisa-jadi-karena-salah-minum-susu.jpg',
    authors: 'Jack Cohen in OneZero',
    date_created: 'Nov 16, 2018'
  }
})

<article class="media content-article">
        <div class="media-content">
          <div class="content">
            <div class="card-content article-content">
                <div class="subtitle content-topics">
                  <span><i><small>{{topics}}</small></i></span> 
                </div>
                <div class="title content-title">
                  {{title}}
                </div>
                <div class="content-short-desc">
                    <div v-html="short_desc">
                </div>
             </div>
             <div class="media">
               <div class="media-left">
                <div class="is-grouped user-content">
                  <p class="heading">{{authors}}</p>
                  <p class="heading">{{date_created}}</p>
                </div>
              </div>
              <div class="media-content"></div>
              <div class="media-right">
                 <div class="is-grouped user-content-btn">
                <div class="level is-mobile">
                        <div class="level-left">
                            <div class="level-item has-text-centered">
                                <a href="">
                                    <i class="material-icons">favorite_border</i>
                                </a>
                            </div>
                            <div class="level-item has-text-centered">
                                <div>
                                    <a href="">
                                        <i class="material-icons">chat_bubble_outline</i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                 </div>
              </div>
             </div>
            </div>
          </div>
          
        </div>
        <div class="media-right">
            <figure class='image is-64x64 is-horizontal-center'><img src='https://unsplash.it/64'></figure>
        </div>
      </article>