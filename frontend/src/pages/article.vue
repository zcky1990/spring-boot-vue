    <template>
  <div class="article-detail-page">
    <article-layout :slug="slug"></article-layout>
  </div>
</template>

<script>
import ArticleLayout from "@/layout/article-layout";

export default {
  name: "article-detail-page",
  props: {
    title: ""
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

 
<style scoped>
</style>
