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
      font-family: Open Sans;" class="mid"><div v-if="!this.loggedin">LIVE A BETTER LIFE <br>
      <div class="mid-inside" >DAILYPULSE will analyze your heart pulse<br>
        using your fitness band and your calendar.<br>
         <b-button variant="primary" href="/login">START NOW</b-button>
      </div>
    </div>
    <div v-else>Good Evening {{this.msg.slice(1, this.msg.length-1)}} <br>
      <div class="mid-inside" >You can now start using our AMAZING tools!

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
    margin:2.5em 0 10em 0;
    width: 100%;
    height: 500px;
    background: white;
    opacity: 0.25;
    -webkit-box-shadow: 10px 10px 53px -44px rgba(0,0,0,0.48);
    -moz-box-shadow: 10px 10px 53px -44px rgba(0,0,0,0.48);
    box-shadow: 10px 10px 53px -44px rgba(0,0,0,0.48);
    border-radius: 2px;
    background: -moz-linear-gradient(top, rgba(255,255,255,0.7) 0%, rgba(5,5,5,0) 98%, rgba(0,0,0,0) 100%);
}
.mid{
  color: #374248;
  font-size: 5em;
  font-weight: normal;
  letter-spacing: .02em;
  line-height: 1.2;
  margin: 0 0 0.05em 0;
  text-align: center;
       text-shadow: 1.5px 2px #007afd;
}
.mid-inside{
       text-shadow: 1px 0px #007afd;

  font-family:'Gothic A1', sans-serif;;
  color:white;
  font-size: 0.3em;
  font-weight: normal;
  letter-spacing: .02em;
  line-height: 1.2;
  margin: 0 0 0.05em 0;
  text-align: center;
}
.pre{
  position: absolute;
  margin-top: 15px;
  right:30px;
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
  components: {  },
  data () {
    return {
      msg: '',
      token: localStorage.getItem('token'),
      loggedin: false
    }
  },
   created: function () {
          this.checkToken()
          this.getMessages();
        },
  methods : {
     getMessages () {
            this.$http.get('https://webapp-180506135919.azurewebsites.net/users/username',{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token')}
            }).then((res) => {
              this.msg = res.bodyText;
            }
            , (err) => {
              console.log(err);
          })
        },
    checkToken(){
   var self = this;
    this.$http.get('https://webapp-180506135919.azurewebsites.net/users/authenticateToken',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}
         }).then((res) => {
            this.loggedin = true
            })
    },
  logout () {
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
