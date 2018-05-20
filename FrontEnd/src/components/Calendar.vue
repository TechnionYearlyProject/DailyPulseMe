<template>
  <!-- <div id="app" style="margin-top:20px;"> -->
      <b-container fluid style="margin-top:20px; background-color: rgba(255, 255, 255, 0.6); ;width:75%;border-raduis:4px;">
        <b-row>
    <vue-event-calendar
      :events="datesList"
      @day-changed="handleDayChanged"
      @month-changed="handleMonthChanged"
    ></vue-event-calendar>
</b-row>
</b-container>
</template>

<script>
let today = new Date()
export default {
  name: 'app',
  data () {
    return {
      datesList : [],
      avgList: [],
      demoEvents: [{
        date: `${today.getFullYear()}/${today.getMonth() + 1}/24`,
        title: 'Title-1',
        desc: 'longlonglong description'
      },{
        date: `${today.getFullYear()}/${today.getMonth() + 1}/24`,
        title: 'Title-2'
      },{
        date: `${today.getFullYear()}/${today.getMonth() === 11 ? 1 : today.getMonth() + 2}/06`,
        title: 'Title-3',
        desc: 'description'
      }],
      
    }
  },
  created: function () {
    this.getEvents();
    // console.log(this.datesList)
    // console.log(this.avgList)
        },
  methods: {
    handleDayChanged (data) {
      console.log('date-changed', data)
      console.log(data.events[0].id)
      var p = document.getElementsByClassName("title");
      var len = p.length
      for (var i = 1; i < len; i++) {
        console.log(p[i])
        var title = p[i].innerHTML;
        var google = 'google.com'
        var location = "eventGraph?id=" + data.events[i-1].id;
        // var open = ;

        // p[i].innerHTML = "<a href="+location+" target="">Go to Yahoo</a>"
        p[i].innerHTML =  '<a href="'+location+'" target="_blank">'+title+'</a>'
      }

      // console.log(date-changed)
      
    },
    handleMonthChanged (data) {
      console.log('month-changed', data)


    },
    getEvents () {
       this.$http.post('http://localhost:8081/users/getEvents',{
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
        desc: 'Average heart rate :' + eventsArr[i].pulseAverage + '. \n Type: ' + eventsArr[i].tag, id:eventsArr[i].id};  
               this.datesList.push(x);
           }
          
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