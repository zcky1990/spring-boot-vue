<template>
  <section class="section profile">
    <div class="profile-container">
      <div class="page-title">
        <div class="overview">Overview</div>
        <div class="title">Teacher Profile</div>
      </div>
      <div class="user-content">
          <div class="left-profile">
            <div class="profile-container">
              <v-card
                class="user-profile"
              >
              <v-card-text>
              <v-avatar
                size="150"
              >
                <v-img
                  v-if="isHasImage == true"
                  alt="Avatar"
                  :src="data.image_url"
                  cover
                ></v-img>
                <v-icon
                  v-else
                >
                  account_circle
                </v-icon>
              </v-avatar>
               
              </v-card-text>
                <div class="user-name">
                 {{data.display_name}}
                </div>
                <div class="upload-file">
                  <div v-if="isDisable === false">
                  <input 
                    type="file" 
                    name="displayname"
                    accept="image/*" 
                    @change="onFilePicked"
                    >
                  </div>
                </div>
                <v-divider></v-divider>
                <v-container>
                <div class="desc">
                  Description :
                </div>
                <div class="desc-content">
                  {{data.description}}
                </div>
                </v-container>
                <v-divider></v-divider>
                <v-container>
                  <div class="account-desc">
                    Account Details
                  </div>
                  <input v-model="data.id" type="text" name="id" disabled hidden>
                  <div class="detail-container">
                    <div class="detail-item">
                        <div class="detail-title">Display Name</div>
                        <div class="detail-form">
                          <input v-model="data.display_name" type="text" name="displayname" :disabled="isDisable">
                        </div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-title">Phone Number</div>
                        <div class="detail-form">
                          <input v-model="data.phonenumber" type="text" name="phonenumber" :disabled="isDisable">
                        </div>
                    </div>
                  </div>

                  <div class="detail-container">
                    <div class="detail-item">
                        <div class="detail-title">Firstname</div>
                        <div class="detail-form">
                          <input v-model="data.firstname" type="text" name="firstname" :disabled="isDisable">
                        </div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-title">Lastname</div>
                        <div class="detail-form">
                          <input v-model="data.lastname" type="text" name="lastname" :disabled="isDisable">
                        </div>
                    </div>
                  </div>

                  <div class="detail-container">
                    <div class="detail-item">
                        <div class="detail-title">Email</div>
                        <div class="detail-form">
                          <input v-model="data.email" type="email" name="email" :disabled="isDisable">
                        </div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-title">Password</div>
                        <div class="detail-form">
                          <input v-model="data.password" type="password" name="password" :disabled="isDisable">
                        </div>
                    </div>
                  </div>

                  <div class="detail-container">
                    <div class="detail-item">
                        <div class="detail-title">Birthday</div>
                        <div class="detail-form date-picker">
                          <v-date-picker v-model="data.birthday" :disabled="isDisable" color="#00d1b2"></v-date-picker>
                        </div>
                        
                    </div>
                    <div class="detail-item">
                      <div class="list-item">
                          <div class="detail-title">Jenis Kelamin</div>
                            <div class="detail-form">
                              <v-select
                                  v-model="data.gender"
                                  :items="item"
                                  solo
                                  label="Jenis Kelamin"
                                  required
                                  :disabled="isDisable"
                                ></v-select>
                            </div>
                            <div class="detail-list">
                              <div class="detail-title">City</div>
                              <div class="detail-form">
                                <input v-model="data.city" type="text" name="city" :disabled="isDisable">
                              </div>
                            </div>
                            <div class="detail-list">
                              <div class="detail-title">Zip Code</div>
                              <div class="detail-form">
                                <input v-model="data.zipcode" type="text" name="zipcode" :disabled="isDisable">
                              </div>
                            </div>
                        </div>
                    </div>
                  </div>

                  <div class="detail-container">
                    <div class="detail-item">
                        <div class="detail-title">Address</div>
                        <div class="detail-form">
                          <input v-model="data.address" type="text" name="address" :disabled="isDisable">
                        </div>
                    </div>
                  </div>

                  <div class="detail-container">
                    <div class="detail-item">
                        <v-textarea
                          v-model="data.description"
                          outlined
                          label="Description"
                          :disabled="isDisable"
                        ></v-textarea>
                    </div>
                  </div>
                </v-container>
              </v-card>
            </div>
          </div>
      </div>
      <div class="btn-container">
        <div class="edit-btn">
          <v-btn small minWidth="100"  class="white--text btn" color="#00d1b2" @click="editField">{{labelBtn}}</v-btn>
        </div>
        <div class="submit-btn">
          <v-btn small minWidth="100" class="white--text btn" color="#00d1b2" @click="saveUserProfile" :disabled="isSaveBtnDisable">Save</v-btn>
          </div>
      </div>

    </div>
  </section>
</template>

<script>
import { EventBus } from './../../EventBus.js';

export default {
  name: "teacher-profile-component",
  data() {
    return {
      data: {
        id:"",
        display_name : "",
        image_url : "",
        firstname : "",
        lastname : "",
        gender : "",
        city: "",
        zipcode: "",
        email : "",
        password : "",
        address : "",
        phonenumber : "",
        postal_code : "" ,
        birthday: "",
        isValidated: false,
        status: false,
        description :"",
      },
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
      messageError:"",
      item: ["Laki-laki", "Perempuan"],
      isDisable : true,
      labelBtn : "Edit",
      isSaveBtnDisable: true,
      getUserProfileUrl : "/users/get_user_detail/",
      saveUserProfileUrl : "/users/edit_user"
    }
  },
  created() {
    this.getUsers();
  },
  methods: {
    editField: function (){
      this.isDisable = !this.isDisable;
      this.isSaveBtnDisable = !this.isSaveBtnDisable;
      if(this.isDisable){
        this.labelBtn = "Edit"
      }else {
        this.labelBtn = "Cancel"
      }
    },
    getUsers: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      let id = this.$session.get('uid')
      this.get(this.getUserProfileUrl + id,  headers, 
       function(response) {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.data = responseData;
          }
        },
        function(e){
          self.setMessage(e, 1)
        });
    },
    saveUserProfile: function() {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.put(this.saveUserProfileUrl, this.data ,  headers,
      function(response) {
          if (response.status == 200) {
            self.$session.set("users", self.data);
            self.editField();
          }
      },
      function(e) {
            self.setMessage(e, 1)
      });
    },
    isImageExists: function() {
      if(this.data.image_url == "" || this.data.image_url == undefined){
        return false;
      }else {
        return true;
      }
    },
    onFilePicked (e) {
      let self = this;
      const files = e.target.files
			if(files[0] !== undefined) {
				let imageName = files[0].name
				if(imageName.lastIndexOf('.') <= 0) {
					return
				}
				const fr = new FileReader ()
				fr.readAsDataURL(files[0])
				fr.addEventListener('load', () => {
					let imageUrl = fr.result
          let imageFile = files[0] 
          self.uploadImage(imageFile)
				})
			}
    },
    uploadImage(imageFile){
      let self = this
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      headers['content-type'] = 'multipart/form-data';
      let data = new FormData();
      data.append('file', imageFile);
      data.append('content', "userProfile");
      this.post("upload_image", data , headers,
      function(response) {
          if(response.data.status == "success"){
            self.data.image_url = response.data.url;
          }else {
            self.setMessage('Upload image failed', 1) 
          }
      },
      function() {
             self.setMessage('Upload image failed', 1) 
      });
    },
    setMessage(message, type){
      let data={}
                data.message = message
                data.type = type
      EventBus.$emit('SNACKBAR_TRIGGERED', data)
    }
  },
  computed: {
      isHasImage() {
        return this.isImageExists()
      }
    },
};
</script>
<style scoped>
.profile {
  width: 100%;
}
@media only screen and (max-width: 600px) {
  .profile {
    width: 100%;
  }
}
.page-title {
    background-color: #00d1b2;
    padding: 10px;
    color: white;
    font-size: 1.2rem;
    font-weight: 400;
}
.date-picker {
    align-items: center;
    text-align: center;
}
.upload-file {
    padding: 20px;
}
.profile-container {
  font-family: lato;
    display: flex;
    justify-content: space-between;
    flex-direction: column;
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
.user-profile {
  align-items: center;
  text-align: center;
}
.user-name {
  font-size: 1.7em !important;
  line-height: 1.2 !important;
  font-weight: 700;
  padding-bottom: 20px;
}
.desc {
    text-align: left;
    font-size: 1.5em;
    line-height: 1.2;
    margin-bottom: 5px;
}
.desc-content {
    text-align: justify;
    line-height: 1.2;
    font-size: 1em;
}
.account-desc {
  text-align: left;
    font-size: 1.5em;
    line-height: 1.2;
    margin-bottom: 5px;
}
.detail-container {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-around;
    text-align: left
}
.detail-item {
    flex-grow: 1;
    padding: 10px;
}
input[type="text"],input[type="password"],input[type="email"] {
    width: 100%;
    border: 1px solid rgba(0,0,0,.12);
    border-radius: 20px;
    padding: 10px;
}
input[type="text"]:disabled ,input[type="password"]:disabled ,input[type="email"]:disabled {
  color: grey;
}
.detail-list {
    margin-bottom: 15px;
}
.btn-container {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    
}
.btn {
font-size: 1em;
    line-height: 1.2;
    margin-bottom: 5px;
    margin-top: 12px;
}
</style>
