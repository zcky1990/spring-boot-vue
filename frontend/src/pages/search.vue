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
    <style scoped>
    h3 {
        margin: 40px 0 0;
    }
    ul {
        list-style-type: none;
        padding: 0;
    }
    li {
        display: inline-block;
        margin: 0 10px;
    }
    a {
        color: #42b983;
    }
    </style>
