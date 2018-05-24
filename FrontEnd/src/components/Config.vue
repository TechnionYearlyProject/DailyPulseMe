<template>
  <b-container style="width:70%;">
    <b-row>
      <b-col>
  <h1 style="font-family: Open Sans; margin-top:10px;">Settings</h1>
  </b-col>
  </b-row>
  <b-row class="text-center" style=" border-radius: 5px 7px;">
    <b-col class="text-center" col lg="5">
           <b-card style="background-color: rgba(255, 255, 255, 0.7); height:265px;" text-variant="dark" title="Enter Your Desired Password And Confirm It" >
             <form class="form-signin" @submit.prevent="changePass">
             <p v-if="toggleMsg" style="color:red"> {{msg}}</p>
             <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
             <p></p>
             <input v-model="rePassword" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
             <p></p>
             <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Change Password</button>
           </form>
           </b-card>
    </b-col>
    <b-col col lg="2"></b-col>
    <b-col class="text-center" col lg="5">
      <b-card style="background-color: rgba(255, 255, 255, 0.7); height:265px;" text-variant="dark" title="Change or Add Accounts" >
         <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="google" >Google</b-btn>
            <br><br>
     <!--  <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="microsoft">Microsoft</b-btn>  
      <br><br> -->
              <b-btn v-b-toggle.collapse1 variant="primary" >Fitbit</b-btn>
      </b-card>
           
   
    </b-col>
  </b-row>
<!-- <div class="Change Password" style="margin-top:10px"> -->
   
</b-container> 
</template>
<script>
import Connect from './Connect'
export default {
  name: 'Config',
  components:{ Connect },
  data() {
    return {
      password : '',
      rePassword : '',
      toggleMsg : false,
      msg : '',
      toshow: false
    }
  },
  
    methods: {
    changePass() {
      this.toggleMsg = true
      if (this.password.localeCompare(this.rePassword) != 0){
        this.msg = 'Passwords Must Match'
      } else if (this.password.length < 6){
        this.msg = 'Password Length Must Be At Least 6'
      } else {
        let url = 'http://localhost:8081/users/changepassword'
        this.$http.post(url,password, {headers: {'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')}}).then((res) => {
          this.msg = 'Password Changed Succefully'
        }, (err) => {
          console.log('Error')
        })
      }
    },
    microsoft(){
         let url = 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=c8b9175b-e478-4c52-b8e6-178246c03006&response_type=token&redirect_uri=http://localhost:8080/token1&scope=Calendars.Read Calendars.ReadWrite&response_mode=fragment&state=12345&nonce=678910'
        var win = window.open(url, "windowname1", 'width=800, height=600'); 
      
       },google(){
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=http://localhost:8080/token&response_type=code&client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com'
      var win = window.open(url, "windowname1", 'width=800, height=600'); 
       }  
  }
}

</script>
