<template>
<div class="container">
        <div class="card card-container">
            <img id="profile-img" class="profile-img-card" src="../images/logo.png"/>
            <form class="form-signin" @submit.prevent="login">
            	      <p v-if="authFailed">Invalid Username and Password</p>
                <span id="reauth-email" class="reauth-email"></span>
                <input class="form-control" v-model="user.username" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input type="password" id="inputPassword" v-model="user.password" class="form-control" placeholder="Password" required style="text-align: center;">
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>

            </form><!-- /form -->
						<button class="btn btn-lg btn-primary btn-block btn-signin" type="submit" v-on:click="logout">logout</button>

            Not registered ?
              <router-link to="/register">Create an account
            </router-link>
            <br>
            <a href="#" class="forgot-password">
                Forgot the password?
            </a>
            {{this.loggedIn}}
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
    created : function () {
      if (localStorage.getItem('loggedIn') && localStorage.getItem('username')) {
        this.user.username=localStorage.getItem('username');
        this.loggedIn=true;
      } else {
        this.loggedIn=false;
      }
    } ,
    methods : {
      login () {
        let url = "http://localhost:8081/login";
        // let params = '"username"='+this.user.username+'&"password"='+this.user.password;
				// let params = '\"username\": \"'+this.user.username+'","password": "'+this.user.password;
				let params = {"username": this.user.username,"password": this.user.password};
				params = JSON.stringify(params);
        // let headers = new Headers(
        // {
        //   'Content-Type': 'application/x-www-form-urlencoded'
        // });
        // this.$http.options.xhr = {withCredentials : true};

          // send post request
          this.$http.post(url, params, {credentials: true, headers: {'Content-Type': 'application/json'}}).then((res) => {
          // success callback
          this.loggedIn=true;
          localStorage.setItem('loggedIn', 'true');
          localStorage.setItem('username', this.user.username);
          this.authFailed=false;
          console.log('success');
					console.log(res.headers);
					// res.headers.set('Authorization','Bearer');
          location.reload();
					localStorage.setItem('token', res.headers.get('authorization'));
        }, (err) => {
          console.log(err);
          this.authFailed=true;
          // error callback
        });
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
<style src="../styles/Form.css"></style>
