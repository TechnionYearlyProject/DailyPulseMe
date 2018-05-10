<template>
  <div>
    <!-- <h1 style="  margin-top:50px;">Good Evening,<b>{{ msg }}</b> (: </br></h1> -->
<link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">

<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
<link href="https://fonts.googleapis.com/css?family=Gothic+A1" rel="stylesheet">
<!-- 
<div>  
  <b-img style="width:120px; position: absolute; margin-left:-750px; margin-top:2px;"src="https://i.imgur.com/KAA30GB.png"/>
  <b-nav class="pre">
    <b-nav-item>SIGN IN</b-nav-item>
    <b-nav-item>ABOUT US</b-nav-item>
  </b-nav>
</div><br> -->
<!-- <div class="div1"></div>
 -->
 <div style="color:white;
    position: absolute;
    top: 270px;
    right: 27%;
      font-family: Open Sans;" class="mid">LIVE A BETTER LIFE <br>
      <div class="mid-inside" >DAILYPULSE will analyze your heart pulse using <br>
        your fitness band and your calendar
      </div>
      <b-button variant="primary" class="butt">START NOW</b-button>

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
    /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#ffffff+0,000000+100&0.65+0,0+98 */
background: -moz-linear-gradient(top, rgba(255,255,255,0.7) 0%, rgba(5,5,5,0) 98%, rgba(0,0,0,0) 100%); /* FF3.6-15 */

}

.butt{
    position: absolute;
    top: 160px;
    right: 40%;
}
.mid{
  color: #374248;
  font-size: 5em;
  font-weight: normal;
  letter-spacing: .02em;
  line-height: 1.2;
  margin: 0 0 0.05em 0;
  text-align: center;
  /*text-transform: uppercase;*/
       text-shadow: 1px 1px #007afd;
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
  /*text-transform: uppercase;*/
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
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueResource from 'vue-resource'
import BootstrapVue from 'bootstrap-vue'
export default {
  name: 'HelloWorld',
  components: {  },
  data () {
    return {
      msg: '',
      token: localStorage.getItem('token')
    }
  },
   created: function () {
          this.getMessages();
        },
  methods : {
     getMessages () {
            this.$http.get('http://localhost:8081/users/username',{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token')}
            }).then((res) => {
              this.msg = res.bodyText;
            }
            , (err) => {
              console.log(err);
          })
        },
  logout () {
    localStorage.setItem('loggedIn', 'false');
    localStorage.setItem('username', '');
    localStorage.setItem('token', 'false');
    location.reload();
    // let url = "http://localhost:8081/logout";
    // this.$http.get(url).then((res) => {
    //   localStorage.setItem('loggedIn', 'false');
    //   localStorage.setItem('username', '');
    // 	localStorage.setItem('token', 'false');
    //   location.reload();
    // 	this.$store.dispatch(types.LOGOUT);
    // 	token: ''
    // 	username: ''
    // });
  }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
<!-- <script src="//unpkg.com/babel-polyfill@latest/dist/polyfill.min.js"></script>
<script src="//unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.js"></script> -->
