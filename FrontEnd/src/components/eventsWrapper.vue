<template>
  <div v-cloak style="width=60%">
  <eventsGraph v-if=!isempty  id="canvas" style="width=60%"></eventsGraph>
  
</div>
</template>

<script>
import eventsGraph from './eventsGraph'
export default {
  name: 'eventsWrapper',
  components: { eventsGraph },
  created: function(){
    this.getEvents();
  },
  data(){
    return{
      isempty: true
    }
  }
  ,methods: {
    getEvents(){
       this.$http.post('http://localhost:8081/users/getEventsBetweenInterval',{
             "first": 151611120000,
             "second": 151639920000000
           }
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              var eventsArr = res.body;
                var arrayLength = eventsArr.length;
              if(arrayLength==0) location.replace('/addevent');
              this.isempty = false
            })
    }
  }
}
</script>
<style>[v-cloak] {
  display: none;
}</style>