<template>
  <div class="validation-page-container">
    <user-validation-component :id-user="userId"></user-validation-component>
  </div>
</template>
<script>
import ValidationComponents from "@/components/widget/user-validation-components";

export default {
  name: "user-validation-page-layout",
  data() {
    return {
      userId: ""
    };
  },
  components: {
    "user-validation-component": ValidationComponents
  },
  created() {
    this.userId = this.getId();
  },
  methods: {
    getId: function() {
      return this.$route.params.id;
    }
  }
};
</script>

<style scoped>
</style>
