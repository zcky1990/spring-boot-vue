<template>
    <v-snackbar
      v-model="snackbar"
      :color="colorType"
      :multi-line="multiLineMode"
      :timeout="6000"
      :vertical="verticalMode"
      :bottom="postionBottom"
      :left="postionLeft"
      :right="postionRight"
      :top="postionTop"
    >
      {{textMessage}}
      <v-btn
        :color = "closebtnColor"
        flat
        @click="snackbar = false"
      >
        Close
      </v-btn>
    </v-snackbar>
</template>
<script>
  //color config : ['success', 'info', 'error', 'cyan darken-2']
    export default {
    name: 'snack-bar',
    data(){
      return {
        snackbar: false,
        closebtnColor: 'pink',
        textMessage:'',
        colorType:'error',
        multiLineMode: false,
        timeOut:6000,
        verticalMode:false,
        postionBottom:false,
        postionLeft:false,
        postionRight:false,
        postionTop:false
      }
    },
     methods: {
       setConfig : function (config){
         if(config['timeout']!= undefined){
           this.timeOut = config.timeout;
         }
         if(config['multi_line']!= undefined){
           this.multiLineMode = config.multi_line;
         }
         if(config['color']!= undefined){
           this.colorType = config.color;
         }
         if(config['vertical_mode']!= undefined){
           this.verticalMode = config.vertical_mode;
         }
         if(config['bottom']!= undefined){
           this.postionBottom = config.bottom;
         }
         if(config['left']!= undefined){
           this.postionLeft = config.left;
         }
         if(config['right']!= undefined){
           this.postionRight = config.right;
         }
         if(config['top']!= undefined){
           this.postionTop = config.top;
         }
         if(config['close_btn_color'] != undefined){
           this.closeBtnColor = config.close_btn_color
         }
       },
       show:function(message){
         this.textMessage = message;
         this.snackbar = true;
       },
       hide: function(){
         this.snackbar = false;
       }
     }
    }
</script>
<style scoped>
</style>
