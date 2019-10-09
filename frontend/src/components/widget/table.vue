<template>
  <v-container>
    <v-card>
      <v-card-title>{{title}}</v-card-title>
      <v-card-title>
        <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
        <v-spacer></v-spacer>
        <v-card-text style="position: relative">
          <v-btn absolute dark fab top right color="rgb(0, 209, 178)" @click="addData">
            <v-icon>add</v-icon>
          </v-btn>
        </v-card-text>
      </v-card-title>

      <v-data-table
        class="table-container"
        :headers="tableHeaderList"
        :items="dataList"
        :search="search"
      >
        <template v-slot:items="props">
          <tr>
            <td v-for="value in props.item" :key="value" class="text-xs-right">{{ value }}</td>
            <td>
              <v-layout align-center justify-space-around>
                <v-icon @click="editData(props.item)">fas fa-edit</v-icon>
                <v-icon @click="deleteData(props.item)">fas fa-edit</v-icon>
              </v-layout>
            </td>
          </tr>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import { AXIOS } from "./../http-common";

export default {
  name: "table-component",
  props: [
    "title",
    "getUrl",
    "updateUrl",
    "delUrl",
    "searchUrl",
    "tableHeaderList",
    "dataList"
  ],
  components: {},
  data() {
    return {
      search: "",
      selected: [],
      singleSelect: true
    };
  },
  methods: {
    editData(a) {
      alert("Alert! \n" + a.name);
    },
    addData() {},
    deleteData(a) {
      alert("Alert! \n" + a.name);
    },
    showAlert(a) {
      if (event.target.classList.contains("btn__content")) return;
      alert("Alert! \n" + a.name);
    }
  },
  computed: {}
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.table-container {
  padding-left: 16px;
  padding-right: 16px;
}
</style>
