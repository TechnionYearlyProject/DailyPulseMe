<template>
  <b-container style="margin-top:30px;width:60%;">

      <div class="card card-container" style="z-index:-2; background: #00000E; position:absolute; opacity:0.9; 
  width:60%; height:400px; margin-left:-15px;" </div>
  <eventsGraph v-if="!isempty"></eventsGraph>
    <Spinner size="massive" v-if="!timeup" style="z-index:1; margin-top:30px;">Loading..</Spinner>

    <!-- </b-row> -->
  </b-container>
</template>

<script>
import eventsGraph from './eventsGraph'
import Spinner from 'vue-simple-spinner'
export default {  
  name: 'eventsWrapper',
  components: { eventsGraph,Spinner },
  created: function(){
    this.getEvents();
  },
  data(){
    return{
      isempty: true,
      timeup: false
    }
  }
  ,methods: {
     onCarouselUpdate () {
      console.log('Carousel Updated!')
    },
    getEvents(){
       this.$http.post('http://localhost:8081/users/getEventsBetweenInterval',{
             "first": '0',
             "second": 151639920000000
           },{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              var eventsArr = res.body;
                var arrayLength = eventsArr.length;
              if(arrayLength==0) console.log('No events!')
              this.isempty = false
              this.timeup = true
            })
    }
  }
}
</script>