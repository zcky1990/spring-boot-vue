<template>
  <section class="section profile">
    <v-container>
   test
    </v-container>
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
      getUserUrl :"/users/get_user_detail/",
      data: {}
    };
  },
  created() {
    this.getUsersById(this.user.id)
  },
  methods: {
    getUsersById: function(id) {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.get(
        this.getUserUrl + id,
        headers,
        function(response) {
          if (response.status == 200) {
            self.data = response.data.response;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    setMessage: function (message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    },
  
  }
};
</script>
<style scoped>

</style>
