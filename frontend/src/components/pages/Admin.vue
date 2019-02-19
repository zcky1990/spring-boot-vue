    <template>
    <div class="admin">
        <nav-bar> </nav-bar>
        <login-form></login-form>
    <div class="container">
    <div class="notification">
        <comment v-for="usercomment in commentList" 
        v-bind:key="usercomment.commentId"
        :userComment="usercomment">
        </comment>
    </div>
    </div>


    </div>
    </template>

    <script>
    import {AXIOS} from './../http-common'
    import Navbar from '../widget/Navbar'
    import Loginform from '../widget/Loginform'
    import Comment from '../widget/Comment'

    export default   {
        name: 'admin',
        props: {
            msg: String,
            commentResponses:[]
        },
        components: {
                    'nav-bar': Navbar,
                    'login-form' : Loginform,
                    'comment':Comment
        },
        computed:{
            commentList: function(){
                return this.commentResponses;
            }
        },
        created() {
            var self= this;
            AXIOS.get('/get_comments')
            .then(response => {
                    var response = JSON.parse(response.data.response);
                    self.commentResponses = response.commentList;
                })
            .catch(e => {
                self.errors.push(e)
            })
        
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
