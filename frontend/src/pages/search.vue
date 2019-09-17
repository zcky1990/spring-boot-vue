<template>
    <search-layout-page :query="query"></search-layout-page>
</template>

<script>
import Layout from '@/layout/search-layout'

export default {
        name: 'search',
        data() {
            return {
                query:""
            };
        },
        components: {
            'search-layout-page' : Layout
        },
        created(){
            this.query = this.$route.params.query;
        }
}
</script>