<template>
 <v-container>
  <div class="bookmark-card-container">
   <div class="bookmark" v-for="n in 4" :key="n">
    <div class="bookmark-date">Feb 15</div>
    <div class="bookmark-post-title">Squarespace</div>
    <div class="marker"></div>
    <div class="category">ipa</div>
    <div
     class="post-desc"
    >With Squarespace, you can swiftly set up your own space on the internet that will accelerate your photo works and services. It is clearly a high-end, sophisticated, simply the best website builder ...</div>
   </div>
  </div>
 </v-container>
</template>

<script>
export default {
 name: "user-bookmark-list-component",
 components: {},
 data() {
  return {
   search: "",
   selected: [],
   tableHeaderList: [],
   dataList: [],
   singleSelect: true
  };
 },
 methods: {},
 computed: {}
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.bookmark-card-container {
 display: flex;
 flex-direction: row;
 justify-content: flex-start;
 flex-wrap: wrap;
}
.bookmark-table-title {
 color: rgb(0, 0, 0);
 font-size: 2rem;
 font-style: normal;
 font-weight: 500;
 letter-spacing: 1.8px;
 padding-bottom: 10px;
 padding-right: 10px;
 padding-top: 10px;
 pointer-events: auto;
 text-decoration-color: rgb(0, 0, 0);
 text-rendering: optimizelegibility;
 text-size-adjust: 100%;
 text-transform: none;
 -webkit-font-smoothing: subpixel-antialiased;
}
.bookmark {
 margin-right: 5%;
 margin-bottom: 10%;
 width: 300px;
}
.bookmark-date {
 font-size: 1.7rem;
 font-style: normal;
 font-weight: 400;
 height: auto;
 letter-spacing: -0.74px;
 color: #00d1b2;
}
.bookmark-post-title {
 color: rgb(0, 0, 0);
 font-size: 1.4rem;
 font-style: normal;
 font-weight: 400;
}
.marker {
 width: 100px;
 border: 1px solid #00d1b2;
 margin-bottom: 5px;
 margin-top: 5px;
}
.category {
 font-family: minion-pro;
 font-size: 1rem;
 font-style: italic;
 font-weight: 400;
 height: auto;
 letter-spacing: normal;
 line-height: 25.5px;
}
.post-desc {
 color: rgb(176, 176, 176);
 font-family: Lato;
 font-size: 12px;
 font-style: normal;
 font-weight: 400;
 letter-spacing: 1.44px;
 line-height: 21.6px;
 text-decoration-color: rgb(176, 176, 176);
 text-decoration-line: none;
 text-decoration-style: solid;
 -moz-text-size-adjust: 100%;
 text-size-adjust: 100%;
 text-transform: uppercase;
}
</style>
