    <template>
        <login-layout-page></login-layout-page>
    </template>

    <script>
    import LoginLayout from '@/layout/LoginLayout'

    export default {
    name: 'login',
    components: {
        'login-layout-page' : LoginLayout
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
