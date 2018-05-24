<template>
  <div style="width:50%; margin-top:100px; margin-left:26%;">
     <div class="card card-container" style="z-index:-2;  position:absolute; opacity:1;  background:white;
  width:685px; height:350px; margin:auto; margin-right:50px;"></div>
  <div style="z-index:2;  position:absolute; width:700px;">
  <form-wizard @on-complete="onComplete"
                  @on-loading="setLoading"
                  @on-validate="handleValidation"
                  @on-error="handleErrorMessage"
                        shape="circle"
                        color="#007bff"
                        title="DailyPulse"
                        subtitle="A few steps are required.">
                        <div class="loader" v-if="loadingWizard"></div>       
       <b-modal ref="myModalRef" hide-footer title="You Must Connect!" style="color:black;">
      <div class="d-block text-center">
        <h3>Please connect to a calendar account!</h3>
      <b-btn class="mt-3" variant="outline-danger" block @click="hideModal">Close Me</b-btn>
    </div>
    </b-modal>
      <tab-content title="Google Account"
                   :before-change="validateAsync">
    <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="google" >Google</b-btn>
      </tab-content>
       <!-- <tab-content title="Microsoft Account">
          <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="microsoft">Microsoft</b-btn>
      </tab-content> -->
      <tab-content title="Fitbit Account">
        <b-btn v-b-toggle.collapse1 variant="primary" >Fitbit</b-btn>
      </tab-content>
      <tab-content title="Last step"> <div style="color:black;">
        You can now start using DailyPulse! </div>
      </tab-content>
  </form-wizard>
</div>
</div>
</template>
<script src="https://unpkg.com/vue-form-wizard/dist/vue-form-wizard.js"></script>

<script>
  import Connect from './Connect'
export default {
  components:{ Connect },
    name:'Wizard',
    data(){
      return {
         loadingWizard: false,
         errorMsg: null,
         count: 0,
         isAccount: false
        }
      }
    ,
     methods: {
      showModal () {
      this.$refs.myModalRef.show()
    },
    hideModal () {
      this.$refs.myModalRef.hide()
    },
      onComplete(){
        this.$router.push('/');
       },
       account(){
          this.$http.get('http://localhost:8081/users/isConnectedToGoogleCalendar',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}
         }).then((res) => {
              this.isAccount = res.body
            })
        },
       microsoft(){
         let url = 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=c8b9175b-e478-4c52-b8e6-178246c03006&response_type=token&redirect_uri=http://localhost:8080/token1&scope=Calendars.Read Calendars.ReadWrite&response_mode=fragment&state=12345&nonce=678910'
        var win = window.open(url, "windowname1", 'width=800, height=600'); 
      
       },google(){
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=http://localhost:8080/token&response_type=code&client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com'
      var win = window.open(url, "windowname1", 'width=800, height=600'); 
       }, setLoading: function(value) {
            this.loadingWizard = value
        },
        handleValidation(isValid, tabIndex){
           console.log('Tab: '+tabIndex+ ' valid: '+isValid)
        },
        handleErrorMessage(errorMsg){
          this.errorMsg = errorMsg
          if(errorMsg){
          this.$refs.myModalRef.show()
        }
        },
       validateAsync() {
        this.account()
          return new Promise((resolve, reject) => {
            setTimeout(() => {
                      // this.account();

              if(this.isAccount == false){
                  reject('You must connect to Connect for Google Calendar')
              }else{
               resolve(true)
              }   
            }, 1000)
          })
         },
       }
      }   
</script>
<!-- validateAsync() {
          return new Promise((resolve, reject) => {
            setTimeout(() => {
              if(this.count < 4){
               this.count ++
                  reject('You must connect to AT LEAST one calendar account')
              }else{
               this.count = 5
               resolve(true)
              }   
            }, 1000)
          })
         } -->
<!-- <style src="../styles/Form.css"></style> -->
<style scoped>
body{
  font-family: 'Roboto', Georgia, Times, serif;
}
</style>