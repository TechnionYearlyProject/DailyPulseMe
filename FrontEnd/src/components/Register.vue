<!--
In this page we perform the registration process of the user.
This includes taking all the necessary information from him,
and check it is validwith help from the backend:
the email/username does not already exist, the email is valid,
the password is long enough and matches the repeat.
 -->
<template>
     <div  style="margin-top:50px;">
        <b-container fluid style="width:350px;">
 <div class="card card-container" style="z-index:-2;  position:absolute; opacity:0.05;
  width:350px; height:430px; margin-top:-20px; margin-left:-15px;"></div>
            <img id="profile-img" class="profile-img-card" src="../images/logo3.png" style="width:60%;"/>

            <form class="form-signin" @submit.prevent="register">
                <span id="reauth-email" class="reauth-email"></span>
                <p v-if="err" style="color:red"> {{errMsg}} </p>
                <input v-model="user.username" class="form-control" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input v-model="user.name" class="form-control" type="text" placeholder="User Name" id="inputUsername" required autofocus style="    text-align: center;"/>
                <input v-model="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
                <input v-model="rePassword" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"  v-on:click="checkEmail">Sign Up</button>
            </form>
          </b-container>
        </div>
</template>
<script type="application/json">
    export default {
        name: 'Register',
        data() {
            return {
                loginType: 'username',
                user: {
                    username: '',
                    password: '',
                    name: ''
                },
                rePassword: '',
                err: false,
                errMsg: ''
            }
        },
        methods: {
            checkEmail() {
                var email = document.getElementById('inputEmail');
                var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

                if (!filter.test(email.value)) {
                    email.focus;
                    return false;
                }
            },
            toggleLoginType() {
                this.loginType === 'username' ? this.loginType = 'email' : this.loginType = 'username'
            },
            validateEmail(email) {
                var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            },
            register() {
                if (!this.validateEmail(this.user.username)) {
                    this.errMsg = 'Email address is invalid'
                    this.err = true
                } else if (this.user.password.length < 6) {
                    this.errMsg = 'Password Length Must Be At Least 6'
                    this.err = true
                } else if (this.user.password.localeCompare(this.rePassword) != 0) {
                    this.errMsg = 'Passwords Must Match'
                    this.err = true
                } else {
                    this.errMsg = ''
                    this.err = false
                    let url = 'https://webapp-180506135919.azurewebsites.net/users/sign-up'
                    this.$http.post(url, this.user, {
                        credentials: true,
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then((res) => {

                        if (String(res.body).localeCompare('false') == 0) {
                            this.err = true
                            this.errMsg = 'User/Email Already Registered'
                            this.user.username = ''
                            this.user.password = ''
                            this.user.name = ''
                            this.rePassword = ''
                        } else {
                            this.$router.push('/');
                        }
                    }, (err) => {
                        console.log('Error: ', err)
                    })
                }
            }
        }
    }
</script>
<style src="../styles/Form.css"></style>
