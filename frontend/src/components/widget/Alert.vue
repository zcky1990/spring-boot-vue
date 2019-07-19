<template>
        <div>
              <v-alert
                v-model="alert"
                :value="isShow"
                :type="type"
                :outline="outline"
                :dismissible="isDismissable"
              >
                {{message}}
              </v-alert>
        </div>
</template>
<script>
  //type alert : 'success', 'info', 'error', 'warning'
    export default {
    name: 'alert-component',
    data(){
      return {
        alert: true,
        type: "error",
        message:'',
        closeBtnColor :'primary',
        outline: false,
        isShow: true,
        isDismissable : true
      }
    },
     methods: {
       setConfig : function (config){
         if(config['type']!= undefined){
           this.type = config.type;
         }
         if(config['closeBtnColor']!= undefined){
           this.closeBtnColor = config.closeBtnColor;
         }
         if(config['outline']!= undefined){
           this.outline = config.outline;
         }
         if(config['isShow'] != undefined){
           this.isShow = config.isShow
         }
         if(config['isDismissable'] != undefined){
           this.isDismissable = config.isDismissable
         }
       },
       setMessage: function(textMessage){
         this.message = textMessage
       },
       show:function(){
         this.isShow = true;
       },
       hide: function(){
         this.isShow = false;
       }
     }
    }
</script>
<style scoped>
</style>
