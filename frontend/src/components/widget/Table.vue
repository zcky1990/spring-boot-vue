<template>
  <v-card>
    <v-card-title>
      {{title}}
      <v-spacer></v-spacer>
      <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
    </v-card-title>

    <v-data-table :headers="tableHeaderList" :items="dataList" :search="search">
      <template v-slot:items="props">
        <tr>
        <td v-for="value in props.item" :key="value" class="text-xs-right">{{ value }}</td>
        <td>
          <v-btn class="ma-2" tile outlined color="success" @click="showAlert(props.item)">
            <v-icon left>mdi-pencil</v-icon>Edit
          </v-btn>
        </td>
        </tr>
      </template>
    </v-data-table>
  </v-card>
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
  created() {
    console.log(this.dataList);
  },
  methods: {
    callRestService(model, url) {
      let self = this;
      AXIOS.post(url, model)
        .then(response => {})
        .catch(e => {
          self.errors.push(e);
        });
    },
    showAlert(a){
      if (event.target.classList.contains('btn__content')) return;
      alert('Alert! \n' + a.name);
    }
  },
  computed: {}
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
