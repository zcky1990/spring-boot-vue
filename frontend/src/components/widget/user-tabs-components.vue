<template>
<div>
  <v-tabs
        v-model="tab"
        background-color="deep-purple accent-4"
        dark
        flat
        :centered="centered"
      >
        <v-tabs-slider></v-tabs-slider>
        <v-tab
            v-for="(item, index) in tabs"
            :class="{active: currentTab === index}"
            @click="currentTab = index"
            :key="item"
        >
          {{ item }}
        </v-tab>
  
        <v-tab-item>
          <div>
           <div v-if="currentTab == 0">
             <user-profile></user-profile>
            </div>
            <div v-if="currentTab == 1">
              <v-card-text>Tab1 content</v-card-text>
            </div>
          </div>
        </v-tab-item>
      </v-tabs>
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
      currentTab: 0,
      tabs: [ "Profile", "Reading list"],
    }
  },
  components: {
    "user-profile" : UserProfile
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.table-container {
    padding-left: 16px;
    padding-right: 16px;
}
</style>
