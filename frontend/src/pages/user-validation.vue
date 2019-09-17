<template>
    <validation-layout :id-user="userId"></validation-layout>
</template>

<script>
import Layout from '@/layout/user-validation-layout'

export default {
  name: "userValidation",
  components: {
    "validation-layout" : Layout
  },
  data() {
    return {
      userId: ""
    };
  },
  created() {
    this.userId = this.getId()
  },
  methods: {
    getId: function() {
      return this.$route.params.id
    },
  }
};
</script>