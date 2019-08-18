<template>
  <section class="section profile">
    <div class="profile-container">
      <div class="page-title">
        <div class="overview">Overview</div>
        <div class="title">User Profile</div>
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
                    <img :src="data.image_url" alt />
              </v-avatar>
              </v-card-text>
                <div class="user-name">
                 {{data.display_name}}
                </div>
                <v-divider></v-divider>
                <v-container>
                <div class="desc">
                  Description :
                </div>
                <div class="desc-content">
                  {{data.desc}}
                </div>
                </v-container>
                <v-divider></v-divider>
                <v-container>
                  <div class="account-desc">
                    Account Details
                  </div>

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
                                <input v-model="data.zip" type="text" name="zipcode" :disabled="isDisable">
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
                          v-model="data.desc"
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
          <v-btn small minWidth="100" class="white--text btn" color="#00d1b2" @click="submit">Save</v-btn>
          </div>
      </div>

    </div>
  </section>
</template>

<script>
import { AXIOS } from "./../http-common";
import { Util } from "@/components/util";

export default {
  name: "profile-component",
  data() {
    return {
      data: {
        display_name : "Aqil Aulia Sidqi",
        image_url : "https://randomuser.me/api/portraits/men/1.jpg",
        firstname : "Aqil",
        lastname : "Aulia Sidqi",
        gender : "Laki-laki",
        city: "Jakarta",
        zip: "17530",
        email : "zcky1990@gmail.com",
        password : "selamatpagi",
        address : "jl.kaliurang km.5 rt 05/w 05",
        phonenumber : "081282870150",
        postal_code : "17530" ,
        birthday: "1990-01-15",
        desc :"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
      },
      item: ["Laki-laki", "Perempuan"],
      isDisable : true,
      labelBtn : "Edit",
     
    }
  },
  created() {
  },
  methods: {
    editField: function (){
      this.isDisable = !this.isDisable;
      if(this.isDisable){
        this.labelBtn = "Edit"
      }else {
        this.labelBtn = "Cancel"
      }
      console.log(this.isDisable)
    },
    submit : function () {
      console.log(this.data)
    }
   
  },
  computed: {
   
    },
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.profile {
  width: 70%;
}
@media only screen and (max-width: 600px) {
  .profile {
    width: 100%;
  }
}

.date-picker {
    align-items: center;
    text-align: center;
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
