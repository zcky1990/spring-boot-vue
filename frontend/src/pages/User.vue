    <template>
    <div class="hello">
        Success Login
    </div>
    </template>

    <script>
import {AXIOS} from '@/components/http-common'

export default {
    name: 'index',
    props: {
        msg: String
    },
    data () {
        return {
            headers : {}
        }
    },
    beforeCreate: function () {
        console.log("this.$session.exists()", this.$session.exists())
        if (!this.$session.exists()) {
            this.$router.push('/admin')
        }
        
    },
    created() {
        this.getUsersDetails();
    },
    methods: {
        logout: function () {
            this.$session.destroy()
            this.$router.push('/')
        },
        getRequestHeader : function(){
            this.requestHeader = "";
            return this.requestHeader;
        },
        getUsersDetails : function(){
            let id = this.$session.get('uid');
            let headers =  this.getRequestHeader();

            AXIOS.get('/users/get_user_detail/'+id, {headers} )
            .then(response => {
                if(response.status == 200){
                    let responseData = response.data
                    console.log(responseData);
                    }
                })
            .catch(e => {
                self.errors.push(e)
            }) 
            }
    },
    computed: {
        requestHeader: {
               get : function(){
                   return this.headers;
               },
               set: function(){
                   this.headers['Authorization'] = 'Bearer '+ this.$session.get('jwt')
                   this.headers['x-uid'] = this.$session.get('uid')
                }
        }
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
