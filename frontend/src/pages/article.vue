<template>
    <article-layout :slug="slug"></article-layout>
</template>

<script>
import ArticleLayout from "@/layout/article-layout";

export default {
  name: "article-detail-page",
  props: {
    title: String
  },
  data() {
    return {
      slug: ""
    };
  },
  components: {
    "article-layout": ArticleLayout
  },
  created() {
    this.slug = this.$route.params.slug;
  }
};
</script>