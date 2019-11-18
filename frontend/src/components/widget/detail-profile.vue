<template>
  <section class="section profile">
    <div class="profile-container">
      <div class="user-content">
        <div class="left-profile">
          <div class="profile-container">
            <div class="user-profile">
              <v-card-text>
                <v-avatar size="150" class="image-container">
                  <v-img v-if="isHasImage == true" alt="Avatar" :src="data.image_url" cover></v-img>
                  <v-icon v-else size="150" color="#00d1b2">account_circle</v-icon>
                </v-avatar>
              </v-card-text>
              <div class="user-name">{{data.display_name}}</div>
              <v-container>
                <div class="desc">Description :</div>
                <div class="desc-content">{{data.description}}</div>
              </v-container>
            </div>
          </div>
        </div>
      </div>
    </div>
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
      getUserUrl: "/users/get_user_detail/",
      data: {}
    };
  },
  created() {
    this.getUsersById(this.user.id);
  },
  methods: {
    isImageExists: function() {
      if (this.data.image_url == "" || this.data.image_url == undefined) {
        return false;
      } else {
        return true;
      }
    },
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
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  },
  computed: {
    isHasImage() {
      return this.isImageExists();
    }
  }
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
  text-align: left;
}
.detail-item {
  flex-grow: 1;
  padding: 10px;
}
.user-profile {
  background-color: #dbdfe5;
}
.image-container {
  border: 1px solid #525e74;
}
</style>
