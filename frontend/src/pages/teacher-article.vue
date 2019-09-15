    <template>
        <teacher-article-layout-page></teacher-article-layout-page>
    </template>

    <script>
    import ArticleLayout from '@/layout/teacher-article-layout'

    export default {
    name: 'teacher-login',
    components: {
        'teacher-article-layout-page' : ArticleLayout
        },
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
