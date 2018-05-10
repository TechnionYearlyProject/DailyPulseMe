<template>
  <div id="app">
<div>  <a href="/">
  <b-img style="width:120px; position: absolute; margin-left:-750px; margin-top:2px;"src="https://i.imgur.com/KAA30GB.png"/></a>
  <b-nav class="pre">
    <b-nav-item href="/login">SIGN IN</b-nav-item>
    <b-nav-item>ABOUT US</b-nav-item>
  </b-nav>
</div><br>
<div class="div1" style="  position:fixed;
  z-index: -1;"></div>
    <router-view id="router-view"></router-view>
  </div>
</template>
  <script>
  export default {
    name: 'app',
    }
</script>
<style>
[v-cloak] {
    display: none;
  }
body{
    background-image: url("./images/back2.jpg");
    background-attachment: fixed;
    background-repeat:no-repeat;
background-position: center center;
}
#app {
  /*-webkit-font-smoothing: antialiased;*/
  text-align: center;
  color: white;
  font-family: 'Roboto', Georgia, Times, serif;
}
.div1 {
    margin:2.5em 0 10em 0;
    width: 100%;
    height: 500px;
    background: white;
    opacity: 0.25;
    -webkit-box-shadow: 10px 10px 53px -44px rgba(0,0,0,0.48);
    -moz-box-shadow: 10px 10px 53px -44px rgba(0,0,0,0.48);
    box-shadow: 10px 10px 53px -44px rgba(0,0,0,0.48);
    border-radius: 2px;
    /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#ffffff+0,000000+100&0.65+0,0+98 */
background: -moz-linear-gradient(top, rgba(255,255,255,0.7) 0%, rgba(5,5,5,0) 98%, rgba(0,0,0,0) 100%); /* FF3.6-15 */
}
.pre{
  color:white;
  position: absolute;
  margin-top: 15px;
  right:30px;
  letter-spacing: 0px;
  word-spacing: 1.4px;
  text-decoration: none;
  font-style: normal;
  font-variant: normal;
  text-transform: none;
  opacity: 0.95;
}

</style>
<style scoped>
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: white;
}
</style>