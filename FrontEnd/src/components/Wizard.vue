<!--
In this page we lead the user through the process of connecting
to the outsource services that are needed for the use of our product.
These are optionally google and microsoft.
 -->
<template>
  <div style="width:50%; margin-top:100px; margin-left:26%;">
     <b-card style="z-index:-2;  position:absolute; opacity:0.8;  background:white;
  width:685px; height:350px; margin:auto; margin-right:50px;"></b-card>
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
                   <p>Connect your Google account in order for us to access your pulse and calendar data</p>
    <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="google" >Google</b-btn>
      </tab-content>
       <tab-content title="Microsoft Account">
         <p>Connect with your Microsoft account if you would like to use their calendar aswell</p>
          <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="microsoft">Microsoft</b-btn>
      </tab-content>
      <tab-content title="Fitbit Account">
        <p>Connect with your Fitbit if you would like an additional fitness band</p>
        <b-btn v-b-toggle.collapse1 variant="primary" v-on:click="fitbit" >Fitbit</b-btn>
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
    export default {
        name: 'Wizard',
        data() {
            return {
                loadingWizard: false,
                errorMsg: null,
                count: 0,
                isAccount: false
            }
        },
        methods: {
            showModal() {
                this.$refs.myModalRef.show()
            },
            hideModal() {
                this.$refs.myModalRef.hide()
            },
            onComplete() {
                this.$router.push('/');
            },
            account() {
                this.$http.get('https://webapp-180506135919.azurewebsites.net/users/isConnectedToGoogleCalendar', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token')
                    }
                }).then((res) => {
                    this.isAccount = res.body
                })
            },
            fitbit() {
                let url = 'https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22CKWG&redirect_uri=http%3A%2F%2Fwww.cs.technion.ac.il%2F&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800'
                var win = window.open(url, "windowname1", 'width=800, height=600');
            },
            microsoft() {
                let url = 'https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=cfe8cb7c-42e5-496d-815e-448f2aa30f5e&response_type=token&redirect_uri=https://dailypulse.azurewebsites.net/token1&scope=Calendars.Read Calendars.ReadWrite&response_mode=fragment&state=12345&nonce=678910'
                var win = window.open(url, "windowname1", 'width=800, height=600');
            },
            google() {
                let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=https://dailypulse.azurewebsites.net/token&response_type=code&client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com'
                var win = window.open(url, "windowname1", 'width=800, height=600');
            },
            setLoading: function(value) {
                this.loadingWizard = value
            },
            handleValidation(isValid, tabIndex) {
                console.log('Tab: ' + tabIndex + ' valid: ' + isValid)
            },
            handleErrorMessage(errorMsg) {
                this.errorMsg = errorMsg
                if (errorMsg) {
                    this.$refs.myModalRef.show()
                }
            },
            validateAsync() {
                this.account()
                return new Promise((resolve, reject) => {
                    setTimeout(() => {
                        // this.account();
                        if (this.isAccount == false) {
                            reject('You must connect to Connect for Google Calendar')
                        } else {
                            resolve(true)
                        }
                    }, 1000)
                })
            },
        }
    }
</script>
<style scoped>
    body {
        font-family: 'Roboto', Georgia, Times, serif;
    }
</style>
