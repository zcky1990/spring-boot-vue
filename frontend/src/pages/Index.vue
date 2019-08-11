    <template>
        <index-layout-page></index-layout-page>
    </template>

    <script>
    import Layout from '@/layout/IndexLayout'

    export default {
    name: 'index',
    components: {
        'index-layout-page' : Layout
        },
    }
    </script>

    <!-- Add "scoped" attribute to limit CSS to this component only -->
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
