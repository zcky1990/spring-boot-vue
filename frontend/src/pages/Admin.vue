    <template>
        <div class="admin-page">
            <nav-menu></nav-menu>
            <login-form></login-form>
            <editor-component></editor-component>
        </div>
    </template>

    <script>
    //import {AXIOS} from '@/components/http-common'
    import Navbar from '@/components/widget/Navbar'
    import Loginform from '@/components/widget/Loginform'
    import Editor from '@/components/widget/Editor'

    export default   {
        name: 'admin',
        data(){
            return {

            }
        },
        components: {
                    'nav-menu' : Navbar,
                    'login-form' : Loginform,
                    'editor-component' : Editor
        }
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
