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
             "first": 151611120000,
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
            borderColor: '#FC2525', 
            pointBackgroundColor: 'black', 

            pointBorderColor: 'black',
        //  borderColor: ' #FFFFFF',
         // pointBackgroundColor: '#f11',
       //   pointBorderColor: 'gray',
       //   fillColor : '#48A497',
          borderWidth: 1,
       //   borderColor: 'FF3333',
          backgroundColor: "rgba(73,188,170,0.7)",
          data: this.avgList
         },
      ] ,
        options: {
    scales: {
      yAxes: [{
        ticks:{
          min : 0,
          stepSize : 1,
          fontColor : "#FFFFFF",
          fontSize : 14
        },
        gridLines:{
          color: "#FFFFFF",
          lineWidth:2,
          zeroLineColor :"#FFFFFF",
          zeroLineWidth : 2
        },
        stacked: true
      }],
      xAxes: [{
        ticks:{
          fontColor : "#FFFFFF",
          fontSize : 14
        },
        gridLines:{
          color: "#FFFFFF",
          lineWidth:2
        }
      }]
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
                        if(age < 2){
                          if(rate <= 100){
                            return "BELOW AVERAGE";
                          }
                          if(rate >= 170){
                            return "ABOVE AVERAGE";
                          }
                        }
                        else if(age > 1 && age <= 11){
                          if(rate <= 60){
                            return "BELOW AVERAGE";
                          }
                          if(rate >= 110){
                            return "ABOVE AVERAGE";
                          }
                        }
                        else if(age > 11){
                          if(rate < 40){
                            return "DANGEROUS";
                          }
                          if(rate < 60 && rate >= 40){
                            return "ATHLETE";
                          }
                          if(rate > 100){
                            return "ABOVE AVERAGE";
                          }
                        }
                        return "AVERAGE";
                      };
                      function sportHeartStats(rate, age){
                        var maxRate = 220 - age;
                        var upper = maxRate * 0.85;
                        var lower = maxRate * 0.5
                        if(rate < lower){
                          return "BELOW AVERAGE";
                        }
                        if(rate > upper){
                          return "ABOVE AVERAGE";
                        }
                        return "AVERAGE";
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
