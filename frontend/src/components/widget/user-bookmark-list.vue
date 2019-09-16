<template>
<v-container>
  <div class="bookmark-table-title">Posts to Remember</div>
  <div class="bookmark-card-container">
    <div class="bookmark" v-for="n in 4" :key="n" >
      <div class="bookmark-date">Feb 15</div>
      <div class="bookmark-post-title">Lorem ipsum dolor sit amet consectetur adipiscing elit</div>
      <div class="marker"> </div>
      <div class="category">ipa</div>
      <div class="post-desc">Lorem ipsum dolor sit amet consectetur adipiscing elit Lorem ipsum dolor sit amet consectetur adipiscing elit</div>
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
      tableHeaderList:[],
      dataList:[],
      singleSelect: true
    };
  },
  methods: {
  },
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
  font-size:1.7rem;
  font-style:normal;
  font-weight:400;
  height:auto;
  letter-spacing:-0.74px;
  color:#00d1b2;
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
  font-family:minion-pro;
  font-size:1rem;
  font-style:italic;
  font-weight:400;
  height:auto;
  letter-spacing:normal;
  line-height:25.5px;
}
.post-desc{
color:rgb(176, 176, 176);
display:block;
font-family:Lato;
font-size:12px;
font-style:normal;
font-weight:400;
height:84px;
letter-spacing:1.44px;
line-height:21.6px;
margin-block-end:0px;
margin-block-start:0px;
margin-bottom:0px;
margin-inline-end:0px;
margin-inline-start:0px;
margin-top:0px;
text-decoration-color:rgb(176, 176, 176);
text-decoration-line:none;
text-decoration-style:solid;
text-size-adjust:100%;
text-transform:uppercase;
}
</style>
