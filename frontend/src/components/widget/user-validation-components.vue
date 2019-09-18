<template>
  <div class="validation">
    <v-container>
      <div v-if="status" class="validation-success">
        <div class="title-validation">
          Selamat
        </div>
        <div class="sub-title-validation">
          Akun anda telah terverifikasi, silahkan login ke account anda untuk melanjutkan
        </div>
        <div class="btn-container">
          <div class="go-to-maribelajar-btn">
            Login
          </div>
        </div>
      </div>
      <div v-else class="validation-failed">
          <div class="title-validation">
          Terjadi Kesalahan
        </div>
        <div class="sub-title-validation">
          Silahkan coba beberapa menit lagi
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import { EventBus } from './../../EventBus.js';

export default {
  name: "validation-user-component",
  props:{
      idUser: {
        type: String,
        default: ""
      }
  },
  data() {
    return {
      status: false
    }
  },
  created() {
   this.requestValidate();
  },
  methods: {
    requestValidate: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get("/users/validate/" + this.idUser, headers,
        function(response){
          if (response.status == 200) {
            if(response.data.status == "success"){
              self.status = true
            }
          }else{
            self.status = false
          }
        },
        function (e){
          self.setMessage(e, 1);
        });
    },
    setMessage(message, type) {
      let data={}
      data.message = message
      data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
    }
  }
};
</script>
<style scoped>
</style>
