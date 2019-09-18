<template>
  <div class="validation">
    <v-container>
      <div v-if="status" class="validation-success">
        <v-container>
            <div class="title-validation">
              Selamat
            </div>
            <div class="sub-title-validation">
              Akun anda telah terverifikasi, silahkan login ke account anda untuk melanjutkan
            </div>
            <div class="btn-container">
              <div class="go-to-maribelajar-btn" @click="goToLogin">
                Login
              </div>
            </div>
        </v-container>
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
    goToLogin:function(){
      this.$router.push("/login");
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
.title-validation {
  font-size: 2em !important;
  line-height: 1.25 !important;
  font-weight: 600;
  text-align: center;
}
.sub-title-validation {
  font-size: 1.2em;
  line-height: 1.5;
  text-align: center;
  color: grey;
}
.btn-container {
    margin-top: 20px;
}
.go-to-maribelajar-btn {
  text-align: center;
  padding-left: 30px;
  padding-right: 30px;
  padding-top: 5px;
  padding-bottom: 5px;
  background: #00d1b2;
  width: max-content;
  color: white;
  font-size: 1.5rem;
  font-weight: 600;
  border-radius: 10px;
  margin: 0 auto;
  border:1px solid #00d1b2;
}

.go-to-maribelajar-btn:hover {
  background: white;
  color: #00d1b2;
  border:1px solid #00d1b2;
}
</style>
