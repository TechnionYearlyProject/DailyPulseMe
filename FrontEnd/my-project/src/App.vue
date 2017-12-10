<template>
  <div id="app">
    <!-- <img src="https://cdn.pixabay.com/photo/2014/09/24/15/12/heart-rate-459225_960_720.png" style="width: 20%;"> -->
    <!-- <Chart id="chart"></Chart> -->
    <Register id="login"></Register>
    <div>
</div>
    <!-- <router-view/> -->
  </div>
</template>

  <script>
  import Register from './components/Form'
  export default {
    name: 'app',
    components: { Register },
    methods: {
      onChangeData: function (data) {
        console.log(JSON.stringify(data))
      }
    }
  }
</script>
<style>
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