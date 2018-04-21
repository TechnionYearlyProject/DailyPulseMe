<template>
</template>
<script>

export default {
  name: 'MicrosoftFit',
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
      // this.$http.post('http://localhost:8081/users/updataGoogleFitToken',{
      //         "first": this.accessToken.toString() ,
      //         "second": this.refreshToken.toString()
      //       }
      //        ,{headers: {'Content-Type': 'application/json',
      //         'Authorization': localStorage.getItem('token'),}
      //       }).then((res) =>{
      //         console.log(res);
      //         console.log(successsssss);
      //       },(err) =>{
      //         console.log(err);
      //         console.log(faillllll);
      //       })
      //       console.log('hi')
      //           window.location.href = "http://localhost:8080/config/";
      //           // router.replace('/');
      // // location.assign('http://localhost:8080/config/');
    }
    let route = this.$route.fullPath;
    var x = route.split('=')[1];
    this.accessCode = decodeURIComponent(x);
    this.accessCode = this.accessCode.split("&")[0]
    this.getTokens();
  },
   methods : {
     getTokens() {
       let url = 'http://cors.io/?https://login.microsoftonline.com/common/oauth2/token/'
       this.$http.post(url, {
        client_id: 'c8b9175b-e478-4c52-b8e6-178246c03006',
        response_type:'token',
        code: this.accessCode,
        redirect_uri: 'http://localhost:8080/token1',
        grant_type: 'authorization_code',
        resource: '00000003-0000-0000-c000-000000000000',
        client_secret: 'xlgsIAORXV4_njfK0325((^'},{headers: {
    'Content-Type': 'application/json;',
    // 'Access-Control-Allow-Origin': '*'  
  }}).then((res)=>{
        console.log("hi")
        if(res.ok != 'false'){
       // this.$http.post('http://localhost:8081/users/updateGoogleFitToken',{
       //        "first": res.body.access_token.toString() ,
       //        "second": res.body.refresh_token.toString()
       //      }
       //       ,{headers: {'Content-Type': 'application/json',
       //        'Authorization': localStorage.getItem('token'),}
       //      }).then((res) =>{
       //        this.$router.push('/');
       //        console.log(res);
       //      },(err) =>{
       //        console.log(err);
       //      })
          }
       // this.accessToken = res.body.access_token;
       // this.refreshToken = res.body.refresh_token;
       this.succesful = true;
            },(err) =>{
       console.log("no");
     });
        // console.log(this.succesful)
     }
  }
}
</script>
