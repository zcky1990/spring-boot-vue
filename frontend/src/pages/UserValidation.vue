<template>
  <div class="user-validation-page">
    <a href="#" v-on:click="requestValidate">Click here</a> to validate your email
  </div>
</template>

<script>
export default {
  name: "userValidation",
  data() {
    return {
      users: {}
    };
  },
  created() {},
  methods: {
    getId: function() {
      let params = this.getUrlParams();
      let id = "";
      for (let i = 0; i < params.length; i++) {
        let param = params[0];
        if (param["id"] != undefined) {
          id = param.id;
          break;
        }
      }
      return id;
    },
    requestValidate: function() {
      let id = this.getId();
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get("/users/validate/" + id, headers,
        function(response){
          if (response.status == 200) {
            let responseData = response.data;
            self.userData = responseData.response;
          }
        },
        function (e){
          self.errors.push(e);
        });
    }
  },
  computed: {
    requestHeader: {
      get: function() {
        return this.headers;
      },
      set: function(header) {
        this.headers = header;
      }
    },
    userData: {
      get: function() {
        return this.users;
      },
      set: function(userdata) {
        this.users = userdata;
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
