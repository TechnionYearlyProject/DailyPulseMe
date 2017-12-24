<template>
  <div class="hello">
    <Chart id="chart" style="width:50%; margin: auto;"></Chart>
    Hello,{{ msg }}
    <a href v-on:click="logout">Logout</a>
  </div>
</template>


<script>
import Chart from './Chart'
export default {
  name: 'HelloWorld',
  components: { Chart },
  data () {
    return {
      msg: '',
      token: localStorage.getItem('token')
    }
  },
   created: function () {
          this.getMessages();
        },
  methods : {
     getMessages () {
            this.$http.get('http://localhost:8081/users/name',{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token')}
            }).then((res) => {
              this.msg = res.bodyText;
            }
            , (err) => {
              console.log(err);
          })
        },
  logout () {
    localStorage.setItem('loggedIn', 'false');
    localStorage.setItem('username', '');
    localStorage.setItem('token', 'false');
    location.reload();
    // let url = "http://localhost:8081/logout";
    // this.$http.get(url).then((res) => {
    //   localStorage.setItem('loggedIn', 'false');
    //   localStorage.setItem('username', '');
    // 	localStorage.setItem('token', 'false');
    //   location.reload();
    // 	this.$store.dispatch(types.LOGOUT);
    // 	token: ''
    // 	username: ''
    // });
  }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
<style src="../styles/Form.css"></style>
