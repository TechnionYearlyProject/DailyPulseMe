<template>
<div style="width:30%; margin:auto">
    <h1>Remove event</h1>
        <b-form @submit.prevent="RemoveEvent">
        <b-form-select label= "Tag:" :options="this.eventsList" v-model="retvalue"  class="mb-3" required>
      </b-form-select>
            <b-button type="submit" variant="primary">Remove</b-button>
    </b-form>
</div>
</template>

<script>
export default {
  name: 'RemoveEvent',
  data () {
  return {
    eventsList: [],
    retvalue: null
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
                for (var i = 0; i < arrayLength; i++) {
                 this.eventsList.push({value:res.body[i].id,text:res.body[i].name})
                }
            })
        },
        RemoveEvent(){
            this.$http.post('http://localhost:8081/users/deleteEvent',this.retvalue
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            })
            location.reload();
        }
  }
}
</script>
