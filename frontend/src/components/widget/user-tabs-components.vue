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
      slider-color="#fff"
      color="#00d1b2"
      grow
    >
      <v-tab
        v-for="item in tabs"
        :key="item.index"
      >
        <div class="tab-name">{{ item.name }}</div>
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item
        v-for="item in tabs"
        :key="item.index"
      >
      <div class="tab-content-container" color="#00d1b2" v-show="item.index == 1">
          <user-profile></user-profile>
      </div>
      <div v-show="item.index == 2">
          ASD
      </div>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import UserProfile from './profile';

export default {
  name: "user-tabs-component",
  data () {
    return {
      tab: null,
      centered: true,
      grow: true,
      currentTab: 0,
      tabs: [ 
        { "name":"Profil",
          "index" : "1"
        }, 
        { "name":"Reading",
          "index" : "2"
        }
      ],
    }
  },
  components: {
    "user-profile" : UserProfile
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@media only screen and (max-width: 640px) {
  .tab-content-container {
    padding: 0 !important;
  }
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
  background-color: #00d1b2;
  padding: 24px;
  color: white;
  font-size: 1.2rem;
  font-weight: 400;
}
.overview {
  font-size: 1.2em;
  line-height: 1.5;
}
.title {
  font-size: 1.7em !important;
  line-height: 1.5 !important;
  font-weight: 700;
}
.tab-name {
    font-size: 1rem;
    font-weight: 600;
    line-height: 1;
    letter-spacing: 0.05rem;
    color: #fff;
}
</style>
