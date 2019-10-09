<template>
  <authors-profile-layout-page></authors-profile-layout-page>
</template>

<script>
import ProfileLayout from "@/layout/authors-profile-layout";

export default {
  name: "authors-login",
  components: {
    "authors-profile-layout-page": ProfileLayout
  }
};
</script>