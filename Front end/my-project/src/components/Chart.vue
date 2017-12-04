<template>
  <div id="no-key-example">
<div v-if="loginType == 'username'">
  <label>Username</label>
  <input placeholder="Enter your username" key="username-input">
</div>
<div v-else>
  <label>Email</label>
  <input placeholder="Enter your email address" key="email-input">
</div>
<button v-on:click="toggleLoginType">Report</button>
</div>
</template>
<script>
export default {
  name: 'Chart',
  data () {
    return {
      loginType: 'username'
    }
  },
  methods: {
    toggleLoginType () {
      this.loginType === 'username' ? this.loginType = 'email' : this.loginType = 'username'
    }
  }
}
</script>