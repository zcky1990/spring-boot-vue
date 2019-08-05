    <template>
  <div class="article-detail-page">
      <article-layout :article="id"></article-layout>
  </div>
</template>

<script>
import ArticleLayout from "@/layout/ArticleLayout";

export default {
  name: "article-detail-page",
  props:{
    title:''
  },
  data() {
    return {
      id:''
    };
  },
  components: {
   'article-layout' : ArticleLayout,
  },
  created(){
    console.log(this.$route.params.title)
    this.id = this.$route.params.title
  }
};
</script>

 
<style scoped>
</style>
