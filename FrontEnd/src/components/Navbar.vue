<!--
This is for the bar in the top of the site that is present in every page.
It always shows the logo (also a link to home), and if the user is logged in
we show links to  Home, events Graph, Calendar, Settings and Logout.
-->
<template>
  <div>
        <div v-if="this.toShow()" id="nav">
 <b-navbar toggleable="md"  style="background-color: rgba(51,122,183,0.9); box-shadow: 0.5px 0.5px 6px gray;">

  <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>

  <b-navbar-brand href="/">
      <img src="../images/logo.png" alt="BV" style=" width:140px; /* you can use % */
    height: auto;">
    </b-navbar-brand>
  <b-collapse is-nav id="nav_collapse">

    <b-navbar-nav>
      <b-nav-item href="/">Home</b-nav-item>
      <b-nav-item href="/eventsGraph">Graphs</b-nav-item>
      <b-nav-item href="/calendar">Calendar</b-nav-item>
    </b-navbar-nav>

    <!-- Right aligned nav items -->
    <b-navbar-nav class="ml-auto">
        <b-nav-text style="font-size:17px;"> Good evening,{{ this.name }} (:</b-nav-text>

      <b-nav-item-dropdown right>
        <template slot="button-content">
          <em>Settings</em>
        </template>
<b-dropdown-item href="/addevent">Add event</b-dropdown-item>
<b-dropdown-item href="/removeevent">Remove event</b-dropdown-item>
<b-dropdown-item href="/config">Configure</b-dropdown-item>
<b-dropdown-item v-on:click="logout">Sign out</b-dropdown-item>
</b-nav-item-dropdown>
</b-navbar-nav>

</b-collapse>
</b-navbar>
</div>
</div>
</template>

<script>
    export default {
        name: 'Navbar',
        data() {
            return {
                name: '',
                token: localStorage.getItem('token'),
                islogin: false
            }
        },
        created: function() {
            this.checkToken();
            this.getMessages();
        },
        methods: {
            checkToken() {
                this.$http.get('https://webapp-180506135919.azurewebsites.net/users/authenticateToken', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token')
                    }
                }).then((res) => {
                    this.islogin = true
                }, (err) => {
                    this.islogin = false
                })
            },
            toShow() {
                return this.islogin
            },
            getMessages() {
                this.$http.get('https://webapp-180506135919.azurewebsites.net/users/username', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token')
                    }
                }).then((res) => {
                    this.name = res.bodyText;
                }, (err) => {
                    console.log(err);
                })
            },
            onChangeData: function(data) {
                console.log(JSON.stringify(data))
            },
            logout() {
                localStorage.setItem('token', 'false');
                location.reload();
            }
        }
    }
</script>
