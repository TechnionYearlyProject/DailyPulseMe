
<!--
In this page we present the user's HRV graph of a specific event
with the data being brought from the back.
-->
<style scoped>
  body {background: white; fontColor: '#000000';}
</style>

<script>
import {Line} from 'vue-chartjs'
export default {
  name: 'HRVEvent',
  extends: Line,
created: function () {
		this.getTime();
        },
  data () {
    return {
      gradient: null,
      gradient2: null,
	  avgList: [],
	  timeList: [],
      name: ['HRV graph:'],
	  goo: ''
    }
  },
  methods:{
    graphClickEvent(event, array){
      var points = this.getElementAtEvent(event)
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
       getTime () {
            var id = this.$route.query.id;
			var ret = [];
			var sum = [];
			sum[0] = 0;
			var type = [];
            this.$http.post('http://localhost:8081/users/getEvent',{"id": id}
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
			  type.push({y: String(res.body.tag)});
              var pulsesArr = res.body.pulses;
              var numofpulses = pulsesArr.length;
              var startTime = parseInt(res.body.startTime) + 60000
                for (var i = 0; i < numofpulses; i++) {
                  var date = new Date(startTime)
                  var hours = date.getHours()
                  hours = ("0" + hours).slice(-2);
                  var minutes = date.getMinutes()
                  minutes = ("0" + minutes).slice(-2);
                  var str = hours + ":" + minutes;
                  this.timeList.push(str);
                  startTime = startTime + 60000;
                }
                for (var i = 0; i < numofpulses-1; i++) {
                    var pulse = pulsesArr[i];
                  this.avgList.push({y: Math.abs(pulsesArr[i+1].value - pulse.value)/60});
				  sum[0] += this.avgList[i].y;
                }
				this.avgList.push({y: Math.abs((pulsesArr[numofpulses-1].value - pulsesArr[numofpulses-2].value)/60)});
				sum[0] += Math.abs((pulsesArr[numofpulses-1].value - pulsesArr[numofpulses-2].value)/60);
				sum[0] = sum[0] / numofpulses;
			var avg = sum[0];

		   if(type[0].y == null){
			   type[0].y = "Rest";
		   }
			this.name[0] += this.HRVStats(type[0].y, avg);
            })
        }
  },
  mounted () {
    this.gradient = this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)
    this.gradient2 = this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)
    this.gradient.addColorStop(0, 'rgba(255, 0,0, 0.5)')
    this.gradient.addColorStop(0.5, 'rgba(255, 0, 0, 0.25)');
    this.gradient.addColorStop(1, 'rgba(255, 0, 0, 0)');

    this.gradient2.addColorStop(0, 'rgba(0, 231, 255, 0.9)')
    this.gradient2.addColorStop(0.5, 'rgba(0, 231, 255, 0.35)');
    this.gradient2.addColorStop(1, 'rgba(0, 231, 255, 0)');
    this.renderChart({
      labels: this.timeList,
      datasets: [
        {
		fontColor: '#000000',
          label: this.name,
          borderColor: 'black',
          pointBackgroundColor: 'white',
          pointBorderColor: 'gray',
          borderWidth: 1,
          backgroundColor:'rgba(51,36,183,0.7)',
          data: this.avgList,
         },
      ]
    ,
     options: {
		legend: {
       labels: {
         fontColor: '#000000'
			}
		},

        scales: {
            xAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
  }

     ,{ onClick: function(event){
      var activePoints = this.getElementAtEvent(event)
       var firstPoint = activePoints[0];
  if(firstPoint !== undefined){
    var label = this.data.labels[firstPoint._index];
    var value = this.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
    }
       }
      , responsive: true, maintainAspectRatio: false,fontColor: '#66226',
    tooltips: {
                enabled: true,
                mode: 'single',
                callbacks: {
                   title: function(tooltipItems, data) {
                    var evnt = data.datasets[0].data[tooltipItems[0].index].label;
                    return evnt
                  },
                    label: function(tooltipItems, data) {
                       var avg = 'HRV value: ' + [tooltipItems.yLabel];
                        return avg;
                    }
                }
            }
          })
}
}
</script>
