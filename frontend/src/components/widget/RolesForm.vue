<template>
<v-container>
    <v-layout class="roles-form-container" align-center justify-center flex fill-height>
      <div class="table-form-container">
        <v-container>
  <v-card>
    <v-card-title >
      {{title}}
    </v-card-title>
    <v-card-title>
      <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
      <v-spacer></v-spacer>
      <v-card-text style="position: relative">
                <v-btn
                  absolute
                  dark
                  fab
                  top
                  right
                  color="rgb(0, 209, 178)"
                  @click="addData"
                >
                  <v-icon>add</v-icon>
                </v-btn>
        </v-card-text>
    </v-card-title>
    
    <v-data-table class="table-container" :headers="tableHeaderList" :items="dataTableList" :search="search">
      <template v-slot:items="props">
        <tr>
          <td class="text-xs-left">{{ props.item.id }}</td>
          <td class="text-xs-left">{{ props.item.type }}</td>
          <td class="text-xs-left">{{ props.item.name }}</td>
          <td class="text-xs-left">{{ props.item.status }}</td>
          <td class="text-xs-left">{{ props.item.created_date }}</td>
        <td>
          <v-layout align-center justify-space-around>
          <v-icon @click="editData(props.item.id)">fas fa-edit</v-icon>
          <v-icon @click="deleteData(props.item.id)">fas fa-edit</v-icon>
          </v-layout>
        </td>
        </tr>
      </template>
    </v-data-table>
    
  </v-card>
</v-container>
      </div>
      <div ref="formContainer" class="form-container hidden">
        <div class="title">{{formTitle}}</div>
        
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
            v-model="data.type"
            :rules="roleTypeRules"
            label="Type"
            required
            outline 
            flat
            color="rgb(0, 209, 178)"
          ></v-text-field>

          <v-text-field
            v-model="data.name"
            :rules="roleNameRules"
            label="Name"
            required
            outline 
            flat
            color="rgb(0, 209, 178)"
          ></v-text-field>

          <v-select v-model="data.status" :items="status" label="Status"></v-select>

          <v-flex align-center justify-center>
              <div class="submit-btn-container">
               <v-btn class="white--text desc" color="#00d1b2" @click="submitForm">Submit</v-btn>
              </div>
          </v-flex>
        </v-form>
        </div>
    </v-layout>
  </v-container>
</template>

<script>

export default {
  name: "Roles-form",
  data() {
    return {
      valid: false,
      page : 0,
      mode: 'new',
      title: "Roles",
      search: "",
      urlData : {
        createRoleUrl: "/roles/create",
        editRoleUrl: "/roles/edit",
        getRoleUrl: "/roles/",
        deleteRoleUrl: "/roles/delete/",
        getRoleListUrl: "/roles/get_roles_list"
      },
      isFormShow: true,
      formTitle: "Add New Role",
      data: {
        id:"",
        type: "",
        name: "",
        status: false,
      },
      tableHeaderList:[
        { text: "Id", value: "id" },
        { text: "Type", value: "type" },
        { text: "Name", value: "name" },
        { text: "Status", value: "status" },
        { text: "Created Date", value: "created_date" },
        { text: "Action", value: "action" }
      ],
      dataTableList:[],
      status:[true,false],
      roleTypeRules: [
        v => !!v || "Role type is required",
      ],
      roleNameRules: [
        v => !!v || "Role Name is required",
      ]
    };
  },
  created(){
    this.getRoleList();
  },
  methods: {
    submitForm: function() {
      if (this.$refs.form.validate()) {
        if(this.mode == 'new'){
            this.createRoles(this.data);
        }else{
            this.updateRoles(this.data);
        }
      }
    },
    resetData: function(){
      let data = {};
    },
    createRoles: function(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.post(this.urlData.createRoleUrl, model, headers,
      function(response){
        if (response.status == 200) {
           self.resetData();
          }
      },
      function(e){
          
      });
    },
    getRoleList: function(){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get(this.urlData.getRoleListUrl+"?status=true&page="+this.page, headers,
      function(response){
        if(response.status == 200){
          self.dataTableList = response.data.response
          self.page++;
        }
      },
      function(e){

      })
    },
    deleteRole: function(id){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.delete(this.urlData.deleteRoleUrl+id, headers,
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

      })
    },
    getRole: function(id){
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.get(this.urlData.getRoleUrl+id, headers,
      function(response){
        if(response.status == 200){
          self.showHideForm(true);
          self.data = response.data.response
          self.formTitle = "Edit Role Data"
          self.mode = 'edit'
        }
      },
      function(e){

      })
    },
    updateRoles: function(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"))
      this.put(this.urlData.editRoleUrl, model, headers,
      function(response){
        if (response.status == 200) {
           self.resetData();
          }
      },
      function(e){
          
      });
    },

    showHideForm: function(isShow){
      if (isShow == true){
        this.$refs.formContainer.classList.remove('hidden')
      }else{
        this.$refs.formContainer.classList.add('hidden')
      }
      this.isFormShow = !this.isFormShow
    },
    addData: function(){
      this.showHideForm(this.isFormShow);
      this.formTitle =  "Add New Role";
      this.mode = 'new'
      this.resetData();
    },
    editData: function(id){
      this.getRole(id);
    },
    deleteData: function(id){
      this.deleteRole(id);
    }
  }
};
</script>
<style scoped>
@media only screen and (max-width: 600px) {
  .sign-up-container {
    flex-direction: column !important;
  }
  .title {
  font-size: 1.4rem !important;
  font-weight: 600;
  color: #444f60;
  padding-bottom: 16px;
  }
}
.hidden {
  display: none;
}
.table-form-container {
    flex-grow: 1;
    text-align: center;
    margin-bottom: 16px;
}
.form-container {
    flex-grow: 0;
    width: 320px;
}

.submit-btn-container {
    display: flex;
    flex-direction: row;
    float: right;
}

.link-not-sign-up {
    flex-grow: 1;
    z-index: 1;
}
.sign-up-link {
  text-decoration: none;
}
.title {
  font-size: 1.8rem !important;
  font-weight: 600;
  color: #444f60;
  border-bottom: 2px solid rgba(0,0,0,.54);
  padding-bottom: 7px;
  margin-bottom: 16px;
}
.sub-title {
  font-size: 1.4rem !important;
  color: #444f60;
  text-align: center;
}
.bulma-color {
   color: rgb(0, 209, 178);
}
.submit-btn-container {
    display: flex;
    flex-direction: row;
}
.link-not-sign-up {
    flex-grow: 1;
}
.sign-up-link {
  text-decoration: none;
}
.link-btn {
  color: rgb(0, 209, 178);
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  cursor: pointer;
}
</style>
