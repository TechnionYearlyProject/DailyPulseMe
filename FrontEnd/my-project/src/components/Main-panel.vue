<template>
<div >
  <div class="row">
    <div class="col-md-4">
      <login></login>
    </div>
    <div class="col-md-8">
      <table id="myTable" width="100%">
        <thead>
          <tr>
            <th>Message List</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="message in messages">
            <td>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h3 class="panel-title">{{ message.username }} @ {{ message.date | date-format}}</h3>
                </div>
                <div class="panel-body" >
                  {{ message.description }}
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div id="bottom"></div>
</div>
</template>

<script>
  import Vue from 'vue'
  import Login from './Login'

  Vue.filter('date-format', function (value) {
    let date = value.split('T')[0];
    let time = value.split('T')[1].split('+')[0].substring(0,5);
    return date + " " + time;
  });

  export default {
        components: { Login },

        name: 'main-panel',
        data () {
          return {
            // msg : 'main-panel',
            messages : ''
          }
        }, 
        created: function () {
          this.getMessages();
        },
        methods : {
          getMessages () {
            this.$http.get('http://localhost:8081/message').then((res) => {
              this.messages = res.body._embedded.message.reverse();
              console.log(this.messages);
              $(document).ready(function() {
                $('#myTable').DataTable({
                  "bSort" : false,
                  "lengthMenu" : [[3,5,10,-1], [3,5,10,"ALL"]],
                  stateSave: true
                });
              });
            }, (err) => {
              console.log(err);
            });
          }
        }

      }
</script>

<style type="text/css">
      
      #bottom {
        padding-bottom: 50px;
      }
    </style>