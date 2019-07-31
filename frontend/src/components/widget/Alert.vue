<template>
  <div>
    <v-alert
      v-model="alert"
      :type="type"
      :outline="outline"
      :dismissible="isDismissable"
    >{{message}}</v-alert>
  </div>
</template>
<script>
//type alert : 'success', 'info', 'error', 'warning'
export default {
  name: "alert-component",
  data() {
    return {
      alert: false,
      type: "error",
      message: "",
      closeBtnColor: "primary",
      outline: false,
      isDismissable: true
    };
  },
  methods: {
    setConfig: function(config) {
      if (config["type"] != undefined) {
        this.type = config.type;
      }
      if (config["closeBtnColor"] != undefined) {
        this.closeBtnColor = config.closeBtnColor;
      }
      if (config["outline"] != undefined) {
        this.outline = config.outline;
      }
      if (config["isDismissable"] != undefined) {
        this.isDismissable = config.isDismissable;
      }
    },
    setMessage: function(textMessage) {
      this.message = textMessage;
    },
    show: function() {
      this.alert = true;
    },
    hide: function() {
      this.alert = false;
    }
  }
};
</script>
<style scoped>
</style>
