<template>
  <div v-cloak>
  <div  class="hello" style="margin-top:10px;" >
      <b-btn v-b-toggle.collapse1 variant="primary" v-b-popover.hover="'In order to start using DailyPulseYou need to configure your fitness tracker and your google account'">Configure your tracker!</b-btn>
   <b-collapse id="collapse1" style="  margin-top:10px; ">
       <b-btn v-b-toggle.collapse1 variant="success" v-on:click="microsoftfit">Microsoft</b-btn>
    <b-btn v-b-toggle.collapse1 variant="warning" v-on:click="googlefit" >Google</b-btn>
  </b-collapse>
</div>
</div>
</template>

<script>
export default {
  name: 'Connect',
  data(){
    return{
      islogin: false,
      isgoogle: true
    }
  },created: function () {
    // this.checkGoogleToken();
    // this.checkToken();
  },
   methods : {
   checkToken(){
    this.$http.get('http://localhost:8081/users/authenticateToken',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}    
         }).then((res) => {
          this.islogin = true
            }, (err) => {
          this.islogin = false
          })
    },checkGoogleToken(){
      this.$http.get('http://localhost:8081/users/verifyAccessToken',{headers: {'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')}    
               }).then((res) => {
                console.log(res)
                this.isgoogle = true
                  }, (err) => {
                this.isgoogle = false
                })
               console.log(this.isgoogle)
    },

    toShow(){
     return this.islogin
    },
     googlefit() {
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=http://localhost:8080/token&response_type=code&client_id=187665345194-0d324v8gel15pj9jh9fecmqknmk4k59k.apps.googleusercontent.com'
      location.assign(url);
    },
    microsoftfit() {
      // let url = 'https://login.microsoftonline.com/common/oauth2/authorize?client_id=c8b9175b-e478-4c52-b8e6-178246c03006&response_type=code&redirect_uri=http://localhost:8080/token1&state=12345&scope=Calendars.Read Calendars.ReadWrite'
      let url2 = 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=c8b9175b-e478-4c52-b8e6-178246c03006&response_type=token&redirect_uri=http://localhost:8080/token1&scope=Calendars.Read Calendars.ReadWrite&response_mode=fragment&state=12345&nonce=678910'
      location.assign(url2);
    }
  }
}
</script>
<style type="text/css">[v-cloak] { display:none; }</style>