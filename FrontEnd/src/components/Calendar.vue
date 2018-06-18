<template>
  <!-- <div id="app" style="margin-top:20px;"> -->
      <b-container fluid style="margin-top:20px; background-color: rgba(255, 255, 255, 0.6); ;width:75%;border-raduis:4px;">
        <b-row>
    <vue-event-calendar
      :events="datesList"
      @day-changed="handleDayChanged"
      @month-changed="handleMonthChanged"
    ></vue-event-calendar>
      <Spinner size="massive" v-if="!timeup" style="margin-left:50%; margin-top:10%; z-index:2; position:absolute;">Loading..</Spinner>

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
                       var maxRate = 220 - age;
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
      HRVStats(type, value){
      if(type != "Rest"){
        return "";
      }
      if(value < 0.3){
        return "Your HRV value indicates you are quite calm, this is wonderful!"
      }
      if(value < 0.5){
        return "Your HRV value indicates your sterss level is not zero but it isnt very high either"
      }
      if(value < 0.7){
        return "Your HRV value indicates your sterss level is medium, we recomend you try to avoid such activities in the future"
      }
      return "Your HRV value indicates your sterss level is high, this is bad for your health, we strongly recomend you to avoid such activities in the future"
      },
       getAvg(id) {
            var avgList = [];
            var timeList = [];
            this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getEvent',{"id": id}
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              var pulsesArr = res.body.pulses;
        var sum = 0;
              var numofpulses = pulsesArr.length;
              var startTime = parseInt(res.body.startTime) + 5000
                for (var i = 0; i < numofpulses; i++) {
                  var date = new Date(startTime)
                  var hours = date.getHours()
                  hours = ("0" + hours).slice(-2);
                  var minutes = date.getMinutes()
                  minutes = ("0" + minutes).slice(-2);
                  var str = hours + ":" + minutes;
                  timeList.push(str);
                  startTime = startTime + 5000;
                }
                for (var i = 0; i < numofpulses-1; i++) {
                    var pulse = pulsesArr[i];
                    this.avgList.push({label: evnt.name,y:evnt.pulseAverage});
                  avgList.push({y: Math.abs(pulsesArr[i+1].value - pulse.value)/60});
                }
        avgList.push({y: Math.abs((pulsesArr[numofpulses-1].value - pulsesArr[numofpulses-2].value)/60)});
            })
       for (var i = 0; i < numofpulses; i++) {
         sum += avgList[i];
       }
       return sum / numofpulses;
        },
    handleDayChanged (data) {
        var p = document.getElementsByClassName("desc")
      var len = p.length
      if(len == data.events.length){
      for (var i = 0; i < data.events.length; i++) {

        var title = data.events[i].title;
    //var pooo = this.getAvg(data.events[i].id);
        var location = "eventGraph?id=" + data.events[i].id;
    var location2 = "HRVGraph?id=" + data.events[i].id;
        var str = p[i].innerHTML
        console.log(data.events[i])
         p[i].innerHTML  = 'Average heart rate :' + data.events[i].avg +'. Type: ' + data.events[i].tag + '<br>' +
         this.heartStats(data.events[i].tag, data.events[i].avg, 30) +
          '<br><a href="'+ location+'" target="_blank">'+'Click here to watch heart rate graph'+'</a>'
      + ' ' + '<br><a href="'+location2+'" target="_blank">'+'Click here to watch HRV graph'+'</a>'
       }
      }
    },
    handleMonthChanged (data) {
      console.log('month-changed', data)


    },
    getEvents () {
       this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getEventsBetweenInterval',{
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
        var avgH = 0;//this.getAvg(eventsArr[i].id);
        var x = {date:`${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`,
                title: eventsArr[i].name, avg: eventsArr[i].pulseAverage,tag:eventsArr[i].tag, avgHRV: avgH,
        desc: 'Average heart rate :' + eventsArr[i].pulseAverage + '. \n Type: ' + eventsArr[i].tag + '.\n' + this.heartStats(eventsArr[i].tag, eventsArr[i].pulseAverage, 30), id:eventsArr[i].id};     if(eventsArr[i].pulseAverage == 0)
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
