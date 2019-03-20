// JS fiddle : https://jsfiddle.net/eL4gvp1c/


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