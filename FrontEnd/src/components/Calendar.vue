<template>
  <!-- <div id="app" style="margin-top:20px;"> -->
      <b-container fluid style="margin-top:20px; background-color: rgba(255, 255, 255, 0.6); ;width:75%;border-raduis:4px;">
        <b-row>
    <vue-event-calendar
      :events="datesList"
      @day-changed="handleDayChanged"
      @month-changed="handleMonthChanged"
    ></vue-event-calendar>
        <Spinner size="massive" v-if="!timeup" style="margin-left:70%; margin-top:-40%; ">Loading..</Spinner>

</b-row>
</b-container>
</template>

<script>
let today = new Date()
import Spinner from 'vue-simple-spinner'

export default {
  name: 'app',
  components:{Spinner},
  data () {
    return {
      datesList : [],
      pList: [],
      timeup: false
    }
  },
  created: function () {
    this.getEvents();
    // console.log(this.datesList)
    // console.log(this.avgList)
        },
  methods: {
    handleDayChanged (data) {
        var p = document.getElementsByClassName("desc")
      var len = p.length
      for (var i = 0; i < data.events.length; i++) {
        var title = data.events[i].title;
        var google = 'google.com'
        var location = "eventGraph?id=" + data.events[i].id;
        var str = p[i].innerHTML
        if(len == data.events.length){
        p[i].innerHTML =  p[i].innerHTML.split("<br>")[0]
      console.log( p[i].innerHTML)
         p[i].innerHTML =  p[i].innerHTML + '<br><a href="'+location+'" target="_blank">'+'Click here to watch graph'+'</a>'
       }
      }
    },
    handleMonthChanged (data) {
      console.log('month-changed', data)


    },
    getEvents () {
       this.$http.post('http://localhost:8081/users/getEventsBetweenInterval',{
         "first": 1515103200000,
         "second": new Date().getTime()
       }
        ,{headers: {'Content-Type': 'application/json',
         'Authorization': localStorage.getItem('token'),}
       }).then((res) => {
         // res.body = array of event object
         var eventsArr = res.body;
           var arrayLength = eventsArr.length;
           for (var i = 0; i < arrayLength; i++) {
             var date = new Date(parseInt(eventsArr[i].startTime))
            
            var x = {date:`${date.getFullYear()}/${4 + 1}/${date.getDate()}`,
                title: eventsArr[i].name,
        desc: 'Average heart rate :' + eventsArr[i].pulseAverage + '. \n Type: ' + eventsArr[i].tag, id:eventsArr[i].id};     if(eventsArr[i].pulseAverage == 0) 
                x.desc = 'NO DATA'
               this.datesList.push(x);
           }
           this.timeup = true
       })

    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
h1, h2, h3 {
  font-weight: normal;
  margin: 0;
  padding: 0;
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