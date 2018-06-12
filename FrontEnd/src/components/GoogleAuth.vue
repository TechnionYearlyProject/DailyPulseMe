<template>
</template>
<script>
export default {
  name: 'GoogleAuth',
  data(){
    return{
      jwttoken: '',
      accessCode: '',
      succesful: false
    }
  },created: function () {
    // console.log(this.succesful)
    if(this.succesful){
      // this.$router.push('/') 
    }else{
    let route = this.$route.fullPath;
    var x = route.split('=')[1];
    this.accessCode = decodeURIComponent(x);
    console.log(this.accessCode)
    this.sendAuth();

}
  },
   methods : {
    sendAuth(){
             this.$http.post('http://localhost:8081/users/sign-up-google', {authToken:this.accessCode}).then((res) => {
              this.jwttoken = 'Bearer ' + res.body
              this.succesful = true
              console.log(res)
            localStorage.setItem('token', this.jwttoken);
            location.reload();
            
        }, (err) => {
          console.log(res.body)
        });
    }
  }
}
</script>
