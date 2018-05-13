<template>
<div class="container" style="margin-top:50px;">
  <div class="card card-container" style="z-index:-2;  position:absolute; opacity:0.05; 
  width:400px; height:430px; margin-left:23.5%;"></div>
        <div style="margin-top:100px; margin-left:25%; width:20%; position:absolute;">
            <img id="profile-img" class="profile-img-card" src="../images/logo3.png" style="width:60%;"/>
          <!--    <div style="
      font-family: Open Sans;" class="mid">Sign In<br>
    </div> -->
            <form class="form-signin" @submit.prevent="login">
            	      <p v-if="authFailed" style="color:red">Invalid Username and Password</p>
                <span id="reauth-email" class="reauth-email"></span>
                <input class="form-control" v-model="user.username" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input type="password" id="inputPassword" v-model="user.password" class="form-control" placeholder="Password" required style="text-align: center;">
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
<!-- Here!!!! -->
            </form><!-- /form -->
              <router-link to="/register">Create an account
            </router-link>
            <a href="#" class="forgot-password">
                Forgot the password?
            </a>

        </div><!-- /card-container -->
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
        }
      }
    } ,
    methods : {
      login () {
        let url = "http://localhost:8081/login";
				let params = {"username": this.user.username,"password": this.user.password};
				params = JSON.stringify(params);
          // send post request
          this.$http.post(url, params, {credentials: true, headers: {'Content-Type': 'application/json'}}).then((res) => {
          // success callback
          location.reload();
					localStorage.setItem('token', res.headers.get('authorization'));
        }, (err) => {
          console.log(err);
          this.authFailed = true
          // error callback
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
  /*text-transform: uppercase;*/
       text-shadow: 0px 1px blue;
}
</style>
