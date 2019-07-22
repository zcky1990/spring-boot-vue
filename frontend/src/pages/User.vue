<template>
    <div class="user-page">
        Success Login {{userData.username}}
    </div>
</template>

<script>
import {AXIOS} from '@/components/http-common'
import {Util} from '@/components/util'

export default {
    name: 'index',
    props: {
        msg: String
    },
    data () {
        return {
            headers : {},
            users : {}
        }
    },
    beforeCreate: function () {
        Util.checkUserSession(this.$session, this.$router);
    },
    created() {
        this.getUsersDetails();
    },
    methods: {
        getRequestHeader : function(){
            this.requestHeader = Util.getHeaders(this.$session);
            return this.requestHeader;
        },
        getUsersDetails : function(){
            let id = this.$session.get('uid');
            let headers =  this.getRequestHeader();
            let self = this;
            AXIOS.get('/users/get_user_detail/'+id, {headers} )
            .then(response => {
                if(response.status == 200){
                    let responseData = response.data
                    self.userData = responseData.response;
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
               set: function(header){
                   this.headers = header;
                }
        },
        userData : {
            get: function(){
                return this.users;
            },
            set: function (userdata){
                this.users = userdata;
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
