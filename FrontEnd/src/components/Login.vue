<template>
<div class="container">
		<div v-if="this.loggedIn">
			Hello, This is {{ user.username }}
            <button class="btn btn-lg btn-primary btn-block btn-signin" @click="logout" type="submit">Log out</button>

		</div>
        <div v-else class="card card-container">
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
        let params = 'username='+this.user.username+'&password='+this.user.password;
        // let headers = new Headers(
        // {
        //   'Content-Type': 'application/x-www-form-urlencoded'
        // });
        // this.$http.options.xhr = {withCredentials : true};

          // send post request
          this.$http.post(url, params, {credentials: true, headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then((res) => {
          // success callback
          this.loggedIn=true;
          localStorage.setItem('loggedIn', 'true');
          localStorage.setItem('username', this.user.username);
          this.authFailed=false;
          console.log('success')
          location.reload();
					res.json().then(json => {
					 this.$store.commit(types.LOGIN_SUCCESS, {
						 token: json.token,
						 username: self.username
					 })
				 	})
        }, (err) => {
          console.log(err);
          this.authFailed=true;
          // error callback
        });
        },

        logout () {
          let url = "http://localhost:8081/logout";
          this.$http.get(url).then((res) => {
            localStorage.setItem('loggedIn', 'false');
            localStorage.setItem('username', '');
            location.reload();
						this.$store.dispatch(types.LOGOUT);
          });
        }
      }
    }

  </script>
<style src="../styles/Form.css"></style>
