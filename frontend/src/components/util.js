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
  }
}
