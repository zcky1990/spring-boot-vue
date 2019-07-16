    <template>
        <div class="login">
            <login-form></login-form>
        </div>
    </template>

    <script>
    //import {AXIOS} from '@/components/http-common'
    import Loginform from '@/components/widget/Loginform'

    export default   {
        name: 'login',
        data(){
            return {
                commentList: []
            }
        },
        components: {
                    'login-form' : Loginform
        }, 
        created() {
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
