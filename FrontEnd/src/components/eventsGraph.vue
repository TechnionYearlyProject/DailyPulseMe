<script>

import {Line, Bar} from 'vue-chartjs'
export default {
  name: 'eventsGraph',
  extends: Bar,
  created: function () {
    this.getEvents();
        },
  data () {
    return {
      gradient: null,
      gradient2: null,
      datesList : [],
      avgList: [],
      isempty: true
    }
  },

  methods:{
    graphClickEvent(event, array){
      var points = this.getElementAtEvent(event)
      },
      getEvents () {
           this.$http.post('http://localhost:8081/users/getAllEventsWhichHavePulses',{
             "first": '0',
             "second": 151639920000000
           }
            ,{headers: {'Content-Type': 'application/json',
             'Authorization': localStorage.getItem('token'),}
           }).then((res) => {

             var eventsArr = res.body;
             var arrayLength = eventsArr.length;
              if(arrayLength==0) location.replace('/addevent');
             // res.body = array of event object
             var eventsArr = res.body;
               var arrayLength = eventsArr.length;
               for (var i = 0; i < arrayLength; i++) {
                 var date = new Date(parseInt(eventsArr[i].startTime))
                 var day = date.getDate()
                 var month =  date.getMonth()
                 var year = date.getFullYear()
                 var hours = date.getHours()
                 hours = ("0" + hours).slice(-2);
                 var minutes = date.getMinutes()
                 minutes = ("0" + minutes).slice(-2);
                 var str = day;
                 var str = day + "." + (month + 1) + "." + year +" - " + hours + ":" + minutes
                   this.datesList.push(str);
               }
               for (var i = 0; i < arrayLength; i++) {
                   var evnt = eventsArr[i];
				   if(evnt.tag == null){
					   evnt.tag = "Rest";
				   }
                 this.avgList.push({label: evnt.name,y: evnt.pulseAverage ,tag: evnt.tag, id: evnt.id });
               }
           })
       }
 },
  mounted () {
   
    this.renderChart({
      labels: this.datesList,
      datasets: [
        {
        //  color: 'FF3333',
            label: 'Events',
          //  borderColor: '#FC2525', 
            pointBackgroundColor: 'black', 

            pointBorderColor: 'black',
        //  borderColor: ' #FFFFFF',
         // pointBackgroundColor: '#f11',
       //   pointBorderColor: 'gray',
       //   fillColor : '#48A497',
          borderWidth: 1,
       //   borderColor: 'FF3333',
          gradient : "['#ffbe88', '#ff93df']",
          backgroundColor: "#800517",
          growDuration: 10,
          data: this.avgList
         },
      ] ,
        options: {
    scales: {
         },
    responsive:false,
  }
  }
    //
     ,{ onClick: function(event){
      var activePoints = this.getElementAtEvent(event)
       var firstPoint = activePoints[0];
  if(firstPoint !== undefined){
    var label = this.data.labels[firstPoint._index];
    var value = this.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
    var location = "eventGraph?id=" + value.id;
     window.open(location, 'event graph', "height=200px,width=200px");
    }
       }
      , responsive: true, maintainAspectRatio: false,fontColor: '#FFFFFF',
    tooltips: {
                enabled: true,
                mode: 'single',
                callbacks: {
                   title: function(tooltipItems, data) {
                    var evnt = data.datasets[0].data[tooltipItems[0].index].label;
                    return evnt
                  },
                    label: function(tooltipItems, data) {
                      function restHeartStats(rate, age){
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
                      };
                      function sportHeartStats(rate, age){
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
						  
                      };
                      function heartStats(type, rate, age){
                        if(type == "Rest"){
                          return restHeartStats(rate, age);
                        }
                        return sportHeartStats(rate, age);
                      };
                       var avg = 'Average heart: ' + [tooltipItems.yLabel];
                       var evnt = 'Type: ' + data.datasets[0].data[tooltipItems.index].tag;
                       var stats = heartStats(data.datasets[0].data[tooltipItems.index].tag, [tooltipItems.yLabel], 30);
                        return [avg,evnt,stats];
                    },
                }
            }
          })
}
}
</script>
