<template>
  <b-container style="width:100%;">
    <b-row>
      <b-col>
  </b-col>
  </b-row>
  <b-row class="text-center" style=" border-radius: 5px 7px;">
    <b-col class="text-center" col lg="4">
           <b-card style="background-color: rgba(255, 255, 255, 0.7); height:380px;" text-variant="dark" title="Enter Your Desired Password And Confirm It" >
             <form class="form-signin" @submit.prevent="changePass">
             <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
             <p></p>
             <input v-model="rePassword" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
             <p></p>
              <p v-if="toggleMsg1" style="color:black"> {{msg1}}</p>
              <p v-if="toggleMsg" style="color:red"> {{msg}}</p>
             <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Change Password</button>
           </form>
           </b-card>
    </b-col>
	<b-col class="text-center" col lg="4">
		<b-card  style="background-color: rgba(255, 255, 255, 0.7); height:380px;" text-variant="dark" title="Stay alerted! Subscribe to our weekly email updates and receive weekly summaries of your activities!">
		<template v-if="subscribed === 0">
			<div style ="width:50%; margin:5px auto;">
				<b-button variant="primary" v-on:click="subscribe" v-b-popover.hover="'Subscribe to our weekly email updates'">Subscribe</b-button>
			</div>
			<p class="text-secondary">You are currently not subscribed. What are you waiting for?</p>
		</template>
		<template v-else-if="subscribed === 1">
			<div style ="width:50%; margin:5px auto;">
				<b-button variant="primary" v-on:click="unsubscribe" v-b-popover.hover="'Unsubscribe from our weekly email updates!'">Unsubscribe</b-button>
			</div>
			<p class="text-secondary">You are currently subscribed. Wise choice.</p>
		</template>
		<template v-else>
		</template>
		</b-card>
    </b-col>
    <b-col class="text-center" col lg="4">
      <b-card style="background-color: rgba(255, 255, 255, 0.7); height:380px;" text-variant="dark" title="Change your calendar account" >
         <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="google" >Google</b-btn>
            <br><br>
      <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="microsoft">Microsoft</b-btn>
      <br><br>
              <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="fitbit">Fitbit</b-btn>
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
      toshow: false,
      msg1: '',
      toggleMsg1: false,
	  subscribed: 2
    }
  },
  created: function () {
	  this.$http.get('https://webapp-180506135919.azurewebsites.net/users/isSubscribed',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}
         }).then((res) => {
		 if(res.body == true){
			this.subscribed = 1;
			} else {
			this.subscribed = 0;
			}
		 })
  },
    methods: {
    changePass() {
      if (this.password.localeCompare(this.rePassword) != 0){
              this.toggleMsg = true
              this.toggleMsg1 = false
        this.msg = 'Passwords Must Match'
      } else if (this.password.length < 6){
              this.toggleMsg = true
              this.toggleMsg1 = false
        this.msg = 'Password Length Must Be At Least 6'
      } else {
        let url = 'https://webapp-180506135919.azurewebsites.net/users/changePassword'
        this.$http.post(url,{"newPassword": this.password}, {headers: {'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')}}).then((res) => {
            if(res.body = true){
                this.toggleMsg = false
              this.toggleMsg1 = true
          this.msg1 = 'Password Changed Succefully'
            }
        }, (err) => {
          console.log(err)
          console.log('Error')
        })
      }
    },
     fitbit(){
          let url = 'https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22CKWG&redirect_uri=http%3A%2F%2Fwww.cs.technion.ac.il%2F&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800'
         var win = window.open(url, "windowname1", 'width=800, height=600');

   },
    microsoft(){
         let url = 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=b08ad020-dc60-41e8-a397-b9a172573bc5&response_type=token&redirect_uri=https://dailypulse.azurewebsites.net/token1&scope=Calendars.Read Calendars.ReadWrite&response_mode=fragment&state=12345&nonce=678910'
        var win = window.open(url, "windowname1", 'width=800, height=600');

       },google(){
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=https://dailypulse.azurewebsites.net/token&response_type=code&client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com'
      var win = window.open(url, "windowname1", 'width=800, height=600');
       },
	subscribe() {
		let url = 'https://webapp-180506135919.azurewebsites.net/users/subscribe';
		this.$http.post(url,{},{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}
         });
		this.subscribed = 1;
	},
	unsubscribe(){
		let r = confirm('Are you sure you want to unsubscribe from our weekly emails?');
		if(r == false) {
			return;
		}
		let url = 'https://webapp-180506135919.azurewebsites.net/users/unsubscribe';
		this.$http.post(url,{},{headers: {'Content-Type': 'application/json',
		'Authorization': localStorage.getItem('token')}});
	  	this.subscribed = 0;
	}
  }
}

</script>
