<template>
  <div style="width:50%;margin: auto;">
  <form-wizard @on-complete="onComplete"
                  @on-loading="setLoading"
                  @on-validate="handleValidation"
                  @on-error="handleErrorMessage"
                        shape="circle"
                        color="#3498db"
                        title="DailyPulse"
                        subtitle="A few steps are required">
                        <div class="loader" v-if="loadingWizard"></div>       
       <b-modal ref="myModalRef" hide-footer title="You Must Connect!">
      <div class="d-block text-center">
        <h3>Please connect to a calendar account!</h3>
      <b-btn class="mt-3" variant="outline-danger" block @click="hideModal">Close Me</b-btn>
    </div>
    </b-modal>
      <tab-content title="Calendar Account"
                   icon="ti-user"
                   :before-change="validateAsync">
                   <b-btn v-b-toggle.collapse1 variant="warning" v-on:click="microsoft">Microsoft</b-btn>
    <b-btn v-b-toggle.collapse1 variant="warning" v-on:click="google" >Google</b-btn>
      </tab-content>
      <tab-content title="Fitness Account"
                   icon="ti-settings">
        <b-btn v-b-toggle.collapse1 variant="success" >Fitbit</b-btn>
    <b-btn v-b-toggle.collapse1 variant="success" >Google Fit</b-btn>
      </tab-content>
      <tab-content title="Last step"
                   icon="ti-check">
        Yuhuuu! This seems pretty damn simple. <br>
        <b-img src="https://www.hprc-online.org/sites/default/files/infographs/3Check_Mark_G4G.png" width="100" fluid alt="done" />
      </tab-content>
  </form-wizard>
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
         count: 0
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
       microsoft(){
         let url = 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=c8b9175b-e478-4c52-b8e6-178246c03006&response_type=token&redirect_uri=http://localhost:8080/token1&scope=Calendars.Read Calendars.ReadWrite&response_mode=fragment&state=12345&nonce=678910'
        var win = window.open(url, "windowname1", 'width=800, height=600'); 
       },google(){
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=http://localhost:8080/token&response_type=code&client_id=187665345194-0d324v8gel15pj9jh9fecmqknmk4k59k.apps.googleusercontent.com'
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
          return new Promise((resolve, reject) => {
            setTimeout(() => {
              if(this.count < 1){
               this.count ++
                  reject('You must connect to AT LEAST one calendar account')
              }else{
               this.count = 2
               resolve(true)
              }   
            }, 1000)
          })
         },
       }
      }   
</script>