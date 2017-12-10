<template>
<div class="container">
        <div v-if="loginType == 'username'" class="card card-container">
            <img id="profile-img" class="profile-img-card" src="../images/logo.png"/>
            <form class="form-signin">
                <span id="reauth-email" class="reauth-email"></span>
                <input class="form-control" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
            </form><!-- /form -->
            Not registered ?
              <a href="#" class="register" v-on:click="toggleLoginType">Create an account
            </a>
            <br>
            <a href="#" class="forgot-password">
                Forgot the password?
            </a>
        </div><!-- /card-container -->
       <div v-else class="card card-container">
            <img id="profile-img" class="profile-img-card" src="../images/logo.png" />
            <form class="form-signin" >
                <span id="reauth-email" class="reauth-email"></span>
                <input v-model="user.email" class="form-control" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input v-model="user.name" class="form-control" type="text" placeholder="User Name" id="inputUsername" required autofocus style="    text-align: center;"/>
                <input v-model="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
                <input v-model="user.password_confirmation" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
                <button class="btn btn-lg btn-primary btn-block btn-signin" @click="register" type="submit">Sign Up</button>
            </form><!-- /form -->
        </div><!-- /card-container -->
    </div><!-- /container -->
</template>
<script type="application/json">
  export default{
    name: 'Register',
    data () {
      return {
        loginType: 'username',
        user: {
          name: '',
          email: '',
          password: '',
          password_confirmation: ''
        }
      }
    },
    methods: {
      toggleLoginType () {
        this.loginType === 'username' ? this.loginType = 'email' : this.loginType = 'username'
      },
      register () {
        let url = 'http://localhost:8081/register'
        this.$http.post(url, this.user).then((res) => {
          console.log('Success', res)
        }, (err) => {
          console.log('Error: ', err)
        })
      }
    }
  }
</script>
<style>
/*
 * Specific styles of signin component
 */
/*
 * General styles
 */
body, html {
    height: 100%;
    background-repeat: no-repeat;
    /*background-image: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/
}

.card-container.card {
    max-width: 350px;
    padding: 40px 40px;
}

.btn {
    font-weight: 700;
    height: 36px;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    cursor: default;
}

/*
 * Card component
 */
.card {
    background-color: #F7F7F7;
    /* just in case there no content*/
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    /* shadows and rounded borders */
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
    width: 85%;
    height: 85%;
    margin: 0 auto 10px;
   /* display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;*/
    /*border-radius: 50%;*/
}

/*
 * Form styles
 */
.profile-name-card {
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    margin: 10px 0 0;
    min-height: 1em;
}

.reauth-email {
    display: block;
    color: #404040;
    line-height: 2;
    margin-bottom: 10px;
    font-size: 14px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin #inputEmail,
.form-signin #inputPassword,
.form-signin #inputPasswordAgain,
.form-signin #inputUsername {
    direction: ltr;
    height: 44px;
    font-size: 16px;
}

.form-signin input[type=email],
.form-signin input[type=password],
.form-signin input[type=text],
.form-signin button {
    width: 100%;
    display: block;
    margin-bottom: 10px;
    z-index: 1;
    position: relative;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin .form-control:focus {
    border-color: rgb(104, 145, 162);
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
}

.btn.btn-signin {
    /*background-color: #4d90fe; */
    background-color: rgb(53, 60, 231);
    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/
    padding: 0px;
    font-weight: 700;
    font-size: 14px;
    color: white;
    height: 36px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    border: none;
    -o-transition: all 0.218s;
    -moz-transition: all 0.218s;
    -webkit-transition: all 0.218s;
    transition: all 0.218s;
}

.btn.btn-signin:hover,
.btn.btn-signin:active,
.btn.btn-signin:focus {
    background-color: rgb(0,0,155);
}

.forgot-password {
    color: rgb(104, 145, 162);
}

.forgot-password:hover,
.forgot-password:active,
.forgot-password:focus{
    color: rgb(12, 97, 33);
}
</style>