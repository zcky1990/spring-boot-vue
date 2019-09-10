export const Util = {
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

  checkAdminSession : function () {
/*if (!this.$session.exists()) {
            this.$router.push('/')
        } else {
            let expDate = new Date(this.$session.get('exp_date'))
            let currDate = new Date();
            if(currDate > expDate){
                this.$session.destroy();
                this.$router.push('/')
            }
        }*/
  } ,

  getHeaders: function(session){
    let headers = {};
    headers['Authorization'] = 'Bearer '+ session.get('jwt')
    headers['x-uid'] = session.get('uid')
    return headers
  },

  getUserId: function(session){
    return session.get('uid')
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
  }
  
}
