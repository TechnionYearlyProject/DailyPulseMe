<template>
  <div id="app">
<Navbar></Navbar>
<Connect></Connect>
    <router-view id="router-view"></router-view>
  </div>
</template>

  <script>
  import Connect from './components/Connect'
  import Navbar from './components/Navbar'
  export default {
    name: 'app',
    components: { Navbar, Connect }
    }
</script>
<style>
body{
    background-image: url("http://static.hdw.eweb4.com/media/wallpapers_dl/1/90/890508-cloud-background.jpg");
    background-attachment: fixed;
    background-repeat:no-repeat;
background-position: center center;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  text-align: center;
  color: #2c3e50;
  /*margin-top: -60px;*/
}
#login {
   font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  /*-moz-osx-font-smoothing: grayscale;*/
  position: relative;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
  width: 40%; /* Need a specific value to work */
  margin-top:100px;

}
</style>
