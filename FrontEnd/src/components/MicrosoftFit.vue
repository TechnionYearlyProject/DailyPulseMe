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
    let route = this.$route.fullPath;
    var x = route.split('=')[1];
    this.accessToken = decodeURIComponent(x);
    this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getOutlookToken',{
        "first": this.accessToken,
        "second": ''
      }
       ,{headers: {'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('token'),}
      }).then((res) =>{
        
        console.log(res);
      },(err) =>{
        close();  
        console.log(err);
      })
    // this.getTokens();

  }
}
</script>
