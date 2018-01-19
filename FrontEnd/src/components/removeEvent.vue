<template>
<div style="width:30%; margin:auto" >
  <p></p>
    <h1>Remove event</h1>
    <div v-if=!isempty v-cloak> 
        <b-form @submit.prevent="RemoveEvent">
        <b-form-select label= "Tag:" :options="this.eventsList" v-model="retvalue"  class="mb-3" required>
      </b-form-select>
      <p>{{this.msg}}</p>
            <b-button type="submit" variant="primary">Remove</b-button>
    </b-form>
      <p>{{this.msg}}</p>
  </div>
</div>
</template>

<script>
export default {
  name: 'RemoveEvent',
  data () {
  return {
    eventsList: [],
    retvalue: '',
<<<<<<< HEAD
    msg: '',
    isempty : true
=======
    msg: ''
>>>>>>> 93e7c0fbeefca0a43d4971517093128491a3c715
  }
  },
  created: function(){
    this.getEvents();
  },
  methods:{
           getEvents () {
            this.$http.get('http://localhost:8081/users/getAllEvents'
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              var eventsArr = res.body;
                var arrayLength = eventsArr.length;
                if(arrayLength != 0) this.isempty = false;
                for (var i = 0; i < arrayLength; i++) {
                  this.eventsList.push( {value:res.body[i].id,text:res.body[i].name});
                }    

            })
        },
        RemoveEvent(){
            this.$http.post('http://localhost:8081/users/deleteEvent',{"eventId": this.retvalue}
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            })
            this.msg = 'Event Removed!'
<<<<<<< HEAD
            // location.reload();
=======
>>>>>>> 93e7c0fbeefca0a43d4971517093128491a3c715
        }
  }
}
</script>
<style> [v-cloak] > * { display:none }
[v-cloak]::before { content: "loading…" } </style>