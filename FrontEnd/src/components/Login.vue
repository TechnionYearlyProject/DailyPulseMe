<!--
In this page we perform the authentication and connection of the user.
The user enters his email and password and we send this stats to the back
and the back returns if the authentication is successful
and creates a token for the connection.
-->
<template>
<div style="margin-top:50px;">
  <b-container fluid style="width:350px;">
  <div class="card card-container" style="z-index:-2;  position:absolute; opacity:0.05;
  width:350px; height:400px; margin-top:-20px; margin-left:-15px;"></div>
            <img id="profile-img" class="profile-img-card" src="../images/logo3.png" style="width:60%;"/>
            <form class="form-signin" @submit.prevent="login">
                <span id="reauth-email" class="reauth-email"></span>
                <input class="form-control" v-model="user.username" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="text-align: center;"/>
                <input type="password" id="inputPassword" v-model="user.password" class="form-control" placeholder="Password" required style="text-align: center;">
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
           </form>
                 <b-btn v-b-toggle.collapse1 variant="success" class="btn btn-success btn-block "
                 href="https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=https://dailypulse.azurewebsites.net/googleauth&response_type=code&client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com" >Google</b-btn>

              <router-link to="/register">Create an account
            </router-link>
            <a href="#" class="forgot-password">
                Forgot the password?
            </a>
  <p v-if="authFailed" style="color:red">Invalid Username and Password</p>

  </b-container>
    </div>
</template>


<script type="text/javascript">

  export default {
    name: 'Login',
    data() {
      return {
        logged: false,
        authFailed : false,
        user : {
          username : '',
          password : ''
        },
        googleSignInParams: {
        client_id: '895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com',

      }
      }
    },
    methods : {
      login () {
        let url = "https://webapp-180506135919.azurewebsites.net/login";
				let params = {"username": this.user.username,"password": this.user.password};
				params = JSON.stringify(params);
          this.$http.post(url, params, {credentials: true, headers: {'Content-Type': 'application/json'}}).then((res) => {
          location.reload();
					localStorage.setItem('token', res.headers.get('authorization'));
        }, (err) => {
          console.log(err);
          this.authFailed = true
        });
        },

        logout () {
					localStorage.setItem('token', 'false');
					location.reload();
        }
      }
    }

  </script>
<style src="../styles/Form.css"></style>
<style scoped>
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: white;
}
body {
    font: normal 10px Verdana, Arial, sans-serif;
}
</style>
<style>
.mid{
  color: #eaeaea;
  font-size: 4.5em;
  font-weight: normal;
  letter-spacing: .02em;
  line-height: 1.2;
  margin: 0 0 0.05em 0;
  text-align: center;
       text-shadow: 0px 1px blue;
}
</style>
