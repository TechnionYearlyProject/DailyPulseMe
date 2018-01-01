<template>
  <div v-if="toShow()" class="hello" style="margin-top:10px;">
      <b-btn v-b-toggle.collapse1 variant="primary" v-b-popover.hover="'In order to start using DailyPulseYou need to configure you fitness tracker and your google account'">Configure your tracker!</b-btn>
   <b-collapse id="collapse1" style="  margin-top:10px; ">
       <b-btn v-b-toggle.collapse1 variant="success">Fitbit</b-btn>
    <b-btn v-b-toggle.collapse1 variant="warning">Google</b-btn>
  </b-collapse>
</div>
</template>

<script>
export default {
  name: 'Connect',
  data(){
    return{
      islogin: false
    }
  },created: function () {
    this.checkToken();
  },
   methods : {
   checkToken(){
    this.$http.get('http://localhost:8081/users/authenticateToken',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}    
         }).then((res) => {
          this.islogin = true
            }, (err) => {
          this.islogin = false
          })
    },
    toShow(){
     return this.islogin
    },
  }
}
</script>
