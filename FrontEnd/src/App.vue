<template>
  <div id="app">
    <!-- <img src="https://cdn.pixabay.com/photo/2014/09/24/15/12/heart-rate-459225_960_720.png" style="width: 20%;"> -->
    <!-- <Chart id="chart"></Chart> -->
    <div v-if="this.login == 'true'" id="nav">
 <b-navbar toggleable="md" type="dark" variant="info" >

  <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>

  <b-navbar-brand href="Home">
      <img src="./images/logo.png" alt="BV" style=" width:140px; /* you can use % */
    height: auto;">
    </b-navbar-brand>
  <b-collapse is-nav id="nav_collapse">

    <b-navbar-nav>
      <b-nav-item href="#">Home</b-nav-item>
      <b-nav-item href="#">Graphs</b-nav-item>
    </b-navbar-nav>

    <!-- Right aligned nav items -->
    <b-navbar-nav class="ml-auto">

      <!-- <b-nav-form>
        <b-form-input size="sm" class="mr-sm-2" type="text" placeholder="Search"/>
        <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
      </b-nav-form> -->
        <b-nav-text style="color:white; font-size:17px;"> Good evening,{{ this.name }} (:</b-nav-text>

      <b-nav-item-dropdown right>
        <!-- Using button-content slot -->
        <template slot="button-content">
          <em>Settings</em>
        </template>
        <b-dropdown-item href="#">Profile</b-dropdown-item>
        <b-dropdown-item v-on:click="logout">Signout</b-dropdown-item>
      </b-nav-item-dropdown>
    </b-navbar-nav>

  </b-collapse>
</b-navbar>
</div>
    <router-view id="router-view"></router-view>
  </div>
</template>

  <script>
  export default {
    name: 'app',
    data () {
    return {
      name: '',
      token: localStorage.getItem('token'),
      login : localStorage.getItem('loggedIn')
    }
  },
   created: function () {
          this.getMessages();
        },
  methods : {
     getMessages () {
            this.$http.get('http://localhost:8081/users/username',{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token')}
            }).then((res) => {
              this.name = res.bodyText;
            }
            , (err) => {
              console.log(err);
          })
        },
      onChangeData: function (data) {
        console.log(JSON.stringify(data))
      },
      logout () {
    localStorage.setItem('loggedIn', 'false');
    localStorage.setItem('username', '');
    localStorage.setItem('token', 'false');
    location.reload();
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
