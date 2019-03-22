    <template>
    <div class="admin">
        <nav-bar></nav-bar>
        
       	<article-home></article-home> 
       	
        <article-comp></article-comp>
        <!--<login-form></login-form> -->
        <div class="container">
            <div class="notification">
                <comment v-for="usercomment in commentList" 
                v-bind:key="usercomment.commentId"
                :userComment="usercomment">
                </comment>
            </div>
        </div>
    <article-editor></article-editor>
       
    </div>
    </template>

    <script>
    import {AXIOS} from './../http-common'
    import Navbar from '../widget/Navbar'
    import Loginform from '../widget/Loginform'
    import CommentWidget from '../widget/Comment'
    import ArticleWidget from '../widget/Article'
    import EditorArticleWidget from '../widget/ArticleEditor'
    import ArticleHome from '../widget/ArticleHome'

    export default   {
        name: 'admin',
        //use data to mute
        data(){
            return {
                commentList: []
            }
        },
        components: {
                    'nav-bar': Navbar,
                    'login-form' : Loginform,
                    'comment': CommentWidget,
                    'article-comp' : ArticleWidget,
                    
                    'article-editor':EditorArticleWidget,
                    'article-home' : ArticleHome
        }, 
        created() {
            var self= this;
            AXIOS.get('/get_comments')
            .then(response => {
                    var responseJson = JSON.parse(response.data.response);
                    self.commentList = responseJson.commentList;
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
