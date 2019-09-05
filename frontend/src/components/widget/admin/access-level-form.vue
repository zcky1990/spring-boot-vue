<template>
<v-container>
            <v-card>
              <div class="btn-add">
                  <v-btn absolute dark fab top
                    right color="rgb(0, 209, 178)"
                    @click="addData"
                    style="position: relative; float:right;"
                  >
                    <v-icon>add</v-icon>
                  </v-btn>
              </div>
              <v-card-title class="table-title">
                {{title}}
              </v-card-title>
              <v-card-title>
                <v-spacer></v-spacer>
                  <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
              </v-card-title>
              <v-data-table class="table-container" :headers="tableHeaderList" :items="dataTableList" :search="search">
                <template v-slot:items="props">
                  <tr>
                    <td class="text-xs-left" hidden>{{ props.item.id }}</td>
                    <td class="text-xs-left">{{ props.item.level }}</td>
                    <td class="text-xs-left">{{ props.item.description }}</td>
                  <td>
                    <v-layout align-center justify-space-around>
                    <v-icon @click="editListener(props.item.id)">fas fa-edit</v-icon>
                    <v-icon @click="deleteListener(props.item.id)">fas fa-trash</v-icon>
                    </v-layout>
                  </td>
                  </tr>
                </template>
              </v-data-table>
            </v-card>
        <v-dialog
          v-model="dialog"
          persistent
          width="600"
        >
        <v-card>
          <v-container>
        <div ref="formContainer" class="form-container ">
          <div class="title">{{titleForm}}</div>
          <v-form ref="form" v-model="valid" width="300">
            <v-text-field
              v-model="data.id"
              label="Id"
              required
              outline 
              flat
              color="rgb(0, 209, 178)"
              class="hidden"
            ></v-text-field>
            <v-text-field
              v-model="data.level"
              :rules="Rules1"
              label="Level"
              required
              outline 
              type="number"
              flat
              color="rgb(0, 209, 178)"
            ></v-text-field>
            <v-text-field
              v-model="data.description"
              :rules="Rules2"
              label="Desrciption"
              required
              outline 
              flat
              color="rgb(0, 209, 178)"
            ></v-text-field>
            <v-flex align-center justify-center>
                <div class="form-bttm-container">
                  <div class="btn-container">
                  <v-btn class="white--text desc" color="#00d1b2" @click="dialog=!dialog">Cancel</v-btn>
                  <v-btn class="white--text desc" color="#00d1b2" @click="submitForm">Submit</v-btn>
                  </div>
                </div>
            </v-flex>
          </v-form>
        </div>
          </v-container>
        </v-card>
        </v-dialog>
  </v-container>
</template>
<script>

import { EventBus } from './../../../EventBus.js';

export default {
  name: "access-level-form",
  data() {
    return {
      valid: false,
      page : 0,
      dialog: false,
      mode: 'new',
      title: "Table Access Level List",
      search: "",
      urlData : {
        createUrl: "/access_level/create",
        editUrl: "/access_level/edit",
        getUrl: "/access_level/",
        deleteUrl: "/access_level/delete/",
        getListUrl: "/access_level/get_access_level_list",
      },
      isFormShow: true,
      data: {
        id:"",
        level: "",
        description: ""
      },
      tableHeaderList:[
        { text: "Level", value: "level" },
        { text: "Description", value: "description" },
        { text: "Action", value: "action" }
      ],
      dataTableList:[],
      status:[true,false],
      Rules1: [
        v => !!v || "Level is required",
      ],
      Rules2: [
        v => !!v || "Description is required",
      ]
    };
  },
  created(){
    this.getDataList();
  },
  methods: {
    submitForm: function() {
      if (this.$refs.form.validate()) {
        if(this.mode == 'new'){
            this.createData(this.data);
        }else{
            this.updateData(this.data);
        }
      }
    },
    resetData: function(){
      this.data = {};
    },
    createData: function(model) {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.post(this.urlData.createUrl, model, headers,
      function(response){
        if (response.status == 200) {
           self.dataTableList.push(response.data.response)
           self.resetData();
           self.dialog = false;
          }
      },
      function(e){
          self.setMessage(e,1)
      });
    },
    getDataList: function(){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get(this.urlData.getListUrl+"?page="+this.page, headers,
      function(response){
        if(response.status == 200){
          self.dataTableList = response.data.response
          self.page++;
        }
      },
      function(e){
        self.setMessage(e,1)
      })
    },
    deleteData: function(id){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.delete(this.urlData.deleteUrl+id, headers,
      function(response){
        if(response.status == 200){
          for( let i = 0; i < self.dataTableList.length; i++){ 
              if ( self.dataTableList[i].id == id) {
                self.dataTableList.splice(i, 1); 
                break;
              }
          }
        }
      },
      function(e){
        self.setMessage(e,1)
      })
    },
    getData: function(id){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get(this.urlData.getUrl+id, headers,
      function(response){
        if(response.status == 200){
          self.dialog = true;
          self.data = response.data.response
          self.mode = 'edit'
        }
      },
      function(e){
        self.setMessage(e,1)
      })
    },
    updateData: function(model) {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.put(this.urlData.editUrl, model, headers,
      function(response){
        if (response.status == 200) {
           self.resetData();
           self.dialog = false;
          }
      },
      function(e){
        self.setMessage(e,1)
      });
    },
    addData: function(){
      this.dialog = true;
      this.mode = 'new'
      this.resetData();
    },
    editListener: function(id){
      this.getData(id);
    },
    deleteListener: function(id){
      this.deleteData(id);
    },
    setMessage: function(message, type){
                let data={}
                data.message = message
                data.type = type
                    EventBus.$emit('SNACKBAR_TRIGGERED', data)
    }
  },
  computed:{
    titleForm:function(){
      if(this.mode == "new"){
        return "Add new Role";
      }else{
        return "Edit Role"
      }
    }
  }
};
</script>
<style scoped>
.table-title {
  font-size: 2em;
}
.title{
  padding-bottom: 0.5em;
}
.roles-form-container {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
}
.hidden {
    display: none;
}
.btn-container {
    position: relative;
    margin: 0 auto;
    text-align: end;
}
</style>
