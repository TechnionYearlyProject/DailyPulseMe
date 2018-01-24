<template>
      <div class="card card-container">
            <img id="profile-img" class="profile-img-card" src="../images/logo.png" />
            <form class="form-signin" @submit.prevent="register">
                <span id="reauth-email" class="reauth-email"></span>
                <p v-if="err" style="color:red"> {{errMsg}} </p>
                <input v-model="user.username" class="form-control" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input v-model="user.name" class="form-control" type="text" placeholder="User Name" id="inputUsername" required autofocus style="    text-align: center;"/>
                <input v-model="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
                <input v-model="rePassword" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign Up</button>
                <!-- <button class="btn btn-lg btn-primary btn-block btn-signin" @click="register" type="submit">Sign Up</button> -->
            </form><!-- /form -->
        </div><!-- /card-container -->
</template>
<script type="application/json">
  export default{
    name: 'Register',
    data () {
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
      toggleLoginType () {
        this.loginType === 'username' ? this.loginType = 'email' : this.loginType = 'username'
      },
      register () {
        if (this.user.password.length < 6){
          this.errMsg = 'Password Length Must Be At Least 6'
          this.err = true
        } else if (this.user.password.localeCompare(this.rePassword) != 0){
          this.errMsg = 'Passwords Must Match'
          this.err = true
        } else {
          this.errMsg = ''
          this.err = false
          let url = 'http://localhost:8081/users/sign-up'
          this.$http.post(url, this.user,{credentials: true, headers: {'Content-Type': 'application/json'}}).then((res) => {

            if(String(res.body).localeCompare('false')==0){
              this.err = true
              this.errMsg = 'User/Email Already Registered'
              this.user.username = ''
              this.user.password = ''
              this.user.name = ''
              this.rePassword =''
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
