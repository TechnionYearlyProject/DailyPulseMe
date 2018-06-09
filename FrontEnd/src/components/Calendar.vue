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
            restHeartStats(rate, age){
                        if(age <= 35){
                          if(rate <= 45){
                            return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
                          }
                          if(rate <= 61){
                            return "Normal activity, heart rate indicates you're in excellent shape."
                          }
                          if(rate <= 70){
                            return "Normal activity, heart rate indicates you're in above average shape."
                          }
                          if(rate <= 90){
                            return "Normal activity, heart rate indicates you're in average shape."
                          }
                          if(rate <= 100){
                            return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
                          }
                          return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
                        }
                        if(age >= 36 && age <= 55){
                          if(rate <= 49){
                            return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
                          }
                          if(rate <= 63){
                            return "Normal activity, heart rate indicates you're in excellent shape."
                          }
                          if(rate <= 72){
                            return "Normal activity, heart rate indicates you're in above average shape."
                          }
                          if(rate <= 77){
                            return "Normal activity, heart rate indicates you're in average shape."
                          }
                          if(rate <= 83){
                            return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
                          }
                          return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
                        }
                        if(age >= 56){
                          if(rate <= 50){
                            return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
                          }
                          if(rate <= 66){
                            return "Normal activity, heart rate indicates you're in excellent shape."
                          }
                          if(rate <= 74){
                            return "Normal activity, heart rate indicates you're in above average shape."
                          }
                          if(rate <= 78){
                            return "Normal activity, heart rate indicates you're in average shape."
                          }
                          if(rate <= 84){
                            return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
                          }
                          return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
                        }				  
                      },
             sportHeartStats(rate, age){
                       maxRate = 220 - age;
                        if(rate < 0.5*maxRate){
                          return "Heart rate is too low, we recommend you to be more active in this session."
                        }
                        if(rate >=0.5*maxRate && rate < 0.6*maxRate){
                          return "This activity is great for low intensity sports and is recommended to do often to generally stay in shape."
                        }
                        if(rate >=0.6*maxRate && rate < 0.7*maxRate){
                          return "This activity is great for for weight loss and calorie burn, ideal for burning fat with preserving as much muscle mass as possible."
                        }
                        if(rate >=0.7*maxRate && rate < 0.8*maxRate){
                          return "This activity is great improving aerobic and cardio fitness, ideal for increasing endurance over long distances."
                        }
                        if(rate >=0.8*maxRate && rate < 0.9*maxRate){
                          return "This activity is great improving anaerobic fitness and muscle strength, ideal if you are trying to build muscle."
                        }
                        return "This activity is great improving maximum performance and speed, ideal for short bursts of intense activity and shouldn't be done over a long period of time."
						  
                      },
               heartStats(type, rate, age){
                        if(type == "Rest"){
                          return this.restHeartStats(rate, age);
                        }
                        return this.sportHeartStats(rate, age);
                      },	  
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
			   if(eventsArr[i].tag == null){
				   eventsArr[i].tag = "Rest";
			   }			   
				var date = new Date(parseInt(eventsArr[i].startTime))
            
				var x = {date:`${date.getFullYear()}/${4 + 1}/${date.getDate()}`,
                title: eventsArr[i].name,
				desc: 'Average heart rate :' + eventsArr[i].pulseAverage + '. \n Type: ' + eventsArr[i].tag + '. \n' + this.heartStats(eventsArr[i].tag, eventsArr[i].pulseAverage, 30), id:eventsArr[i].id};     if(eventsArr[i].pulseAverage == 0) 
                x.desc = 'NO DATA'
               this.datesList.push(x);
           }
           this.timeup = true
		   // + this.heartStats(eventsArr[i].tag, eventsArr[i].pulseAverage, 30)
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