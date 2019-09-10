import axios from 'axios'

export default {
  //default install (Vue, options)
  install (Vue) {
      Vue.mixin({
        data: function () {
          return {
            AXIOS:'',
          }
        },
        created() {
            this.AXIOS = axios.create({baseURL: `/api`})
        },
        methods : {
          /* AJAX FUNCTION USING AXIOSS */
          post: function (url , model,  headers, callbackSuccess, callbackFailed){
            this.AXIOS.post(url, model, { headers })
            .then(response => {
              callbackSuccess(response)
            })
            .catch(e => {
              callbackFailed(e);
            });
          },

          put: function (url , model,  headers, callbackSuccess, callbackFailed){
            this.AXIOS.put(url, model, { headers })
            .then(response => {
              callbackSuccess(response)
            })
            .catch(e => {
              callbackFailed(e);
            });
          },

          get: function (url , headers, callbackSuccess, callbackFailed){
            this.AXIOS.get(url, { headers })
            .then(response => {
              callbackSuccess(response)
            })
            .catch(e => {
              callbackFailed(e);
            });
          },

          delete: function (url , headers, callbackSuccess, callbackFailed){
            this.AXIOS.delete(url, { headers })
            .then(response => {
              callbackSuccess(response)
            })
            .catch(e => {
              callbackFailed(e);
            });
          },

        /* SESSION UTIL FUNCTION */
            checkUserSession : function (session, router) {
              if (!session.exists()) {
                router.push('/')
              } else {
                  let expDate = new Date(session.get('exp_date'))
                  let currDate = new Date();
                  if(currDate > expDate){
                    session.destroy();
                    router.push('/')
                  }
                  return true
              }
            },  

            getUsers : function(session){
              return session.get('users')
            },
          
            isLoggin : function(session){
              if (!session.exists()) {
                return false
              } else {
                  let expDate = new Date(session.get('exp_date'))
                  let currDate = new Date();
                  if(currDate > expDate){
                    session.destroy();
                    return false
                  }
                  return true
              }
            },
            
            getUserId: function(session){
              return session.get('uid')
            },
          
            checkAdminSession : function () {
              if (!this.$session.exists()) {
                      this.$router.push('/admin/login')
                  } else {
                      let expDate = new Date(this.$session.get('exp_date'))
                      let currDate = new Date();
                      if(currDate > expDate){
                          this.$session.destroy();
                          this.$router.push('/admin/login')
                      }
                  }
            },
          
            getHeaders: function(session){
              let headers = {};
              headers['Authorization'] = 'Bearer '+ session.get('jwt')
              headers['x-uid'] = session.get('uid')
              return headers
            },
          
            getDefaultHeaders: function(token){
              let headers = {};
              headers['Authorization'] = 'Bearer '+ token
              return headers
            },
          
            getUrlParams: function(){
              let params = window.location.search
              params = params.replace('?','');
              let vars = params.split("&");
              let paramsList = [];
              for(let i = 0 ; i < vars.length; i++){
                let pair = vars[i].split("=");
                let key = decodeURIComponent(pair[0]);
                let value = decodeURIComponent(pair[1]);
                let data = {};
                data[key] = value;
                paramsList.push(data)
              }
              return paramsList;
            },
          
            getMeta: function(metaName) {
              const metas = document.getElementsByTagName('meta');
              for (let i = 0; i < metas.length; i++) {
                if (metas[i].getAttribute('name') === metaName) {
                  return metas[i].getAttribute('content');
                }
              }
              return '';
            },
          }
     })
  }
}