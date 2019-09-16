<template>
<v-text-field
    v-model="query"
    v-on:keyup.enter="search"
    outlined
    label="Search Article Keyword"
    append-icon="search"
></v-text-field>
</template>

<script>
export default {
  name: "search-article",
  data() {
    return {
      query: ""
    };
  },
  methods: {
    search(){
      this.$router.push("/search/"+this.query);
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
