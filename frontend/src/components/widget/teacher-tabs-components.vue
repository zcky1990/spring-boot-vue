<template>
 <div class="user-tab-container">
  <div class="page-title">
   <v-container>
    <div class="overview">Overview</div>
    <div class="title">User Details Profile</div>
   </v-container>
  </div>
  <v-tabs
   v-model="tab"
   background-color="transparent"
   slider-color="#00d1b2"
   grow
   class="tabs-container"
  >
   <v-tab v-for="item in tabs" :key="item.index">
    <div class="tab-name">{{ item.name }}</div>
   </v-tab>
  </v-tabs>

  <v-tabs-items v-model="tab">
   <v-tab-item v-for="item in tabs" :key="item.index">
    <div class="tab-content-container" color="#00d1b2" v-show="item.index == 1">
     <teacher-profile></teacher-profile>
    </div>
    <div v-show="item.index == 2">
     <teacher-article></teacher-article>
    </div>
   </v-tab-item>
  </v-tabs-items>
 </div>
</template>

<script>
import Profile from "./teacher-profile";
import Article from "./teacher-list-article";

export default {
 name: "teacher-tabs-component",
 data() {
  return {
   tab: null,
   centered: true,
   grow: true,
   currentTab: 0,
   tabs: [{ name: "Profil", index: "1" }, { name: "Article", index: "2" }]
  };
 },
 components: {
  "teacher-profile": Profile,
  "teacher-article": Article
 }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@media only screen and (max-width: 640px) {
 .tab-content-container {
  padding: 0 !important;
 }
 .page-title {
  padding: 0 !important;
  color: #000;
  font-size: 1.2rem;
  font-weight: 400;
 }
}

.tabs-container {
 border-bottom: 1px solid #dee2e6 !important;
}
.table-container {
 padding-left: 16px;
 padding-right: 16px;
}
.user-tab-container {
 width: 100%;
}
.tab-content-container {
 padding: 24px;
}
.page-title {
 padding: 24px;
 color: #000;
 font-size: 1.2rem;
 font-weight: 400;
}
.overview {
 font-size: 1.2em;
 line-height: 1.5;
}
.title {
 font-size: 2em !important;
 line-height: 1.25 !important;
 font-weight: 600;
}
.tab-name {
 font-size: 1rem;
 font-weight: 600;
 color: #000;
}
</style>
