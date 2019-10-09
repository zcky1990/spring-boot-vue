<template>
  <section class="section profile">
   test
  </section>
</template>

<script>
import { EventBus } from "./../../EventBus.js";

export default {
  name: "detail-profile-component",
  props: {
    user: Object
  },
  data() {
    return {
      data: {
        id:""
      }
    };
  },
  created() {
    console.log(this.user)
  },
  methods: {
  
  }
};
</script>
<style scoped>

</style>
