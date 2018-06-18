<template>
</template>
<script>
export default {
  name: 'GoogleFit',
  data(){
    return{
      accessToken: '',
      refreshToken: '',
      accessCode: '',
      succesful: false
    }
  },created: function () {
    // console.log(this.succesful)
    if(this.succesful){
      console.log('second time ?')
    }else{
    let route = this.$route.fullPath;
    var x = route.split('=')[1];
    this.accessCode = decodeURIComponent(x);
    console.log(this.accessCode)
    this.getTokens();
}
  },
   methods : {
     getTokens() {
       let url = 'https://www.googleapis.com/oauth2/v4/token'
       this.$http.post(url, {code: this.accessCode, client_id: '895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com', client_secret: 'FGLsX3PBtIHEypj88z7UkI6R', redirect_uri: 'https://dailypulse.azurewebsites.net/token', grant_type: 'authorization_code'}).then((res)=>{
          console.log(res)
        if(res.ok != 'false'){
          console.log(res.body)
       this.$http.post('https://webapp-180506135919.azurewebsites.net/users/updateTokens',{
              "first": res.body.access_token.toString() ,
              "second": res.body.refresh_token.toString()
            }
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) =>{
              this.$router.push('/');
              // close();
              console.log(res);
            },(err) =>{
              console.log(err);
            })
          }
          else{
          	console.log('hi')
          	// close();
          }
                 this.succesful = true;
            },(err) =>{
       // close();
     });
        // console.log(this.succesful)
     }
  }
}
</script>
