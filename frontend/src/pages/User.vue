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
            axioHeader : {}
        }
    },
    beforeCreate: function () {
        console.log("this.$session.exists()", this.$session.exists())
        if (!this.$session.exists()) {
            this.$router.push('/')
        }
        
    },
    created() {
        /*
        const headers = {
        'Content-Type': 'application/json',
        'Authotization': 'Bearer '+ this.$session.get('jwt'),
        'x-uid' : this.$session.get('uid')
        };
        AXIOS.get('/users/find_all_users', {headers})
          .then(response => {
              if(response.status == 200){
                let responseData = response.data
                console.log(responseData);
                }
            })
          .catch(e => {
            self.errors.push(e)
          }) 
          */
    },
    methods: {
        setHeader: function (){

        },
        logout: function () {
            this.$session.destroy()
            this.$router.push('/')
        }
    },
    computed: {
        requestHeader: {
               get : function(){
                   return this.axioHeader;
               },
               set: function(){
                   this.axioHeader['Authotization'] = 'Bearer '+ this.$session.get('jwt')
                   this.axioHeader['x-uid'] = this.$session.get('uid')
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
