<!--
This is the page a user see's immediately when he goes into the site.
If the user is not logged in we show a short explanation of the product.
If he is we show a personal greeting.
-->
<template>
  <div>
<link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">

<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
<link href="https://fonts.googleapis.com/css?family=Gothic+A1" rel="stylesheet">
<b-container fluid style="margin-top:150px;">
    <b-row>
        <b-col style="color:white;
      font-family: Open Sans;" class="mid">
    <div>Welcome {{this.msg.slice(1, this.msg.length-1)}}, <br>
      Feel free to use our excellent tools.
      <div class="mid-inside" >
          You can view our <router-link to="/Calendar" style="color:#4d94ff">Calendar</router-link> section where you can add events from your favorite calendar <br>
          at times where you wore your connected fitness band and it will be automatically <br> synchronized and shown
          to you in a comprehensive way with the help of our <br><a href="https://en.wikipedia.org/wiki/Heart_rate_variability" target="_blank" style="color:#4d94ff">NLP</a> algorithim which we use to learn about your event, <a href="https://en.wikipedia.org/wiki/Heart_rate_variability" target="_blank" style="color:#4d94ff">HRV</a> and Pulse analysis.
          <br>
          You can also view our <router-link to="/eventsgraph" style="color:#4d94ff">Graphs</router-link> section which gives a more of an overall look on your data and your progress.
      </div>
    </div>
  </b-col>
    </b-row>
    <b-row>
      <b-col>

    </b-col>
    </b-row>
</b-container>

    </div>
  </div>
</template>

<style>
    .div1 {
        margin: 2.5em 0 10em 0;
        width: 100%;
        height: 500px;
        background: white;
        opacity: 0.25;
        -webkit-box-shadow: 10px 10px 53px -44px rgba(0, 0, 0, 0.48);
        -moz-box-shadow: 10px 10px 53px -44px rgba(0, 0, 0, 0.48);
        box-shadow: 10px 10px 53px -44px rgba(0, 0, 0, 0.48);
        border-radius: 2px;
        /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#ffffff+0,000000+100&0.65+0,0+98 */
        background: -moz-linear-gradient(top, rgba(255, 255, 255, 0.7) 0%, rgba(5, 5, 5, 0) 98%, rgba(0, 0, 0, 0) 100%);
    }

    .mid {
        color: #374248;
        font-size: 5em;
        font-weight: normal;
        letter-spacing: .02em;
        line-height: 1.2;
        margin: 0 0 0.05em 0;
        text-align: center;
        /*text-transform: uppercase;*/
        text-shadow: 1.5px 2px #007afd;
    }

    .mid-inside {
        text-shadow: 1px 0px #007afd;

        font-family: 'Gothic A1', sans-serif;
        ;
        color: white;
        font-size: 0.3em;
        font-weight: normal;
        letter-spacing: .02em;
        line-height: 1.2;
        margin: 0 0 0.05em 0;
        text-align: center;
        /*text-transform: uppercase;*/
    }

    .pre {
        position: absolute;
        margin-top: 15px;
        right: 30px;
        letter-spacing: 0px;
        word-spacing: 1.4px;
        text-decoration: none;
        font-style: normal;
        font-variant: normal;
        text-transform: none;
        opacity: 0.95
    }
</style>
<script>
    export default {
        name: 'HelloWorld',
        components: {},
        data() {
            return {
                msg: '',
                token: localStorage.getItem('token'),
                loggedin: false
            }
        },
        created: function() {
            this.checkToken()
            this.getMessages();
        },
        methods: {
            getMessages() {
                this.$http.get('https://webapp-180506135919.azurewebsites.net/users/username', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token')
                    }
                }).then((res) => {
                    this.msg = res.bodyText;
                }, (err) => {
                    console.log(err);
                })
            },
            checkToken() {
                var self = this;
                this.$http.get('https://webapp-180506135919.azurewebsites.net/users/authenticateToken', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token')
                    }
                }).then((res) => {
                    this.loggedin = true
                })
            },
            logout() {
                localStorage.setItem('token', 'false');
                location.reload();
            }
        }
    }
</script>

<style scoped>
    li {
        display: inline-block;
        margin: 0 10px;
        font-family: 'Roboto', Georgia, Times, serif;


    }

    a {
        color: white;
    }
</style>
