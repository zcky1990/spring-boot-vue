<template>
   <div class="article-component">
     <div class="article-image-container">
       <v-container grid-list-xs>
         <v-layout column>
           <v-flex>
              <v-card
              class="mx-auto"
              outlined
              elevation="0"
              >
              <v-img
                  src="https://picsum.photos/500/300?image=11"
                  lazy-src="https://picsum.photos/10/6?image=11"
                  aspect-ratio="1"
                  class="grey lighten-2 rounded"
                  max-width="100%"
                  max-height="300"
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
              <v-card-title class="headline">Ketahui Ragam Cara Menjaga Tulang yang Sehat</v-card-title>
              <v-card-text class="subtitle-1 no-padding-top no-padding-btm ">
                Untuk memiliki tulang yang sehat, Anda harus memelihara kesehatan tulang sejak dini. Cara menjaga tulang agar tetap sehat antara lain dengan menjaga pola makan dan menerapkan gaya hidup sehat, termasuk aktif bergerak. 
              </v-card-text>
              <v-card-actions>
                <v-btn text large class="btn-no-shadow link-btn no-padding">Primary</v-btn>
              </v-card-actions>
              </v-card>
           </v-flex>
            
            <v-divider></v-divider>

           <v-flex xs-12>
                    <v-card elevation="0">
                      <v-container fluid grid-list-sm>
                        <v-layout justify-space-between wrap class="article-content">
                          <v-flex v-for="i in 4" :key="i" class="article-content-card">
                            <v-card
                              class="mx-auto"
                              max-width="350"
                            >
                              <v-img
                                class="white--text"
                                height="200px"
                                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                              >
                                <v-card-title class="align-end fill-height">Top 10 Australian beaches</v-card-title>
                              </v-img>

                              <v-card-text>
                                <span>Number 10</span><br>
                                <span class="text--primary">
                                  <span>Whitehaven Beach</span><br>
                                  <span>Whitsunday Island, Whitsunday Islands</span>
                                </span>
                              </v-card-text>

                              <v-card-actions>
                                <v-btn rounded class="btn-no-shadow link-btn" small>Rounded Button</v-btn>
                              </v-card-actions>
                            </v-card>
                          </v-flex>
                        </v-layout>
                      </v-container>

                    </v-card>

           </v-flex>
         </v-layout>
       </v-container>
     </div>
   </div>
</template>
<script>
//type alert : 'success', 'info', 'error', 'warning'
export default {
  name: "list-article-component",
  data() {
    return {
      
    };
  },
  methods: {
    
  }
};
</script>
<style scoped>
.article-content {
  flex-direction: row !important;
}

.article-content-card {
  flex-basis: 33.33333333333333%;
    -webkit-box-flex: 0;
    -ms-flex-positive: 0;
    flex-grow: 0;
    max-width: 100%;
    padding:5px !important;
}
@media only screen and (max-width: 600px) {
 .article-content {
  flex-direction: column !important;
  }
}

.btn-no-shadow {
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    background: none !important;
}

.link-btn {
  color: #3670d2 !important;
  
}

.no-padding{
  padding: 0px;
}

.article-image-container {
    width: 100%;
}
.no-padding-btm {
  padding-bottom: 0 !important;
}

.no-padding-top {
  padding-top: 0 !important;
}

.article-component {
  display: flex;
  flex-direction: row;
}
.image-article {
  width : 240px;
  height : 128px;
}
</style>
